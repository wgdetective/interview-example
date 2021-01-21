package com.wgdetective.interviewexample.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ResultResponse {

    private Long id;
    private String name;
    private Long voices;
    private BigDecimal percentage;
}
