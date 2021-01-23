package com.wgdetective.interviewexample.repository.impl;

import java.util.Set;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Profile("test")
@Repository
public class CleanableInMemoryVoteRepository extends InMemoryVoteRepository {

    public void clean() {
        data.values().forEach(Set::clear);
    }
}
