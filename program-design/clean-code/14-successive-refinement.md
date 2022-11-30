# 14. Successive Refinement

- [14. Successive Refinement](#14-successive-refinement)
  - [Introduction](#introduction)
  - [Args Implementation](#args-implementation)
    - [How Did I Do This?](#how-did-i-do-this)
  - [Args: The Rough Draft](#args-the-rough-draft)
    - [So I Stopped](#so-i-stopped)
    - [On Incrementalism](#on-incrementalism)
  - [String Arguments](#string-arguments)
  - [Conclusion](#conclusion)

## Introduction

- Case Study of a Command-Line Argument Parser
- Main Code
  ```java
  public static void main(String[] args) {
    try {
      // l: Boolean, p: Integer, d: String
      Args arg = new Args("l,p#,d*", args);
      boolean logging = arg.getBoolean('l');
      int port = arg.getInt('p');
      String directory = arg.getString('d');
      executeApplication(logging, port, directory);
    } catch (ArgsException e) {
      System.out.printf("Argument error: %s\n", e.errorMessage());
    }
  }
  ```

## Args Implementation

- Args Code
  ```java
  public class Args {
    private Map<Character, ArgumentMarshaler> marshalers;
    private Set<Character> argsFound;
    private ListIterator<String> currentArgument;
    
    public Args(String schema, String[] args) throws ArgsException { 
      marshalers = new HashMap<Character, ArgumentMarshaler>(); 
      argsFound = new HashSet<Character>();
      
      parseSchema(schema);
      parseArgumentStrings(Arrays.asList(args)); 
    }
    
    private void parseSchema(String schema) throws ArgsException { 
      for (String element : schema.split(","))
        if (element.length() > 0) 
          parseSchemaElement(element.trim());
    }
    
    private void parseSchemaElement(String element) throws ArgsException { 
      char elementId = element.charAt(0);
      String elementTail = element.substring(1); validateSchemaElementId(elementId);
      if (elementTail.length() == 0)
        marshalers.put(elementId, new BooleanArgumentMarshaler());
      else if (elementTail.equals("*")) 
        marshalers.put(elementId, new StringArgumentMarshaler());
      else if (elementTail.equals("#"))
        marshalers.put(elementId, new IntegerArgumentMarshaler());
      else if (elementTail.equals("##")) 
        marshalers.put(elementId, new DoubleArgumentMarshaler());
      else if (elementTail.equals("[*]"))
        marshalers.put(elementId, new StringArrayArgumentMarshaler());
      else
        throw new ArgsException(INVALID_ARGUMENT_FORMAT, elementId, elementTail);
    }
    
    private void validateSchemaElementId(char elementId) throws ArgsException { 
      if (!Character.isLetter(elementId))
        throw new ArgsException(INVALID_ARGUMENT_NAME, elementId, null); 
    }
    
    private void parseArgumentStrings(List<String> argsList) throws ArgsException {
      for (currentArgument = argsList.listIterator(); currentArgument.hasNext();) {
        String argString = currentArgument.next(); 
        if (argString.startsWith("-")) {
          parseArgumentCharacters(argString.substring(1)); 
        } else {
          currentArgument.previous();
          break; 
        }
      } 
    }
    
    private void parseArgumentCharacters(String argChars) throws ArgsException { 
      for (int i = 0; i < argChars.length(); i++)
        parseArgumentCharacter(argChars.charAt(i)); 
    }
    
    private void parseArgumentCharacter(char argChar) throws ArgsException { 
      ArgumentMarshaler m = marshalers.get(argChar);
      if (m == null) {
        throw new ArgsException(UNEXPECTED_ARGUMENT, argChar, null); 
      } else {
        argsFound.add(argChar); 
        try {
          m.set(currentArgument); 
        } catch (ArgsException e) {
          e.setErrorArgumentId(argChar);
          throw e; 
        }
      } 
    }
    
    public boolean has(char arg) { 
      return argsFound.contains(arg);
    }
    
    public int nextArgument() {
      return currentArgument.nextIndex();
    }
    
    public boolean getBoolean(char arg) {
      return BooleanArgumentMarshaler.getValue(marshalers.get(arg));
    }
    
    public String getString(char arg) {
      return StringArgumentMarshaler.getValue(marshalers.get(arg));
    }
    
    public int getInt(char arg) {
      return IntegerArgumentMarshaler.getValue(marshalers.get(arg));
    }
    
    public double getDouble(char arg) {
      return DoubleArgumentMarshaler.getValue(marshalers.get(arg));
    }
    
    public String[] getStringArray(char arg) {
      return StringArrayArgumentMarshaler.getValue(marshalers.get(arg));
    } 
  }
  ```
- Marchaler Code
  ```java
  public interface ArgumentMarshaler {
    void set(Iterator<String> currentArgument) throws ArgsException;
  }

  public class BooleanArgumentMarshaler implements ArgumentMarshaler { 
    private boolean booleanValue = false;
    
    public void set(Iterator<String> currentArgument) throws ArgsException { 
      booleanValue = true;
    }
    
    public static boolean getValue(ArgumentMarshaler am) {
      if (am != null && am instanceof BooleanArgumentMarshaler)
        return ((BooleanArgumentMarshaler) am).booleanValue; 
      else
        return false; 
    }
  }

  public class StringArgumentMarshaler implements ArgumentMarshaler { 
    private String stringValue = "";
    
    public void set(Iterator<String> currentArgument) throws ArgsException { 
      try {
        stringValue = currentArgument.next(); 
      } catch (NoSuchElementException e) {
        throw new ArgsException(MISSING_STRING); 
      }
    }
    
    public static String getValue(ArgumentMarshaler am) {
      if (am != null && am instanceof StringArgumentMarshaler)
        return ((StringArgumentMarshaler) am).stringValue; 
      else
        return ""; 
    }
  }

  import static com.objectmentor.utilities.args.ArgsException.ErrorCode.*;

  public class IntegerArgumentMarshaler implements ArgumentMarshaler { 
    private int intValue = 0;
    
    public void set(Iterator<String> currentArgument) throws ArgsException { 
      String parameter = null;
      try {
        parameter = currentArgument.next();
        intValue = Integer.parseInt(parameter);
      } catch (NoSuchElementException e) {
        throw new ArgsException(MISSING_INTEGER);
      } catch (NumberFormatException e) {
        throw new ArgsException(INVALID_INTEGER, parameter); 
      }
    }
    
    public static int getValue(ArgumentMarshaler am) {
      if (am != null && am instanceof IntegerArgumentMarshaler)
        return ((IntegerArgumentMarshaler) am).intValue; 
      else
      return 0; 
    }
  }
  ```
- ArgsException Code
  ```java
  public class ArgsException extends Exception { 
    private char errorArgumentId = '\0'; 
    private String errorParameter = null; 
    private ErrorCode errorCode = OK;
    
    public ArgsException() {}
    
    public ArgsException(String message) {super(message);}
    
    public ArgsException(ErrorCode errorCode) { 
      this.errorCode = errorCode;
    }
    
    public ArgsException(ErrorCode errorCode, String errorParameter) { 
      this.errorCode = errorCode;
      this.errorParameter = errorParameter;
    }
    
    public ArgsException(ErrorCode errorCode, char errorArgumentId, String errorParameter) {
      this.errorCode = errorCode; 
      this.errorParameter = errorParameter; 
      this.errorArgumentId = errorArgumentId;
    }
    
    public char getErrorArgumentId() { 
      return errorArgumentId;
    }
    
    public void setErrorArgumentId(char errorArgumentId) { 
      this.errorArgumentId = errorArgumentId;
    }
    
    public String getErrorParameter() { 
      return errorParameter;
    }
    
    public void setErrorParameter(String errorParameter) { 
      this.errorParameter = errorParameter;
    }
    
    public ErrorCode getErrorCode() { 
      return errorCode;
    }
    
    public void setErrorCode(ErrorCode errorCode) { 
      this.errorCode = errorCode;
    }
    
    public String errorMessage() { 
      switch (errorCode) {
        case OK:
          return "TILT: Should not get here.";
        case UNEXPECTED_ARGUMENT:
          return String.format("Argument -%c unexpected.", errorArgumentId);
        case MISSING_STRING:
          return String.format("Could not find string parameter for -%c.", errorArgumentId);
        case INVALID_INTEGER:
          return String.format("Argument -%c expects an integer but was '%s'.", errorArgumentId, errorParameter);
        case MISSING_INTEGER:
          return String.format("Could not find integer parameter for -%c.", errorArgumentId);
        case INVALID_DOUBLE:
          return String.format("Argument -%c expects a double but was '%s'.", errorArgumentId, errorParameter);
        case MISSING_DOUBLE:
          return String.format("Could not find double parameter for -%c.", errorArgumentId); 
        case INVALID_ARGUMENT_NAME:
          return String.format("'%c' is not a valid argument name.", errorArgumentId);
        case INVALID_ARGUMENT_FORMAT:
          return String.format("'%s' is not a valid argument format.", errorParameter);
      }
      return ""; 
    }
    
    public enum ErrorCode {
      OK, INVALID_ARGUMENT_FORMAT, UNEXPECTED_ARGUMENT, INVALID_ARGUMENT_NAME, 
      MISSING_STRING, MISSING_INTEGER, INVALID_INTEGER, MISSING_DOUBLE, INVALID_DOUBLE
    }
  }
  ```
- 이 코드는 잘 짜여져 있음. 새로운 인자를 추가할 때 ArgumentMarshaler 구현체를 추가해서 getXXX 함수를 추가한 후 parseSchemaElement 함수에 새 case 문만 추가하면 끝. 필요한 경우 ArgsException.ErrorCode를 만들고 추가.

### How Did I Do This?

- 한번에 이렇게 깔끔한 코드를 작성하려고 하지 않았음. 먼저 돌아가는 더러운 코드를 작성하고 정리함.
- 처음에 대충 작성하고 계속 정리해가는 과정이 successive refinement.

## Args: The Rough Draft

- 1차 초안 Code : 돌아가지만 엉망임.
  ```java
  public class Args {
    private String schema;
    private String[] args;
    private boolean valid = true;
    private Set<Character> unexpectedArguments = new TreeSet<Character>(); 
    private Map<Character, Boolean> booleanArgs = new HashMap<Character, Boolean>();
    private Map<Character, String> stringArgs = new HashMap<Character, String>(); 
    private Map<Character, Integer> intArgs = new HashMap<Character, Integer>(); 
    private Set<Character> argsFound = new HashSet<Character>();
    private int currentArgument;
    private char errorArgumentId = '\0';
    private String errorParameter = "TILT";
    private ErrorCode errorCode = ErrorCode.OK;
    
    private enum ErrorCode {
      OK, MISSING_STRING, MISSING_INTEGER, INVALID_INTEGER, UNEXPECTED_ARGUMENT}
      
    public Args(String schema, String[] args) throws ParseException { 
      this.schema = schema;
      this.args = args;
      valid = parse();
    }
    
    private boolean parse() throws ParseException { 
      if (schema.length() == 0 && args.length == 0)
        return true; 
      parseSchema(); 
      try {
        parseArguments();
      } catch (ArgsException e) {
      }
      return valid;
    }
    
    private boolean parseSchema() throws ParseException { 
      for (String element : schema.split(",")) {
        if (element.length() > 0) {
          String trimmedElement = element.trim(); 
          parseSchemaElement(trimmedElement);
        } 
      }
      return true; 
    }
    
    private void parseSchemaElement(String element) throws ParseException { 
      char elementId = element.charAt(0);
      String elementTail = element.substring(1); 
      validateSchemaElementId(elementId);
      if (isBooleanSchemaElement(elementTail)) 
        parseBooleanSchemaElement(elementId);
      else if (isStringSchemaElement(elementTail)) 
        parseStringSchemaElement(elementId);
      else if (isIntegerSchemaElement(elementTail)) 
        parseIntegerSchemaElement(elementId);
      else
        throw new ParseException(String.format("Argument: %c has invalid format: %s.", 
          elementId, elementTail), 0);
      } 
    }
      
    private void validateSchemaElementId(char elementId) throws ParseException { 
      if (!Character.isLetter(elementId)) {
        throw new ParseException("Bad character:" + elementId + "in Args format: " + schema, 0);
      }
    }
    
    private void parseBooleanSchemaElement(char elementId) { 
      booleanArgs.put(elementId, false);
    }
    
    private void parseIntegerSchemaElement(char elementId) { 
      intArgs.put(elementId, 0);
    }
    
    private void parseStringSchemaElement(char elementId) { 
      stringArgs.put(elementId, "");
    }
    
    private boolean isStringSchemaElement(String elementTail) { 
      return elementTail.equals("*");
    }
    
    private boolean isBooleanSchemaElement(String elementTail) { 
      return elementTail.length() == 0;
    }
    
    private boolean isIntegerSchemaElement(String elementTail) { 
      return elementTail.equals("#");
    }
    
    private boolean parseArguments() throws ArgsException {
      for (currentArgument = 0; currentArgument < args.length; currentArgument++) {
        String arg = args[currentArgument];
        parseArgument(arg); 
      }
      return true; 
    }
    
    private void parseArgument(String arg) throws ArgsException { 
      if (arg.startsWith("-"))
        parseElements(arg); 
    }
    
    private void parseElements(String arg) throws ArgsException { 
      for (int i = 1; i < arg.length(); i++)
        parseElement(arg.charAt(i)); 
    }
    
    private void parseElement(char argChar) throws ArgsException { 
      if (setArgument(argChar))
        argsFound.add(argChar); 
      else 
        unexpectedArguments.add(argChar); 
        errorCode = ErrorCode.UNEXPECTED_ARGUMENT; 
        valid = false;
    }
    
    private boolean setArgument(char argChar) throws ArgsException { 
      if (isBooleanArg(argChar))
        setBooleanArg(argChar, true); 
      else if (isStringArg(argChar))
        setStringArg(argChar); 
      else if (isIntArg(argChar))
        setIntArg(argChar); 
      else
        return false;
      
      return true; 
    }
    
    private boolean isIntArg(char argChar) {
      return intArgs.containsKey(argChar);
    }
    
    private void setIntArg(char argChar) throws ArgsException { 
      currentArgument++;
      String parameter = null;
      try {
        parameter = args[currentArgument];
        intArgs.put(argChar, new Integer(parameter)); 
      } catch (ArrayIndexOutOfBoundsException e) {
        valid = false;
        errorArgumentId = argChar;
        errorCode = ErrorCode.MISSING_INTEGER;
        throw new ArgsException();
      } catch (NumberFormatException e) {
        valid = false;
        errorArgumentId = argChar; 
        errorParameter = parameter;
        errorCode = ErrorCode.INVALID_INTEGER; 
        throw new ArgsException();
      } 
    }
    
    private void setStringArg(char argChar) throws ArgsException { 
      currentArgument++;
      try {
        stringArgs.put(argChar, args[currentArgument]); 
      } catch (ArrayIndexOutOfBoundsException e) {
        valid = false;
        errorArgumentId = argChar;
        errorCode = ErrorCode.MISSING_STRING; 
        throw new ArgsException();
      } 
    }
    
    private boolean isStringArg(char argChar) { 
      return stringArgs.containsKey(argChar);
    }
    
    private void setBooleanArg(char argChar, boolean value) { 
      booleanArgs.put(argChar, value);
    }
    
    private boolean isBooleanArg(char argChar) { 
      return booleanArgs.containsKey(argChar);
    }
    
    public int cardinality() { 
      return argsFound.size();
    }
    
    public String usage() { 
      if (schema.length() > 0)
        return "-[" + schema + "]"; 
      else
        return ""; 
    }
    
    public String errorMessage() throws Exception { 
      switch (errorCode) {
        case OK:
          throw new Exception("TILT: Should not get here.");
        case UNEXPECTED_ARGUMENT:
          return unexpectedArgumentMessage();
        case MISSING_STRING:
          return String.format("Could not find string parameter for -%c.", errorArgumentId);
        case INVALID_INTEGER:
          return String.format("Argument -%c expects an integer but was '%s'.", errorArgumentId, errorParameter);
        case MISSING_INTEGER:
          return String.format("Could not find integer parameter for -%c.", errorArgumentId);
      }
      return ""; 
    }
    
    private String unexpectedArgumentMessage() {
      StringBuffer message = new StringBuffer("Argument(s) -"); 
      for (char c : unexpectedArguments) {
        message.append(c); 
      }
      message.append(" unexpected.");
      
      return message.toString(); 
    }
    
    private boolean falseIfNull(Boolean b) { 
      return b != null && b;
    }
    
    private int zeroIfNull(Integer i) { 
      return i == null ? 0 : i;
    }
    
    private String blankIfNull(String s) { 
      return s == null ? "" : s;
    }
    
    public String getString(char arg) { 
      return blankIfNull(stringArgs.get(arg));
    }
    
    public int getInt(char arg) {
      return zeroIfNull(intArgs.get(arg));
    }
    
    public boolean getBoolean(char arg) { 
      return falseIfNull(booleanArgs.get(arg));
    }
    
    public boolean has(char arg) { 
      return argsFound.contains(arg);
    }
    
    public boolean isValid() { 
      return valid;
    }
    
    private class ArgsException extends Exception {
    } 
  }
  ```
- 나름대로 구조를 갖추려고 했지만 조잡하고 인자 타입이 추가될 때 마다 더 개판됨.

### So I Stopped

- 인자 타입이 추가될때마다 개판되는걸 알아서 리팩토링을 결심.
- 인자가 추가되면 1. HashMap, 2. parser, 3. setXXX, getXXX 의 3개를 변경해야 함. 이걸 ArgumentMarshaler 라는 class로 빼보자!

### On Incrementalism

- 프로그램을 망치는 최고의 방법중 하나는 개선이라는 이름으로 큰 변화를 가하는 것. 개선 전이랑 동일하게 동작한다고 보장할 수 없음.
- 그래서 TDD를 도입해서 acceptance 테스트를 추가하고 리팩토링을 시작.
- ArgumentMarshaler Class 추가
  ```java
  // ArgumentMarshaler class 추가
  private class ArgumentMarshaler { 
    private boolean booleanValue = false;

    public void setBoolean(boolean value) { 
      booleanValue = value;
    }
    
    public boolean getBoolean() {return booleanValue;} 
  }

  private class BooleanArgumentMarshaler extends ArgumentMarshaler { }
  private class StringArgumentMarshaler extends ArgumentMarshaler { }
  private class IntegerArgumentMarshaler extends ArgumentMarshaler { }
  ```
- BooleanArgumentMarshaler Class 추가
  ```java
  // 1. change map value
  private Map<Character, ArgumentMarshaler> booleanArgs  = new HashMap<Character, ArgumentMarshaler>();
  ```
  ```java
  // 2. change parse method
  private void parseBooleanSchemaElement(char elementId) {
    stringArgs.put(elementId, new BooleanArgumentMarshaler());());
  }

  // 3. change set function
  private void setBooleanArg(char argChar, boolean value) {
    stringArgs.get(argChar).setBoolean(value);
  }

  // 3. change get function
  public boolean getBoolean(char arg) {
    Args.ArgumentMarshaler am = booleanArgs.get(arg);
    return am != null && am.getBoolean();
  }
  ```

## String Arguments

- StringArgumentMarshaler Class 추가
  ```java
  // ArgumentMarshaler class 변경
  private class ArgumentMarshaler { 
    private boolean booleanValue = false;
    private String stringValue;

    public void setBoolean(boolean value) { 
      booleanValue = value;
    }
    
    public boolean getBoolean() {
      return booleanValue;
    } 

    public void setString(String s) {
      stringValue = s;
    }

    public String getString() {
      return stringValue == null ? "" : stringValue;
    }

    private class BooleanArgumentMarshaler extends ArgumentMarshaler { }
    private class StringArgumentMarshaler extends ArgumentMarshaler { }
    private class IntegerArgumentMarshaler extends ArgumentMarshaler { }
  }
  ```
  ```java
  // 1. change map value
  private Map<Character, ArgumentMarshaler> stringArgs = new HashMap<Character, ArgumentMarshaler>();
  ```
  ```java
  // 2. change parse method
  private void parseStringSchemaElement(char elementId) {
    booleanArgs.put(elementId, new StringArgumentMarshaler());
  }

  // 3. change set function
  private void setStringArg(char argChar, boolean value) {
    currentArgument++;
    try {
      stringArgs.get(argChar).setString(args[currentArgument]);
    } catch (ArrayIndexOutOfBoundsException e) {
      valid = false;
      errorArgumentId = argChar;
      errorCode = ErrorCode.MISSING_STRING;
      throw new ArgsException();
    }
  }

  // 3. change get function
  public boolean getString(char arg) {
    Args.ArgumentMarshaler am = stringArgs.get(arg);
    return am != null ? am.getString() : "";
  }
  ```
- ArgumentMarshaler base class 에 모든 것을 넣어준걸 derivatives에 옮김.
  ```java
  private abstract class ArgumentMarshaler {
    public abstract void set(String s);

    public abstract Object get();
  }

  private class BooleanArgumentMarshaler extends ArgumentMarshaler {
    private boolean booleanValue = false;

    public void set(String s) {
      booleanValue = true;
    }

    public Object get() {
      return booleanValue;
    }
  }

  private class StringArgumentMarshaler extends ArgumentMarshaler {
    private String stringValue = "";

    public void set(String s) {
      stringValue = s;
    }

    public Object get() {
      return stringValue;
    }
  }

  private class IntegerArgumentMarshaler extends ArgumentMarshaler {
    private int intValue = 0;

    public void set(String s) throws ArgsException {
      try {
        intValue = Integer.parseInt(s);
      } catch (NumberFormatException e) {
        throw new ArgsException();
      }
    }

    public Object get() {
      return intValue;
    }
  }
  ```
- 최종 코드
  ```java
  public class Args {
    private String schema;
    private Map<Character, ArgumentMarshaler> marshalers = new HashMap<Character, ArgumentMarshaler>();
    private Set<Character> argsFound = new HashSet<Character>(); 
    private Iterator<String> currentArgument;
    private List<String> argsList;
    
    public Args(String schema, String[] args) throws ArgsException { 
      this.schema = schema;
      argsList = Arrays.asList(args);
      parse();
    }
    
    private void parse() throws ArgsException { 
      parseSchema();
      parseArguments();
    }
    
    private boolean parseSchema() throws ArgsException {
      for (String element : schema.split(",")) { 
        if (element.length() > 0) {
          parseSchemaElement(element.trim()); 
        }
      }
      return true; 
    }
    
    private void parseSchemaElement(String element) throws ArgsException { 
      char elementId = element.charAt(0);
      String elementTail = element.substring(1); 
      validateSchemaElementId(elementId);
      if (elementTail.length() == 0)
        marshalers.put(elementId, new BooleanArgumentMarshaler());
      else if (elementTail.equals("*")) 
        marshalers.put(elementId, new StringArgumentMarshaler());
      else if (elementTail.equals("#"))
        marshalers.put(elementId, new IntegerArgumentMarshaler());
      else if (elementTail.equals("##")) 
        marshalers.put(elementId, new DoubleArgumentMarshaler());
      else
        throw new ArgsException(ArgsException.ErrorCode.INVALID_FORMAT, elementId, elementTail);
        
    private void validateSchemaElementId(char elementId) throws ArgsException { 
      if (!Character.isLetter(elementId)) {
        throw new ArgsException(ArgsException.ErrorCode.INVALID_ARGUMENT_NAME, elementId, null);
      } 
    }
    
    private void parseArguments() throws ArgsException {
      for (currentArgument = argsList.iterator(); currentArgument.hasNext();) {
        String arg = currentArgument.next();
        parseArgument(arg); 
      }
    }
    
    private void parseArgument(String arg) throws ArgsException { 
      if (arg.startsWith("-"))
        parseElements(arg); 
    }
    
    private void parseElements(String arg) throws ArgsException { 
      for (int i = 1; i < arg.length(); i++)
        parseElement(arg.charAt(i)); 
    }
    
    private void parseElement(char argChar) throws ArgsException { 
      if (setArgument(argChar))
        argsFound.add(argChar); 
      else 
        throw new ArgsException(ArgsException.ErrorCode.UNEXPECTED_ARGUMENT, argChar, null);
    } 
    
    private boolean setArgument(char argChar) throws ArgsException { 
      ArgumentMarshaler m = marshalers.get(argChar);
      if (m == null)
        return false; 
      try {
        m.set(currentArgument);
        return true;
      } catch (ArgsException e) {
        e.setErrorArgumentId(argChar);
        throw e; 
      }
    }
    
    public int cardinality() { 
      return argsFound.size();
    }
    
    public String usage() { 
      if (schema.length() > 0)
        return "-[" + schema + "]"; 
      else
        return ""; 
    }
    
    public boolean getBoolean(char arg) { 
      ArgumentMarshaler am = marshalers.get(arg); 
      boolean b = false;
      try {
        b = am != null && (Boolean) am.get(); 
      } catch (ClassCastException e) {
        b = false; 
      }
      return b; 
    }
    
    public String getString(char arg) { 
      ArgumentMarshaler am = marshalers.get(arg); 
      try {
        return am == null ? "" : (String) am.get(); 
      } catch (ClassCastException e) {
        return ""; 
      }
    }
    
    public int getInt(char arg) { 
      ArgumentMarshaler am = marshalers.get(arg); 
      try {
        return am == null ? 0 : (Integer) am.get(); 
      } catch (Exception e) {
        return 0; 
      }
    }
    
    public double getDouble(char arg) { 
      ArgumentMarshaler am = marshalers.get(arg); 
      try {
        return am == null ? 0 : (Double) am.get(); 
      } catch (Exception e) {
        return 0.0; 
      }
    }
    
    public boolean has(char arg) { 
      return argsFound.contains(arg);
    } 
  }
  ```
  ```java
  public class ArgsTest extends TestCase {
    public void testCreateWithNoSchemaOrArguments() throws Exception {
      Args args = new Args("", new String[0]);
      assertEquals(0, args.cardinality());
    }

    public void testWithNoSchemaButWithOneArgument() throws Exception {
      try {
        new Args("", new String[]{"-x"});
        fail();
      } catch (ArgsException e) {
        assertEquals(ArgsException.ErrorCode.UNEXPECTED_ARGUMENT,
            e.getErrorCode());
        assertEquals('x', e.getErrorArgumentId());
      }
    }

    public void testWithNoSchemaButWithMultipleArguments() throws Exception {
      try {
        new Args("", new String[]{"-x", "-y"});
        fail();
      } catch (ArgsException e) {
        assertEquals(ArgsException.ErrorCode.UNEXPECTED_ARGUMENT,
            e.getErrorCode());
        assertEquals('x', e.getErrorArgumentId());
      }
    }

    public void testNonLetterSchema() throws Exception {
      try {
        new Args("*", new String[]{});
        fail("Args constructor should have thrown exception");
      } catch (ArgsException e) {
        assertEquals(ArgsException.ErrorCode.INVALID_ARGUMENT_NAME,
            e.getErrorCode());
        assertEquals('*', e.getErrorArgumentId());
      }
    }

    public void testInvalidArgumentFormat() throws Exception {
      try {
        new Args("f~", new String[]{});
        fail("Args constructor should have throws exception");
      } catch (ArgsException e) {
        assertEquals(ArgsException.ErrorCode.INVALID_FORMAT, e.getErrorCode());
        assertEquals('f', e.getErrorArgumentId());
      }
    }

    public void testSimpleBooleanPresent() throws Exception {
      Args args = new Args("x", new String[]{"-x"});
      assertEquals(1, args.cardinality());
      assertEquals(true, args.getBoolean('x'));
    }

    public void testSimpleStringPresent() throws Exception {
      Args args = new Args("x*", new String[]{"-x", "param"});
      assertEquals(1, args.cardinality());
      assertTrue(args.has('x'));
      assertEquals("param", args.getString('x'));
    }

    public void testMissingStringArgument() throws Exception {
      try {
        new Args("x*", new String[]{"-x"});
        fail();
      } catch (ArgsException e) {
        assertEquals(ArgsException.ErrorCode.MISSING_STRING, e.getErrorCode());
        assertEquals('x', e.getErrorArgumentId());
      }
    }

    public void testSpacesInFormat() throws Exception {
      Args args = new Args("x, y", new String[]{"-xy"});
      assertEquals(2, args.cardinality());
      assertTrue(args.has('x'));
      assertTrue(args.has('y'));
    }

    public void testSimpleIntPresent() throws Exception {
      Args args = new Args("x#", new String[]{"-x", "42"});
      assertEquals(1, args.cardinality());
      assertTrue(args.has('x'));
      assertEquals(42, args.getInt('x'));
    }

    public void testInvalidInteger() throws Exception {
      try {
        new Args("x#", new String[]{"-x", "Forty two"});
        fail();
      } catch (ArgsException e) {
        assertEquals(ArgsException.ErrorCode.INVALID_INTEGER, e.getErrorCode());
        assertEquals('x', e.getErrorArgumentId());
        assertEquals("Forty two", e.getErrorParameter());
      }
    }

    public void testMissingInteger() throws Exception {
      try {
        new Args("x#", new String[]{"-x"});
        fail();
      } catch (ArgsException e) {
        assertEquals(ArgsException.ErrorCode.MISSING_INTEGER, e.getErrorCode());
        assertEquals('x', e.getErrorArgumentId());
      }
    }

    public void testSimpleDoublePresent() throws Exception {
      Args args = new Args("x##", new String[]{"-x", "42.3"});
      assertEquals(1, args.cardinality());
      assertTrue(args.has('x'));
      assertEquals(42.3, args.getDouble('x'), .001);
    }

    public void testInvalidDouble() throws Exception {
      try {
        new Args("x##", new String[]{"-x", "Forty two"});
        fail();
      } catch (ArgsException e) {
        assertEquals(ArgsException.ErrorCode.INVALID_DOUBLE, e.getErrorCode());
        assertEquals('x', e.getErrorArgumentId());
        assertEquals("Forty two", e.getErrorParameter());
      }
    }

    public void testMissingDouble() throws Exception {
      try {
        new Args("x##", new String[]{"-x"});
        fail();
      } catch (ArgsException e) {
        assertEquals(ArgsException.ErrorCode.MISSING_DOUBLE, e.getErrorCode());
        assertEquals('x', e.getErrorArgumentId());
      }
    }
  }
  ```
- 관심사 분리는 코드를 더 깔끔하고 유지보수가 쉽게 만듦.

## Conclusion

- 코드가 돌아만 가서는 안됨. 나쁜 코드는 장기적으로 더 안좋은 영향을 끼침.
- 코드를 단순하고 깔끔하게 유지해라. 썩지 않게 해라.