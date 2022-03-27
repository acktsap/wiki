# 12. Emergence

- [12. Emergence](#12-emergence)
  - [Getting Clean via Emergent Design](#getting-clean-via-emergent-design)
  - [Simple Design Rule 1: Runs All the Tests](#simple-design-rule-1-runs-all-the-tests)
  - [Simple Design Rules 2–4: Refactoring](#simple-design-rules-24-refactoring)
  - [No Duplication](#no-duplication)
  - [Expressive](#expressive)
  - [Minimal Classes and Methods](#minimal-classes-and-methods)
  - [Conclusion](#conclusion)

## Getting Clean via Emergent Design

- Kent beck에 따르면 다음의 규칙을 따르면 design은 simple함
  - Runs all the tests
  - Contains no duplication
  - Expresses the intent of the programmer
  - Minimizes the number of classes and methods

## Simple Design Rule 1: Runs All the Tests

- 테스트 할 수 없는 시스템은 검증할 수 없음.
- 응집도가 높고 결합도가 낮은 코드일수록 테스트 하기 쉬움. 
- 그래서 테스트를 짜는 행위 자체가 좋은 디자인을 하는데 영향을 줌.

## Simple Design Rules 2–4: Refactoring

- 코드를 처음에 짜고 난 후에도 Refactoring을 통해 개선해야 함.
- 테스트를 짜면 Refactoring 전 후 버그가 생겼는지 검증할 수 있음.

## No Duplication

- 코드 중복은 좋은 디자인의 적임. 제거해라.
  ```java
  // bad
  public void scaleToOneDimension(float desiredDimension, float imageDimension) {
    if (Math.abs(desiredDimension - imageDimension) < errorThreshold) {
      return;
    }

    float scalingFactor = desiredDimension / imageDimension;
    scalingFactor = (float)(Math.floor(scalingFactor * 100) * 0.01f);
    RenderedOp newImage = ImageUtilities.getScaledImage(image, scalingFactor, scalingFactor);
    image.dispose(); // 코드 중복
    System.gc();
    image = newImage;
  }

  public synchronized void rotate(int degrees) {
    RenderedOp newImage = ImageUtilities.getRotatedImage(image, degrees);
    image.dispose(); // 코드 중복
    System.gc();
    image = newImage;
  }

  // good
  public void scaleToOneDimension(float desiredDimension, float imageDimension) {
    if (Math.abs(desiredDimension - imageDimension) < errorThreshold)
    return;
    float scalingFactor = desiredDimension / imageDimension;
    scalingFactor = (float)(Math.floor(scalingFactor * 100) * 0.01f);

    replaceImage(ImageUtilities.getScaledImage(image, scalingFactor, scalingFactor));
  }

  public synchronized void rotate(int degrees) {
    replaceImage(ImageUtilities.getRotatedImage(image, degrees));
  }

  private void replaceImage(RenderedOp newImage) {
    image.dispose(); // 중복코드 method로 뺌
    System.gc();
    image = newImage;
  }
  ```
- 중복을 계속 제거하다 보면 비슷한 코드가 여러 클래스에 생겨서 SRP를 깨는 경우가 생김. 이럴때 다른 class로 옮겨.
- 구현에도 중복체가 나타날 수 있음.
  ```java
  // bad : 구현체가 둘다 구현해야 함
  interface Test {
    int size();

    boolean isEmpty();
  }

  // good : 구현체가 size만 구현하면 됨
  interface Test {
    int size();

    default boolean isEmpty() {
      return 0 == size();
    }
  }
  ```
- Template method pattern은 high-level 코드 중복을 제거하는 좋은 방법임.
  ```java
  // bad : 비슷한 코드를 중복해서 사용
  public class VacationPolicy {
    public void accrueUSDivisionVacation() {
      // code to calculate vacation based on hours worked to date
      // ...
      // code to ensure vacation meets US minimums
      // ...
      // code to apply vaction to payroll record
      // ...
    }

    public void accrueEUDivisionVacation() {
      // code to calculate vacation based on hours worked to date
      // ...
      // code to ensure vacation meets EU minimums
      // ...
      // code to apply vaction to payroll record
      // ...
    }
  }

  // good : 다른 부분만 abstract method로 두기
  abstract public class VacationPolicy {
    public void accrueVacation() {
      calculateBaseVacationHours();
      alterForLegalMinimums();
      applyToPayroll();
    }

    abstract protected void alterForLegalMinimums();
    private void calculateBaseVacationHours() { /* ... */ };
    private void applyToPayroll() { /* ... */ };
  }

  public class USVacationPolicy extends VacationPolicy {
    @Override
    protected void alterForLegalMinimums() {
      // US specific logic
    }
  }

  public class EUVacationPolicy extends VacationPolicy {
    @Override
    protected void alterForLegalMinimums() {
      // EU specific logic
    }
  }
  ```

## Expressive

- 유지보수가 소프트웨어에서 큰 비용임.
- 코드를 통해 해결하고자 하는 문제를 모르는 사람도 잘 이해할 수 있는 코드를 짜야 함.
- 몇가지 방법
  - 이해하기 쉬운 이름을 짓는다.
  - method나 class를 최소화 한다.
  - Design pattern 이름 등 몇가지 알려진 이름을 사용한다.
  - 테스트의 의도를 명확히 한다. 좋은 테스트는 곧 코드에 대한 좋은 문서다.
- 표현력이 충분한 코드를 짜기 위해 시간을 써라. 높은 확률로 너 자신이 그 코드를 다시봐야 함.

## Minimal Classes and Methods

- 중복 제거를 너무 고집하다 보면 너무 많은 class나 method가 생길 수 있음.
- 쓸데없는 코드 원칙이 너무 많은 class나 method를 만들 수 있음.
  - eg. 모든 class에 interface를 붙여라.
- 물론 class나 method를 최소한으로 줄이라는 것은 앞에 말한 3개보다 상대적으로 덜 중요하긴 함.

## Conclusion

- 이 장에서 이야기한 원칙들을 실천하려고 하면 좋은 디자인에 점점 다가가게 될 것.
