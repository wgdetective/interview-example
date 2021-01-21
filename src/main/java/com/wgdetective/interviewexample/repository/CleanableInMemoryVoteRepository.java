package com.wgdetective.interviewexample.repository;

import java.util.Set;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("test")
@Component
public class CleanableInMemoryVoteRepository extends InMemoryVoteRepository {

    public void clean() {
        data.values().forEach(Set::clear);
    }
}
