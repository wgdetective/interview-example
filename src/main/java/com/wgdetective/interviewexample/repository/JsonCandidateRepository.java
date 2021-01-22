package com.wgdetective.interviewexample.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wgdetective.interviewexample.dto.Candidate;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
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
