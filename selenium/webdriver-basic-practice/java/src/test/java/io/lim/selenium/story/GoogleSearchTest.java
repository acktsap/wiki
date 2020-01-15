package io.lim.selenium.story;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertTrue;

import io.lim.AbstractTestCase;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class GoogleSearchTest extends AbstractTestCase {

  @Test
  public void testGoogleSearch() {
    // set implicit timeout as 3 seconds
    driver.manage().timeouts().implicitlyWait(3L, TimeUnit.SECONDS);

    // nativate too google
    driver.get("https://www.google.com");
    logger.info("Current url: {}, title: {}", driver.getCurrentUrl(), driver.getTitle());

    // type aaa and search
    final WebElement inputElement = driver.findElement(By.name("q"));
    inputElement.sendKeys("abc");
    inputElement.submit();
    logger.info("Current url: {}, title: {}", driver.getCurrentUrl(), driver.getTitle());

    // go to randomly selected search result
    final List<WebElement> searchResults = driver.findElements(By.className("r"));
    Collections.shuffle(searchResults);
    final WebElement searchResult = searchResults.get(0).findElement(By.tagName("a"));
    logger.info("Selected search url result: {}", searchResult.getAttribute("href"));
    searchResult.click();
    logger.info("Current url: {}, title: {}", driver.getCurrentUrl(), driver.getTitle());

    // get back
    driver.navigate().back();
    logger.info("Current url: {}, title: {}", driver.getCurrentUrl(), driver.getTitle());
  }

  @Test
  public void testGoogleSearchByBdd() {
    // set implicit timeout as 3 seconds
    driver.manage().timeouts().implicitlyWait(3L, TimeUnit.SECONDS);

    // given
    driver.get("https://www.google.com");

    // when
    final String input = randomUUID().toString();
    final WebElement inputElement = driver.findElement(By.name("q"));
    inputElement.sendKeys(input);
    inputElement.submit();

    // then
    assertTrue("should contain search string", driver.getTitle().contains(input));
  }

}
