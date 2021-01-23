package com.wgdetective.interviewexample.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class Vote {

    @EqualsAndHashCode.Exclude
    private String name;
    private String passport;
}
