#!/usr/bin/env bash
set -euo pipefail

usage() {
    cat <<'HELP'
Usage: .github/scripts/batch-dependabot.sh [--dry-run]

Combine open Dependabot PRs for the default branch into one PR, preserving
their commits. Run from the repository with Git 2.45+ and authenticated gh.
--dry-run applies the same commits locally and prints the proposed batch.
The source PRs stay open for review and can be closed after the batch merges.
HELP
}

fail() {
    printf '%s\n' "$*" >&2
    exit 1
}

mode=${1:-}
case "$mode" in
    --help|-h) usage; exit 0 ;;
    ''|--dry-run) ;;
    *) usage >&2; exit 2 ;;
esac
[[ $# -le 1 ]] || fail "Expected at most one argument."

remote=$(git config --get remote.origin.url)
if [[ $remote =~ ^(https://github\.com/|ssh://git@github\.com/|git@github\.com:)([[:alnum:]_.-]+/[[:alnum:]_.-]+)$ ]]; then
    repo=${BASH_REMATCH[2]%.git}
else
    fail "origin must point to a github.com repository."
fi
base=$(gh api "repos/$repo" --jq .default_branch)
owner=${repo%%/*}
name=${repo#*/}
branch="maintenance/dependabot-batch/$base"
branch_ref="refs/heads/$branch"

pull_requests() {
    local filters=$1 fields=$2 output=$3
    shift 3
    gh api graphql --paginate -f "owner=$owner" -f "name=$name" "$@" \
        -f query="$(cat <<QUERY
query(\$owner: String!, \$name: String!, \$endCursor: String, \$ref: String!) {
    repository(owner: \$owner, name: \$name) {
        pullRequests(first: 100, after: \$endCursor, $filters) {
            pageInfo { hasNextPage endCursor }
            nodes { $fields }
        }
    }
}
QUERY
)" --jq ".data.repository.pullRequests.nodes[] | $output"
}

# A lease allows one run to publish the branch and lets later runs reuse it
# after the associated PR is closed.
remote_branch=$(git ls-remote origin "$branch_ref")
previous_sha=${remote_branch%%$'\t'*}
previous_prs=$(pull_requests "headRefName: \$ref" 'state headRefOid url isCrossRepository' \
    'select(.isCrossRepository == false) | [.state, .headRefOid, .url] | @tsv' -f "ref=$branch")
reusable_branch=false
while IFS=$'\t' read -r state sha url; do
    if [[ $state == OPEN ]]; then
        printf 'A batch PR is already open: %s\n' "$url"
        exit 0
    fi
    if [[ ( $state == CLOSED || $state == MERGED ) && $sha == "$previous_sha" ]]; then
        reusable_branch=true
    fi
done <<< "$previous_prs"
if [[ -n $previous_sha && $reusable_branch != true ]]; then
    fail "Branch $branch exists without a matching closed PR. Check it before rerunning."
fi

candidates=$(pull_requests \
    "baseRefName: \$ref, states: OPEN, orderBy: {field: CREATED_AT, direction: ASC}" \
    'number headRefOid url isDraft isCrossRepository author { __typename login }' \
    'select(.author.__typename == "Bot" and .author.login == "dependabot"
        and .isDraft == false and .isCrossRepository == false)
        | [.number, .headRefOid, .url] | @tsv' -f "ref=$base")
if [[ -z $candidates ]]; then
    printf 'No open Dependabot PRs for %s.\n' "$base"
    exit 0
fi

work=$(mktemp -d)
trap 'rm -rf -- "$work"' EXIT
git clone --quiet --no-tags --single-branch --branch "$base" -- "$remote" "$work"
base_sha=$(git -C "$work" rev-parse HEAD)
for key in user.name user.email; do
    if value=$(git config --get "$key"); then
        git -C "$work" config "$key" "$value"
    fi
done

body=$'Dependabot updates applied as separate commits.\n\nSource pull requests:\n'
count=0
while IFS=$'\t' read -r number expected_sha url; do
    git -C "$work" fetch --quiet --no-tags origin "refs/pull/$number/head"
    actual_sha=$(git -C "$work" rev-parse FETCH_HEAD)
    [[ $expected_sha == "$actual_sha" ]] || fail "PR #$number changed during the run. Retry."

    merges=$(git -C "$work" rev-list --merges "$base_sha..$actual_sha")
    if [[ -n $merges ]]; then
        fail "PR #$number includes a merge commit. Review it before batching."
    fi
    before=$(git -C "$work" rev-parse HEAD)
    commits=$(git -C "$work" rev-list --reverse --topo-order "$base_sha..$actual_sha")
    if [[ -n $commits ]] && ! git -C "$work" cherry-pick --stdin --empty=drop -x <<< "$commits"; then
        fail "Could not apply PR #$number. No batch branch was pushed."
    fi
    if [[ $before != "$(git -C "$work" rev-parse HEAD)" ]]; then
        body+="- $url"$'\n'
        count=$((count + 1))
    fi
done <<< "$candidates"

if (( count < 2 )); then
    printf 'Fewer than two Dependabot PRs contribute changes to %s.\n' "$base"
    exit 0
fi
printf '%s\n' "$body"
git -C "$work" log --reverse --oneline "$base_sha..HEAD"
[[ $mode != --dry-run ]] || exit 0

git -C "$work" push --force-with-lease="$branch_ref:$previous_sha" origin "HEAD:$branch_ref"
if ! gh pr create --repo "$repo" --base "$base" --head "$branch" \
    --title 'Batch Dependabot updates' --body "$body"; then
    fail "Branch $branch was pushed. Check GitHub and open a PR from it if needed."
fi
