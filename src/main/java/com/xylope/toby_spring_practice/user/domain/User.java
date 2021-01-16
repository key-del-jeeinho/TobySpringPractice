package com.xylope.toby_spring_practice.user.domain;


import lombok.Getter;
import lombok.Setter;

public class User {
    @Getter @Setter
    String id;
    @Getter @Setter
    String name;
    @Getter @Setter
    String password;
}
