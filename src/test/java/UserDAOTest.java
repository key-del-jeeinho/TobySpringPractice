import com.xylope.toby_spring_practice.user.dao.UserDao;
import com.xylope.toby_spring_practice.user.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class UserDAOTest {
    private UserDao dao;

    @Before
    public void setUp() throws SQLException {
        //테스트 이전 필요객체 설정
        ApplicationContext context = new GenericXmlApplicationContext("application.xml");
        dao = context.getBean("userDao", UserDao.class);
        dao.deleteAll(); //테스트 이전 모든 잔여 튜플을 지운다
    }

    @Test
    public void testAddAndGet() throws SQLException {
        //테스트 더미데이터 설정
        User[] users = {
                new User("acc1@", "김아무개", "amugae11@"),
                new User("acc2@", "개돌순", "st0neGirl@")
        };

        assertEquals(dao.getCount(), 0); //테이블에 아직 행을 추가하지 않았으므로, 튜플의 수가 0이어야함 <- getCount 메서드 테스트

        for(User user : users)
            dao.add(user); //dao 를 통해 db 에 user 를 추가한다 <- add 메서드 테스트

        assertEquals(dao.getCount(), 2); //테이블에 아직 행을 1개 추가하였으므로, 튜플의 수가 1이어야함 <- getCount 메서드 테스트

        User testUser = users[0]; //get 테스트를 진행할 user 객체 설정
        User user2 = dao.get(testUser.getId()); //테이블에 user 를 추가하였으므로, user 의 id(기본값)로, user 의 데이터를 가져올 수 있어야함 <- get 메서드 테스트

        dao.deleteAll(); //모든 튜플을 지운다

        assertEquals(dao.getCount(), 0); //모든 튜플을 삭제하였으므로, 튜플의 수가 0이어야함 <- deleteAll 메서드 테스트
        assertEquals(testUser.getName(), user2.getName()); //<- get 메서드 테스트
        assertEquals(testUser.getPassword(), user2.getPassword()); //<- get 메서드 테스트
    }

    @Test
    public void testCount() throws SQLException {
        assertEquals(dao.getCount(), 0); //모든 튜플이 다 삭제되었는지 검사한다

        //테스트 더미데이터 설정
        User[] users = {
                new User("acc1@", "홍길동", "sujaXD@"),
                new User("acc2@", "땡철이", "stupid123@"),
                new User("acc3@", "이몽룡", "lovechun12"),
        };

        //예상 카운트값
        int expectedCount = 0; //deleteAll 이 실행되어 튜플이 모두 삭제되었으므로, 초기값을 0으로 설정함
        for(User user : users) {
            dao.add(user); //dao 를 통해 차례로 users 에 있는 데이터를 db에 주입한다
            expectedCount++; //dao 에 튜플을 추가하였으므로, 예상 카운트값에 1을 더한다
            assertEquals(dao.getCount(), expectedCount); //예상값과 실제값이 일치하는지 검사한다
        }
    }

    @Test (expected = EmptyResultDataAccessException.class)
    public void getUserFailure() throws SQLException {
        assertEquals(dao.getCount(), 0);

        User user = new User("acc@", "말복순", "fortune01@");
        dao.add(user);

        dao.get("acc_unknown@");
    }
}
