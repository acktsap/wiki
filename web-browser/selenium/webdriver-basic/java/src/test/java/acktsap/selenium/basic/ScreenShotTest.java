package acktsap.selenium.basic;

import acktsap.selenium.AbstractTestCase;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

public class ScreenShotTest extends AbstractTestCase {

  @Test
  public void testFullScreenShot() {
    driver.get("https://www.google.com");
    try {
      if (driver instanceof TakesScreenshot) {
        File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        BufferedImage bufferedImage = ImageIO.read(screenShot);
        logger.info("Full screenshot image: {}", bufferedImage);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testScreenShotOnSearchInput() {
    try {
      driver.get("https://www.google.com");

      // input field element
      WebElement inputElement = driver.findElement(By.name("q"));

      // take screenshot before typing
      File beforeScreenShot = inputElement.getScreenshotAs(OutputType.FILE);
      BufferedImage beforeTyping = ImageIO.read(beforeScreenShot);
      logger.info("Screenshot image before typing: {}", beforeTyping);

      // type "aaa"
      inputElement.sendKeys("aaa");

      // take screenshot after typing
      File afterScreenShot = inputElement.getScreenshotAs(OutputType.FILE);
      BufferedImage afterTyping = ImageIO.read(afterScreenShot);
      logger.info("Screenshot image after typing: {}", afterTyping);

      // show difference ratio
      double diffRatio = ImageUtils.calculateDifferenceRatio(beforeTyping, afterTyping);
      logger.info("Image difference ratio: {}%", diffRatio);

      // write to a file
      // BufferedImage differenceImage = ImageUtils.generateDifference(afterTyping,
      // beforeTyping);
      // ImageUtils.writeToFile(differenceImage, "/Users/taeiklim/Desktop/diff", "png");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
