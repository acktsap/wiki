package acktsap.selenium.basic;

import acktsap.selenium.AbstractTestCase;
import org.junit.Test;

public class BrowserNavigationTest extends AbstractTestCase {

  @Test
  public void testNavigate() {
    // navigate to google. on google
    driver.get("https://www.google.com");
    logger.info("Current url: {}, title: {}", driver.getCurrentUrl(), driver.getTitle());

    // navigate to duckduckgo. on duckduckgo
    driver.navigate().to("https://www.duckduckgo.com");
    logger.info("Current url: {}, title: {}", driver.getCurrentUrl(), driver.getTitle());

    // navigate to back. on google
    driver.navigate().back();
    logger.info("Current url: {}, title: {}", driver.getCurrentUrl(), driver.getTitle());

    // navigate to back. on duckduckgo
    driver.navigate().forward();
    logger.info("Current url: {}, title: {}", driver.getCurrentUrl(), driver.getTitle());

    // refresh current page. on duckduckgo
    driver.navigate().refresh();
    logger.info("Current url: {}, title: {}", driver.getCurrentUrl(), driver.getTitle());
  }

}
