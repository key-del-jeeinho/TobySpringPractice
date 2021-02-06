package com.xylope.toby_spring_practice.user.service;

import com.xylope.toby_spring_practice.user.dao.UserDao;
import com.xylope.toby_spring_practice.user.domain.Level;
import com.xylope.toby_spring_practice.user.domain.User;
import lombok.Setter;

import java.util.List;

public class UserService {
    @Setter
    UserDao userDao;
    @Setter
    UserLevelUpgradePolicy userLevelUpgradePolicy;


    public void upgradeLevels() {
        List<User> users = userDao.getAll();
        Boolean changed = null;
        for(User user : users) {
            if(canUpgradeLevel(user))
                upgradeLevel(user);
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
