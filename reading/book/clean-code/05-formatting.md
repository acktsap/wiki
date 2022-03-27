# 5. Formatting

- [5. Formatting](#5-formatting)
  - [Introduction](#introduction)
  - [The Purpose of Formatting](#the-purpose-of-formatting)
  - [Vertical Formatting](#vertical-formatting)
    - [The Newspaper Metaphor](#the-newspaper-metaphor)
    - [Vertical Openness Between Concepts](#vertical-openness-between-concepts)
    - [Vertical Density](#vertical-density)
    - [Vertical Distance](#vertical-distance)
    - [Vertical Ordering](#vertical-ordering)
  - [Horizontal Formatting](#horizontal-formatting)
    - [Horizontal Openness and Density](#horizontal-openness-and-density)
    - [Horizontal Alignment](#horizontal-alignment)
    - [Indentation](#indentation)
    - [Dummy Scopes](#dummy-scopes)
  - [Team Rules](#team-rules)
  - [Uncle Bob's Formatting Rules](#uncle-bobs-formatting-rules)

## Introduction

- 코드가 깔끔하지 못하면 프로답지 못하다.
- 단순한 몇가지 규칙을 가지고 일관성 있게 적용하라.

## The Purpose of Formatting

- formatting은 너무 중요해서 종교적으로 대해도 될 정도임.
- formatting도 communication임.
- 코드가 돌아만 가서는 안됨. 나중에 사람들이 그거 기반으로 코드를 짬.

## Vertical Formatting

- 세로로 큰 파일보다 작은 파일이 읽기 더 쉬움.

### The Newspaper Metaphor

- 신문을 작성하는 것 처럼. headline을 먼저 작성하고, 아래로 내려갈수록 상세 정보가 나오게.
- 신문은 여러 개의 작은 article로 구성됨.

### Vertical Openness Between Concepts

- 다른 개념 사이에 space를.
  ```java
  // bad
  package fitnesse.wikitext.widgets;
  import java.util.regex.*;
  public class BoldWidget extends ParentWidget {
    public static final String REGEXP = "'''.+?'''";
    private static final Pattern pattern = Pattern.compile("'''(.+?)'''",
      Pattern.MULTILINE + Pattern.DOTALL);
    public BoldWidget(ParentWidget parent, String text) throws Exception {
      super(parent);
      Matcher match = pattern.matcher(text);
      match.find();
      addChildWidgets(match.group(1));}
    public String render() throws Exception {
      StringBuffer html = new StringBuffer("<b>");
      html.append(childHtml()).append("</b>");
      return html.toString();
    }
  }

  // good
  package fitnesse.wikitext.widgets;

  import java.util.regex.*;

  public class BoldWidget extends ParentWidget {
    public static final String REGEXP = "'''.+?'''";
    private static final Pattern pattern = Pattern.compile("'''(.+?)'''",
      Pattern.MULTILINE + Pattern.DOTALL
    );

    public BoldWidget(ParentWidget parent, String text) throws Exception {
      super(parent);
      Matcher match = pattern.matcher(text);
      match.find();
      addChildWidgets(match.group(1));
    }

    public String render() throws Exception {
      StringBuffer html = new StringBuffer("<b>");
      html.append(childHtml()).append("</b>");
      return html.toString();
    }
  }
  ```

### Vertical Density

- 같은 개념들은 묶어서.
  ```java
  // bad
  public class ReporterConfig {
    /**
    * The class name of the reporter listener
    */
    private String m_className;

    /**
    * The properties of the reporter listener
    */
    private List<Property> m_properties = new ArrayList<Property>();

    public void addProperty(Property property) {
      m_properties.add(property);
    }
  }

  // good
  public class ReporterConfig {
    private String m_className;
    private List<Property> m_properties = new ArrayList<Property>();

    public void addProperty(Property property) {
      m_properties.add(property);
    }
  }
  ```

### Vertical Distance

- 연관된 개념들은 가능하면 가까이 있어야 함. 가능하면 한 파일에. 그래서 protected가 별로임.
- 한 파일 내에서도 서로 비슷한 개념끼리 묶여야함.
- Variable Declarations 
  - 사용되는 곳에서 가장 가까이에 선언.
    ```java
    private static void readPreferences() {
      InputStream is= null; // here
      try {
        is= new FileInputStream(getPreferencesFile());
        setPreferences(new Properties(getPreferences()));
        getPreferences().load(is);
      } catch (IOException e) {
        try {
        if (is != null)
          is.close();
        } catch (IOException e1) {
        }
      }
    }
    ```
  - loop 안에서 사용되는 변수는 loop 안에 선언
    ```java
    public int countTestCases() {
      int count= 0;
      for (Test each : tests) // 'each' at here
        count += each.countTestCases();
      return count;
    }
    ```
- Instance variables
  - 잘 설계된 class의 instance variable는 여러 method에서 사용됨. 그러므로 class 최상단에 정의.
  - 무엇보다 convension을 따르셈 (cpp는 제일 아래에 선언)
- Dependent Functions
  - 한 method가 다른 method를 call 하면 묶어서 두셈. caller 먼저, callee를 후에.
    ```java
    public class WikiPageResponder implements SecureResponder {
      protected WikiPage page;
      protected PageData pageData;
      protected String pageTitle;
      protected Request request;
      protected PageCrawler crawler;

      public Response makeResponse(FitNesseContext context, Request request) throws Exception {
        // 이렇게 constant를("FrontPage") high level에서 넘기는게 좋음
        // 사용하는 쪽에서 알기 쉽게
        String pageName = getPageNameOrDefault(request, "FrontPage"); 

        loadPage(pageName, context);
        if (page == null)
          return notFoundResponse(context, request);
        else
          return makePageResponse(context);
      }

      private String getPageNameOrDefault(Request request, String defaultPageName) {
        String pageName = request.getResource();
        if (StringUtil.isBlank(pageName))
          pageName = defaultPageName;
        return pageName;
      }

      protected void loadPage(String resource, FitNesseContext context) {
        WikiPagePath path = PathParser.parse(resource);
        crawler = context.root.getPageCrawler();
        crawler.setDeadEndStrategy(new VirtualEnabledPageCrawler());
        page = crawler.getPage(context.root, path);
        if (page != null)
          pageData = page.getData();
      }

      private Response notFoundResponse(FitNesseContext context, Request request) {
        return new NotFoundResponder().makeResponse(context, request);
      }

      private SimpleResponse makePageResponse(FitNesseContext context) {
        pageTitle = PathParser.render(crawler.getFullPath(page));
        String html = makeHtml(context);
        SimpleResponse response = new SimpleResponse();
        response.setMaxAge(0);
        response.setContent(html);
        return response;
      }
    }
    ```
- Conceptual Affinity (개념 친화도)
  - 직접적으로 의존하는 경우 (eg. caller-callee)
  - 서로 비슷한 개념인 경우
    ```java
    public class Assert {
      static public void assertTrue(String message, boolean condition) {
        if (!condition)
          fail(message);
      }

      static public void assertTrue(boolean condition) {
        assertTrue(null, condition);
      }

      static public void assertFalse(String message, boolean condition) {
        assertTrue(message, !condition);
      }

      static public void assertFalse(boolean condition) {
        assertFalse(null, condition);
      }
      ...
    ```

### Vertical Ordering

- 중요한걸 먼저, 상세한걸 나중에.

## Horizontal Formatting

- 개발자는 소스 코드의 width가 적은걸 더 선호.
- 가로로 scroll을 안해도 될 정도의 width. 모니터가 커도 막하지 말고 120 width 이내로.

### Horizontal Openness and Density

- 서로 연관 있는거 사이에는 space x. 없는거 사이에는 o
  ```java
  private void measureLine(String line) {
    lineCount++;
    int lineSize = line.length(); // assignment '=' 양옆으로 space
    totalChars += lineSize;

    // function call argument 사이에는 space로 분리
    lineWidthHistogram.addLine(lineSize, lineCount); 

    recordWidestLine(lineSize);
  }
  ```

### Horizontal Alignment

- 수평 정렬을 하는 것은 의미가 없음. 오히려 가독성을 헤침. 그게 필요한 경우라면 field가 너무 많은지 먼저 생각해봐.
  ```java
  public class FitNesseExpediter implements ResponseSender
  {
    private Socket          socket;
    private InputStream     input;
    private OutputStream    output;
    private Request         request;
    private Response        response;
    private FitNesseContext context;
    protected long          requestParsingTimeLimit;
    private long            requestProgress;
    private long            requestParsingDeadline;
    private boolean         hasError;
    public FitNesseExpediter(Socket          s,
                            FitNesseContext context) throws Exception
    {
      this.context =            context;
      socket =                  s;
      input =                   s.getInputStream();
      output =                  s.getOutputStream();
      requestParsingTimeLimit = 10000;
    }
  }
  ```

### Indentation

- file, class, method, field, block을 작성할 때 indent를 씀. 없으면 사람이 읽기 힘듬.
  ```java
  // bad
  public class FitNesseServer implements SocketServer { private FitNesseContext
  context; public FitNesseServer(FitNesseContext context) { this.context =
  context; } public void serve(Socket s) { serve(s, 10000); } public void
  serve(Socket s, long requestTimeout) { try { FitNesseExpediter sender = new
  FitNesseExpediter(s, context);
  sender.setRequestParsingTimeLimit(requestTimeout); sender.start(); }
  catch(Exception e) { e.printStackTrace(); } } }

  // good
  public class FitNesseServer implements SocketServer {
    private FitNesseContext context;

    public FitNesseServer(FitNesseContext context) {
      this.context = context;
    }

    public void serve(Socket s) {
      serve(s, 10000);
    }

    public void serve(Socket s, long requestTimeout) {
      try {
        FitNesseExpediter sender = new FitNesseExpediter(s, context);
        sender.setRequestParsingTimeLimit(requestTimeout);
        sender.start();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  ```
- Breaking Indentation : body가 짧아도 indent넣어.
  ```java
  // bad
  public class CommentWidget extends TextWidget {
    public static final String REGEXP = "^#[^\r\n]*(?:(?:\r\n)|\n|\r)?";

    public CommentWidget(ParentWidget parent, String text){super(parent, text);}
    public String render() throws Exception {return ""; }
  }

  // good
  public class CommentWidget extends TextWidget {
    public static final String REGEXP = "^#[^\r\n]*(?:(?:\r\n)|\n|\r)?";

    public CommentWidget(ParentWidget parent, String text) {
      super(parent, text);
    }

    public String render() throws Exception {
      return "";
    }
  }
  ```

### Dummy Scopes

- while문에서 empty body를 해야 하는 경우가 있으면 표시좀;
  ```java
  while (dis.read(buf, 0, readBufferSize) != -1)
    ; // like this
  ```

## Team Rules

- 코드의 일관성을 위해서 팀 규칙을 만들고 이를 지켜야 함.
- ide의 formatter 에서 사용할 수 있는 formatting rule 파일을 만들어.

## Uncle Bob's Formatting Rules

```java
public class CodeAnalyzer implements JavaFileAnalysis {
  private int lineCount;
  private int maxLineWidth;
  private int widestLineNumber;
  private LineWidthHistogram lineWidthHistogram;
  private int totalChars;

  public CodeAnalyzer() {
    lineWidthHistogram = new LineWidthHistogram();
  }

  public static List<File> findJavaFiles(File parentDirectory) {
    List<File> files = new ArrayList<File>();
    findJavaFiles(parentDirectory, files);
    return files;
  }

  private static void findJavaFiles(File parentDirectory, List<File> files) {
    for (File file : parentDirectory.listFiles()) {
      if (file.getName().endsWith(".java"))
        files.add(file);
      else if (file.isDirectory())
        findJavaFiles(file, files);
    }
  }

  public void analyzeFile(File javaFile) throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(javaFile));
    String line;
    while ((line = br.readLine()) != null)
      measureLine(line);
  }

  private void measureLine(String line) {
    lineCount++;
    int lineSize = line.length();
    totalChars += lineSize;
    lineWidthHistogram.addLine(lineSize, lineCount);
    recordWidestLine(lineSize);
  }

  // 제일 width가 긴거 기록
  private void recordWidestLine(int lineSize) {
    if (lineSize > maxLineWidth) {
      maxLineWidth = lineSize;
      widestLineNumber = lineCount;
    }
  }

  public int getLineCount() {
    return lineCount;
  }

  public int getMaxLineWidth() {
    return maxLineWidth;
  }

  public int getWidestLineNumber() {
    return widestLineNumber;
  }

  public LineWidthHistogram getLineWidthHistogram() {
    return lineWidthHistogram;
  }

  public double getMeanLineWidth() {
    return (double)totalChars/lineCount;
  }

  public int getMedianLineWidth() {
    Integer[] sortedWidths = getSortedWidths();
    int cumulativeLineCount = 0;
    for (int width : sortedWidths) {
      cumulativeLineCount += lineCountForWidth(width);
      if (cumulativeLineCount > lineCount/2)
        return width;
    }
    throw new Error("Cannot get here");
  }

  private int lineCountForWidth(int width) {
    return lineWidthHistogram.getLinesforWidth(width).size();
  }

  private Integer[] getSortedWidths() {
    Set<Integer> widths = lineWidthHistogram.getWidths();
    Integer[] sortedWidths = (widths.toArray(new Integer[0]));
    Arrays.sort(sortedWidths);
    return sortedWidths;
  }
}
```