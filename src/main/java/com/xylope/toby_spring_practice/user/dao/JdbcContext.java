package com.xylope.toby_spring_practice.user.dao;

import lombok.Getter;
import lombok.Setter;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {
    @Setter
    private DataSource dataSource;

    public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = dataSource.getConnection();
            ps = stmt.makePreparedStatement(c);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if(ps != null) try {ps.close();} catch (SQLException e) {}
            if(c != null) try {c.close();} catch (SQLException e) {}
        }
    }

    public void executeQuery(String sql) throws SQLException {
        workWithStatementStrategy(c -> c.prepareStatement(sql));
    }
}
