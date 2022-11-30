# 6. Objects and Data Structures

- [6. Objects and Data Structures](#6-objects-and-data-structures)
  - [Introduction](#introduction)
  - [Data Abstraction](#data-abstraction)
  - [Data/Object Anti-Symmetry](#dataobject-anti-symmetry)
  - [The Law of Demeter](#the-law-of-demeter)
    - [Train Wrecks](#train-wrecks)
    - [Hybrids](#hybrids)
    - [Hiding Structure](#hiding-structure)
  - [Data Transfer Objects](#data-transfer-objects)
    - [Active Record](#active-record)
  - [Conclusion](#conclusion)

## Introduction

- variable을 private으로 하면 좋음. 사용자는 구현을 알 필요 없음.

## Data Abstraction

- 구현을 숨기는 것은 함수 사이에 layer를 넣는게 아니라 추상화를 한다는 개념임.
  ```java
  // bad : 구현을 드러냄
  public class Point {
    public double x;
    public double y;
  }

  // good : Point에 대한 추상화. 구현 안보임.
  public interface Point {
    double getX();
    double getY();
    void setCartesian(double x, double y);
    double getR();
    double getTheta();
    void setPolar(double r, double theta);
  }
  ```
- 이 둘은 같은 Vehicle이지만 서로 다른 것을 추상화 함. 단순히 getter, setter 만 넣는다고 이런게 되는게 아님. 표현하려는 object에 대한 깊은 이해가 필요. 
  ```java
  public interface Vehicle {
    double getFuelTankCapacityInGallons();
    double getGallonsOfGasoline();
  }

  public interface Vehicle {
    double getPercentFuelRemaining();
  }
  ```

> 코드를 짜는 것은 하나의 세상을 창조하는 것.

## Data/Object Anti-Symmetry

- data structure (Procedural code) : 구현을 노출시키고 추상화 단계에서는 무의미한 method를 제공.
  - Pros : data structure를 바꾸지 않고 method를 추가하기 쉬움.
  - Cons : 새 data structure를 추가하면 모든 method를 변경해야 함.
- object (oop) : 구현을 감추고 추상화된 method만 제공.
  - Pros : 이미 존재하는 method를 바꾸지 않고 class를 추가하기 쉬움
  - Cons : 새로운 method를 추가하면 모든 class가 변경되어야 함.
- 모든 것을 객체로 풀어나갈 필요는 없음. 필요에 따라 절차지향으로 풀어도 괜찮음.
  - method가 많이 추가되는 경우 Procedural code가 좋음
  - 새 method보다 새 객체를 추가하는 경우가 많으면 oop가 좋음.

```java
/* data structure */
// shape가 추가 : 모든 method에 영향o
// method가 추가 : 모든 Shape에 영향x
public class Square {
  public Point topLeft;
  public double side;
}

public class Rectangle {
  public Point topLeft;
  public double height;
  public double width;
}

public class Circle {
  public Point center;
  public double radius;
}

public class Geometry {
  public final double PI = 3.141592653589793;
  public double area(Object shape) {
    if (shape instanceof Square) {
      Square s = (Square)shape;
      return s.side * s.side;
    } else if (shape instanceof Rectangle) {
      Rectangle r = (Rectangle)shape;
      return r.height * r.width;
    } else if (shape instanceof Circle) {
      Circle c = (Circle)shape;
      return PI * c.radius * c.radius;
    } else {
      throw new NoSuchShapeException();
    }
  }
}

/* object */
// shape가 추가 : 모든 method에는 영향x
// method가 추가 : 모든 Shape에 영향o
public class Square implements Shape {
  private Point topLeft;
  private double side;

  public double area() {
    return side*side;
  }
}

public class Rectangle implements Shape {
  private Point topLeft;
  private double height;
  private double width;

  public double area() {
    return height * width;
  }
}

public class Circle implements Shape {
  private Point center;
  private double radius;
  public final double PI = 3.141592653589793;

  public double area() {
    return PI * radius * radius;
  }
}
```

## The Law of Demeter

- class C의 method f는 객체의  다음의 method들만 호출 할 수 있다.
  - C
  - method f에 의해 정의된 객체
  - method f에 인자로 들어온 객체
  - C instance에 field로 있는 객체

### Train Wrecks

- method에서 리턴된 객체의 method를 direct로 호출하는 식은 안좋음. 분리.
  ```java
  // bad
  final String outputDir = ctxt.getOptions().getScratchDir().getAbsolutePath();

  // good
  Options opts = ctxt.getOptions();
  File scratchDir = opts.getScratchDir();
  final String outputDir = scratchDir.getAbsolutePath();
  ```
- 그렇다고 이게 `The Law of Demeter`를 위반인지는 다음의 여부에 따라 다름.
  - ctxt, opts, scratchDir이 object라면 각자의 구현이 추상화 되어 있으니 direct로 호출할 수 있는 **object의 method**가 아니므로 위반.
  - ctxt, opts, scratchDir이 단순 data structure 이라면 사실상 **ctxt의 method**를 호출하는 것이므로 위반이 아님.

### Hybrids

- 위에서 설명한 규칙은 절반은 object이고 절반은 data structure인 녀석한테는 애매함.
- 뭔가를 변경하는 유의미한 method와 단순 setter, mutator를 가진 녀석의 경우임.
- 이런 녀석은 method를 추가하기도 애매하고 새 data structure를 추가하기도 애매함. 하지 말 것.

### Hiding Structure

- 위에 Train Wrecks 같은 경우 ctxt, options, scratchDir이 object라면 ctxt에 의미 있는 method를 추가하는게 좋음. 그렇다고 이름을 의식의 흐름으로 막 짓진 말고.
  ```java
  // bad : 이름 대충 지음
  ctxt.getAbsolutePathOfScratchDirectoryOption();

  // good : 이름 잘 지음
  ctxt.createScratchFileStream();
  ```

## Data Transfer Objects

- 앞에서 계속 이야기한 variable만 있고 유의미한 method가 없는 data structure는 DTO(Data Transfer Object) 라고도 불림.
  - eg. db 정보를 담거나 통신 관련 메시지를 담는 객체
- java bean이 이런 좋은 예시임.
  ```java
  // java bean example
  public class Address {
    private String street;
    private String streetExtra;
    private String city;
    private String state;
    private String zip;

    public Address(String street, String streetExtra,
      String city, String state, String zip) {
      this.street = street;
      this.streetExtra = streetExtra;
      this.city = city;
      this.state = state;
      this.zip = zip;
    }

    public String getStreet() {
      return street;
    }

    public String getStreetExtra() {
      return streetExtra;
    }

    public String getCity() {
      return city;
    }

    public String getState() {
      return state;
    }

    public String getZip() {
      return zip;
    }
  }
  ```

### Active Record

- DTO에서 `save`, `find`같은 navigational method가 있는 경우. 즉, 가공해서 제공하는 getter.
- 개발자들이 여기 business logic을 넣고는 하는데 좋지 않음. 그런거 보다 business logic을 하는 객체를 만들고 active record를 내부적으로 숨기는 식으로 하는게 좋음.

## Conclusion

- object랑 data structure 차이점 기억해서 케바케로 둘중 하나 사용해.
