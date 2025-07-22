package com.example.github_api.controller;

import org.springframework.web.bind.annotation.*;

import com.example.github_api.service.GitHubService;
import com.example.github_api.dto.GitHubRepoResponse;

import java.util.List;
@RestController
@RequestMapping("/github")
public class GitHubController {
    private final GitHubService service;

    public GitHubController(GitHubService service) {
        this.service = service;
    }

    @GetMapping("/{username}/repositories")
    public List<GitHubRepoResponse> getRepositories(@PathVariable String username) {
        return service.getNonForkedRepositories(username);
    }

}
