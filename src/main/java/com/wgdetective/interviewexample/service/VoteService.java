package com.wgdetective.interviewexample.service;

import com.wgdetective.interviewexample.entity.Candidate;
import com.wgdetective.interviewexample.dto.ResultResponse;
import com.wgdetective.interviewexample.dto.VoteRequest;
import com.wgdetective.interviewexample.entity.Vote;
import com.wgdetective.interviewexample.exception.UnknownCandidateException;
import com.wgdetective.interviewexample.repository.CandidateRepository;
import com.wgdetective.interviewexample.repository.VoteRepository;
import com.wgdetective.interviewexample.unil.CalculatePercentageUtil;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final CandidateRepository candidateRepository;

    @SneakyThrows
    public Mono<Void> vote(final VoteRequest voteRequest) {
        final Optional<Candidate> candidate = candidateRepository.getById(voteRequest.getCandidateId());
        if (candidate.isEmpty()) {
            return Mono.error(new UnknownCandidateException());
        } else {
            return voteRepository.save(voteRequest, candidate.get());
        }
    }

    public Mono<List<ResultResponse>> getResults() {
        return voteRepository.getResults()
                .flatMap(map -> candidateRepository.getCandidatesList().map(candidates -> {
            final long totalCount = map.values().stream().collect(Collectors.summarizingInt(Set::size)).getSum();
            candidates.forEach(c-> map.computeIfAbsent(c, _c -> Collections.emptySet()));
            return map.entrySet().stream().map(e -> convertResult(totalCount, e)).collect(Collectors.toList());
        }));
    }

    private ResultResponse convertResult(final long totalCount,
            final java.util.Map.Entry<Candidate, Set<Vote>> e) {
        final ResultResponse result = new ResultResponse();
        result.setId(e.getKey().getId());
        result.setName(e.getKey().getName());
        result.setVoices((long) e.getValue().size());
        result.setPercentage(CalculatePercentageUtil.calculate(e.getValue().size(), totalCount));
        return result;
    }
}
