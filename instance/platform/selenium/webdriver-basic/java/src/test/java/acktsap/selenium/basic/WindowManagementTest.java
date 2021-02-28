package acktsap.selenium.basic;

import acktsap.selenium.AbstractTestCase;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

public class WindowManagementTest extends AbstractTestCase {

  @Test
  public void testWindowSizeManagement() {
    driver.get("https://www.google.com");

    // maximize window
    driver.manage().window().maximize();
    logger.info("Current position: {}, size: {}", driver.manage().window().getPosition(),
        driver.manage().window().getSize());

    // move window to (x: 200, y: 100)
    driver.manage().window().setPosition(new Point(200, 100));
    logger.info("Current position: {}, size: {}", driver.manage().window().getPosition(),
        driver.manage().window().getSize());

    // set size as 640 * 480 (width * height)
    driver.manage().window().setSize(new Dimension(640, 480));
    logger.info("Current position: {}, size: {}", driver.manage().window().getPosition(),
        driver.manage().window().getSize());

    // fill entire screen, similar with f11 in most browsers
    // driver.manage().window().fullscreen();
    // logger.info("Current position: {}, size: {}", driver.manage().window().getPosition(),
    // driver.manage().window().getSize());
  }

}
