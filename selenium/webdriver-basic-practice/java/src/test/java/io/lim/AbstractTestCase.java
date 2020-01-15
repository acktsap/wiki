package io.lim;

import static org.slf4j.LoggerFactory.getLogger;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;

public class AbstractTestCase {

  // change to FirefoxDriver if you want
  protected static final Class<? extends WebDriver> webDriverClass = ChromeDriver.class;

  // set as false if you want
  protected final boolean isHeadless = true;

  protected final Logger logger = getLogger(getClass());

  protected WebDriver driver;

  @BeforeClass
  public static void setUpBeforeClass() {
    // WebDriverManager save webdriver binary into ~/.m2/repository/webdriver
    // corresponding to operating system and selected browser
    WebDriverManager.getInstance(webDriverClass).setup();
  }

  @Before
  public void setUp() {
    try {
      if (webDriverClass.isAssignableFrom(ChromeDriver.class)) {
        logger.info("Use chrome driver");
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(isHeadless);
        this.driver = webDriverClass.getConstructor(ChromeOptions.class).newInstance(options);
      } else if (webDriverClass.isAssignableFrom(FirefoxDriver.class)) {
        logger.info("Use firefox driver");
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(isHeadless);
        this.driver = webDriverClass.getConstructor(FirefoxOptions.class).newInstance(options);
      } else {
        logger.info("Use {}", webDriverClass);
        this.driver = webDriverClass.newInstance();
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @After
  public void tearDown() {
    // comment it if you wanna play
    this.driver.quit();
  }

}
