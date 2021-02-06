package com.xylope.toby_spring_practice.user.service;

import com.xylope.toby_spring_practice.user.dao.UserDao;
import com.xylope.toby_spring_practice.user.domain.Level;
import com.xylope.toby_spring_practice.user.domain.User;
import lombok.Setter;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserService {
    @Setter
    DataSource dataSource;
    @Setter
    UserDao userDao;
    @Setter
    UserLevelUpgradePolicy userLevelUpgradePolicy;


    public void upgradeLevels() throws Exception{
        TransactionSynchronizationManager.initSynchronization();
        Connection c = DataSourceUtils.getConnection(dataSource);
        c.setAutoCommit(false);

        try {
            List<User> users = userDao.getAll();
            for (User user : users) {
                if (canUpgradeLevel(user))
                    upgradeLevel(user);
            }
            c.commit();
        } catch (Exception e) {//어떤 예외든 발생하면 트렌젝션 롤백 필요
            c.rollback();
            throw e;
        } finally {
            TransactionSynchronizationManager.unbindResource(dataSource);
            TransactionSynchronizationManager.clearSynchronization();
        }
    }

    protected void upgradeLevel(User user) {
        userLevelUpgradePolicy.upgradeLevel(user, userDao);
    }

    protected boolean canUpgradeLevel(User user) {
        return userLevelUpgradePolicy.canUpgradeLevel(user);
    }

    public void add(User user) {
        //Service Logic
        if(user.getLevel() == null)
            user.setLevel(Level.BRONZE);
        userDao.add(user);
    }
}
