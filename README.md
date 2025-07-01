# markdown-slide

[![CircleCI](https://circleci.com/gh/ssobue/markdown-slide.svg?style=svg)](https://circleci.com/gh/ssobue/markdown-slide)

markdown-slide is a Spring Boot application that lets you upload a document written in Markdown and easily view it as a slide deck.

## Features

- Automatically split uploaded Markdown into slides
- Convert Markdown to HTML using GitHub's Markdown API
- Persist slides with an embedded H2 database and Spring Cache
- Navigate slides with simple next/back operations

## Usage

### Build

```bash
./mvnw package
```

### Run

```bash
./mvnw spring-boot:run
```

After starting, open `http://localhost:8080` and use the following pages:

1. **Index page** (`/index`)
2. **Upload page** (`/upload`)
   - Specify a file name and choose a Markdown file to upload.
3. **Slide view** (`/view/{fileName}`)
   - Use the right arrow key or click to go forward and the left arrow key to go back.

### Test

```bash
./mvnw test
```

Internet access is required to download dependencies.

## Requirements

- Java 21 or later

The GitHub API endpoint can be overridden with the `github.api.markdown` property.
