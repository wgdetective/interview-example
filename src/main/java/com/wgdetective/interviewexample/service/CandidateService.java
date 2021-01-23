package com.wgdetective.interviewexample.service;

import com.wgdetective.interviewexample.entity.Candidate;
import com.wgdetective.interviewexample.repository.CandidateRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CandidateService {

    private final CandidateRepository candidateRepository;

    public Mono<List<Candidate>> getCandidatesList() {
        return candidateRepository.getCandidatesList();
    }
}
