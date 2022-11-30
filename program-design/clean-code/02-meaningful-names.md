# 2. Meaningful Names

- [2. Meaningful Names](#2-meaningful-names)
  - [Introduction](#introduction)
  - [Use Intention-Revealing Names](#use-intention-revealing-names)
  - [Avoid Disinformation](#avoid-disinformation)
  - [Make Meaningful Distinctions](#make-meaningful-distinctions)
  - [Use Pronounceable Names](#use-pronounceable-names)
  - [Avoid Encodings](#avoid-encodings)
    - [Hungarian Notation](#hungarian-notation)
    - [Member Prefixes](#member-prefixes)
    - [Interfaces and Implementations](#interfaces-and-implementations)
  - [Avoid Mental Mapping](#avoid-mental-mapping)
  - [Class Names](#class-names)
  - [Method Names](#method-names)
  - [Don’t Be Cute](#dont-be-cute)
  - [Pick One Word per Concept](#pick-one-word-per-concept)
  - [Don't Pun (말장난)](#dont-pun-말장난)
  - [Use Solution Domain Names](#use-solution-domain-names)
  - [Use Problem Domain Names](#use-problem-domain-names)
  - [Add Meaningful Context](#add-meaningful-context)
  - [Don’t Add Gratuitous Context (불필요한 맥락)](#dont-add-gratuitous-context-불필요한-맥락)
  - [Final Words](#final-words)

## Introduction

- 개발하는 동안 네이밍을 너무 많이 해서 잘 할 필요가 있음.
- 궁서체로 쓴 **진심**임.

## Use Intention-Revealing Names

- 의도를 잘 나타내는 이름을 짓는 것은 시간이 걸리는 일이지만 그만한 효용이 있음.
- It should tell you
  - why it exists
  - what it does
  - how it is used.
- 주석이 필요한 이름이라면 이미 쓰레기.
  ```java
  // bad
  int d; // elapsed time in day

  // good
  int elapsedTimeInDays;
  ```
- 내재된 의미가 적은 코드는 보기 어렵다.
  ```java
  // bad
  public List<int[]> getThem() {
    List<int[]> list1 = new ArrayList<int[]>();
    for (int[] x : theList) // what is 'theList'?
      if (x[0] == 4) // what meaning of '4'? what is x[0]?
        list1.add(x);
    return list1; // what list is returned?
  }

  // good
  static final int STATUS_VALUE = 0;
  static final int FLAGGED = 4;
  public List<int[]> getFlaggedCells() {
    List<int[]> flaggedCells = new ArrayList<int[]>();
    for (int[] cell : gameBoard)
      if (cell[STATUS_VALUE] == FLAGGED)
        flaggedCells.add(cell);
    return flaggedCells;
  }
  ```

## Avoid Disinformation

- 이름이 충분한 정보를 담고 있어야 함.
- 약자는 모호할 수 있음.
  ```java
  // bad
  int bpCount;
  
  // good
  int blockProducerCount;
  ```
- postfix 이상하게 쓰지마.
  ```java
  // bad
  Set<String> accountList; // 'List' has meaning for programmer.
  
  // good
  List<String> accounts;
  ```
- 이름은 일관성 있게 사용해. 그래야 코드에 예측성이 더해짐.
- 실제로 이런 느낌의 코드도 있었음. 구분을 위해 특정 폰트를 사용하라고 가이드가 있었음. 이렇게 하지마.
  ```java
  // bad
  int a = l;
  int l = 2;
  int O = 3;

  if ( O == l )
    a = O1;
  else
    l = 01;
  ```
  
## Make Meaningful Distinctions

- 한 스코프 내에서 같은 이름의 변수를 못쓴다고 noise word만 사용해서 두개의 이름을 대충 짓지마.
  ```java
  // bad
  public static void copyChars(char a1[], char a2[]) {
    for (int i = 0; i < a1.length; i++) {
      a2[i] = a1[i];
    }
  }

  // good
  public static void copyChars(char source[], char destination[]) {
    for (int i = 0; i < source.length; i++) {
      destination[i] = source[i];
    }
  }
  ```
- 타입이나 특성에 이미 의미가 있는거는 중복해서 사용하지말것. (Avoid redundant name)
  ```java
  // bad
  int variable; // already variable
  String nameString; // already string
  ```
- 그냥 구분이 잘되게 이름지을것.
  ```java
  // convension 없다면 차이를 알기 힘듬
  getActiveAccount();
  getActiveAccounts();
  getActiveAccountInfo();
  ```

## Use Pronounceable Names

- 인간의 뇌의 상당수가 말하는 언어를 위해 진화되었음. 발음할 수 있게 해라.
- programming is a social activity.
  ```java
  // bad
  class DtaRcrd102 {
    private Date genymdhms; // gen why emm dee aich emm ess????
    private Date modymdhms;
    private final String pszqint = "102";
  };

  // bood
  class Customer {
    private Date generationTimestamp; // clear
    private Date modificationTimestamp;;
    private final String recordId = "102";
  };
  ```

## Avoid Encodings

- 괜한 encoding은 신규입사자한테 배워야 할 짐만 더 추가함.

### Hungarian Notation

- 헝가리안 표기법 : 변수 및 함수의 인자 앞에 데이터 타입을 명시하는 코딩 규칙
- integer, long pointer handle 등이 주였던 Windows C API에서 특히 중요했음. 사용 안하면 기억하기 힘들어서.
- But 현대 언어에서는 type system이 훨씬 강력하므로 그런 짓 안해도 됨.

### Member Prefixes

- `m_` prefix 쓰지마. 그걸 안써도 될 정도도 모듈이 작아야 함.
  ```java
  // bad
  public class Part {
    private String m_dsc;
    void setName(String name) {
      m_dsc = name;
    }
  }

  // good
  public class Part {
    String description;
    void setDescription(String description) {
      this.description = description;
    }
  }
  ```

### Interfaces and Implementations

- `I` prefix는 너무 과한 정보임.
  ```java
  // bad
  interface IShapeFactory

  // good
  interface ShapeFactory
  ```

## Avoid Mental Mapping

- 의식의 흐름대로 이름을 짓지 말것. 특히 i 같은 약자는 전통적인 loop naming이 아니라면 짓지 말 것.
- 명료함한 코드가 최고.

## Class Names

- 명사형으로 짓자.
  - good : Customer, WikiPage, Account, AddressParser
  - bad : Manager, Processor, Data -> 명료하지 않음

## Method Names

- 동사형으로 짓자
  - good : postPayment, deletePage, save
- java 기준
  - accessor, mutator, predicate는 `get`, `set`, `is`으로.
  - constructor가 overloaded된 경우 static factory method를 사용해서 인자의 의미를 명료하게. 이 경우 constructor는 private으로 해서 static factory method를 강제로 사용하게 하라.
    ```java
    // bad
    Complex fulcrumPoint = new Complex(23.0);

    // good
    Complex fulcrumPoint = Complex.FromRealNumber(23.0);
    ```

## Don’t Be Cute

- 일부만 알아들을 수 있는 조크로 이름짓지마.
  - bad : HolyHandGrenade, eatMyShorts
  - good : DeleteItems, abort

## Pick One Word per Concept

- 하나의 개념에 한개의 단어를 선택하고 이를 계속 사용해라.
  - bad : fetch, retrieve, get 혼용해서 사용

## Don't Pun (말장난)

- 하나의 단어를 두개 이상의 목적으로 사용하지 말라.
  - eg. 기존 값 두 개를 더해서 새로운 값 생성을 하는 걸 add로 썼다가 list.add처럼 ㄴㄴ. insert/append가 맞음

## Use Solution Domain Names

- 당신의 코드를 읽는 사람은 프로그래머임. cs terms를 사용하는거는 문제 없음.
  - good : AccountVisitor (visitor pattern)

## Use Problem Domain Names

- problem domain관련이 더 많은 코드는 problem domain name을 사용.

> eg. 서비스 회사

## Add Meaningful Context

- 자체적으로 의미 있는 이름들은 많지 않음. 아닌 경우 그 경우 이름을 특정한 context에다가 넣으면 (eg. 의미 있는 클래스 이름 안에)이해하기 쉬움. Prefix를 붙일 수도 있지만 이는 최후의 방법임.
  ```java
  // bad
  String firstName;
  String lastName;
  String street;

  // good
  class Address {
    String firstName;
    String lastName;
    String street;
  }

  // not bad
  String addrFirstName;
  String addrLastName;
  String addrStreet;
  ```
  ```java
  // bad
  private void printGuessStatistics(char candidate, int count) {
    String number;
    String verb;
    String pluralModifier;

    if (count == 0) {
      number = "no";
      verb = "are";
      pluralModifier = "s";
    } else if (count == 1) {
      number = "1";
      verb = "is";
      pluralModifier = "";
    } else {
      number = Integer.toString(count);
      verb = "are";
      pluralModifier = "s";
    }

    String guessMessage = String.format(
      "There %s %s %s%s", verb, number, candidate, pluralModifier
    );
    print(guessMessage);
  }

  // good (introduce class)
  private void printGuessStatistics(char candidate, int count) {
    class GuessStatisticsMessage {
      String number;
      String verb;
      String pluralModifier;

      String toString() {
        return String.format(
          "There %s %s %s%s", verb, number, candidate, pluralModifier
        );
      }
    }

    GuessStatisticsMessage message = new GuessStatisticsMessage();
    if (count == 0) {
      message.number = "no";
      message.verb = "are";
      message.pluralModifier = "s";
    } else if (count == 1) {
      message.number = "1";
      message.verb = "is";
      message.pluralModifier = "";
    } else {
      message.number = Integer.toString(count);
      message.verb = "are";
      message.pluralModifier = "s";
    }

    print(message.toString());
  }

  // good (breaking it into many smaller functions)
  public class GuessStatisticsMessage {
    private String number;
    private String verb;
    private String pluralModifier;

    public String make(char candidate, int count) {
      createPluralDependentMessageParts(count);
      return String.format(
        "There %s %s %s%s", verb, number, candidate, pluralModifier );
    }

    private void createPluralDependentMessageParts(int count) {
      if (count == 0) {
        thereAreNoLetters();
      } else if (count == 1) {
        thereIsOneLetter();
      } else {
        thereAreManyLetters(count);
      }
    }

    private void thereAreManyLetters(int count) {
      number = Integer.toString(count);
      verb = "are";
      pluralModifier = "s";
    }

    private void thereIsOneLetter() {
      number = "1";
      verb = "is";
      pluralModifier = "";
    }

    private void thereAreNoLetters() {
      number = "no";
      verb = "are";
      pluralModifier = "s";
    }
  }
  ```

## Don’t Add Gratuitous Context (불필요한 맥락)

- Context의 경우 짧은 이름으로도 명확하다면 더 길게 할 필요 없음.
  - bad : MailingAddress, GSDAccountAddress
  - good : Address


## Final Words

- 이미 있는 이름 바꿔도 그게 더 좋은 방향으로 바뀌면 아무도 뭐라고 안함. 바꾸셈.
