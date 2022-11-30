# 7. Cancellation and Shutdown

- [7. Cancellation and Shutdown](#7-cancellation-and-shutdown)
  - [7.1. Task Cancellation](#71-task-cancellation)
    - [Interruption](#interruption)
    - [Interruption Policies](#interruption-policies)
    - [Responding to Interruption](#responding-to-interruption)
    - [Example: Timed Run](#example-timed-run)
    - [Cancellation Via Future](#cancellation-via-future)
    - [Dealing with Non-interruptible Blocking](#dealing-with-non-interruptible-blocking)
    - [Encapsulating Nonstandard Cancellation with Newtaskfor](#encapsulating-nonstandard-cancellation-with-newtaskfor)
  - [7.2. Stopping a Thread based Service](#72-stopping-a-thread-based-service)
    - [Example: A Logging Service](#example-a-logging-service)
    - [ExecutorService Shutdown](#executorservice-shutdown)
    - [Poison Pills](#poison-pills)
    - [Example: A One-shot Execution Service](#example-a-one-shot-execution-service)
    - [Limitations of Shutdownnow](#limitations-of-shutdownnow)
  - [7.3. Handling Abnormal Thread Termination](#73-handling-abnormal-thread-termination)
    - [Uncaught Exception Handlers](#uncaught-exception-handlers)
  - [7.4. JVM Shutdown](#74-jvm-shutdown)
    - [Shutdown Hooks](#shutdown-hooks)
    - [Daemon Threads](#daemon-threads)
    - [Finalizers](#finalizers)

End-of-lifecycle issue는 시스템 디자인에 중요한 요소중 하나임. 이를태면 작업을 하는 thread가 직접 resource를 정리하게 하는거랑 해당 thread를 종료하는 thread가 자원을 종료시키는 경우에 시스템 디자인이 달라질 수 있음.

## 7.1. Task Cancellation

Task가 정상적이지 않게 종료되는 케이스가 있음

- User-requested cancellation : eg. 사용자가 cancel버튼을 누른 경우
- Time-limited activities : eg. 제한된 시간 동안 찾은 후 그 사이 최고의 solution을 내놓는 경우
- Application events : eg. application이 작업을 나눠서 다른 thread가 처리하게 해서 찾은 경우 다른 thread들이 종료되는 경우
- Errors : eg. crawling 중 디스크가 가득 차서 에러를 던지는 경우
- Shutdown : Application이 종료되는 경우
  - Graceful shutdown : 하던 작업 마무리 후 종료
  - Immediate shutdown : 하던 작업이 있어도 그냥 종료

Java에서는 thread를 죽이는 안전한 방법이 없음. 다른 Thread가 종료를 하는 등 협력적인 방법으로만 해야 함.

Task를 종료할 때는 How? (어떻게 종료할 것인가?), When? (종료 요청이 온 후 언제 종료할 것인가?), What? (종료 후 어떤 response를 보낼 것인가? 또는 어떤 행동을 취할 것인가?)를 생각해야 함.

```java
// stopping thread by flag
@ThreadSafe
public class PrimeGenerator implements Runnable {
  @GuardedBy("this")
  private final List<BigInteger> primes = new ArrayList<BigInteger>();

  // note that it must be volatile
  private volatile boolean cancelled;

  public void run() {
    BigInteger p = BigInteger.ONE;
    while (!cancelled) {
      p = p.nextProbablePrime();
      synchronized (this) {
        primes.add(p);
      }
    }
  }

  public void cancel() { cancelled = true; }

  public synchronized List<BigInteger> get() {
    return new ArrayList<BigInteger>(primes);
  }
}

List<BigInteger> aSecondOfPrimes() throws InterruptedException {
  PrimeGenerator generator = new PrimeGenerator();
  new Thread(generator).start();
  try {
    // 1초 뒤 멈춤
    SECONDS.sleep(1);
  } finally {
    generator.cancel();
  }
  return generator.get();
}
```

### Interruption

flag로 thread를 죽이는 것은 flag 체크로 진입조차 하지 못하는 상황이 되면 잘 안될 수 있음.

```java
// producer가 consumer보다 더 빨리 생성하는 경우 queue.put에서 blocking이 걸림
// 이 경우 flag 체크도 진입조차 못함
class BrokenPrimeProducer extends Thread {
  private final BlockingQueue<BigInteger> queue;

  private volatile boolean cancelled = false;

  BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
    this.queue = queue;
  }

  public void run() {
    try {
      BigInteger p = BigInteger.ONE;
      while (!cancelled) {
        queue.put(p = p.nextProbablePrime());
      }
    } catch (InterruptedException consumed) { }
  }

  public void cancel() { cancelled = true; }
}

void consumePrimes() throws InterruptedException {
  BlockingQueue<BigInteger> primes = ...;
  BrokenPrimeProducer producer = new BrokenPrimeProducer(primes);
  producer.start();
  try {
    while (needMorePrimes()) {
      consume(primes.take());
    }
  } finally {
    producer.cancel();
  }
}
```

Thread에는 interrupted라는 boolean status가 있음.

Blocking method인 `Thread.sleep`이나 `Object.wait`는 interrupt가 발생하면 InterruptException을 던짐.

Non-blocking 케이스의 경우 cancel되어야 하는 Thread가 interrupt flag를 pooling하면서 처리해야함. 안하면 그냥 그 interrupted flag는 방치됨. 즉 interrupt를 거는 것은 단순히 interrupt를 하라는 메시지만 남기는 것 뿐임. 처리는 직접 해줘야 함.

```java
public class Thread {
  // interrupt a thread
  public void interrupt() { ... }

  public boolean isInterrupted() { ... }

  // clear interrupt status & return previous status
  // this is the only way to clear interrupt status
  // so, BE CAREFUL to use this method
  public static boolean interrupted() { ... }
}
```

하지만 어찌됬건 interrupt를 하는 방법이 최선임. cancel을 사용하는 것 보다 interrupt flag를 활용하자.

```java
class PrimeProducer extends Thread {
  private final BlockingQueue<BigInteger> queue;

  PrimeProducer(BlockingQueue<BigInteger> queue) {
    this.queue = queue;
  }

  public void run() {
    try {
      BigInteger p = BigInteger.ONE;
      // TODO
      // 여기서 체크를 해주는게 p.nextProbablePrime()를 안해주는 의미가 있다는데
      // 그럼 InterruptedException 안던져지는거 아님?
      while (!Thread.currentThread().isInterrupted()) {
        // queue.put이 어차피 interrupt 걸렸을 경우 InterruptedException 던짐
        queue.put(p = p.nextProbablePrime());
      }
    } catch (InterruptedException consumed) {
      /* Allow thread to exit */
    }
  }

  public void cancel() { interrupt(); }
}
```

### Interruption Policies

Thread는 interrupt가 발생했을 때 무엇을 할 것인지? 어떠한 작업들이 atomic한지? interrupt에 대해서 얼마나 빨리 반응해야 하는지 등의 정책이 있어야 함.

interrupt는 작업을 중단시키라는 의미일 수도 있고 작업을 수행하는 thread를 중단시키라는 의미일 수도 있음.

작업 코드 자체 (eg. Runnable을 구현한 녀석)에서는 interrupt가 어떤 의미인지 가정을 해서는 안되고 Thread를 소유하는 녀석이 처리할 수 있게 interrupt 상태를 유지해야 함. `Thread.currentThread().interrupt()`를 통해 유지하거나 InterruptedException을 던져야 함.

### Responding to Interruption

`Thread.sleep` 또는 `BlockingQueue.put`같은 것을 쓰면 `InterruptedException`이 발생할 수 있음. 다음의 두 가지 전략을 처리할 수 있음.

- `InterruptedException`를 상위로 propagation시킨다.
- `InterruptedException` 을 잡아서 제거된 interrupt 상태를 되돌려놓는다.

todo

### Example: Timed Run

### Cancellation Via Future

```java
public static void timedRun(Runnable r, long timeout, TimeUnit unit)
    throws InterruptedException {
  Future<?> task = taskExec.submit(r);
  try {
    task.get(timeout, unit);
  } catch (TimeoutException e) {
    // task will be cancelled below
  } catch (ExecutionException e) {
    // exception thrown in task; rethrow
    throw launderThrowable(e.getCause());
  } finally {
    // Harmless if task already completed
    task.cancel(true); // interrupt if running
  }
}
```

### Dealing with Non-interruptible Blocking

todo

```java
public class ReaderThread extends Thread {
  private final Socket socket;
  private final InputStream in;

  public ReaderThread(Socket socket) throws IOException {
    this.socket = socket;
    this.in = socket.getInputStream();
  }

  public void interrupt() {
    try {
      socket.close();
    } catch (IOException ignored) {
    } finally {
      super.interrupt();
    }
  }

  public void run() {
    try {
      byte[] buf = new byte[BUFSZ];
      while (true) {
        int count = in.read(buf);
        if (count < 0) {
          break;
        } else if (count > 0) {
          processBuffer(buf, count);
        }
      }
    } catch (IOException e) { /* Allow thread to exit */ }
  }
}
```

### Encapsulating Nonstandard Cancellation with Newtaskfor

```java
// custom Callable with cancel interface
public interface CancellableTask<T> extends Callable<T> {
  void cancel();
  RunnableFuture<T> newTask();
}

// custom ThreadPoolExecutor using CancellableTask interface
@ThreadSafe
public class CancellingExecutor extends ThreadPoolExecutor {
  ...
  protected<T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
    if (callable instanceof CancellableTask) {
      return ((CancellableTask<T>) callable).newTask();
    } else {
      return super.newTaskFor(callable);
    }
  }
}

// CancellableTask 사용법. 이걸 사용하면 socket.close를 알아서 할 수 있음.
// hooking의 기능에서 좋음.
public abstract class SocketUsingTask<T> implements CancellableTask<T> {
  @GuardedBy("this") private Socket socket;

  protected synchronized void setSocket(Socket s) { socket = s; }

  public synchronized void cancel() {
    try {
      if (socket != null) {
        socket.close();
      }
    } catch (IOException ignored) { }
  }

  public RunnableFuture<T> newTask() {
    return new FutureTask<T>(this) {
      public boolean cancel(boolean mayInterruptIfRunning) {
        try {
          SocketUsingTask.this.cancel();
        } finally {
          return super.cancel(mayInterruptIfRunning);
        }
      }
    };
  }
}
```

## 7.2. Stopping a Thread based Service

Thread에 대한 엄밀한 소유권은 없음. But thread를 생성한 객체가 해당 Thread를 소유한다고 생각하면 Thread pool이 해당 thread를 소유한다고 생각할 수 있음. 이런 식으로 캡슐화를 하면 thread에 대한 관리를 캡슐화된 객체 단위로 할 수 있음. ExecutorService가 대표적인 예시임(shutdown, shutdownNow를 제공).

### Example: A Logging Service

Producer consumer example.

```java
// LogWriter의 log를 호출하는 쪽이 producer, LoggerThread가 consumer가 됨
// But 이 코드는 queueing된 log를 다 날려버린다는 문제와
// queue가 가득 차서 queue.put에서 blocking된 producer가 계속 기다릴 수도 있음
public class LogWriter {
  private final BlockingQueue<String> queue;
  private final LoggerThread logger;

  public LogWriter(Writer writer) {
    this.queue = new LinkedBlockingQueue<String>(CAPACITY);
    this.logger = new LoggerThread(writer);
  }

  public void start() { logger.start(); }

  public void log(String msg) throws InterruptedException {
    queue.put(msg);
  }

  private class LoggerThread extends Thread {
    private final PrintWriter writer;
    ...
    public void run() {
      try {
        while (true) {
          writer.println(queue.take());
        }
      } catch (InterruptedException ignored) {
        // LoggerThread.interrupt가 발생한 경우
        // eg. 이 thread에서 무한 대기 상태
      } finally {
        writer.close();
      }
    }
  }
}
```

shutdownRequested flag 활용.

```java
// gracefully shutdown을 위해 이렇게 하고
// shutdownRequested일 경우 뒷처리를 하고 종료할 수도 있음

// But shutdownRequested 변수 부분에서 race condition이 발생할 수 있음
// 그렇다고 race condition을 해결하기 위해 log자체에 lock을 걸어도
// put에서 blocking된 상태가 발생할 수 있음?!?!
public void log(String msg) throws InterruptedException {
  if (shutdownRequested) {
    throw new IllegalStateException("logger is shut down");
  }
  queue.put(msg);
}
```

reservation라는 변수를 활용.

```java
// reservation이라는 변수를 활용해서 shutdown 시점에 put 요청을 했지만
// blocking된 상태인 log들을 모두 처리할 수 있게 만듬
public class LogService {
  private final BlockingQueue<String> queue;
  private final LoggerThread loggerThread;
  private final PrintWriter writer;
  @GuardedBy("this") private boolean isShutdown;
  @GuardedBy("this") private int reservations;

  public void start() { loggerThread.start(); }

  public void stop() {
    synchronized (this) { isShutdown = true; }
    loggerThread.interrupt();
  }

  public void log(String msg) throws InterruptedException {
    synchronized (this) {
      if (isShutdown) {
        throw new IllegalStateException(...);
      }
      ++reservations;
    }
    queue.put(msg);
  }

  private class LoggerThread extends Thread {
    public void run() {
      try {
        while (true) {
          try {
            synchronized (this) {
              if (isShutdown && reservations == 0) {
                break;
              }
            }
            String msg = queue.take();
            synchronized (this) { --reservations; }
            writer.println(msg);
          } catch (InterruptedException e) { /* retry */ }
        }
      } finally {
        writer.close();
      }
    }
  }
}
```

### ExecutorService Shutdown

flag를 써서 thread를 직접 죽이지 않고 ExecutorService의 shutdown을 이용할 수도 있음.

```java
public class LogService {
  private final ExecutorService exec = newSingleThreadExecutor();
  ...
  public void start() { }

  public void stop() throws InterruptedException {
    try {
      // shutdown과 awaitTermination는 보통 같이 쓰임
      // 종료 요청을 하고 종료되기 까지 기다림
      // exec.awaitTermination의 return 값 가지고 종료 여부에 따라 처리해줘야함
      exec.shutdown();
      exec.awaitTermination(TIMEOUT, UNIT);
    } finally {
      writer.close();
    }
  }

  public void log(String msg) {
    try {
      exec.execute(new WriteTask(msg));
    } catch (RejectedExecutionException ignored) { }
  }
}
```

### Poison Pills

Producer가 queue에서 독약 객체를 만나면 종료하는 방법. 독약 객체보다 먼저들어간 객체들은 처리됨. 한번 독약 객체를 넣고 나서 더이상 다른 task를 넣지 못하게 막아줘야함.

Producer가 여러개인 경우 consumer에서는 producer 개수 만큼 독약 객체가 들어오면 종료. Consumer가 여러개인 경우 producer가 consumer 개수 만큼 독약 객체를 넣어주면 됨.

```java
public class IndexingService {
  private static final File POISON = new File("");
  private final BlockingQueue<File> queue;
  private final FileFilter fileFilter;
  private final File root;

  private final CrawlerThread producer = new CrawlerThread();
  private final IndexerThread consumer = new IndexerThread();

  public class CrawlerThread extends Thread {
    public void run() {
      try {
        crawl(root);
      } catch (InterruptedException e) {
        /* fall through */
      } finally {
        // interrupt 걸리면 queue에 POISON 객체를 쌓아버림
        while (true) {
          try {
            queue.put(POISON);
            break;
          } catch (InterruptedException e1) { /* retry */ }
          }
        }
    }

    private void crawl(File root) throws InterruptedException {
    ...
    }
  }

  public class IndexerThread extends Thread {
    public void run() {
      try {
        while (true) {
          File file = queue.take();
          // POISON 객체를 만나면 끝
          if (file == POISON) {
            break;
          }

          indexFile(file);
        }
      } catch (InterruptedException consumed) { }
    }
  }

  public void start() {
    producer.start();
    consumer.start();
  }

  public void stop() { producer.interrupt(); }

  public void awaitTermination() throws InterruptedException {
    consumer.join();
  }
}
```

### Example: A One-shot Execution Service

TODO

```java
boolean checkMail(Set<String> hosts, long timeout, TimeUnit unit)
    throws InterruptedException {
  ExecutorService exec = Executors.newCachedThreadPool();
  final AtomicBoolean hasNewMail = new AtomicBoolean(false);
  try {
    for (final String host : hosts) {
      exec.execute(new Runnable() {
        public void run() {
          if (checkMail(host)) {
            hasNewMail.set(true);
          }
        }
      });
    }
  } finally {
    exec.shutdown();
    exec.awaitTermination(timeout, unit);
  }
  return hasNewMail.get();
}
```

### Limitations of Shutdownnow

TODO

```java
public class TrackingExecutor extends AbstractExecutorService {
  private final ExecutorService exec;
  private final Set<Runnable> tasksCancelledAtShutdown = Collections.synchronizedSet(new HashSet<Runnable>());
  ...
  public List<Runnable> getCancelledTasks() {
    if (!exec.isTerminated()) {
      throw new IllegalStateException(...);
    }
    return new ArrayList<Runnable>(tasksCancelledAtShutdown);
  }

  public void execute(final Runnable runnable) {
    exec.execute(new Runnable() {
      public void run() {
        try {
          runnable.run();
        } finally {
          if (isShutdown() && Thread.currentThread().isInterrupted()) {
            tasksCancelledAtShutdown.add(runnable);
          }
        }
      }
    });
  }
  // delegate other ExecutorService methods to exec
}
```

```java
public abstract class WebCrawler {
  private volatile TrackingExecutor exec;
  @GuardedBy("this")
  private final Set<URL> urlsToCrawl = new HashSet<URL>();
  ...
  public synchronized void start() {
    exec = new TrackingExecutor(Executors.newCachedThreadPool());
    for (URL url : urlsToCrawl) {
      submitCrawlTask(url);
    }
    urlsToCrawl.clear();
  }

  public synchronized void stop() throws InterruptedException {
    try {
      saveUncrawled(exec.shutdownNow());
      if (exec.awaitTermination(TIMEOUT, UNIT)) {
        saveUncrawled(exec.getCancelledTasks());
      }
    } finally {
      exec = null;
    }
  }

  protected abstract List<URL> processPage(URL url);

  private void saveUncrawled(List<Runnable> uncrawled) {
    for (Runnable task : uncrawled) {
      urlsToCrawl.add(((CrawlTask) task).getPage());
    }
  }

  private void submitCrawlTask(URL u) {
    exec.execute(new CrawlTask(u));
  }

  private class CrawlTask implements Runnable {
    private final URL url;
    ...
    public void run() {
      for (URL link : processPage(url)) {
        if (Thread.currentThread().isInterrupted()) {
          return;
        }
        submitCrawlTask(link);
      }
    }

    public URL getPage() { return url; }
  }
}
```

## 7.3. Handling Abnormal Thread Termination

Multi threaded application에서는 에러가 발생하면 에러를 console에 찍음. But single thread application과는 다르게 아무도 인지 못할 수도 있음. Thread level에서 던지고 해당 thread만 죽기 때문임.

Thread level에서 exception을 던져버리면 해당 thread가 죽어버림. Thread pool에서 해당 thread가 죽어버렸는데 다시 안살리면 시스템 전체가 멈출 수 있음. 예를 들면 GUI application에서 event dispatch thread가 죽어버리면 event를 처리하지 못하고 멈춰버릴 수 있음. 그래서 보통 try-catch로 감싸서 죽는 것을 시스템에 알려주던지 해야 함.

Threadpool을 작성할 때는 새 thread를 만들거나 이미 충분한 thread가 있을 시 해당 thread를 그냥 죽여버리는 전략을 취할 수 있음.

```java
public void run() {
  Throwable thrown = null;
  try {
    while (!isInterrupted()) {
      runTask(getTaskFromWorkQueue());
    }
  } catch (Throwable e) {
    thrown = e;
  } finally {
    // 이런 식으로 알려주는게 좋음
    threadExited(this, thrown);
  }
}
```

### Uncaught Exception Handlers

Thread별로 `thread.setUncaughtExceptionHandler`를 사용해서 Exception handler 설정 가능. 기본 동작은 `System.err`에 stack trace를 찍는 것임.

Thread pool을 대상으로 `UncaughtExceptionHandler`을 설정하려면 `ThreadPoolExecutor`를 생성할 때 thread 생성을 담당하는 `ThreadFactory`를 넘겨주면 된다 (?? 이해안됨). 자바에서 제공하는 기본 thread pool에서는 try-finally를 사용해서 thread pool에 thread가 종료된다는 사실을 알려서 다른 thread로 대체될 수 있게 함.

참고로 execute로 제출된 task들만 UncaughtExceptionHandler로 처리할 수 있음. submit으로 제출된 것은 `future.get()`을 했을 때 ExecutionException로 감싸져서 던져짐.

```java
// exception handler interface
// thread.setUncaughtExceptionHandler 로 설정 가능
public interface UncaughtExceptionHandler {
  void uncaughtException(Thread t, Throwable e);
}

// custom exception handler
public class UEHLogger implements Thread.UncaughtExceptionHandler {
  public void uncaughtException(Thread t, Throwable e) {
    Logger logger = Logger.getAnonymousLogger();
    logger.log(Level.SEVERE, "Thread terminated with exception: " + t.getName(), e);
  }
}
```

## 7.4. JVM Shutdown

Jvm은 다음의 경우에 종료된다

- 마지막 non-daemon thread가 종료되었을 경우
- System.exit이 호출되거나 등 platform level에서의 종료 신호를 받았을 때 (Ctrl + c나 SIGINT 등)
- Runtime.halt에 의해 강제종료 되거나 os에 의해 jvm process가 죽었을 경우 (SIGKILL 같은거)

### Shutdown Hooks

`Runtime.getRuntime().addShutdownHook`로 jvm shutdown hook을 등록할 수 있음. But shutdownHook의 실행 순서는 보장되지 않음. 종료되고 있는 시점에 다른 thread가 실행되고 있으면 그 thread랑 hook이 같이 돌음. 모든 shutdownHook가 실행되고 나면 runFinalizersOnExit flag가 true인 경우 finalizer를 실행시킨 후 종료하고 아니면 바로 종료함. abrupt일 경우 hook이나 finalizer가 실행되지 않고 바로 종료됨.

shutdown hook나 finalizer가 종료되지 않으면 jvm도 계속 종료되지 않음 이 경우 abrupt시켜줘야함.

shutdown hook은 보통 resource cleanup등에서 사용되는데 병렬적으로 실행되어서 서비스간 문제가 발생할 수 있음. 그래서 race condition등을 피하기 위해 한개의 shutdown hook을 사용하는 것이 좋음. 그냥 종료할 때 single thread로 clean up해주는게 좋음 순서도 필요할 수 있고.

```java
public void start() {
  // register shutdown hooks
  Runtime.getRuntime().addShutdownHook(new Thread() {
    public void run() {
      try { LogService.this.stop(); }
      catch (InterruptedException ignored) {}
    }
  });
}
```

### Daemon Threads

Thread는 normal thread랑 daemon thread가 있음. jvm이 실행될 때 main thread를 제외하고는 daemon thread임. Thread가 생성될 때 해당 thread가 생성되는 thread의 상태를 받음. 그래서 보통 만들면 main thread의 상태를 받아서 normal thread임.

Jvm이 종료될 때 normal thread가 모두 종료된 것을 확인하고 shutdown process를 진행함. Daemon thread는 돌아가고 있든 말든 무시함. 그래서 Daemon thread의 자원같은게 정리되지 못할 수 있기 때문에 clean up이 없어도 되는 경우에만 사용하는게 좋음 (eg. 기간이 만료된 app내의 cache 제거).

### Finalizers

finalize method는 실행될지 확신할 수 없고 대부분 try-finally로 대체될 수 있음. 유일한 예외는 native method에서 사용한 resource를 정리하는 경우 그 외는 finalize method는 사용하지 마라.
