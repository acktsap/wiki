package acktsap.sample;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class CoreTest {

  @Test
  public void test() {
    final Core core = new Core();
    assertNotNull(core.getGreeting());
  }

}
