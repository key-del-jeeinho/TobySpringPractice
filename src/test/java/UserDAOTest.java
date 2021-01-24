import com.xylope.toby_spring_practice.user.dao.UserDao;
import com.xylope.toby_spring_practice.user.domain.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class UserDAOTest {
    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {
        //테스트 이전 필요객체 설정
        ApplicationContext context = new GenericXmlApplicationContext("application.xml");
        UserDao dao = context.getBean("userDao", UserDao.class);

        dao.deleteAll(); //테스트 이전 모든 잔여 튜플을 지운다
        
        //테스트 더미데이터 설정
        User user = new User();
        user.setId("abdd123");
        user.setName("홍길동");
        user.setPassword("hellow0rld");

        assertEquals(dao.getCount(), 0); //테이블에 아직 행을 추가하지 않았으므로, 튜플의 수가 0이어야함 <- getCount 메서드 테스트
         
        dao.add(user); //dao 를 통해 db 에 user 를 추가한다 <- add 메서드 테스트

        assertEquals(dao.getCount(), 1); //테이블에 아직 행을 1개 추가하였으므로, 튜플의 수가 1이어야함 <- getCount 메서드 테스트
        
        User user2 = dao.get(user.getId()); //테이블에 user 를 추가하였으므로, user 의 id(기본값)로, user 의 데이터를 가져올 수 있어야함 <- get 메서드 테스트

        dao.deleteAll(); //모든 튜플을 지운다

        assertEquals(dao.getCount(), 0); //모든 튜플을 삭제하였으므로, 튜플의 수가 0이어야함 <- deleteAll 메서드 테스트
        assertEquals(user.getName(), user2.getName()); //<- get 메서드 테스트
        assertEquals(user.getPassword(), user2.getPassword()); //<- get 메서드 테스트
    }
}
