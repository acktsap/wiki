# 8. Boundaries

- [8. Boundaries](#8-boundaries)
  - [Introduction](#introduction)
  - [Using Third-Party Code](#using-third-party-code)
  - [Exploring and Learning Boundaries](#exploring-and-learning-boundaries)
  - [Learning log4j](#learning-log4j)
  - [Learning Tests Are Better Then Free](#learning-tests-are-better-then-free)
  - [Using Code That Does Not Yet Exists](#using-code-that-does-not-yet-exists)
  - [Clean Boundaries](#clean-boundaries)

## Introduction

- 개발할 때 가끔은 3rd party library나 framework 같은 것에 의존한다. 이 때 우리가 개발하는 코드와 3rd party의 boundaries를 잘 나누는 것이 중요함.

## Using Third-Party Code

- Third-Party Code를 제공해주는 쪽은 보다 범용적인 타겟이 대상이지만 User들은 자기네 시스템에 딱 맞는걸 원함. 이것이 문제를 야기.
- Java의 Map의 경우 여러 api를 제공해서 그대로 쓸 경우 문제가 될 수가 있음.
  - 사례 1. generic이 없을 때 Map에서 구체적인 값을 가져오려면 직접 casting을 했어야 했음.
  - 사례 2. 사용자는 `map.clear`라는 기능이 필요 없는데 그대로 사용하게 되면 이게 clear할 수 있어보임.
- 솔루션은 Map (3rd party)를 사용하는 쪽에서 3rd party의 상세 구현을 감추는 것. 가능하면 이런거는 return하거나 인자에 넣지 않는게 좋음.
  ```java
  // good
  public class Sensors {
    private Map sensors = new HashMap();

    public Sensor getById(String id) {
      return (Sensor) sensors.get(id);
    }
  }
  ```

## Exploring and Learning Boundaries

- Third-party code는 다양한 기능을 제공해주지만 처음에 배우기 어려울 수 있음.
- 이 때 3rd party code를 이해하기 위한 테스트를 작성해 보는 것은 어떨까? (learning tests)

## Learning log4j

- Learning Tests의 과정 (개발중인 프로그램하고 독립적으로 테스트)
  ```java
  // hello를 출력할거 같지만 Appender가 필요하다고 에러를 뱉음
  @Test
  public void testLogCreate() {
    Logger logger = Logger.getLogger("MyLogger");
    logger.info("hello");
  }

  // Appender를 추가해서 뭔가 할거 같지만 여전히 출력은 없음
  @Test
  public void testLogAddAppender() {
    Logger logger = Logger.getLogger("MyLogger");
    ConsoleAppender appender = new ConsoleAppender();
    logger.addAppender(appender);
    logger.info("hello");
  }

  // OutputStream이 없어서 그랬던걸 확인하고 추가 : 잘나옴
  @Test
  public void testLogAddAppenderWithStream() {
    Logger logger = Logger.getLogger("MyLogger");
    logger.removeAllAppenders();
    logger.addAppender(new ConsoleAppender(
      new PatternLayout("%p %t %m%n"),
      ConsoleAppender.SYSTEM_OUT));
    logger.info("hello");
  }

  // 그런데? OutputStream을 넣지 않아도 결과가 나옴
  @Test
  public void testLogAddAppenderWithoutStream() {
    Logger logger = Logger.getLogger("MyLogger");
    logger.removeAllAppenders();
    logger.addAppender(new ConsoleAppender(
      new PatternLayout("%p %t %m%n"));
    logger.info("hello");
  }
  ```

> 이게 뭐하는 짓이지? 문서 보면 안되나? 문서랑 잘 짜여인 라이브러리라면 javadoc같은거에 있을거인데.\
> 아니면 어찌 보면 내가 패턴을 정리하는 방식과 비슷한거 같기도..

## Learning Tests Are Better Then Free

- Learning Test는 비용이 별로 안든다.
- Learning Test를 하면 해당 library에 새 기능이 출시되었을때 기존 api가 변화했는지도 알 수 있음.

> 삽질하는 비용이 들거같은데? Regression Test에는 좋긴하겠네

## Using Code That Does Not Yet Exists

- 지금 내가 모르는 Api에 의존하고 있으면 그것에 대한 Boundaries를 만들면 됨.
  - eg. Communication Controller class를 작성해야 하는데 아직 모르는 Transmitter API를 써야 하는 경우
    ```java
    // Boundary를 위한 interface
    interface Transmitter {
      void transmit();
    }

    class FakeTransmitter implements Transmitter {
      @Override
      void transmit() {
        // do something to mock api 
      }
    }

    class TramsmitterAdapter implements Transmitter {
      private TransmitterApi transmitterApi; // real api

      @Override
      void transmit() {
        // do something using transmitterApi
      }
    }

    class CommunicationController {
      // 실제 구현체를 아직 모를때는 Mocking해서 사용
      private Transmitter transmitter = new FakeTransmitter();

      // 실제 api가 들어오면 이렇게 바꿔치기 하면 됨
      // private Transmitter transmitter = new TramsmitterAdapter();
    }
    ```

## Clean Boundaries

- Boundaries를 잘 설정하지 않으면 변화가 생겼을 때 많은 부분의 코드를 바꿔야 할 수도 있음. 그런 경우에는 리팩토링을 해라. 
- Boundaries에 있는 code가 제공하는 api는 3rd party와 분리가 잘 되어 있어야 하고 3rd party에 의존적이지 않은 test를 작성해야 함.

---

> Boundaries를 설정하라는 말은 알겠는데 Learning Test는 regression test에서 좋다는거 말고는 잘 모르겠음.