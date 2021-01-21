package com.wgdetective.interviewexample.dto;

import lombok.Data;

@Data
public class VoteRequest {

    private String name;
    private String passport;
    private Long candidateId;
}
