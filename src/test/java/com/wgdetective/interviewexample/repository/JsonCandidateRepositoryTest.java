package com.wgdetective.interviewexample.repository;


import static org.junit.jupiter.api.Assertions.*;

import com.wgdetective.interviewexample.entity.Candidate;
import com.wgdetective.interviewexample.repository.impl.JsonCandidateRepository;
import java.util.List;
import org.junit.jupiter.api.Test;

class JsonCandidateRepositoryTest {

    @Test
    public void testReadFromJson() {
        final JsonCandidateRepository repository = new JsonCandidateRepository("candidates.json");
        final List<Candidate> candidatesList = repository.getCandidatesList().block();
        assertNotNull(candidatesList);
        assertEquals(3, candidatesList.size());
    }
}