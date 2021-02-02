package com.xylope.toby_spring_practice.user.templateexam;

import java.io.IOException;

@FunctionalInterface
public interface BufferedReaderLineCallback {
    Integer doSomethingWithLine(String line, int res) throws IOException;
}
