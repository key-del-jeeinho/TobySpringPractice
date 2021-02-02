package com.xylope.toby_spring_practice.user.templateexam;

import java.io.BufferedReader;
import java.io.IOException;

@FunctionalInterface
public interface BufferedReaderCallback {
    Integer doSomethingWithReader(BufferedReader reader) throws IOException;
}
