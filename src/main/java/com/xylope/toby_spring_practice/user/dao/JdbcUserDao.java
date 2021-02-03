package com.xylope.toby_spring_practice.user.dao;

import com.xylope.toby_spring_practice.user.domain.User;
import lombok.Setter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;

public class JdbcUserDao implements UserDao{
    @Setter
    private JdbcOperations jdbcOperations;
    private RowMapper<User> userMapper = (rs, rowNum) -> new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));

    @Override
    public void add(final User user) {
        jdbcOperations.update("insert into users (id, name, password) values (?, ?, ?)", user.getId(), user.getName(), user.getPassword());
    }

    @Override
    public User get(String id) {
        return jdbcOperations.queryForObject("select * from users where id = ?", new Object[]{id},
                userMapper);
    }

    @Override
    public ArrayList<User> getAll() {
        return (ArrayList<User>) jdbcOperations.query("select * from users order by id", userMapper);
    }

    @Override
    public void deleteAll() {
        jdbcOperations.update("delete from users");
    }


    @Override
    public int getCount() {
        Integer count;
        if((count = jdbcOperations.queryForObject("select count(*) from users", Integer.class)) != null)
            return count;
        return 0; //queryForObject return null
    }
}
