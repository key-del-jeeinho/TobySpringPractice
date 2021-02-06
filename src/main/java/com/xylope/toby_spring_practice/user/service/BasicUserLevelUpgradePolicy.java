package com.xylope.toby_spring_practice.user.service;

import com.xylope.toby_spring_practice.user.dao.UserDao;
import com.xylope.toby_spring_practice.user.domain.User;

public class BasicUserLevelUpgradePolicy implements UserLevelUpgradePolicy{
    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_VOTECOUNT_FOR_GOLD = 30;

    public void upgradeLevel(User user, UserDao userDao) {
        user.upgradeLevel();
        userDao.update(user);
    }

    public boolean canUpgradeLevel(User user) {
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
}
