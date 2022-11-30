# 3. Functions

- [3. Functions](#3-functions)
  - [Introduction](#introduction)
  - [Small!](#small)
  - [Do One Thing](#do-one-thing)
  - [One Level of Abstraction per Function](#one-level-of-abstraction-per-function)
    - [Reading Code from Top to Bottom: The Stepdown Rule](#reading-code-from-top-to-bottom-the-stepdown-rule)
  - [Switch Statements](#switch-statements)
  - [Use Descriptive Names](#use-descriptive-names)
  - [Function Arguments](#function-arguments)
    - [Common Monadic Forms](#common-monadic-forms)
    - [Flag Arguments](#flag-arguments)
    - [Dyadic Functions](#dyadic-functions)
    - [Triads](#triads)
    - [Argument Objects](#argument-objects)
    - [Argument Lists](#argument-lists)
    - [Verbs and Keywords](#verbs-and-keywords)
  - [Have No Side Effects](#have-no-side-effects)
    - [Output Arguments](#output-arguments)
  - [Command Query Separation](#command-query-separation)
  - [Prefer Exceptions to Returning Error Codes](#prefer-exceptions-to-returning-error-codes)
    - [Extract Try/Catch Blocks](#extract-trycatch-blocks)
    - [Error Handling Is One Thing](#error-handling-is-one-thing)
    - [The Error.java Dependency Magnet](#the-errorjava-dependency-magnet)
  - [Don’t Repeat Yourself](#dont-repeat-yourself)
  - [Structured Programming](#structured-programming)
  - [How Do You Write Functions Like This?](#how-do-you-write-functions-like-this)
  - [Conclusion](#conclusion)

## Introduction

- 어떻게 함수를 그 의도가 잘 표현될 수 있게 만들 수 있을까?

```java
// bad
public static String testableHtml(
    PageData pageData,
    boolean includeSuiteSetup
    ) throws Exception {
  WikiPage wikiPage = pageData.getWikiPage();
  StringBuffer buffer = new StringBuffer();
  if (pageData.hasAttribute("Test")) {
    if (includeSuiteSetup) {
      WikiPage suiteSetup =
        PageCrawlerImpl.getInheritedPage(
            SuiteResponder.SUITE_SETUP_NAME, wikiPage
            );
      if (suiteSetup != null) {
        WikiPagePath pagePath =
          suiteSetup.getPageCrawler().getFullPath(suiteSetup);
        String pagePathName = PathParser.render(pagePath);
        buffer.append("!include -setup .")
          .append(pagePathName)
          .append("\n");
      }
    }
    WikiPage setup =
      PageCrawlerImpl.getInheritedPage("SetUp", wikiPage);
    if (setup != null) {
      WikiPagePath setupPath =
        wikiPage.getPageCrawler().getFullPath(setup);
      String setupPathName = PathParser.render(setupPath);
      buffer.append("!include -setup .")
        .append(setupPathName)
        .append("\n");
    }
  }
  buffer.append(pageData.getContent());
  if (pageData.hasAttribute("Test")) {
    WikiPage teardown =
      PageCrawlerImpl.getInheritedPage("TearDown", wikiPage);
    if (teardown != null) {
      WikiPagePath tearDownPath =
        wikiPage.getPageCrawler().getFullPath(teardown);
      String tearDownPathName = PathParser.render(tearDownPath);
      buffer.append("\n")
        .append("!include -teardown .")
        .append(tearDownPathName)
        .append("\n");
    }
    if (includeSuiteSetup) {
      WikiPage suiteTeardown =
        PageCrawlerImpl.getInheritedPage(
            SuiteResponder.SUITE_TEARDOWN_NAME,
            wikiPage
            );
      if (suiteTeardown != null) {
        WikiPagePath pagePath =
          suiteTeardown.getPageCrawler().getFullPath (suiteTeardown);
        String pagePathName = PathParser.render(pagePath);
        buffer.append("!include -teardown .")
          .append(pagePathName)
          .append("\n");
      }
    }
  }
  pageData.setContent(buffer.toString());
  return pageData.getHtml();
}

// good
public static String renderPageWithSetupsAndTeardowns(
  PageData pageData, boolean isSuite
  ) throws Exception {
  boolean isTestPage = pageData.hasAttribute("Test");
  if (isTestPage) {
    WikiPage testPage = pageData.getWikiPage();
    StringBuffer newPageContent = new StringBuffer();
    includeSetupPages(testPage, newPageContent, isSuite);
    newPageContent.append(pageData.getContent());
    includeTeardownPages(testPage, newPageContent, isSuite);
    pageData.setContent(newPageContent.toString());
  }
  return pageData.getHtml();
}
```

## Small!

- 함수는 작아야함. 무조건 작은게 좋음. 적어도 한 모니터 안에 들어갈 수 있게.
- kent beck은 한 함수당 2~4줄의 코드를 짠 적이 있음. 각 함수는 하나의 명료한 스토리를 의미했음.
- if, while 문 같은거 안에 다른 함수를 호출하는 딱 한줄만 있게 짜는 정도로 하는게 좋음.
  ```java
  // good
  public static String renderPageWithSetupsAndTeardowns(
    PageData pageData, boolean isSuite) throws Exception {
    if (isTestPage(pageData))
      includeSetupAndTeardownPages(pageData, isSuite);
    return pageData.getHtml();
  }
  ```

## Do One Thing

- 함수를 작성하는 이유는 큰 개념을 decompose해서 여러 step으로 분리하는데 있음.
- **FUNCTIONS SHOULD DO ONE THING. THEY SHOULD DO IT WELL. THEY SHOULD DO IT ONLY**.
- 추상화 레벨에서 바로 아랫단계의 함수를 호출하거나 더이상 나눌 수 없는 작업을 하면 한개의 일을 하는 것임.
- 한개의 일을 하고 있는지 확인하기 위해서는 단순한 재작성이 아니라 진짜 분리될 수 있는 함수를 작성할 수 있는지 보면 됨.
  - 아래의 코드에서 if statement만 분리하다고 해도 딱히 추상화 레벨을 다르게 하는게 아님. 그냥 되풀이될 뿐.
    ```java
    // before
    public static String renderPageWithSetupsAndTeardowns(
      PageData pageData, boolean isSuite) throws Exception {
      if (isTestPage(pageData))
        includeSetupAndTeardownPages(pageData, isSuite);
      return pageData.getHtml();
    }

    // after (불필요한 분리)
    public static String renderPageWithSetupsAndTeardowns(
      PageData pageData, boolean isSuite) throws Exception {
      includeSetupsAndTeardownsIfTestPage(pageData, isSuite)
      return pageData.getHtml();
    }

    void includeSetupsAndTeardownsIfTestPage(PageData pageData, boolean i) {
      if (isTestPage(pageData))
        includeSetupAndTeardownPages(pageData, isSuite);
    }
    ```
  - 아래의 함수는 section으로 분리될 수 있음 (declaration, init, sieve). 이런게 one thing을 깨는것.
    ```java
    // bad
    public static int[] generatePrimes(int maxValue) {
      /* declarations */
      int s = maxValue + 1; // size of array
      boolean[] f = new boolean[s];
      int i;

      /* initialize array to true. */
      for (i = 0; i < s; i++)
        f[i] = true;
      // get rid of known non-primes
      f[0] = f[1] = false;

      /* sieve */
      int j;
      for (i = 2; i < Math.sqrt(s) + 1; i++)
      {
        if (f[i]) // if i is uncrossed, cross its multiples.
        {
          for (j = 2 * i; j < s; j += i)
            f[j] = false; // multiple is not prime
        }
      }
      // ...
    }
    ```

## One Level of Abstraction per Function

- 한 함수에서 호출하는 다른 함수들은 같은 추상화 level이어야 함. 안그럼 헷갈림.

### Reading Code from Top to Bottom: The Stepdown Rule

eg.

```text
To include the setups and teardowns, we include setups, then we include the test page content, and then we include the teardowns.
  To include the setups, ...
  To include the suite setup, ...
  To search the parent, ...
```

```java
// good
public class SetupTeardownIncluder {
  private PageData pageData;
  private boolean isSuite;
  private WikiPage testPage;
  private StringBuffer newPageContent;
  private PageCrawler pageCrawler;

  public static String render(PageData pageData) throws Exception {
    return render(pageData, false);
  }

  public static String render(PageData pageData, boolean isSuite)
    throws Exception {
    return new SetupTeardownIncluder(pageData).render(isSuite);
  }

  private SetupTeardownIncluder(PageData pageData) {
    this.pageData = pageData;
    testPage = pageData.getWikiPage();
    pageCrawler = testPage.getPageCrawler();
    newPageContent = new StringBuffer();
  }

  private String render(boolean isSuite) throws Exception {
    this.isSuite = isSuite;
    if (isTestPage())
      includeSetupAndTeardownPages();
    return pageData.getHtml();
  }

  private boolean isTestPage() throws Exception {
    return pageData.hasAttribute("Test");
  }

  private void includeSetupAndTeardownPages() throws Exception {
    includeSetupPages();
    includePageContent();
    includeTeardownPages();
    updatePageContent();
  }

  private void includeSetupPages() throws Exception {
    if (isSuite)
      includeSuiteSetupPage();
    includeSetupPage();
  }

  private void includeSuiteSetupPage() throws Exception {
    include(SuiteResponder.SUITE_SETUP_NAME, "-setup");
  }

  private void includeSetupPage() throws Exception {
    include("SetUp", "-setup");
  }

  private void includePageContent() throws Exception {
    newPageContent.append(pageData.getContent());
  }

  private void includeTeardownPages() throws Exception {
    includeTeardownPage();
    if (isSuite)
      includeSuiteTeardownPage();
  }

  private void includeTeardownPage() throws Exception {
    include("TearDown", "-teardown");
  }

  private void includeSuiteTeardownPage() throws Exception {
    include(SuiteResponder.SUITE_TEARDOWN_NAME, "-teardown");
  }

  private void updatePageContent() throws Exception {
    pageData.setContent(newPageContent.toString());
  }

  private void include(String pageName, String arg) throws Exception {
    WikiPage inheritedPage = findInheritedPage(pageName);
    if (inheritedPage != null) {
      String pagePathName = getPathNameForPage(inheritedPage);
      buildIncludeDirective(pagePathName, arg);
    }
  }

  private WikiPage findInheritedPage(String pageName) throws Exception {
    return PageCrawlerImpl.getInheritedPage(pageName, testPage);
  }

  private String getPathNameForPage(WikiPage page) throws Exception {
    WikiPagePath pagePath = pageCrawler.getFullPath(page);
    return PathParser.render(pagePath);
  }

  private void buildIncludeDirective(String pagePathName, String arg) {
    newPageContent
      .append("\n!include ")
      .append(arg)
      .append(" .")
      .append(pagePathName)
      .append("\n");
  }
}
```

## Switch Statements

- 종특상 switch statement는 한개의 일을 할 수 없음. 하지만 좋게 쓸 방법이 있음. switch가 한번만 호출되게 하면 됨.
  ```java
  // bad
  // problem 1 : 새 employee가 추가될 때 마다 커짐
  // problem 2 : 한개의 일을 하지 않음
  // problem 3 : Single Responsibility Principle 을 위반 (more than one reason for it to change)
  // problem 4 : Open Closed Principle 를 위반 (새 employee가 추가되면 변경되어야 함)
  // problem 5 : 이런 식의 함수가 더 추가될 수 있음 (isPayDay, delivarPay, ...). 그때마다 switch쓸건가?
  public Money calculatePay(Employee e) throws InvalidEmployeeType {
    switch (e.type) {
      case COMMISSIONED:
        return calculateCommissionedPay(e);
      case HOURLY:
        return calculateHourlyPay(e);
      case SALARIED:
        return calculateSalariedPay(e);
      default:
        throw new InvalidEmployeeType(e.type);
    }
  }

  // good : Abstract Factory
  public abstract class Employee {
    // 새 함수가 추가되어도 이거 구현한거에서만 하면 됨
    public abstract boolean isPayday();
    public abstract Money calculatePay();
    public abstract void deliverPay(Money pay);
  }

  public interface EmployeeFactory {
    public Employee makeEmployee(EmployeeRecord r) throws InvalidEmployeeType;
  }

  public class EmployeeFactoryImpl implements EmployeeFactory {
    public Employee makeEmployee(EmployeeRecord r) throws InvalidEmployeeType {
      // switch는 다형성을 위해 한번만 호출되고 사용자에게 드러나지 않음
      switch (r.type) {
        case COMMISSIONED:
          return new CommissionedEmployee(r) ;
        case HOURLY:
          return new HourlyEmployee(r);
        case SALARIED:
          return new SalariedEmploye(r);
        default:
          throw new InvalidEmployeeType(r.type);
      }
    }
  }
  ```
- 다형성을 위해 사용, 한번만 호출, 드러나지 않기 의 3개의 조건을 만족하면 switch statement를 써도 됨.

## Use Descriptive Names

- You know you are working on clean code when each routine turns out to be pretty much what you expected.
- 함수를 추상화 레벨에서 최대한 작게 하고 이름을 명확하게 지어라. 길어도 됨 명료한게 더 좋음.
- 이름이 길어지는 경우 이름이 어떻게 이어질건지 naming convention이 있는게 좋음.
- 요즘 ide 좋으니까 refactoring 활용해서 좋은 이름 나올때까지 시간을 투자해봐. 장기적으로 결국 더 좋음.
- 같은 레벨의 함수라면 이름이 일관성 있게.

## Function Arguments

- 인자는 적을 수록 좋음. 가능하면 3개 미만으로. 인자가 있으면 읽는 사람이 인자에 대해서 한번 더 생각해야함 (double-take).
- 한개의 인자를 사용하면 의미가 명확해짐.
  eg. `render(pageData)`

### Common Monadic Forms

- Single argument.
  - `boolean fileExists("fileName")`
  - `InputStream fileOpen("fileName")`
- Event.
  - `void passwordAttemptFailedNTimes(int attempts)`
- 인자를 변경하지마.
  ```java
  // bad
  void transform(StringBuffer out)

  // good
  StringBuffer transform(StringBuffer in)
  ```

### Flag Arguments

- flag 인자를 가지는 함수는 이름 짓기도 어렵고 한개의 일을 하는 함수가 아님. 하지마.

### Dyadic Functions

- 인자가 2개인거는 1개인거보다 이해하기 어려움.
  - eg. `writeField(output-stream, name)` -> 한번 더 생각해야 함
- 2개의 인자가 유효한 경우는 point같은거를 하는 경우임. But 이 경우 2개의 인자는 한개의 값을 나열한것 뿐.
  - eg. `new Point(0, 0)`
- 한개의 값을 자연스럽게 나열한 경우가 아니라면 헷갈릴 수 있음.
  - eg. `assertEquals(expected, actual)` -> expected 자리에 actual을?
- 2개의 인자 그리 나쁘지 않아서 사용할 수 있지만 1개로 합치는 경우의 이점을 기억해줘~

### Triads

- 3개의 인자 이상을 쓰면 슬슬 이해하기 힘들어짐.
  - eg. `assertEquals(message, expected, actual)` -> 이거 너무 헷갈림
- 필요한 경우도 있긴 함. 이럴때만 사용.
  - eg. `assertEquals(1.0, amount, .001)` -> expected, actual, delta

### Argument Objects

- 인자가 길어지면 묶어서 객체로 만드는 것도 좋은 방법임.
  ```java
  // bad
  Circle makeCircle(double x, double y, double radius);
  
  // good
  Circle makeCircle(Point center, double radius);
  ```

### Argument Lists

- vararg로 인자를 받는거는 사실상 list로 받는거라 인자가 1개인거랑 동급임.
  - eg. `String format(String format, Object... args)` -> Dyadic 취급

### Verbs and Keywords

- 동사랑 명사를 잘 조합해서 사용.
  - eg. `write(name)`
- keyword를 함수 이름에 넣는거도 안헷갈리고 좋음.
  - eg. `assertExpectedEqualsActual(expected, actual)`

## Have No Side Effects

- Side Effect는 실질적으로 한개 이상의 일을 더 해서 한개의 일을 해야 한다는 것에 위배됨.
  ```java
  // bad
  public boolean checkPassword(String userName, String password) {
    User user = UserGateway.findByName(userName);
    if (user != User.NULL) {
      String codedPhrase = user.getPhraseEncodedByPassword();
      String phrase = cryptographer.decrypt(codedPhrase, password);
      if ("Valid Password".equals(phrase)) {
        Session.initialize(); // side effect
        return true;
      }
    }
    return false;
  }
  ```

### Output Arguments

- input이 아니라 사실상 output처럼 동작하는 인자 금지. 현대 OOP 언어로는 이걸 쉽게 해결가능.
  ```java
  // bad
  void appendFooter(StringBuffer report);
  appendFooter(s); // append footer to 's'

  // good
  report.appendFooter();
  ```

> c에서는 어쩔 수 없는듯

## Command Query Separation

- command와 query가 혼용된 함수는 헷갈림. 분리해라.
```java
// bad
if (set("username", "unclebob")) { // set이 verb인가 adjective인가?
  ...
}

// good
if (attributeExists("username")) {
  setAttribute("username", "unclebob");
  ...
}
```

## Prefer Exceptions to Returning Error Codes

- exception이 아니라 error code를 쓰면 error code를 처리하는 것이 강제됨.
  ```java
  // bad
  if (deletePage(page) == E_OK) {
    if (registry.deleteReference(page.name) == E_OK) {
      if (configKeys.deleteKey(page.name.makeKey()) == E_OK){
        logger.log("page deleted");
      } else {
        logger.log("configKey not deleted");
      }
    } else {
      logger.log("deleteReference from registry failed");
    }
  } else {
    logger.log("delete failed");
    return E_ERROR;
  }

  // good
  try {
    deletePage(page);
    registry.deleteReference(page.name);
    configKeys.deleteKey(page.name.makeKey());
  } catch (Exception e) {
    logger.log(e.getMessage());
  }
  ```

### Extract Try/Catch Blocks

- try/catch block은 ugly하니까 try, catch body를 분리.
  ```java
  // good
  public void delete(Page page) {
    try {
      deletePageAndAllReferences(page);
    }
    catch (Exception e) {
      logError(e);
    }
  }

  private void deletePageAndAllReferences(Page page) throws Exception {
    deletePage(page);
    registry.deleteReference(page.name);
    configKeys.deleteKey(page.name.makeKey());
  }

  private void logError(Exception e) {
    logger.log(e.getMessage());
  }
  ```

> 개인적으로 예제 코드는 좀 과도한거 같음

### Error Handling Is One Thing

- function이 one thing을 해야 하는 것처럼 error handling도 one thing을 해야 함.
- 즉, error handling 함수는 `try`로 시작해서 `catch/finally` 이후로는 아무 코드도 없어야 함.

### The Error.java Dependency Magnet

- Error code를 쓰면 해당 에러를 쓰는 모든 코드가 해당 에러에 의존성이 생김. 수정되면 다 재빌드 해야 함.

## Don’t Repeat Yourself

- 코드 반복하지마. 관리 포인트가 늘어남. 반복은 소프트웨어의 최대의 적임.

## Structured Programming

- Dijkstra’s rules of structured programming 에서는 return이 한개여야 한다고 함.
- But 이미 함수가 충분히 작으면 괜찮음. 함수가 큰 경우만 적용.

## How Do You Write Functions Like This?

- 함수를 작성하는 것을 글쓰기처럼 해라.
- 처음에는 의식의 흐름대로 복잡하게 썼다가 Unit test 작성하고 이를 분리해라. 바로 잘짜는거 힘듬.

## Conclusion

- 모든 시스템은 Domain Specific Language로 구성됨. 
- function은 dsl에서의 동사고 class는 명사임.
- 시스템에 대해서 story telling을 하듯이 코드를 작성해라.