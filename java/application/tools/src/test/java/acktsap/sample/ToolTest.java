package acktsap.sample;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import acktsap.sample.Tool;

public class ToolTest {

  @Test
  public void test() {
    final Tool classUnderTest = new Tool();
    assertNotNull("app should have a greeting", classUnderTest.getGreeting());
  }

}
