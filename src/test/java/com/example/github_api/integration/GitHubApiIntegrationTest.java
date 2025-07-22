package com.example.github_api.integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GitHubApiIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnRepoWithBranches() {
        // given
        String username = "mgorny";
        String url = "http://localhost:" + port + "/github/" + username + "/repositories";

        // when
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isGreaterThan(0);

        Map repo = (Map) response.getBody().get(0);
        assertThat(repo).containsKeys("repositoryName", "ownerLogin", "branches");

        List branches = (List) repo.get("branches");
        if (!branches.isEmpty()) {
            Map branch = (Map) branches.get(0);
            assertThat(branch).containsKeys("name", "lastCommitSha");
        }



    }
}
