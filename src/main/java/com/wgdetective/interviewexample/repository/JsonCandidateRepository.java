package com.wgdetective.interviewexample.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wgdetective.interviewexample.dto.Candidate;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class JsonCandidateRepository implements CandidateRepository {

    private static final String FILE_NAME = "candidates.json";

    private final List<Candidate> candidates;

    @SneakyThrows
    public JsonCandidateRepository() {
        final InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(FILE_NAME);
        final ObjectMapper mapper = new ObjectMapper();
        candidates = mapper.readValue(resourceAsStream, new TypeReference<>() {});
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
