package com.xylope.toby_spring_practice.user.dao;

import com.xylope.toby_spring_practice.user.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class UserDao {
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(final User user) throws SQLException {
        jdbcTemplate.update("insert into users (id, name, password) values (?, ?, ?)", user.getId(), user.getName(), user.getPassword());
    }

    public User get(String id) throws SQLException {
        return jdbcTemplate.queryForObject("select * from users where id = ?", new Object[]{id},
                (rs, rowNum) -> new User(rs.getString("id"), rs.getString("name"), rs.getString("password")));
    }

    public ArrayList<User> getAll() {
        return (ArrayList<User>) jdbcTemplate.query("select * from users order by id", (rs, rowVal) ->
             new User(rs.getString("id"), rs.getString("name"), rs.getString("password")));
    }

    public void deleteAll() throws SQLException {
        jdbcTemplate.update("delete from users");
    }


    public int getCount() {
        Integer count;
        if((count = jdbcTemplate.queryForObject("select count(*) from users", Integer.class)) != null)
            return count;
        return 0; //queryForObject return null
    }
}
