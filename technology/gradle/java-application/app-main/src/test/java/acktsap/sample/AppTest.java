package acktsap.sample;

import static org.junit.Assert.assertNotNull;

import acktsap.sample.App;
import org.junit.Test;

public class AppTest {

  @Test
  public void test() {
    final App classUnderTest = new App();
    assertNotNull("app should have a greeting", classUnderTest.getGreeting());
  }

}
