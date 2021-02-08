package com.xylope.toby_spring_practice.user.service;

import com.xylope.toby_spring_practice.user.dao.UserDao;
import com.xylope.toby_spring_practice.user.domain.Level;
import com.xylope.toby_spring_practice.user.domain.User;
import lombok.Setter;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import java.util.List;

public class UserService {
    @Setter
    UserDao userDao;
    @Setter
    UserLevelUpgradePolicy userLevelUpgradePolicy;
    @Setter
    PlatformTransactionManager transactionManager;
    @Setter
    MailSender mailSender;


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
        sendUpgradeMsgToEmail(user);
    }

    protected boolean canUpgradeLevel(User user) {
        return userLevelUpgradePolicy.canUpgradeLevel(user);
    }

    protected void sendUpgradeMsgToEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("admin@spring.com");
        message.setTo(user.getEmail());
        message.setSubject("토비의 스프링 안내사항입니다.");
        message.setText("님 렙업함 ㅅㄱ");
        mailSender.send();
    }

    public void add(User user) {
        //Service Logic
        if(user.getLevel() == null)
            user.setLevel(Level.BRONZE);
        userDao.add(user);
    }
}
