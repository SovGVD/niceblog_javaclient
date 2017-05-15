import org.junit.Test;
import static org.junit.Assert.*;

public class userTest {
	@Test
    public void testuserSID() {
        user userclass = new user();
        
        boolean result = userclass.setUserSID("");
        assertEquals(false, result);

        result = userclass.setUserSID("ERROR");
        assertEquals(false, result);

        result = userclass.setUserSID("randomasdfhdjfghsdfkgsd");
        assertEquals(true, result);

        result = userclass.setUserSID("вронгвалуетайп");
        assertEquals(false, result);

        
    }
}