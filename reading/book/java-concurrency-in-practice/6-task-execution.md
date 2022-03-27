# 6. Task Execution

- [6. Task Execution](#6-task-execution)
  - [6.1. Executing Tasks in Thread](#61-executing-tasks-in-thread)
    - [Executing Tasks Sequentially](#executing-tasks-sequentially)
    - [Explicitly Creating Threads for Tasks](#explicitly-creating-threads-for-tasks)
    - [Disadvantages of Unbounded Thread Creation](#disadvantages-of-unbounded-thread-creation)
  - [6.2. The Executor Framework](#62-the-executor-framework)
    - [Example: Web Server Using Executor](#example-web-server-using-executor)
    - [Execution Policies](#execution-policies)
    - [Thread Pools](#thread-pools)
    - [Executor Lifecycle](#executor-lifecycle)
    - [Delayed and Periodic Tasks](#delayed-and-periodic-tasks)
  - [6.3. Finding Exploitable Parallelism](#63-finding-exploitable-parallelism)
    - [Example: Sequential Page Renderer](#example-sequential-page-renderer)
    - [Result bearing Tasks: Callable and Future](#result-bearing-tasks-callable-and-future)
    - [Example: Page Renderer with Future](#example-page-renderer-with-future)
    - [Limitations of Parallelizing Heterogeneous Tasks](#limitations-of-parallelizing-heterogeneous-tasks)
    - [CompletionService: Executor Meets BlockingQueue](#completionservice-executor-meets-blockingqueue)
    - [Example: Page Renderer with CompletionService](#example-page-renderer-with-completionservice)
    - [Placing Time Limits on Tasks](#placing-time-limits-on-tasks)
    - [Example: A Travel Reservations Portal](#example-a-travel-reservations-portal)

## 6.1. Executing Tasks in Thread

작업은 서로 독립적이고 작은 단위로 나누어야 한다. 그래야 에러 복구도 쉽고 병렬처리를 잘 할 수 있다. 서버의 입장에서 작은 단위가 client 요청 한개 정도일 수도 있다.

서버는 과부하에서 죽지 말고 성능이 점진적으로 떨어지게 설계되어야 한다.

### Executing Tasks Sequentially

서버를 single thread로 실행시키면 resource utilization에서도 안좋고 I/O나 db접근 등에 blocking 상태가 되면 cpu를 낭비하고 다른 요청을 받기 힘듬.

```java
class SingleThreadWebServer {
  // acceping & processing request in a single thread
  public static void main(String[] args) throws IOException {
    ServerSocket socket = new ServerSocket(80);
    while (true) {
      Socket connection = socket.accept();
      handleRequest(connection);
    }
  }
}
```

### Explicitly Creating Threads for Tasks

요청마다 다른 thread를 만들어서 처리하면

- 요청을 받는 main thread가 다른 요청을 더 빨리 받을 수 있음
- 요청 처리를 병렬적으로 할 수 있음
- But 요청 처리에 thread safety를 확보해야함

```java
class ThreadPerTaskWebServer {
  // acceping request in a single thread & processing it in multi thread
  public static void main(String[] args) throws IOException {
    ServerSocket socket = new ServerSocket(80);
      while (true) {
        final Socket connection = socket.accept();
        Runnable task = () -> handleRequest(connection);
        new Thread(task).start();
      }
  }
}
```

### Disadvantages of Unbounded Thread Creation

- Thread를 생성하고 소멸시키는데 비용이 많이듦
- 처리할 수 있는 processor는 한정되어 있는데 너무 많은 thread를 생성하면 memory가 낭비됨. 또한 gc에도 부담을 주며 많은 thread가 cpu를 가지려고 경쟁해서 더 느려짐.
- jvm 설정에 따라 생성될 수 있는 개수가 한계가 있음. 또한 `-Xss` 설정으로 할 수 있는 thread별 stack size도 한계가 있음.

이처럼 너무 많은 thread를 생성하면 안좋은 점이 많아서 thread 수에 bound를 줄 필요가 있음.

## 6.2. The Executor Framework

Executor는 task submission과 task execution사이의 decoupling을 제공함.

Executor는 submiting하는 것이 producer로 executing하는 것이 consumer로 해서 producer consumer로 동작함.

```java
public interface Executor {
  void execute(Runnable command);
}
```

### Example: Web Server Using Executor

```java
class TaskExecutionWebServer {
  private static final int NTHREADS = 100;
  // making thread pool is one-time event
  private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);

  public static void main(String[] args) throws IOException {
    ServerSocket socket = new ServerSocket(80);
    while (true) {
      final Socket connection = socket.accept();
      exec.execute(() -> handleRequest(connection));
    }
  }
}

// it's easy to write custom executor
public class ThreadPerTaskExecutor implements Executor {
  public void execute(Runnable r) {
    new Thread(r).start();
  };
}

// it's easy to write single-threaded version
public class WithinThreadExecutor implements Executor {
  public void execute(Runnable r) {
    r.run();
  };
}
```

### Execution Policies

작업 정책은 What? Where? When? How?를 결정한다.

- In what thread will tasks be executed?
- In what order should tasks be executed (FIFO, LIFO, priority order)?
- How many tasks may execute concurrently?
- How many tasks may be queued pending execution?
- If a task has to be rejected because the system is overloaded, which task should be selected as the victim, and how should the application be notified?
- What actions should be taken before or after executing a task?

이러한 작업 정책을 통해 resource에 따라 정책을 다르게 결정할 수 있다. 예를 들면 resource가 과도하게 사용되는 경우 throttling을 걸어서 system의 안전성을 확보할 수 있다.

```java
// 이러한 코드가 보이면 Executor framework를 적용하는 것을 강하게 고려해 보셈
// resource상태 별로 정책을 세울 수가 없음 이렇게 작성하면
new Thread(runnable).start();
```

### Thread Pools

Thread pool을 사용하면 thread를 생성 및 소멸하는 비용을 아낄 수 있음. 요청이 왔을 때 thread pool에 thread가 있는 경우 responsiveness도 좋아짐.

`Executors`의 static factory method를 통해 thread pool을 생성할 수 있음.

- newFixedThreadPool : 고정된 갯수의 thread를 생성. 죽으면 새로 생성.
- newCachedThreadPool : 요청에 따라 thread를 생성하고 필요 없으면 죽여버림. But bound가 없음.
- newSingleThreadExecutor : 1개의 thread를 생성. 죽으면 새로 생성.
- newScheduledThreadPool : 고정된 갯수의 thread를 생성. delayed & periodic thread execution을 제공.

Thread pool을 사용하면 자원 튜닝, 관리, 모니터링 등을 보다 손쉽게 할 수 있음.

### Executor Lifecycle

Jvm은 daemon이 아닌 thread가 끝나기 전까지 죽지 못함. 그래서 Executor를 잘 죽이거나 (gracefully shotdown) 바로 죽이거나 해야 함. 이러한 행위에 대한 추상화가 `ExecutorService` interface.

3가지 상태가 있음

- running : 만들면 이 상태
- shutting down
  - shutdown : 새 작업을 받지는 않으나 이미 실행되는 것과 queueing된 task는 실행.
  - showdownNow : 실행 중인 task를 가능하면 취소, queueing된 것들은 취소.
  - shutting down상태일 때 task를 받으면 `RejectedExecutionException` 던짐
- terminated : 모든 작업이 완료된 후의 상태.

보통 shutdown을 실행시키고 awaitTermination을 실행시킴

```java
public interface ExecutorService extends Executor {
  void shutdown();

  List<Runnable> shutdownNow();

  boolean isShutdown();

  boolean isTerminated();

  boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException;
  // ... additional convenience methods for task submission
}
```

```java
class LifecycleWebServer {
  private final ExecutorService exec = ...;

  public void start() throws IOException {
    ServerSocket socket = new ServerSocket(80);
    while (!exec.isShutdown()) {
      try {
        final Socket conn = socket.accept();
        exec.execute(() -> handleRequest(conn);
        // shutting down 상태일 때 task 받으면 RejectedExecutionException
      } catch (RejectedExecutionException e) {
        if (!exec.isShutdown()) {
          log("task submission rejected", e);
        }
      }
    }
  }

  public void stop() {
    exec.shutdown();
  }
}
```

### Delayed and Periodic Tasks

`ScheduledThreadPoolExecutor`가 delayed & periodic task를 실행할 수 있는 interface를 제공. 이는 Single thread로 `TimerTask`를 실행시키게 동작하는 `Timer`의 대체 버전으로 활용될 수 있음.

`Timer`는 unchecked exception이 던져지면 해당 thread가 죽고 canceled된 것으로 간주해버림. 그래서 이미 scheduled 되었거나 나 앞으로 추가될 task들을 사실상 실행할 수 없는 상태가 되어버림. 이 현상을 Thread leakage라고 함.

```java
public class OutOfTime {
  public static void main(String[] args) throws Exception {
    Timer timer = new Timer();
    timer.schedule(new ThrowTask(), 1);
    // 이 때 ThrowTask가 실행. 그러면서 timer task를 실행하는 thread가 죽어버림
    SECONDS.sleep(1);

    // throws IllegalStateException (Timer already cancelled)
    timer.schedule(new ThrowTask(), 1);
    SECONDS.sleep(5);
  }

  static class ThrowTask extends TimerTask {
    public void run() { throw new RuntimeException(); }
  }
}
```

## 6.3. Finding Exploitable Parallelism

single client request를 하나의 작은 단위로 볼 수도 있음. But single client request도 나눌 수 있음.

이 장에서는 paged renderer에 대한 여러 버전을 다룬다.

### Example: Sequential Page Renderer

```java
public class SingleThreadRenderer {
  void renderPage(CharSequence source) {
    // image는 네모난 상자로 냅두고 text를 rendering
    renderText(source);
    List<ImageData> imageData = new ArrayList<ImageData>();
    for (ImageInfo imageInfo : scanForImageInfo(source)) {
      // download는 I/O라 CPU시간을 낭비
      imageData.add(imageInfo.downloadImage());
    }
    for (ImageData data : imageData) {
      // 네모난 상자에 image를 rendering
      renderImage(data);
    }
  }
}
```

### Result bearing Tasks: Callable and Future

Callable

- 행위에 대한 추상화
- Runnable과는 다르게 return이 있고 checked exception을 던질 수 있음
- return이 없는 행위에 대해서는 `Callable<Void>`를 쓸 수 있음

Future

- Future::get
  - 완료되었으면 값을 return
  - Exception과 함께 끝났으면 ExecutionException로 감싸서 throws. getCause로 원래 exception 알 수 있음
  - cancelled 되었으면 CancellationException를 던짐
- ExecutorService::submit이 Future를 return함

```java
public interface Callable<V> {
  V call() throws Exception;
}

public interface Future<V> {
  boolean cancel(boolean mayInterruptIfRunning);

  boolean isCancelled();

  boolean isDone();

  V get() throws InterruptedException, ExecutionException,
  CancellationException;

  V get(long timeout, TimeUnit unit)
  throws InterruptedException, ExecutionException,
  CancellationException, TimeoutException;
}
```

AbstractExecutorService를 상속하면 새로 생성되는 Future에 대한 customizing을 할 수 있음.

```java
// newTaskFor 기본 동작
protected <T> RunnableFuture<T> newTaskFor(Callable<T> task) {
  return new FutureTask<T>(task);
}
```

### Example: Page Renderer with Future

```java
public class FutureRenderer {
  private final ExecutorService executor = ...;
  void renderPage(CharSequence source) {
    final List<ImageInfo> imageInfos = scanForImageInfo(source);
    Callable<List<ImageData>> task = new Callable<List<ImageData>>() {
      public List<ImageData> call() {
        List<ImageData> result = new ArrayList<ImageData>();
        for (ImageInfo imageInfo : imageInfos) {
          result.add(imageInfo.downloadImage());
        }
        return result;
      }
    };
    // I/O 작업인 download를 async하게 실행
    Future<List<ImageData>> future = executor.submit(task);

    // image는 네모난 상자로 냅두고 text를 rendering
    renderText(source);
    try {
      // download실행이 모두 완료되면 image를 rendering
      List<ImageData> imageData = future.get();
      for (ImageData data : imageData) {
        renderImage(data);
      }
    } catch (InterruptedException e) {
      // Re-assert the thread's interrupted status
      Thread.currentThread().interrupt();
      // We don't need the result, so cancel the task too
      future.cancel(true);
    } catch (ExecutionException e) {
      // cause를 감싸서 throws
      throw launderThrowable(e.getCause());
    }
  }
}
```

### Limitations of Parallelizing Heterogeneous Tasks

TODO

### CompletionService: Executor Meets BlockingQueue

### Example: Page Renderer with CompletionService

### Placing Time Limits on Tasks

### Example: A Travel Reservations Portal
