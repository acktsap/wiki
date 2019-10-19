package io.lim.sample;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;

public class Tool {

  protected final Logger logger = getLogger(getClass());

  public String getGreeting() {
    final String message = "I'm tool";
    final Common common = new Common("tool");
    logger.info("Message: {}, (common: {})", message, common);
    return message;
  }

  public static void main(String[] args) {
    System.out.println(new Tool().getGreeting());
  }

}
