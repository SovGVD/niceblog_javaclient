import org.junit.Test;
import static org.junit.Assert.*;

public class blogDBTest {
	@Test
    public void blogDBget() {
        blogDB blogdbclass = new blogDB();
        
        // check not allowed table, and almost SQL injection
        String result = blogdbclass.get("not_allowed_table_name -- ","key1");
        assertEquals("", result);

        // empty table name
        result = blogdbclass.get("","key2");
        assertEquals("", result);

        // is DB init successfully
        result = blogdbclass.get("user","version");
        assertEquals("1", result);

        
    }
}