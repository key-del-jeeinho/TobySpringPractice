package com.xylope.toby_spring_practice.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {
    @Bean
    public UserDao userDao() {
        ConnectionMaker connectionMaker = new DConnectionMaker();
        return new UserDao(connectionMaker);
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }
}
