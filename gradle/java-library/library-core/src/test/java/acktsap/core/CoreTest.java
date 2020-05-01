package acktsap.core;

import static org.junit.Assert.assertNotNull;

import acktsap.core.Core;
import org.junit.Test;

public class CoreTest {

  @Test
  public void test() {
    final Core core = new Core();
    assertNotNull(core.getGreeting());
  }

}
