package com.xylope.toby_spring_practice.user.exceptionexam;

import com.mysql.cj.exceptions.MysqlErrorNumbers;
import com.xylope.toby_spring_practice.user.domain.User;
import org.springframework.context.support.GenericXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {
    public void add(User user) throws DuplicateUserIdException, SQLException {
        DataSource dataSource = new GenericXmlApplicationContext("applicationContext.xml").getBean(DataSource.class);
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement("insert into users (id, name, password) values (?, ?, ?)");
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());

            ps.executeUpdate();
        } catch (SQLException e) {
            if(e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY)
                throw new DuplicateUserIdException(e);
            else throw new RuntimeException(e);
        } finally {
            if(ps != null) {
                ps.close();
            }
            if(c != null) {
                c.close();
            }
        }
    }
}
