package com.wgdetective.interviewexample.controller;

import com.wgdetective.interviewexample.dto.Candidate;
import com.wgdetective.interviewexample.dto.ResultResponse;
import com.wgdetective.interviewexample.dto.ResultWithMessage;
import com.wgdetective.interviewexample.dto.Vote;
import com.wgdetective.interviewexample.dto.VoteRequest;
import com.wgdetective.interviewexample.exception.DuplicateVoteException;
import com.wgdetective.interviewexample.exception.UnknownCandidateException;
import com.wgdetective.interviewexample.service.CandidateService;
import com.wgdetective.interviewexample.service.VoteService;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ElectionsController {

    private final CandidateService candidateService;
    private final VoteService voteService;

    @GetMapping("/v1/candidates")
    public Mono<List<Candidate>> getCandidatesList() {
        return candidateService.getCandidatesList();
    }

    @PostMapping("/v1/vote")
    public Mono<ResponseEntity<ResultWithMessage>> vote(@RequestBody final VoteRequest voteRequest) {
        return voteService.vote(voteRequest)
                .then(Mono.just(ResponseEntity.ok(new ResultWithMessage("Your voice was accepted."))))
                .onErrorReturn(UnknownCandidateException.class, ResponseEntity.badRequest().body(new ResultWithMessage("Error: there is no such candidate.")))
                .onErrorReturn(DuplicateVoteException.class, ResponseEntity.badRequest().body(new ResultWithMessage("Error: you already voted.")));
    }

    @GetMapping("/v1/results")
    public Mono<List<ResultResponse>> getResults() {
        return voteService.getResults();
    }
}
