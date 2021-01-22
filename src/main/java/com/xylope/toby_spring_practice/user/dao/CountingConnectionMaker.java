package com.xylope.toby_spring_practice.user.dao;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Connection;
import java.sql.SQLException;

@RequiredArgsConstructor
public class CountingConnectionMaker implements ConnectionMaker {
    @Getter
    private int count;
    @Setter
    private ConnectionMaker realConnectionMaker;

    @Override
    public Connection makeConnection() throws SQLException, ClassNotFoundException {
        count++;
        return realConnectionMaker.makeConnection();
    }
}
