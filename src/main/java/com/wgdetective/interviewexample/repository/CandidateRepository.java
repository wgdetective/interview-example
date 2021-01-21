package com.wgdetective.interviewexample.repository;

import com.wgdetective.interviewexample.dto.Candidate;
import java.util.List;
import java.util.Optional;
import reactor.core.publisher.Mono;

public interface CandidateRepository {

    Mono<List<Candidate>> getCandidatesList();

    Optional<Candidate> getById(final Long candidateId);
}
