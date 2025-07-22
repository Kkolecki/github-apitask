package com.example.github_api.dto;

import java.util.List;
public record GitHubRepoResponse(
        String repositoryName,
        String ownerLogin,
        List<BranchResponse> branches
) {
}
