package io.lim.selenium.basic;

import static org.junit.Assert.fail;

import io.lim.AbstractTestCase;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitTest extends AbstractTestCase {

  @Test
  public void testExplicitlyWait() {
    driver.get("https://www.google.com");

    // type aaa and search
    final WebElement inputElement = driver.findElement(By.name("q"));
    inputElement.sendKeys("abc");
    logger.info("Time before submit: {}", System.currentTimeMillis());
    inputElement.submit();
    logger.info("Time after submit: {}", System.currentTimeMillis());

    // wait
    new WebDriverWait(driver, 3L).until(ExpectedConditions.titleContains("abc"));
    logger.info("Time after wait: {}", System.currentTimeMillis());
  }

  @Test
  public void testImplicitWait() {
    driver.get("https://www.google.com");

    // explicitly wait 2.5 seconds which means the driver would be polling until find some element
    driver.manage().timeouts().implicitlyWait(2500L, TimeUnit.MILLISECONDS);

    // type aaa and search
    final WebElement inputElement = driver.findElement(By.name("q"));
    inputElement.sendKeys("abc");
    inputElement.submit();

    // wait
    try {
      logger.info("Time before submit: {}", System.currentTimeMillis());
      driver.findElement(By.id("no-such-element"));
      fail();
    } catch (NoSuchElementException e) {
      // good we expected this
    } finally {
      logger.info("Time after submit: {}", System.currentTimeMillis());

    }
  }

}
