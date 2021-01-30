package com.xylope.toby_spring_practice.user.dao;

import com.xylope.toby_spring_practice.user.domain.User;
import lombok.Setter;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {
    @Setter
    private DataSource dataSource;

    public void add(User user) throws SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement(
                "insert into users (id, name, password) values(?,?,?)"
        );
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws SQLException {
        Connection c = dataSource.getConnection();

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
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement(
                "delete from users"
        );

        try {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if(ps != null) { //close 메서드 호출시 NullPointerException 방지용
                try {
                    ps.close();
                } catch (SQLException e) { //예외가 발생하여 Connection 이 close 되지 않을경우를 방지하기위한 empty catch 문
                }
            }

            if(c != null) { //close 메서드 호출시 NullPointerException 방지용
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public int getCount() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        int count = 0;
        try {
            c = dataSource.getConnection();
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
