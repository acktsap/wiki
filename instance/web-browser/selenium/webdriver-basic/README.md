# Selenium WebDriver Basic usage

## Goal

Simulate google search using selenium webdriver.  
Must include handling screenshot.

## Javascript vs Java

All the selenium library enable us to handle dom element.  
Also they provides screenshot and recording by themself.  

### Criteria

- How to handle screenshot difference (visual comparision)
- Ecosystem for behavior based test

- Javascript
  - core : `webdriverio`, more advanced wrapper of webdriver protocol
  - visual comparision
    - `webdrivercss` : Just like Phantomcss. Not actively maintained
    - `hermione` : A wrapper using `webdriverio` and `mocha`. Need selenium-standalone
    - `resemblejs` : A image comparision library. `webdrivercss` uses it.
  - bdd : `cucumber-js`
- Java
  - core : `selenium-server` in maven repository
  - visual comparision : With image standard api
  - bdd : `cucumber`, `junit`

... too many!!! I choose java & plain selenium api

## See also

- [Selenium WebDriver](https://www.seleniumhq.org/docs/03_webdriver.jsp)
- [WebDriverIO](https://webdriver.io/)
- [webdriverio vs selenium-webdriver](https://github.com/webdriverio/webdriverio/issues/1968)
- [selenium and cucumber in java](https://medium.com/@lucie.duchemin/ui-automated-test-project-example-with-selenium-cucumber-and-java-b33788bd11c4)
- [webdriverio and webdrivercss](https://itnext.io/visual-regression-test-with-webdriverio-webdrivercss-d7675a1812b2)
- [hermione](https://github.com/gemini-testing/hermione)
- [compare imagte using java](http://mundrisoft.com/tech-bytes/compare-images-using-java/)

