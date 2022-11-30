# Appendix A: Concurrency 2

- [Appendix A: Concurrency 2](#appendix-a-concurrency-2)
  - [Client/Server Example](#clientserver-example)
    - [The Server](#the-server)
    - [Adding Threading](#adding-threading)
    - [Server Observations](#server-observations)
    - [Conclusion](#conclusion)
  - [Possible Paths of Execution](#possible-paths-of-execution)
    - [Number of Paths](#number-of-paths)
    - [Digging Deeper](#digging-deeper)
    - [Conclusion](#conclusion-1)
  - [Knowing Your Library](#knowing-your-library)
    - [Executor Framework](#executor-framework)
    - [Nonblocking Solutions](#nonblocking-solutions)
    - [Nonthread-Safe Classe](#nonthread-safe-classe)
  - [Dependencies Between Methods Can Break Concurrent Code](#dependencies-between-methods-can-break-concurrent-code)
    - [Tolerate the Failure](#tolerate-the-failure)
    - [Client-Based Locking](#client-based-locking)
    - [Server-Based Locking](#server-based-locking)
  - [Incresing Throughput](#incresing-throughput)
    - [Single-Thread Calculation of Throughput](#single-thread-calculation-of-throughput)
    - [Multithread Calculation of Throughput](#multithread-calculation-of-throughput)
  - [Deadlock](#deadlock)
    - [Mutual Exclusion](#mutual-exclusion)
    - [Lock & Wait](#lock--wait)
    - [No Preemption](#no-preemption)
    - [Circular Wait](#circular-wait)
    - [Breaking Mutual Exclusion](#breaking-mutual-exclusion)
    - [Breaking Lock & Wait](#breaking-lock--wait)
    - [Breaking Preemption](#breaking-preemption)
    - [Breaking Circular Wait](#breaking-circular-wait)
  - [Testing Multithreaded Code](#testing-multithreaded-code)
  - [Tool Support for Testing Thread-Based Code](#tool-support-for-testing-thread-based-code)
  - [Conclusion](#conclusion-2)
  - [Tutorial: Full Code Examples](#tutorial-full-code-examples)
    - [Client/Server Nonthreaded](#clientserver-nonthreaded)
    - [Client/Server Using Threads](#clientserver-using-threads)

## Client/Server Example

### The Server

- Single thread server client example
  ```java
  // server. single thread로 busy waiting을 하면서 들어오면 처리함
  ServerSocket serverSocket = new ServerSocket(8009);
  while (keepProcessing) {
    try {
      Socket socket = serverSocket.accept();
      process(socket);
    } catch (Exception e) {
      handle(e);
    }
  }

  // client code
  private void connectSendReceive(int i) {
    try {
      Socket socket = new Socket("localhost", PORT);
      MessageUtils.sendMessage(socket, Integer.toString(i));
      MessageUtils.getMessage(socket);
      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  ```
- 이 서버의 성능을 빠르게 하려면 어떻게 해야할까? thread의 갯수를 많이 하면 되는가? 이는 성능 저하가 어디에서 일어나느냐에 따라 다름.
  - I/O
    - socker, db, swapping 등
    - thread의 수를 늘리면 성능향상이 있음. 한 thread가 i/o waiting하는 동안 processor가 다른 일을 할 수 있음.
  - Processor
    - 정규식 계산, gc 등
    - process 수를 늘리면 성능 향상이 일어남. cpu clock수를 늘리는건 큰 성능 향상이 없음.

### Adding Threading

- process 작업이 I/O bound라면 process에서 multi thread를 사용하게 바꾸면 빨라짐. 해치웠나?
  ```java
  void process(final Socket socket) {
    if (socket == null) {
      return;
    }

    Runnable clientHandler = new Runnable() {
      public void run() {
        try {
          String message = MessageUtils.getMessage(socket);
          MessageUtils.sendMessage(socket, "Processed: " + message);
          closeIgnoringException(socket);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    };

    Thread clientConnection = new Thread(clientHandler);
    clientConnection.start();
  }
  ```

### Server Observations

- 앞에 코드는 thread가 너무 많이 만들어질 수 있다는 문제점이 있음
- 또 서버가 다음의 4가지 기능을 한다는 점에서 SRP를 위반함.
  - Socket connection management
  - Client processing
  - Threading policy
  - Server shutdown policy
- 여러 클래스를 도입해서 SRP를 지켜라. 그러면 나중에 바꾸기도 쉬움.
  ```java
  public interface ClientScheduler {
    void schedule(ClientRequestProcessor requestProcessor);
  }

  public class ThreadPerRequestScheduler implements ClientScheduler {
    public void schedule(final ClientRequestProcessor requestProcessor) {
      Runnable runnable = new Runnable() {
        public void run() {
          requestProcessor.process();
        }
      };

      Thread thread = new Thread(runnable);
      thread.start();
    }
  }

  // new server code
  public void run() {
    while (keepProcessing) {
      try {
        ClientConnection clientConnection = connectionManager.awaitClient();
        ClientRequestProcessor requestProcessor = new ClientRequestProcessor(clientConnection);
        clientScheduler.schedule(requestProcessor);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    connectionManager.shutdown();
  }
  ```

### Conclusion

- concurrency code를 여러 클래스로 나눠라.

## Possible Paths of Execution

- class 예제 : single thread만 이 클래스를 사용한다면 문제가 없음.
  ```java
  public class IdGenerator {
    int lastIdUsed;

    public int incrementValue() {
      return ++lastIdUsed;
    }
  }
  ```
-  But 2개의 thread가 사용한다면 다음의 결과가 나타날 수 있음 (lastIdUsed가 93인 경우).
  - Thread 1 gets 94, thread 2 gets 95, and lastIdUsed is now 95.
  - Thread 1 gets 95, thread 2 gets 94, and lastIdUsed is now 95.
  - Thread 1 gets 94, thread 2 gets 94, and lastIdUsed is now 94. // !!

### Number of Paths

- N 개의 instruction을 T개의 thread에서 실행한다면 다음의 경우의 수가 나옴 
  $$
  \frac{(NT)!}{(N!)^T}
  $$
- 수식 증명도 있는데 귀찮으니 정리 패스. 그냥 경우의 수가 총 NT개의 step에 기반해서 많다는 거만 알아둬.

### Digging Deeper

- Atomic operation이란 uninterruptable한 operation.
  ```java
  int intValue; // 4 bytes
  long longValue; // 8 bytes

  // (jvm memory model의 경우)
  intValue = 0; // 1개의 32-bit assignment로 구성되어 있어서 atomic
  longValue = 0; // 2개의 32-bit assignment로 구성되어 있어서 atomic 하지 않음
  ```
- bytecode를 보기 위해 중요한 개념들.
  - Frame : Call stack frame.
  - Local variable : Method 내부에서 정의된 로컬 변수들. nonstatic method는 최소 `this`라는 한개의 local variable이 있음.
  - Operand stack : instruction에 들어가는 operand에 대한 stack.
- byte code 설명 대상 class
  ```java
  class Example {
    int lastId;

    public void resetId() {
      value = 0;
    }

    public int getNextId() {
      ++value;
    }
  }
  ```
- resetId()
  | Mnemonic | Description | Operand Stack After |
  | -------- | ----------- | ------------------- |
  | ALOAD 0 | Load the 0th variable (this) onto the operand stack. | this |
  | ICONST_0 | Put the constant value 0 onto the operand stack. | this, 0 |
  | PUTFIELD lastId | Store the top value on the stack (which is 0) into the field value of the object referred to by the object reference one away from the top of the stack, this. | empty  |
  - PUTFIELD instruction에서 사용하는 operand stack의 값이 다른 thread에 의해 간섭받을 일이 없음 -> atomic함.
  - 사실 어차피 간섭해도 constant 0을 넣는거라 결과는 동일함.
- getNextId()
  | Mnemonic | Description | Operand Stack After |
  | -------- | ----------- | ------------------- |
  | ALOAD 0 | Load the 0th variable (this) onto the operand stack. | this |
  | DUP | Copy the top of the stack. | this, this |
  | GETFIELD lastId | Retrieve the value of the field lastId from the object pointed to on the top of the stack (this) and store the value to the stack. | this, 42 |
  | ICONST_1 | Push the integer constant 1 on the stack. | this, 42, 1 |
  | IADD | Integer add the top two values on the operand stack and store the result back. | this, 43 |
  | DUP_X1 | Duplicate the value 43 and put it before this. | 43, this, 43 |
  | PUTFIELD value | Store the top value on the operand stack, 43, into the field value of the current object. | 43 |
  | IRETURN | Return the top (and only) value on the stack. | empty |
  - 다음의 시나리오의 경우 두 연산중 한개가 씹힐 수 있음.
    1. Therad A가 GETFIELD 까지 처리 (42 인식).
    2. Thread B가 PUTFIELD 까지 처리 (42 인식해서 43 저장).
    3. Thread A가 PUTFIELD 가지 처리 (1에서 인식한 42를 사용해서 43 저장).
    -> 44가 되어야 하는게 43이 됨.

### Conclusion

- memory model상 뭐가 안전하고 뭐가 안전하지 않은지 알자.
  - shared object/value가 어디 있는지.
  - concurrent read/update issue가 일어나는 부분이 어디 있는지.
  - 그런 이슈를 방지하는 방법.

## Knowing Your Library

### Executor Framework

- `java.util.concurrent`에 있는 executor framework 쓰면 executor에 대한 추상화를 제공해주고 thread pool 정책도 쉽게 설정할 수 있음.
  ```java
  public class ExecutorClientScheduler implements ClientScheduler {
    Executor executor;

    public ExecutorClientScheduler(int availableThreads) {
      executor = Executors.newFixedThreadPool(availableThreads);
    }

    public void schedule(final ClientRequestProcessor requestProcessor) {
      Runnable runnable = new Runnable() {
        public void run() {
        requestProcessor.process();
        }
      };
      executor.execute(runnable);
    }
  }
  ```
- future도 있음.
  ```java
  public String processRequest(String message) throws Exception {
    Callable<String> makeExternalCall = new Callable<String>() {
      public String call() throws Exception {
        String result = "";
        // make external request
        return result;
      }
    };

    Future<String> result = executorService.submit(makeExternalCall);
    String partialResult = doSomeLocalProcessing();
    return result.get() + partialResult; // result.get() : result 가 끝날때까지 기다림.
  }
  ```

### Nonblocking Solutions

- java 5에서 CAS(Compare and SWap)을 사용하는 atomic operation을 지원함.
  ```java
  // before : synchronized 사용
  public class ObjectWithValue {
    private int value;

    public synchronized void incrementValue() { ++value; }

    public int getValue() { return value; }
  }

  // after : atomic class 사용
  public class ObjectWithValue {
    private AtomicInteger value = new AtomicInteger(0);

    public void incrementValue() {
      value.incrementAndGet();
    }

    public int getValue() {
      return value.get();
    }
  }
  ```
- synchronized
  - pessimistic(비관적) locking
  - 다른 thread가 값을 바꿀 일 없어도 무조건 lock 걸음.
- Compare and Swap
  - optimistic(낙천적) locking
  - 문제가 발생할 정도로 다른 thread가 해당 값을 바꿀 일이 없다고 가정.
    ```java
    // CAS 작동 원리
    int variableBeingSet;

    void simulateNonBlockingSet(int newValue) {
      int currentValue;
      do {
        currentValue = variableBeingSet
      } while(currentValue != compareAndSwap(currentValue, newValue));
    }

    int synchronized compareAndSwap(int currentValue, int newValue) {
      if (variableBeingSet == currentValue) {
        variableBeingSet = newValue;
        return currentValue;
      }
      return variableBeingSet; // 다르면 return해서 새로운 값을 currentValue 다시 넣고 재시도
    }
    ```

### Nonthread-Safe Classe

- java에는 여러 nonthread-Safe한 class들이 있음.
  - SimpleDateFormat
  - Database Connections.
  - Containers in `java.util`
  - ...
- 각각의 method가 thread-safe하지만 한개 이상의 method를 호출했을 때 thread-safe하지 않을 수 있음
  ```java
  // bad : containsKey 이후 다른 thread가 put을 할 수가 있음
  if(!hashTable.containsKey(someKey)) {
    hashTable.put(someKey, new SomeValue());
  }

  // solution 1 : map 자체를 lock
  synchronized(map) {
    if(!map.conainsKey(key)) {
      map.put(key,value);
    }
  }

  // solution 2 : 다른 객체로 감싸서 그걸 lock
  public class WrappedHashtable<K, V> {
    private Map<K, V> map = new Hashtable<K, V>();

    public synchronized void putIfAbsent(K key, V value) {
      if (map.containsKey(key))
      map.put(key, value);
    }
  }

  // solution 3 : Thread-safe한 collection 사용
  ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
  map.putIfAbsent(key, value);
  ```

## Dependencies Between Methods Can Break Concurrent Code

- IntegerIterator class
  ```java
  public class IntegerIterator implements Iterator<Integer> {
    private Integer nextValue = 0;

    public synchronized boolean hasNext() {
      return nextValue < 100000;
    }

    public synchronized Integer next() {
      if (nextValue == 100000)
        throw new IteratorPastEndException();
      return nextValue++;
    }

    public synchronized Integer getNextValue() {
      return nextValue;
    }
  }
  ```
- 아래 코드를 여러 thread에서 사용할 경우 iteration의 끝에서 에러가 발생할 수 있음.
  ```java
  while (iterator.hasNext()) {
    int nextValue = iterator.next();
    // ...
  }
  ```
  - 에러 상황
    1. Thread A가 hasNext에 도달해서 다음으로 넘어감 (nextValue == 99999)
    2. Thread B가 hasNext에 도달해서 다음으로 넘어감 (nextValue == 99999)
    3. Thread A가 next호출 (nextValue == 100000)
    4. Thread B가 next호출 (exception 발생)

### Tolerate the Failure

- 저런 코드를 catch하고 clean-up으로만 해결한다고 하면 그건 야매임. 그건 마치 memory leak이 있다고 컴퓨터를 주기적으로 재시작 하는 거랑 같음.

### Client-Based Locking

- 앞서 말한 에러 상황을 방지하기 위해서는 client쪽에서 lock을 거는 방법이 있음.
  ```java
  while (true) {
    int nextValue;

    // client side lock
    synchronized (iterator) {
      if (!iterator.hasNext()) {
        break;
      }
      nextValue = iterator.next();
    }
    doSometingWith(nextValue);
  }
  ```
- But 이 방법은 client 쪽에서 실수하기 쉬움.

### Server-Based Locking

- 앞서 말한 에러 상황을 방지하기 위해 server쪽에서 lock을 거는 방법이 있음.
  ```java
  // server side lock
  public class IntegerIteratorServerLocked {
    private Integer nextValue = 0;

    public synchronized Integer getNextOrNull() {
      if (nextValue < 100000) {
        return nextValue++;
      } else {
        return null;
      }
    }
  }

  // call server side lock api
  while (true) {
    Integer nextValue = iterator.getNextOrNull();
    if (next == null) {
      break;
    }
    // ...
  }
  ```
- 이렇게 하면 다음의 이점이 있음.
  - lock을 위해 사용되는 중복 코드들을 줄일 수 있음.
  - 성능상 이점이 있을 수 있음.
  - client쪽에서 실수로 lock을 하지 않는 실수를 방지할 수 있음.
  - lock을 할 때 단일 정책을 사용 가능.
  - lock변수의 scope를 server side로 한정. client는 알 필요 없음.
- Server code가 없을때 client에서 이런 비슷한걸 하는 방법.
  - locking을 하는 client code를 사용.
    ```java
    public class ThreadSafeIntegerIterator {
      private IntegerIterator iterator = new IntegerIterator();

      public synchronized Integer getNextOrNull() {
        if(iterator.hasNext()) {
          return iterator.next();
        }
        return null;
      }
    }
    ```

## Incresing Throughput

- page를 read해서 요약할 때 사용하는 class 예제.
  ```java
  public class PageReader {
    public String getPageFor(String url) {
      HttpMethod method = new GetMethod(url);
      try {
        httpClient.executeMethod(method);
        return method.getResponseBodyAsString();
      } catch (Exception e) {
        handle(e);
      } finally {
        method.releaseConnection();
      }
    }
  }

  public class PageIterator {
    private PageReader reader;
    private URLIterator urls;

    public PageIterator(PageReader reader, URLIterator urls) {
      this.urls = urls;
      this.reader = reader;
    }

    // 이렇게 가능하면 critical section을 줄이는 것이 좋음.
    public synchronized String getNextPageOrNull() {
      if (urls.hasNext()) {
        getPageFor(urls.next());
      } else {
        return null;
      }
    }

    public String getPageFor(String url) {
      return reader.getPageFor(url);
    }
  }
  ```

### Single-Thread Calculation of Throughput

- 다음의 세 가지를 가정하면 single thread일 때 처리량은 1.5 * T seconds임.
  - I/O time to retrieve a page (average): 1 second
  - Processing time to parse page (average): .5 seconds
  - I/O requires 0 percent of the CPU while processing requires 100 percent.

### Multithread Calculation of Throughput

- 같은 가정 하에 3개의 thread를 사용할 경우 1개를 read할 때 2개를 처리 할 수 있으므로 처리량은 single thread의 3배임.

## Deadlock

- Deadlock이 발생하는 상황.
  - 가정
    - 각가 10개의 master repository, process db connection pool.
    - Create : process db의 connection을 얻고 이어서 master repository의 connection을 얻어서 처리.
    - Update : master repository의 connection을 얻고 이어서 process db의 connection을 얻어서 처리.
  - 상황.
    1. 10개의 user가 create를 해서 우선 process db의 connection을 얻음.
    2. 10개의 user가 update를 해서 우선 master repository의 connection을 얻음.
    3. 1에의 user가 master repository의 connection을 얻으려고 하고 2의 user가 process db의 connection을 얻으려고 함.
    4. deadlock.
- 보통 deadlock이 발생할 경우 원인을 찾기 위해 debugging code를 넣지만 가끔은 이 코드 자체가 deadlock의 원인을 제거할 때가 있음. 진짜 원인을 알 필요가 있음.
- deadlock은 다음의 4 가지 조건이 모두 충족되어야 발생.
  - Mutual exclusion
  - Lock & wait
  - No preemption
  - Circular wait

### Mutual Exclusion

- Mutual Exclusion : 여러 thread가 다음의 resource를 얻으려고 하는 경우.
  - Cannot be used by multiple threads at the same time.
  - Are limited in number.
- e.g. semaphore, db connection pool

### Lock & Wait

- Resource를 요청하면 해당 resource를 얻고 처리를 끝낼때까지 놓지 않음.

### No Preemption

- 한 thread가 한번 선점한 resource는 다른 Thread에 의해 빼앗기지 않음. 선점한 thread가 resource를 놓아야 다른 thread가 사용 가능.

### Circular Wait

- 순환 참조.
  - eg. 
    1. T1이 R1을 가지고 있음. T2가 R2을 가지고 있음.
    2. T1이 R2을 요청, T2가 R1을 요청.
    3. Circular Wait.

### Breaking Mutual Exclusion

- Mutual Exclusion을 방지하는 방법.
  - 동시에 사용할 수 있는 자원을 사용 (eg. AtomicInteger)
  - resource 수를 최소한 thread 수 만큼 늘림
  - resource를 얻기 전에 남은게 있는지 확인.
- 문제
  - 현실적으로 resource는 항상 제한되어 있고 동시에 사용할 수 없음.

### Breaking Lock & Wait

- Wait을 방지하는 방법.
  - resource가 있는지 확인하고 없으면 가지고 있는 resource를 release하고 재시도.
- 문제점
  - Starvation
    - 몇개의 thread가 resource를 계속 얻지 못함. -> CPU 이용량 감소.
  - Livelock
    - 몇개의 thread가 서로 lock을 계속 얻고 놓는거를 반복하는 상황. -> CPU 이용량은 높으나 쓸데없이 높음.

> livelock example.
```java
// Thread A
while (true) {
  acquire(A)
  if (acquire(B) == true) {
    // do something
    release(B)
    release(A)
    break
  } else {
    release(A)
  }
}

// Thread B
while (true) {
  acquire(B)
  if (acquire(A) == true) {
    // do something
    release(A)
    release(B)
  } else {
    release(B)
  }
}
```

### Breaking Preemption

- 선점을 제거해서 한 thread가 필요한 자원을 다른 thread에서 가져오게 하는 방법이 있음.
- 장점
  - 처음부터 다시 시작하는 횟수 감소.
- 문제점
  - 구현이 매우 어려움.

### Breaking Circular Wait

- Resource를 가져오는 순서를 강제해서 circular wait을 제거하는 방법이 제일 무난함.
  - eg. 
    - T1, T2를 R1, R2를 순서대로 가지게 하면 circular wait 자체가 방지됨.
- 문제점
  - 사용하는 때 보다 미리 resource를 가지게 되어서 성능 저하가 발생.
  - 순서를 강제하지 못할 상황도 있음.

## Testing Multithreaded Code

- thread safety를 테스트 하는 코드.
  ```java
  public class ClassWithThreadingProblem {
    int nextId;

    public int takeNextId() {
      return nextId++;
    }
  }

  @Test
  public void twoThreadsShouldFailEventually() {
    final ClassWithThreadingProblem classWithThreadingProblem = new ClassWithThreadingProblem();
    Runnable runnable = new Runnable() {
      public void run() {
        classWithThreadingProblem.takeNextId();
      }
    };

    for (int i = 0; i < 50000; ++i) {
      int startingId = classWithThreadingProblem.lastId;
      int expectedResult = 2 + startingId;

      Thread t1 = new Thread(runnable);
      Thread t2 = new Thread(runnable);
      t1.start();
      t2.start();
      t1.join();
      t2.join();

      int endingId = classWithThreadingProblem.lastId;

      if (endingId != expectedResult) {
        return;
      }
    }

    fail("Should have exposed a threading issue but it did not.");
  }
  ```
- 사실 이 코드는 잘 동작 안함. 1000000번은 해야 한번 감지. 이는 심지어 jvm, os 등 환경따라 달라질 수 있음.
- 이에 대한 대안은 이런게 있음.
  - Monte Carlo Testing : loop값 같은 것을 계속 바꿔가면서 테스트. 깨지면 실패하는 것.
  - 모든 target platform에 대해서 테스트.
  - 부하를 다양하게 줘가면서 테스트. real 환경하고 비슷한 부하면 더 좋음.
- 이런걸 해도 여전히 critical section이 작다면 일어날 확률이 너무 낮아서 감지하기 힘듬. But 이런 장애가 나타나면 크리티컬함..

> 대부분 그냥 thread pool을 쓰고 iterating을 엄청나게 하면 재현되긴 했는데.. 절대적으로 감지하는 방법은 없을까?

## Tool Support for Testing Thread-Based Code

- non-thread-safe code를 쉽게 실패하게 만드는 도구도 있으니 사용.
- IBM ConTest라는 도구도 있음.

> ConTest는 지금은 묻힌듯.

## Conclusion

- 별말 없음.

## Tutorial: Full Code Examples

### Client/Server Nonthreaded

- [SingleThreadServer](./pracitce/appendixA/SingleThreadServer.java)
- [Client](./pracitce/appendixA/Client.java)

### Client/Server Using Threads

- [MultiThreadServer](./pracitce/appendixA/MultiThreadServer.java)
- [Client](./pracitce/appendixA/Client.java)