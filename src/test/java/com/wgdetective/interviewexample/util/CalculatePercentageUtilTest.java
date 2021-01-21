package com.wgdetective.interviewexample.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.wgdetective.interviewexample.CalculatePercentageUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculatePercentageUtilTest {

    @Test
    public void calculateTest() {
        Assertions.assertEquals(BigDecimal.valueOf(1.5).setScale(2, RoundingMode.HALF_UP),
                CalculatePercentageUtil.calculate(3, 200));
    }
}