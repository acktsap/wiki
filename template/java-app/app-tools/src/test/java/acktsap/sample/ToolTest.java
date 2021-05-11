package acktsap.sample;

import static org.junit.Assert.assertNotNull;

import acktsap.sample.Tool;
import org.junit.Test;

public class ToolTest {

  @Test
  public void test() {
    final Tool classUnderTest = new Tool();
    assertNotNull("app should have a greeting", classUnderTest.getGreeting());
  }

}
