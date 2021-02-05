import com.xylope.toby_spring_practice.user.domain.Level;
import com.xylope.toby_spring_practice.user.domain.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {
    User user;

    @Before
    public void setUp() {
        user = new User("acc@1");
    }

    @Test
    public void upgradeLevel() {
        for(Level level : Level.values()) {
            if(level.next() == null) continue;
            user.setLevel(level);
            user.upgradeLevel();
            assertEquals(user.getLevel(), level.next());
        }
    }


    @Test(expected = IllegalArgumentException.class)
    public void cannotUpgradeLevel() {
        for(Level level : Level.values()) {
            if(level.next() != null) continue;
            user.setLevel(level);
            user.upgradeLevel();
            assertEquals(user.getLevel(), level.next());
        }
    }
}
