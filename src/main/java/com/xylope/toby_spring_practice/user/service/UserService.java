package com.xylope.toby_spring_practice.user.service;

import com.xylope.toby_spring_practice.user.dao.UserDao;
import com.xylope.toby_spring_practice.user.domain.Level;
import com.xylope.toby_spring_practice.user.domain.User;
import lombok.Setter;

import java.util.List;

public class UserService {
    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_VOTECOUNT_FOR_GOLD = 30;
    @Setter
    UserDao userDao;

    public void upgradeLevels() {
        List<User> users = userDao.getAll();
        Boolean changed = null;
        for(User user : users) {
            if(canUpgradeLevel(user))
                upgradeLevel(user);
        }
    }

    private void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
    }

    private boolean canUpgradeLevel(User user) {
        switch (user.getLevel()) {
            case BRONZE:
                return (user.getLoginCnt() >= MIN_LOGCOUNT_FOR_SILVER);
            case SILVER:
                return (user.getVoteCnt() >= MIN_VOTECOUNT_FOR_GOLD);
            case GOLD:
                return false;
            default:
                throw new IllegalArgumentException("unknown level : " + user.getLevel());
        }
    }

    public void add(User user) {
        //Service Logic
        if(user.getLevel() == null)
            user.setLevel(Level.BRONZE);
        userDao.add(user);
    }
}
