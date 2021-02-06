import com.xylope.toby_spring_practice.user.dao.UserDao;
import com.xylope.toby_spring_practice.user.domain.Level;
import com.xylope.toby_spring_practice.user.domain.User;
import com.xylope.toby_spring_practice.user.service.BasicUserLevelUpgradePolicy;
import com.xylope.toby_spring_practice.user.service.UserService;
import lombok.AllArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static com.xylope.toby_spring_practice.user.service.BasicUserLevelUpgradePolicy.MIN_LOGCOUNT_FOR_SILVER;
import static com.xylope.toby_spring_practice.user.service.BasicUserLevelUpgradePolicy.MIN_VOTECOUNT_FOR_GOLD;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    DataSource dataSource;
    @Autowired
    UserDao dao;
    List<User> users;

    @Before
    public void setUp() {
        users = new ArrayList<>();
        users.add(new User("acc@1BtB", "브딱이", "topgapgg", Level.BRONZE, MIN_LOGCOUNT_FOR_SILVER-1, 0));
        users.add(new User("acc@2BtS", "브4의승급전", "plz_win", Level.BRONZE, MIN_LOGCOUNT_FOR_SILVER, 0));
        users.add(new User("acc@3StS", "실버40LP", "noBronze", Level.SILVER, Integer.MAX_VALUE, MIN_VOTECOUNT_FOR_GOLD-1));
        users.add(new User("acc@4StG", "은이금으로변할떄", "plz_win", Level.SILVER, Integer.MAX_VALUE, MIN_VOTECOUNT_FOR_GOLD));
        users.add(new User("acc@5GtG", "골드황제", "plz_win", Level.GOLD, Integer.MAX_VALUE, Integer.MAX_VALUE));
    }

    @Test
    public void bean() {
        assertNotNull(userService);
    }

    @Test
    public void upgradeLevels() throws Exception {
        dao.deleteAll(); //테스트 이전 잔여데이터 초기화
        for(User user: users) dao.add(user); //테스트용 데이터를 dao 를통해 DB에 추가

        userService.upgradeLevels(); //트렌젝션을 통한 승급로직에서 발생하는 예외를 던진다.

        boolean[] success_upgrade = {false, true, false, true, false};

        for(int i = 0; i < users.size(); i++) {
            checkLevel(users.get(i), success_upgrade[i]);
        }
    }

    @Test
    public void add() {
        dao.deleteAll(); //테스트 이전 잔여데이터 초기화

        User userWithLevel = new User("acc@2", "위드미위드레벨", "novel", Level.GOLD, 0, 0);
        User userWithoutLevel = new User("acc@1", "슈퍼계정이라랩없음", "novel", null, 0, 0);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = dao.get(userWithLevel.getId());
        User userWithoutLevelRead = dao.get(userWithoutLevel.getId());

        assertEquals(userWithLevelRead.getLevel(), userWithLevel.getLevel());
        assertEquals(userWithoutLevelRead.getLevel(), Level.BRONZE);
    }

    public void checkLevel(User user, boolean isUpgrade) {
        Level userLevel = dao.get(user.getId()).getLevel();
        Level level = isUpgrade ? user.getLevel().next() : user.getLevel();
        assertEquals(userLevel, level);
    }

    @Test
    public void upgradeOrNothing() throws Exception {
        dao.deleteAll();
        for(User user : users) dao.add(user);

        TestUserService userService = new TestUserService(users.get(3).getId());
        userService.setDataSource(dataSource);
        userService.setUserDao(dao);
        userService.setUserLevelUpgradePolicy(new BasicUserLevelUpgradePolicy());
        try {
            userService.upgradeLevels(); //트렌젝션을 통한 승급로직에서 발생하는 예외를 던진다.
            fail();
        } catch (TestUserServiceException e) {}
        checkLevel(users.get(1), false);
    }

    @AllArgsConstructor
    static class TestUserService extends UserService {
        String id;

        @Override
        protected void upgradeLevel(User user) {
            if(user.getId().equals(id)) throw new TestUserServiceException();
            super.upgradeLevel(user);
        }
    }

    static class TestUserServiceException extends RuntimeException{}
}
