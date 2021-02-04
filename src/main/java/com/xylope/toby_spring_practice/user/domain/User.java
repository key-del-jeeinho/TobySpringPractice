package com.xylope.toby_spring_practice.user.domain;


import lombok.*;

@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
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

    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.level = Level.BRONZE;
        this.loginCnt = 0;
        this.voteCnt = 0;
    }
}
