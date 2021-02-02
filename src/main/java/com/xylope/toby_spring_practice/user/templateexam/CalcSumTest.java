package com.xylope.toby_spring_practice.user.templateexam;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class CalcSumTest {
    @Test
    public void sumOfNumbers() throws IOException {
        Calculator calculator = new Calculator();
        int sum = calculator.calcSum(getClass().getResource("/numbers.txt").getPath());
        assertEquals(sum, 10);
    }
}
