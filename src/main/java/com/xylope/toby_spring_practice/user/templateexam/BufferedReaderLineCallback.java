package com.xylope.toby_spring_practice.user.templateexam;

import java.io.IOException;

@FunctionalInterface
public interface BufferedReaderLineCallback<T> {
    T doSomethingWithLine(String line, T res) throws IOException;
}
