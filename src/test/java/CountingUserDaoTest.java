import com.xylope.toby_spring_practice.user.dao.CountingConnectionMaker;
import com.xylope.toby_spring_practice.user.dao.CountingDaoFactory;
import com.xylope.toby_spring_practice.user.dao.DaoFactory;
import com.xylope.toby_spring_practice.user.dao.UserDao;
import com.xylope.toby_spring_practice.user.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class CountingUserDaoTest {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        UserDao dao = context.getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("efgh123");
        user.setName("홍길동");
        user.setPassword("hellow0rld");

        int cnt = 5;
        for (int i = 0; i < cnt; i++) {
            dao.get(user.getId());
        }

        CountingConnectionMaker connectionMaker = context.getBean("connectionMaker", CountingConnectionMaker.class);
        System.out.println((connectionMaker.getCount() == cnt) + " | " + connectionMaker.getCount());
    }
}
