package com.xylope.toby_spring_practice.user.domain;


import lombok.*;

@EqualsAndHashCode
@AllArgsConstructor
public class User {
    @Getter @NonNull
    private final String id;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String password;
    @Getter @Setter
    private Level level;
    @Getter @Setter
    private int loginCnt;
    @Getter @Setter
    private int voteCnt;
}
