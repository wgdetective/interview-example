package com.wgdetective.interviewexample.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CandidateResponse {
    private Long id;
    private String name;
}
