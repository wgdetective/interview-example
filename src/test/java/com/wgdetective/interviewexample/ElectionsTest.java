package com.wgdetective.interviewexample;

import com.wgdetective.interviewexample.controller.ElectionsController;
import com.wgdetective.interviewexample.repository.CleanableInMemoryVoteRepository;
import com.wgdetective.interviewexample.repository.InMemoryVoteRepository;
import com.wgdetective.interviewexample.repository.JsonCandidateRepository;
import com.wgdetective.interviewexample.service.CandidateService;
import com.wgdetective.interviewexample.service.VoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ElectionsController.class)
@Import({CandidateService.class, JsonCandidateRepository.class, VoteService.class, InMemoryVoteRepository.class,
        CleanableInMemoryVoteRepository.class})
@ActiveProfiles("test")
public class ElectionsTest {

    @Autowired
    private WebTestClient webClient;

    @Autowired
    private CleanableInMemoryVoteRepository voteRepository;

    @BeforeEach
    public void clean() {
        voteRepository.clean();
    }

    @Test
    public void getCandidatesList() {
        webClient.get().uri("/v1/candidates")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("[\n"
                        + "    {      \n"
                        + "        \"id\" : 1,      \n"
                        + "        \"name\" : \"A. Luck\"  \n"
                        + "    },\n"
                        + "    {\n"
                        + "        \"id\" : 2,\n"
                        + "        \"name\" : \"S. Tich\"\n"
                        + "    },\n"
                        + "    {\n"
                        + "        \"id\" : 3,\n"
                        + "        \"name\" : \"N. Unkn\"\n"
                        + "    }\n"
                        + "]");
    }

    @Test
    public void voteTest() {
        webClient.post().uri("/v1/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\n"
                        + "  \"name\" : \"Wladimir Litvinov\",\n"
                        + "  \"passport\" : \"123455\",\n"
                        + "  \"candidateId\" : 3\n"
                        + "}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("{\n"
                        + "  \"message\" : \"Your voice was accepted.\"\n"
                        + "}");
        webClient.post().uri("/v1/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\n"
                        + "  \"name\" : \"Wladimir Litvinov\",\n"
                        + "  \"passport\" : \"123455\",\n"
                        + "  \"candidateId\" : 0\n"
                        + "}")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .json("{\n"
                        + "  \"message\" : \"Error: there is no such candidate.\"\n"
                        + "}");
        webClient.post().uri("/v1/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\n"
                        + "  \"name\" : \"Wladimir Litvinov\",\n"
                        + "  \"passport\" : \"123455\",\n"
                        + "  \"candidateId\" : 3\n"
                        + "}")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .json("{\n"
                        + "  \"message\" : \"Error: you already voted.\"\n"
                        + "}");
    }

    @Test
    public void getResultsTest() {
        webClient.post().uri("/v1/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\n"
                        + "  \"name\" : \"Wladimir Litvinov\",\n"
                        + "  \"passport\" : \"123455\",\n"
                        + "  \"candidateId\" : 3\n"
                        + "}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("{\n"
                        + "  \"message\" : \"Your voice was accepted.\"\n"
                        + "}");
        webClient.post().uri("/v1/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\n"
                        + "  \"name\" : \"Wladimir Litvinov\",\n"
                        + "  \"passport\" : \"123454\",\n"
                        + "  \"candidateId\" : 1\n"
                        + "}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("{\n"
                        + "  \"message\" : \"Your voice was accepted.\"\n"
                        + "}");

        webClient.get().uri("/v1/results")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("[\n"
                        + "  {\n"
                        + "    \"id\" : 1,\n"
                        + "    \"name\" : \"A. Luck\",\n"
                        + "    \"voices\" : 1,\n"
                        + "    \"percentage\" : 50\n"
                        + "  },\n"
                        + "  {\n"
                        + "    \"id\" : 2,\n"
                        + "    \"name\" : \"S. Tich\",\n"
                        + "    \"voices\" : 0,\n"
                        + "    \"percentage\" : 0\n"
                        + "  },\n"
                        + "  {\n"
                        + "    \"id\" : 3,\n"
                        + "    \"name\" : \"N. Unkn\",\n"
                        + "    \"voices\" : 1,\n"
                        + "    \"percentage\" : 50\n"
                        + "  }\n"
                        + "]");
    }
}