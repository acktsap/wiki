# 4. Comments

- [4. Comments](#4-comments)
  - [Introduction](#introduction)
  - [Comments Do Not Make Up for Bad Code](#comments-do-not-make-up-for-bad-code)
  - [Explain Yourself in Code](#explain-yourself-in-code)
  - [Good Comments](#good-comments)
    - [Legal Comments](#legal-comments)
    - [Informative Comments](#informative-comments)
    - [Explanation of Intent](#explanation-of-intent)
    - [Clarification](#clarification)
    - [Warning of Consequences](#warning-of-consequences)
    - [TODO Comments](#todo-comments)
    - [Amplification](#amplification)
    - [Javadocs in Public APIs](#javadocs-in-public-apis)
  - [Bad Comments](#bad-comments)
    - [Mumbling (중얼거림)](#mumbling-중얼거림)
    - [Redundant Comments](#redundant-comments)
    - [Misleading Comments](#misleading-comments)
    - [Mandated Comments](#mandated-comments)
    - [Journal Comments](#journal-comments)
    - [Noise Comments](#noise-comments)
    - [Scary Noise](#scary-noise)
    - [Don’t Use a Comment When You Can Use a Function or a Variable](#dont-use-a-comment-when-you-can-use-a-function-or-a-variable)
    - [Position Markers](#position-markers)
    - [Closing Brace Comments](#closing-brace-comments)
    - [Attributions and Bylines](#attributions-and-bylines)
    - [Commented-Out Code](#commented-out-code)
    - [HTML Comments](#html-comments)
    - [Nonlocal Information](#nonlocal-information)
    - [Too Much Information](#too-much-information)
    - [Inobvious Connection](#inobvious-connection)
    - [Function Headers](#function-headers)
    - [Javadocs in Nonpublic Code](#javadocs-in-nonpublic-code)

## Introduction

- Don’t comment bad code — rewrite it
- comment는 필요악임. 표현력이 충분히 좋다면 comment가 필요 없음. comment는 항상 실패임.
- 프로그래머가 comment를 관리를 잘 안해서 오래될수록 구라를 칠 확률이 높음. 코드로 표현해라.

## Comments Do Not Make Up for Bad Code

- 주석은 나쁜 코드를 덮기 위해서 있는게 아니다. 주석을 달아야 할거 같으면 새로 짜라.

## Explain Yourself in Code

- 코드의 의도를 표현해라.
  ```java
  // bad
  // Check to see if the employee is eligible for full benefits
  if ((employee.flags & HOURLY_FLAG) && (employee.age > 65))

  // good
  if (employee.isEligibleForFullBenefits())
  ```

## Good Comments

- 가끔은 필요한 주석이 있긴 하다. 하지만 주석이 없는게 최고인걸 명심할 것.

### Legal Comments

- 라이선스 정보 등을 위해 다는 주석은 필요. 그렇다고 라이선스 전문을 쓰진 말 것.
  ```java
  // good
  // Copyright (C) 2003,2004,2005 by Object Mentor, Inc. All rights reserved.
  // Released under the terms of the GNU General Public License version 2 or later.
  ```

### Informative Comments

- 정보정 주석은 도움이 되긴 한다. But 함수명을 바꾸는게 최고.
  ```java
  // not bad

  // Returns an instance of the Responder being tested.
  protected abstract Responder responderInstance();

  // good
  protected abstract Responder responderBeingTested();
  ```

### Explanation of Intent

- 결정사항 등 의도를 파악하기 위한 주석은 도움이 됨.
  ```java
  // good
  public int compareTo(Object o) {
    if (o instanceof WikiPagePath) {
      WikiPagePath p = (WikiPagePath) o;
      String compressedName = StringUtil.join(names, "");
      String compressedArgumentName = StringUtil.join(p.names, "");
      return compressedName.compareTo(compressedArgumentName);
    }
    return 1; // we are greater because we are the right type.
  }
  ```
  ```java
  // good
  public void testConcurrentAddWidgets() throws Exception {
    WidgetBuilder widgetBuilder = new WidgetBuilder(new Class[]{BoldWidget.class});
    String text = "'''bold text'''";
    ParentWidget parent = new BoldWidget(new MockWidgetRoot(), "'''bold text'''");
    AtomicBoolean failFlag = new AtomicBoolean();
    failFlag.set(false);

    //This is our best attempt to get a race condition
    //by creating large number of threads.
    for (int i = 0; i < 25000; i++) {
      WidgetBuilderThread widgetBuilderThread =
      new WidgetBuilderThread(widgetBuilder, text, parent, failFlag);
      Thread thread = new Thread(widgetBuilderThread);
      thread.start();
    }
    assertEquals(false, failFlag.get());
  }
  ```

### Clarification

- 리턴값이나 인자를 더이상 명료하게 표현하지 못할 경우 주석은 도움이 됨.
  ```java
  // good
  public void testCompareTo() throws Exception {
    WikiPagePath a = PathParser.parse("PageA");
    WikiPagePath ab = PathParser.parse("PageA.PageB");
    WikiPagePath b = PathParser.parse("PageB");
    WikiPagePath aa = PathParser.parse("PageA.PageA");
    WikiPagePath bb = PathParser.parse("PageB.PageB");
    WikiPagePath ba = PathParser.parse("PageB.PageA");

    assertTrue(a.compareTo(a) == 0);    // a == a
    assertTrue(a.compareTo(b) != 0);    // a != b
    assertTrue(ab.compareTo(ab) == 0);  // ab == ab
    assertTrue(a.compareTo(b) == -1);   // a < b
    assertTrue(aa.compareTo(ab) == -1); // aa < ab
    assertTrue(ba.compareTo(bb) == -1); // ba < bb
    assertTrue(b.compareTo(a) == 1);    // b > a
    assertTrue(ab.compareTo(aa) == 1);  // ab > aa
    assertTrue(bb.compareTo(ba) == 1);  // bb > ba
  }
  ```

### Warning of Consequences

- 경고의 의미에서 하는 주석도 필요한 경우가 있음.
  ```java
  // good
  public static SimpleDateFormat makeStandardHttpDateFormat() {
    //SimpleDateFormat is not thread safe,
    //so we need to create each instance independently.
    SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
    df.setTimeZone(TimeZone.getTimeZone("GMT"));
    return df;
  }
  ```

### TODO Comments

- 미래에 해야 할 일을 적어두는 경우에도 좋음.
  ```java
  // good
  // TODO-MdM these are not needed
  // We expect this to go away when we do the checkout model
  protected VersionInfo makeVersion() throws Exception {
    return null;
  }
  ```
- 요즘 ide는 todo 주석도 tracking해줌.

### Amplification

- 얼핏 보면 사소해 보이는 코드의 의도를 중요하게 할 때도 좋음.
  ```java
  // good
  String listItemContent = match.group(3).trim();
  // the trim is real important. It removes the starting
  // spaces that could cause the item to be recognized
  // as another list.
  new ListItemWidget(this, listItemContent, this.level + 1);
  return buildList(text.substring(match.end()));
  ```

### Javadocs in Public APIs

- Public API에서 Javadoc 같은 문서는 필요.

## Bad Comments

- 대부분의 주석은 안좋은 주석에 속함.

### Mumbling (중얼거림)

- 주석을 적어야만 한다면 대충 적지 말고 제대로 적어라.
  ```java
  // bad : default가 load 되었다는 말임?? 알 수 없다..
  public void loadProperties() {
    try {
      String propertiesPath = propertiesLocation + "/" + PROPERTIES_FILE;
      FileInputStream propertiesStream = new FileInputStream(propertiesPath);
      loadedProperties.load(propertiesStream);
    } catch(IOException e) {
    // No properties files means all defaults are loaded
    }
  }
  ```

### Redundant Comments

- 코드의 의미를 그대로 설명하는 주석은 코드보다 오히려 더 안좋음.
  ```java
  /**
   * The Manager implementation with which this Container is
   * associated.
   */
  protected Manager manager = null;
  /**
   * The cluster with which this Container is associated.
   */
  protected Cluster cluster = null;
  /**
   * The human-readable name of this Container.
   */
  protected String name = null;
  ```

### Misleading Comments

- 주석 가지고 구라 치지 마라.
  ```java
  // bad
  // Utility method that returns when this.closed is true. Throws an exception
  // if the timeout is reached.
  public synchronized void waitForClose(final long timeoutMillis) {
    if (!closed) {
      wait(timeoutMillis);
      // this.closed가 true가 되더라도 timeout을 기다리고 return하므로 시간이 더 걸림.
      // 그러므로 위에 주석은 혼동을 줄 수가 있음.
      if (!closed) { 
        throw new Exception("MockResponseSender could not be closed");
      }
    }
  }
  ```

### Mandated Comments

- 의무적으로 주석을 다는 것은 어리석음.
  ```java
  /**
   *
   * @param title The title of the CD
   * @param author The author of the CD
   * @param tracks The number of tracks on the CD
   * @param durationInMinutes The duration of the CD in minutes
   */
  public void addCD(String title, String author,
    int tracks, int durationInMinutes) {
    CD cd = new CD();
    cd.title = title;
    cd.author = author;
    cd.tracks = tracks;
    cd.duration = duration;
    cdList.add(cd);
  }
  ```

> library에는 javadoc 의무로 달아야 한다고 봄. 적어도 직접적으로 노출되는 api에는.

### Journal Comments

- 수정사항을 담은 주석은 별로임. 예전에 code control system이 없을 때나 유효.
  ```java
  /**
   * Changes (from 11-Oct-2001)
   * --------------------------
   * 11-Oct-2001 : Re-organised the class and moved it to new package
   * 05-Dec-2001 : Fixed bug in SpreadsheetDate class (DG);
   * 29-May-2002 : Moved the month constants into a separate interface
   * 27-Aug-2002 : Fixed bug in addMonths() method, thanks to N???levka Petr (DG);
   * 03-Oct-2002 : Fixed errors reported by Checkstyle (DG);
   * 13-Mar-2003 : Implemented Serializable (DG);
   * 29-May-2003 : Fixed bug in addMonths method (DG);
   * 04-Sep-2003 : Implemented Comparable. Updated the isInRange javadocs (DG);
   * 05-Jan-2005 : Fixed bug in addYears() method (1096282) (DG);
   */
  ```

### Noise Comments

- 오히려 있어서 코드를 읽는데 더 방해되는 주석도 있음.
  ```java
  // bad : Give me a break??? 주석쓰다 빡쳐서 적은거 같음.
  private void startSending() {
    try {
      doSending();
    } catch (SocketException e) {
      // normal. someone stopped the request.
    }
    catch(Exception e) {
      try {
        response.add(ErrorResponder.makeExceptionString(e));
        response.closeAll();
      }
      catch(Exception e1) {
        // Give me a break!
      }
    }
  }
  ```

### Scary Noise

- 쓸데없는 주석을 넘어 아예 이상한 주석도 있음.
  ```java
  /** The name. */
  private String name;

  /** The version. */
  private String version;

  /** The licenceName. */
  private String licenceName;

  // cut-paste error
  /** The version. */
  private String info;
  ```

### Don’t Use a Comment When You Can Use a Function or a Variable

- 함수나 변수로 설명의 대체가 가능한 경우 그걸 사용.
  ```java
  // bad
  // does the module from the global list <mod> depend on the
  // subsystem we are part of?
  if (smodule.getDependSubsystems().contains(subSysMod.getSubSystem()))

  // good
  ArrayList moduleDependees = smodule.getDependSubsystems();
  String ourSubSystem = subSysMod.getSubSystem();
  if (moduleDependees.contains(ourSubSystem))
  ```

### Position Markers

- position을 위해 적는 comment는 없는게 좋음. 꼭 필요할 때만 사용.
  ```java
  // bad
  // Actions //////////////////////////////////
  ```

### Closing Brace Comments

- closeing comment를 사용하지 말고 block 길이를 짧게 해라.
  ```java
  public static void main(String[] args) {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    String line;
    int lineCount = 0;
    int charCount = 0;
    int wordCount = 0;
    try {
      while ((line = in.readLine()) != null) {
        lineCount++;
        charCount += line.length();
        String words[] = line.split("\\W");
        wordCount += words.length;
      } //while
      System.out.println("wordCount = " + wordCount);
      System.out.println("lineCount = " + lineCount);
      System.out.println("charCount = " + charCount);
    } // try
    catch (IOException e) {
      System.err.println("Error:" + e.getMessage());
    } //catch
  } //main
  ```

### Attributions and Bylines

- 누가 추가했는지 같은걸 주석으로 쓰지 마셈. code control system이 더 좋음.
  ```java
  /* Added by Rick */
  ```

### Commented-Out Code

- code를 comment out 하지 마라. 남들이 보면 뭔가 중요해서 지울 수 없거 같아서 지우기 힘듬.
- code control system에 코드가 남아 있으니 그냥 지워.
  ```java
  InputStreamResponse response = new InputStreamResponse();
  response.setBody(formatter.getResultStream(), formatter.getByteCount());
  // InputStream resultsStream = formatter.getResultStream();
  // StreamReader reader = new StreamReader(resultsStream);
  // response.setContent(reader.read(formatter.getByteCount()));
  ```

### HTML Comments

TODO

### Nonlocal Information

### Too Much Information

### Inobvious Connection

### Function Headers

### Javadocs in Nonpublic Code

---

> 이 책에서 제일 논란의 여지가 없을 장 같음. 최고의 주석은 없는 주석이다.