package com.example.github_api.service;

import com.example.github_api.dto.BranchResponse;
import com.example.github_api.dto.GitHubRepoResponse;
import com.example.github_api.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

@Service
public class GitHubService {
    private final RestClient restClient;

    public GitHubService(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://api.github.com").build();
    }
    public List<GitHubRepoResponse> getNonForkedRepositories(String username) {
        try {
            GitHubRepo[] repos = restClient.get()
                    .uri("/users/{username}/repos", username)
                    .retrieve()
                    .body(GitHubRepo[].class);

            if (repos == null) return List.of();

            List<GitHubRepoResponse> responseList = new ArrayList<>();

            for (GitHubRepo repo : repos) {
                if (!repo.fork) {
                    Branch[] branches = restClient.get()
                            .uri("/repos/{owner}/{repo}/branches", repo.owner.login, repo.name)
                            .retrieve()
                            .body(Branch[].class);
                    List<BranchResponse> branchResponses = new ArrayList<>();
                    if (branches != null){
                        for (Branch branch : branches) {
                            branchResponses.add(new BranchResponse(branch.name, branch.commit.sha));
                        }
                    }
                    responseList.add(new GitHubRepoResponse(
                            repo.name,
                            repo.owner.login,
                            branchResponses
                    ));
                }
            }
            return responseList;
        } catch (HttpClientErrorException.NotFound e){
            throw new UserNotFoundException("GutHub user not found: " + username);
        }
    }

    private record GitHubRepo(String name, boolean fork, Owner owner){
        private record Owner(String login) {}
    }

    private record Branch(String name, Commit commit){
        private record Commit(String sha) {}
    }
}
