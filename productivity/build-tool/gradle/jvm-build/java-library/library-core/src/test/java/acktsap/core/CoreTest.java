package acktsap.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class CoreTest {

  @Test
  public void test() {
    final Core core = new Core();
    assertNotNull(core.getGreeting());
    assertEquals(3, core.calculate(1, 2));
  }

}
