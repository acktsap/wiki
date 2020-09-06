package acktsap.tool;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import acktsap.util.Tool;
import java.util.List;
import org.junit.Test;

public class ToolTest {

  @Test
  public void test() {
    final List<String> expected = asList("localhost", "7845");
    final List<String> actual = Tool.parse("localhost:7845");
    assertEquals(expected, actual);
  }

}
