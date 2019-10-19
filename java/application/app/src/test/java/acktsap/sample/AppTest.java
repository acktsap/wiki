package acktsap.sample;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import acktsap.sample.App;

public class AppTest {

  @Test
  public void test() {
    final App classUnderTest = new App();
    assertNotNull("app should have a greeting", classUnderTest.getGreeting());
  }

}
