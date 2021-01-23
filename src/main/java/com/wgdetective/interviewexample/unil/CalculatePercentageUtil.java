package com.wgdetective.interviewexample.unil;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatePercentageUtil {

    private CalculatePercentageUtil(){};

    public static BigDecimal calculate(final long part, final long total) {
        return BigDecimal.valueOf(part)
                .multiply(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(total), RoundingMode.HALF_UP);
    }
}
