package acktsap.sample;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;
import org.slf4j.Logger;

public class Core {

  protected final Logger logger = getLogger(getClass());

  /**
   * Gets greeting message.
   *
   * @return a greeting message
   */
  public String getGreeting() {
    final List<String> parsed = Tool.parse("localhost:7845");
    final Common common = new Common(parsed.get(0));
    logger.debug("Common: {}", common);
    return common.getValue();
  }

}
