package acktsap.sample;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import acktsap.sample.Common;

public class App {

  protected final Logger logger = getLogger(getClass());

  public String getGreeting() {
    final String message = "I'm app";
    final Common common = new Common("app");
    logger.info("Message: {}, (common: {})", message, common);
    return message;
  }

  public static void main(String[] args) {
    System.out.println(new App().getGreeting());
  }

}
