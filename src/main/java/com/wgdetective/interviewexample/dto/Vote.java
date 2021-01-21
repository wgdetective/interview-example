package com.wgdetective.interviewexample.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class Vote {

    @EqualsAndHashCode.Exclude
    private String name;
    private String passport;
}
