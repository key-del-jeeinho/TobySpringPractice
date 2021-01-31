package com.xylope.toby_spring_practice.user.dao;

import com.xylope.toby_spring_practice.user.domain.User;
import lombok.Setter;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.*;

public class UserDao {
    @Setter
    private JdbcContext jdbcContext;

    public void add(final User user) throws SQLException {
        jdbcContext.workWithStatementStrategy(c -> {
            PreparedStatement ps = c.prepareStatement(
                    "insert into users (id, name, password) values(?,?,?)"
            );
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            return ps;
        }
        );
    }

    //스파게티코드
    public User get(String id) throws SQLException {
        Connection c = jdbcContext.getDataSource().getConnection();

        PreparedStatement ps = c.prepareStatement(
                "select * from users where id = ?"
        );
        ps.setString(1, id);

        ResultSet rs;
        try {
            rs = ps.executeQuery();
        } catch (SQLException e) {
            throw e;
        }

        User user = null; //id 를 찾을 수 없을 경우를 대비하기위해 null 로 초기화한다.

        if(rs.next()) {
            user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }

        if(user == null)
            throw new EmptyResultDataAccessException(1);

        rs.close();
        ps.close();
        c.close();

        return user;
    }

    public void deleteAll() throws SQLException {
        jdbcContext.workWithStatementStrategy(c -> c.prepareStatement("delete from users"));
    }


    //스파게티코드
    public int getCount() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        int count = 0;
        try {
            c = jdbcContext.getDataSource().getConnection();
            ps = c.prepareStatement(
                    "select count(*) from users"
            );
            rs = ps.executeQuery();
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException e) {}
        finally {
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {}
            }
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {}
            }
            if(c != null) {
                try {
                    c.close();
                } catch (SQLException e) {}
            }
        }
        return count;
    }
}
