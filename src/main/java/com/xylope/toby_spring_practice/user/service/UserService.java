package com.xylope.toby_spring_practice.user.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.xylope.toby_spring_practice.user.dao.UserDao;
import com.xylope.toby_spring_practice.user.domain.Level;
import com.xylope.toby_spring_practice.user.domain.User;
import com.xylope.toby_spring_practice.user.exception.UnknownUserLevelException;
import lombok.Setter;

import java.util.List;

public class UserService {
    @Setter
    UserDao userDao;

    public void upgradeLevels() {
        List<User> users = userDao.getAll();
        Boolean changed = null;
        for(User user : users) {
            switch (user.getLevel()) {
                case BRONZE:
                    changed = upgradeToSilver(user);
                    break;
                case SILVER:
                    changed = upgradeToGold(user);
                    break;
                case GOLD:
                    changed = false;
                    break;
                default:
                    throw new UnknownUserLevelException();
            }
            if(changed) userDao.update(user);
            changed = null;
        }
    }

    private boolean upgradeToSilver(User user) {
        if(user.getLoginCnt() >= 50) {
            user.setLevel(Level.SILVER);
            return true;
        } else return false;
    }
    private boolean upgradeToGold(User user) {
        if(user.getVoteCnt() >= 30) {
            user.setLevel(Level.GOLD);
            return true;
        } else return false;
    }

    public void add(User user) {
        //Service Logic
        if(user.getLevel() == null)
            user.setLevel(Level.BRONZE);
        userDao.add(user);
    }
}
