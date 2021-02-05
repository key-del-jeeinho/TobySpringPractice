package com.xylope.toby_spring_practice.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Level {
    GOLD(0, null), SILVER(1, GOLD), BRONZE(2, SILVER);

    @Getter
    private final int value;
    private final Level next;

    public static Level valueOf(int value) {
        for(Level level : Level.values())
            if(value == level.getValue())
                return level;
        throw new AssertionError("unknown value : " + value);
    }

    public Level next() {
        return next;
    }
}