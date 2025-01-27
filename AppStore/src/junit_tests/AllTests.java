package junit_tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GradingTestsExt.class, StarterTests.class, StarterTestsMod.class })
public class AllTests {

}
