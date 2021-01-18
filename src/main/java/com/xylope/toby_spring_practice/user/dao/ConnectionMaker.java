package com.xylope.toby_spring_practice.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface ConnectionMaker {
    Connection makeConnection() throws SQLException, ClassNotFoundException;
}
