package com.xylope.toby_spring_practice.user.exception;

public class UnknownUserLevelException extends RuntimeException {
    public UnknownUserLevelException() {
        super("해당 유저의 레벨이 존재하지 않는 레벨값입니다.");
    }
}
