import com.xylope.toby_spring_practice.user.domain.Level;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LevelTest {
    @Test
    public void valueOf() {
        //optimist test
        Level testLevel = Level.BRONZE;
        Level level = Level.valueOf(testLevel.getValue());

        assertEquals(Level.BRONZE, level);
    }

    @Test(expected = AssertionError.class)
    public void valueOfWrongValue() {
        //negative test
        Level level = Level.valueOf(-1); //-1은 존재하지않는 Level 값이므로, AssertionError 발생
    }
}
