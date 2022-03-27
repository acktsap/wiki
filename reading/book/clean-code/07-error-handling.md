# 7. Error Handling

- [7. Error Handling](#7-error-handling)
  - [Introduction](#introduction)
  - [Use Exceptions Rather Than Return Codes](#use-exceptions-rather-than-return-codes)
  - [Write Your Try-Catch-Finally Statement First](#write-your-try-catch-finally-statement-first)
  - [Use Unchecked Exceptions](#use-unchecked-exceptions)
  - [Provide Context with Exceptions](#provide-context-with-exceptions)
  - [Define Exception Classes in Terms of a Caller’s Needs](#define-exception-classes-in-terms-of-a-callers-needs)
  - [Define the Normal Flow](#define-the-normal-flow)
  - [Don’t Return Null](#dont-return-null)
  - [Don’t Pass Null](#dont-pass-null)
  - [Conclusion](#conclusion)

## Introduction

- 에러 핸들링 때문에 메인 로직이 흐려져서는 안됨. 에러 핸들링은 명확해야 함.

## Use Exceptions Rather Than Return Codes

- 에러 코드를 리턴하는거는 매번 확인해야 하고 caller를 복잡하게 함. exception을 던져라.

```java
// bad
public void sendShutDown() {
  DeviceHandle handle = getHandle(DEV1);
  // Check the state of the device
  if (handle != DeviceHandle.INVALID) {
    // Save the device status to the record field
    retrieveDeviceRecord(handle);
    // If not suspended, shut down
    if (record.getStatus() != DEVICE_SUSPENDED) {
      pauseDevice(handle);
      clearDeviceWorkQueue(handle);
      closeDevice(handle);
    } else {
      logger.log("Device suspended. Unable to shut down");
    }
  } else {
    logger.log("Invalid handle for: " + DEV1.toString());
  }
}

// good
public void sendShutDown() {
  try {
    tryToShutDown();
  } catch (DeviceShutDownError e) {
    logger.log(e);
  }
}

private void tryToShutDown() throws DeviceShutDownError {
  DeviceHandle handle = getHandle(DEV1);
  DeviceRecord record = retrieveDeviceRecord(handle);
  pauseDevice(handle);
  clearDeviceWorkQueue(handle);
  closeDevice(handle);
}

private DeviceHandle getHandle(DeviceID id) {
  ...
  throw new DeviceShutDownError("Invalid handle for: " + id.toString());
  ...
}
```

> 여전히 try-catch를 분리하는게 좋은지는 잘 모르겠음.

## Write Your Try-Catch-Finally Statement First

- try가 시작되는 순간 이거는 에러를 던질 수 있다는걸 의미. 에러를 던질 수 있는 거는 try부터 시작해.
  ```java
  public List<RecordedGrip> retrieveSection(String sectionName) {
    try {
      FileInputStream stream = new FileInputStream(sectionName);
      stream.close();
    } catch (FileNotFoundException e) {
      throw new StorageException("retrieval error", e);
    }
    return new ArrayList<RecordedGrip>();
  }
  ```

## Use Unchecked Exceptions

- checked exception은 에러 핸들링을 강제한다는 점에서 좋은 점이 있긴 한데 실제로 robust한 software를 만들 때 큰 도움이 되지는 않음.
- 무엇보다 low level에서 exception을 던지는 부분이 바뀌면 high level도 강제로 바뀌어야 한다는 점에서 Open/Closed Principle을 위반함.

## Provide Context with Exceptions

- 에러를 던질때 그냥 stack trace만 던지지 말고 유의미한 message를 넣어.

## Define Exception Classes in Terms of a Caller’s Needs

- exception을 던질때 제일 중요한 것은 caller가 어떻게 받느냐임.
- 예를 들어서 api design을 할 경우 3rd party library를 사용할 때는 거기서 발생하는 에러를 감싸서 던지면 해당 라이브러리에 의존성을 최소화 하게 됨.
  ```java
  // bad
  ACMEPort port = new ACMEPort(12);
  try {
    port.open();
  } catch (DeviceResponseException e) {
    reportPortError(e);
    logger.log("Device response exception", e);
  } catch (ATM1212UnlockedException e) {
    reportPortError(e);
    logger.log("Unlock exception", e);
  } catch (GMXError e) {
    reportPortError(e);
    logger.log("Device response exception");
  }

  // good
  LocalPort port = new LocalPort(12);
  try {
    port.open();
  } catch (PortDeviceFailure e) {
    reportError(e);
    logger.log(e.getMessage(), e);
  }

  public class LocalPort {
    private ACMEPort innerPort;

    public LocalPort(int portNumber) {
      innerPort = new ACMEPort(portNumber);
    }

    public void open() {
      // 한번 감싸
      try {
        innerPort.open();
      } catch (DeviceResponseException e) {
        throw new PortDeviceFailure(e);
      } catch (ATM1212UnlockedException e) {
        throw new PortDeviceFailure(e);
      } catch (GMXError e) {
        throw new PortDeviceFailure(e);
      }
    }
  }
  ```
- 예외 클래스에 포함된 정보로 오류를 구분해도 되는 경우는 예외 클래스가 하나만 있어도 충분. 한 예외는 잡아내고 다른 예외는 무시하도 괜찮은 경우라면 여러 예외 클래스를 사용.

## Define the Normal Flow

- 에러가 던져진다고 다 감싸지 않고 던져니는 케이스를 특별한 비즈니스 케이스로 취급해서 할 수도 있음.
  ```java
  // bad
  try {
    MealExpenses expenses = expenseReportDAO.getMeals(employee.getID());
    m_total += expenses.getTotal();
  } catch(MealExpensesNotFound e) {
    m_total += getMealPerDiem();
  }

  // good
  public class PerDiemMealExpenses implements MealExpenses {
    public int getTotal() {
    // return the per diem default
    }
  }
  MealExpenses expenses = expenseReportDAO.getMeals(employee.getID());
  m_total += expenses.getTotal(); // get diem as default
  ```

## Don’t Return Null

- null을 return하면 null check를 넣어야 함. special case object를 사용.
  ```java
  // bad
  List<Employee> employees = getEmployees();
  if (employees != null) {
    for(Employee e : employees) {
      totalPay += e.getPay();
    }
  }

  // good
  List<Employee> employees = getEmployees();
  for(Employee e : employees) {
    totalPay += e.getPay();
  }
  ```

## Don’t Pass Null

- null을 인자로 넘기지 마라. 인자를 받는 쪽에서는 null check를 해라. null check를 해서 runtime error를 던져도 에러를 던진다는 점은 마찬가지 이므로 가능하면 null을 인자로 ㄴㄴ.
  ```java
  // bad : npe 발생
  public double xProjection(Point p1, Point p2) {
    return (p2.x – p1.x) * 1.5;
  }

  // soso : npe 는 발생 안하지만 여전히 runtime exception이 발생
  public double xProjection(Point p1, Point p2) {
    if (p1 == null || p2 == null) {
      throw InvalidArgumentException("Invalid argument xProjection");
    }
    return (p2.x – p1.x) * 1.5;
  }
  ```

## Conclusion

- error handling을 잘 함으로써 robust한 코드를 짤 수 있음.

---

> [when to throw exception](https://www.google.com/search?client=firefox-b-d&q=when+to+throw+exception) 참고해서 언제 exception을 던질지도 생각해보는게 좋을듯.