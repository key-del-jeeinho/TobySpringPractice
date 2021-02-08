package com.xylope.toby_spring_practice.user.service;

import com.xylope.toby_spring_practice.user.dao.UserDao;
import com.xylope.toby_spring_practice.user.domain.Level;
import com.xylope.toby_spring_practice.user.domain.User;
import lombok.Setter;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.sql.Connection;
import java.util.List;

public class UserService {
    @Setter
    UserDao userDao;
    @Setter
    UserLevelUpgradePolicy userLevelUpgradePolicy;
    @Setter
    PlatformTransactionManager transactionManager;


    public void upgradeLevels() {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            List<User> users = userDao.getAll();
            for (User user : users) {
                if (canUpgradeLevel(user))
                    upgradeLevel(user);
            }
            transactionManager.commit(status);
        } catch (Exception e) {//어떤 예외든 발생하면 트렌젝션 롤백 필요
            transactionManager.rollback(status);
            throw e;
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
