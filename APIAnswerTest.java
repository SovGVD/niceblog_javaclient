import org.junit.Test;
import static org.junit.Assert.*;

public class APIAnswerTest {
	@Test
    public void apiSet() {
        APIAnswer apiAnswerclass = new APIAnswer("");
        
        apiAnswerclass.setResult("");
        boolean result = apiAnswerclass.isOK();
        assertEquals(false, result);

        apiAnswerclass.setResult("{}");
        result = apiAnswerclass.isOK();
        assertEquals(false, result);

        apiAnswerclass.setResult("{\"r\":\"ok\",\"d\":{},\"e\":{}}");
        result = apiAnswerclass.isOK();
        assertEquals(true, result);

        apiAnswerclass.setResult("{\"r\":\"error\",\"d\":{},\"e\":{}}");
        result = apiAnswerclass.isOK();
        assertEquals(false, result);

        
    }
}