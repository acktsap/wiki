package io.lim.selenium.basic;

import io.lim.AbstractTestCase;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class WebElementTest extends AbstractTestCase {

  @Test
  public void testFindElement() {
    driver.get("https://www.google.com");

    // upper bar
    final WebElement elementById = driver.findElement(By.id("gb"));
    printWebElement(elementById);

    // mail link
    final WebElement elementByLinkText = driver.findElement(By.partialLinkText("mail"));
    final WebElement elementByCssSelector = driver.findElement(By.cssSelector(".gb_f .gb_e .gb_d"));
    printWebElement(elementByLinkText);
    printWebElement(elementByCssSelector);

    // input field element
    final WebElement elementByClassName = driver.findElement(By.className("vdLsw"));
    final WebElement elementByName = driver.findElement(By.name("q"));
    printWebElement(elementByClassName);
    printWebElement(elementByName);
  }

  protected void printWebElement(final WebElement webElement) {
    logger.info("Element origin: {}, tag name: {}, text: {}, location: {}, rect: {}, size: {}",
        webElement, webElement.getTagName(), webElement.getText(), webElement.getLocation(),
        webElement.getRect(), webElement.getSize());
  }

}
