package com.xylope.toby_spring_practice.user.templateexam;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class CalcSumTest {
    Calculator calculator;
    String path;

    @Before
    public void setUp() {
        calculator = new Calculator();
        path = getClass().getResource("/numbers.txt").getPath();
    }

    @Test
    public void sumOfNumbers() throws IOException {
        int sum = calculator.calcSum(path);
        assertEquals(sum, 10);
        int mul = calculator.calcMultiple(path);
        assertEquals(mul, 24);
        String concatenate = calculator.concatenate(path);
        assertEquals(concatenate, "1234");
    }
}
