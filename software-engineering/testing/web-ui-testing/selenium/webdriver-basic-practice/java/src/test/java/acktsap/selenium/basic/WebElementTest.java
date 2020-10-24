package acktsap.selenium.basic;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import acktsap.selenium.AbstractTestCase;

public class WebElementTest extends AbstractTestCase {

  @Test
  public void testFindElement() {
    driver.get("https://www.google.com");

    // upper bar
    WebElement elementById = driver.findElement(By.id("gb"));
    printWebElement(elementById);

    // mail link
    WebElement elementByLinkText = driver.findElement(By.partialLinkText("mail"));
    WebElement elementByCssSelector = driver.findElement(By.cssSelector(".hp"));
    printWebElement(elementByLinkText);
    printWebElement(elementByCssSelector);

    // input field element
    WebElement elementByClassName = driver.findElement(By.className("hp"));
    WebElement elementByName = driver.findElement(By.name("q"));
    printWebElement(elementByClassName);
    printWebElement(elementByName);
  }

  protected void printWebElement(WebElement webElement) {
    logger.info("Element origin: {}, tag name: {}, text: {}, location: {}, rect: {}, size: {}",
        webElement, webElement.getTagName(), webElement.getText(), webElement.getLocation(),
        webElement.getRect(), webElement.getSize());
  }

}
