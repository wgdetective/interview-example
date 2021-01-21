package com.wgdetective.interviewexample.repository;

import com.wgdetective.interviewexample.dto.Candidate;
import com.wgdetective.interviewexample.dto.Vote;
import com.wgdetective.interviewexample.dto.VoteRequest;
import com.wgdetective.interviewexample.exception.DuplicateVoteException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Profile("!test")
@Component
public class InMemoryVoteRepository implements VoteRepository {

    protected final Map<Candidate, Set<Vote>> data = new HashMap<>();

    @Override
    public Mono<Void> save(final VoteRequest voteRequest, final Candidate candidate) throws DuplicateVoteException {
        final Vote vote = new Vote();
        vote.setName(voteRequest.getName());
        vote.setPassport(voteRequest.getPassport());

        final boolean add = data.computeIfAbsent(candidate, c-> new HashSet<>()).add(vote);
        if (!add) {
            return Mono.error(new DuplicateVoteException());
        } else {
            return Mono.empty();
        }
    }

    @Override
    public Mono<Map<Candidate, Set<Vote>>> getResults() {
        return Mono.just(data);
    }
}
