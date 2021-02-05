import com.xylope.toby_spring_practice.user.dao.UserDao;
import com.xylope.toby_spring_practice.user.domain.Level;
import com.xylope.toby_spring_practice.user.domain.User;
import com.xylope.toby_spring_practice.user.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserDao dao;
    List<User> users;

    @Before
    public void setUp() {
        users = new ArrayList<>();
        users.add(new User("acc@1BtB", "브딱이", "topgapgg", Level.BRONZE, 49, 0));
        users.add(new User("acc@2BtS", "브4의승급전", "plz_win", Level.BRONZE, 50, 0));
        users.add(new User("acc@3StS", "실버40LP", "noBronze", Level.SILVER, 50, 29));
        users.add(new User("acc@4StG", "은이금으로변할떄", "plz_win", Level.SILVER, 50, 30));
        users.add(new User("acc@5GtG", "골드황제", "plz_win", Level.GOLD, 50, 1234));
    }

    @Test
    public void bean() {
        assertNotNull(userService);
    }

    @Test
    public void upgradeLevels() {
        dao.deleteAll(); //테스트 이전 잔여데이터 초기화
        for(User user: users) dao.add(user); //테스트용 데이터를 dao 를통해 DB에 추가

        userService.upgradeLevels();;

        Level[] success_level = {Level.BRONZE, Level.SILVER, Level.SILVER, Level.GOLD, Level.GOLD};

        for(int i = 0; i < users.size(); i++) {
            checkLevel(users.get(i), success_level[i]);
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

    public void checkLevel(User user, Level level) {
        Level userLevel = dao.get(user.getId()).getLevel();
        assertEquals(userLevel, level);
    }
}
