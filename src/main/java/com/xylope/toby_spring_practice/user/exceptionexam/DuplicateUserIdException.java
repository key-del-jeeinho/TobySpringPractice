package com.xylope.toby_spring_practice.user.exceptionexam;

public class DuplicateUserIdException extends RuntimeException {
    public DuplicateUserIdException(Throwable e) {
        super(e);
    }
}
