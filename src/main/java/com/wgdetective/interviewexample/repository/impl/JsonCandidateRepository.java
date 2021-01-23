package com.wgdetective.interviewexample.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wgdetective.interviewexample.entity.Candidate;
import com.wgdetective.interviewexample.repository.CandidateRepository;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class JsonCandidateRepository implements CandidateRepository {

    private final List<Candidate> candidates;

    @SneakyThrows
    public JsonCandidateRepository(@Value("${candidates.file}") final String candidatesFileName) {
        final InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(candidatesFileName);
        final ObjectMapper mapper = new ObjectMapper();
        candidates = Collections.unmodifiableList(mapper.readValue(resourceAsStream, new TypeReference<>() {}));
    }

    @Override
    public Mono<List<Candidate>> getCandidatesList() {
        return Mono.just(candidates);
    }

    @Override
    public Optional<Candidate> getById(final Long candidateId) {
        return candidates.stream().filter(c -> candidateId.equals(c.getId())).findFirst();
    }
}
