package com.xylope.toby_spring_practice.user.dao;

import com.xylope.toby_spring_practice.user.domain.User;

import java.util.ArrayList;

public interface UserDao {
    void add(final User user);
    User get(String id);
    ArrayList<User> getAll();
    void deleteAll();
    int getCount();
}
