package com.wgdetective.interviewexample.repository;

import com.wgdetective.interviewexample.dto.Candidate;
import com.wgdetective.interviewexample.dto.Vote;
import com.wgdetective.interviewexample.dto.VoteRequest;
import com.wgdetective.interviewexample.exception.DuplicateVoteException;
import java.util.Map;
import java.util.Set;
import reactor.core.publisher.Mono;

public interface VoteRepository {

    Mono<Void> save(final VoteRequest voteRequest, final Candidate candidate) throws DuplicateVoteException;

    Mono<Map<Candidate, Set<Vote>>> getResults();
}
