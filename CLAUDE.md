# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
./mvnw quarkus:dev          # Run in dev mode with live reload (http://localhost:8080)
./mvnw test                 # Run all tests
./mvnw test -Dtest=GitHubServiceTests#shouldListCommits  # Run a single test
./mvnw package              # Package the application
```

## Configuration

Required environment variables or application.properties:
- `gh.user` - GitHub username
- `gh.pat` - GitHub personal access token

## Architecture

This is a Quarkus application (Java 21) that generates AI-powered release notes from GitHub commits.

**Core Services** (`net.herhoffer.releasenotr`):
- `GitHubService` - Fetches commits and README from GitHub repositories using the GitHub API
- `ReleasenoteAiService` - LangChain4j AI service that generates release notes from commits and README content
- `ReleaseNotes` - Structured output model with `prosaicText` (store-style description) and `mdText` (markdown changelog)

**Key Technologies**:
- Quarkus REST (Jakarta REST) for HTTP endpoints
- Qute for templating
- LangChain4j with OpenAI for AI generation
- hub4j GitHub API for repository access