package com.xylope.toby_spring_practice.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Level {
    BRONZE(0), SILVER(1), GOLD(2);

    @Getter
    private final int value;

    public static Level valueOf(int value) {
        for(Level level : Level.values())
            if(value == level.getValue())
                return level;
        throw new AssertionError("unknown value : " + value);
    }
}