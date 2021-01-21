package com.wgdetective.interviewexample.repository;


import static org.junit.jupiter.api.Assertions.*;

import com.wgdetective.interviewexample.dto.Candidate;
import java.util.List;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class JsonCandidateRepositoryTest {

    @Test
    public void testReadFromJson() {
        final JsonCandidateRepository repository = new JsonCandidateRepository();
        final List<Candidate> candidatesList = repository.getCandidatesList().block();
        assertNotNull(candidatesList);
        assertEquals(3, candidatesList.size());
    }
}