# Github Repo API

## Description

A Spring Boot application exposing an HTTP endpoint that returns a GitHub user's repositories that are not forks.
For each repository, it also provides information about its branches and the latest commit.

## Technologies Used
- Java 21
- Spring Boot 3.5.3
- Spring WEB
- Spring Boot Test
- GitHub REST API v3
- RestTemplate

## Endpoint

'GET /github/{username}/repositories'

Returns a list of the user's public repositories.
Each repository includes:

- repositoryName
- ownerLogin
- branches[], each containing:
  - name
  - lastCommitSha

## Error Handling
If the GitHub user does not exist, the application returns a 404 error in the following format:


    "status": 404,
    "message": "User not found"


# Running the Project
### Requirements
- Java 21
- Maven

### Running
Run the application in IDE by executing the GithubApiApplication class or use:

    ./mvnw spring-boot:run

# Test

An integration test GitHubApiIntegrationTest has been implemented, which:
- Verifies a successful 200 OK response for an existing user
- Checks if repositories and branches are returned correctly
- Uses the real GitHub API instead of mocks
