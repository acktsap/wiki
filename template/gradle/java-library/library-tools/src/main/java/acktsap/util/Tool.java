package acktsap.util;

import static java.util.Arrays.asList;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;

public class Tool {

  protected static final Logger logger = getLogger(Tool.class);

  /**
   * Parse a message.
   *
   * @param message a message
   * @return a parsed message
   */
  public static List<String> parse(final String message) {
    Objects.requireNonNull(message);
    final String[] splited = message.split(":");
    logger.debug("Parsed message: {}", Arrays.toString(splited));
    return asList(splited);
  }

}
