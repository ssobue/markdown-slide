# Markdown Slide

[![Java CI](https://github.com/ssobue/markdown-slide/actions/workflows/main.yaml/badge.svg)](https://github.com/ssobue/markdown-slide/actions/workflows/main.yaml)

Markdown Slide is a small Spring Boot application that turns Markdown documents into slide presentations. Each first-level heading becomes a separate slide that can be viewed in the browser.

## Features

- Upload Markdown files through a web form
- Convert content to HTML using the GitHub Markdown API
- Store and cache converted slides in an embedded H2 database
- Navigate through slides by clicking or using the arrow keys

## Usage

### Build and Run

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

### Creating Slides

1. Open `/upload` and select a Markdown file.
2. After uploading, you are redirected to `/view/<name>` where `<name>` is the file name without the `.md` extension.
3. Use the arrow keys or click the page to move forward and backward.

### Running Tests

Execute the tests directly with Maven:

```bash
mvn test
```

No Maven Wrapper is required.
