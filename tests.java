import org.junit.runner.RunWith;
import org.junit.runners.Suite;

//JUnit Suite Test
@RunWith(Suite.class)

@Suite.SuiteClasses({ 
  APIAnswerTest.class ,userTest.class, blogDBTest.class
})

public class tests {
}