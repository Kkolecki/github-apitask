package com.example.github_api.dto;

public record BranchResponse(
        String name,
        String lastCommitSha
) {
}
