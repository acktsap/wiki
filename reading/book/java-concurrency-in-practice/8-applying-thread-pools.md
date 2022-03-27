# 8. Applying Thread Pools

- [8. Applying Thread Pools](#8-applying-thread-pools)
  - [8.1. Implicit Couplings Between Tasks and Execution Policies](#81-implicit-couplings-between-tasks-and-execution-policies)
    - [Thread Starvation Deadlock](#thread-starvation-deadlock)
    - [Long-running Tasks](#long-running-tasks)
  - [8.2. Sizing Thread Pools](#82-sizing-thread-pools)
  - [8.3. Configuring ThreadPoolExecutor](#83-configuring-threadpoolexecutor)
    - [Thread Creation and Teardown](#thread-creation-and-teardown)
    - [Managing Queued Tasks](#managing-queued-tasks)
    - [Saturation Policies](#saturation-policies)
    - [Thread Factories](#thread-factories)
    - [Customizing ThreadPoolExecutor After Construction](#customizing-threadpoolexecutor-after-construction)
  - [8.4. Extending ThreadPoolExecutor](#84-extending-threadpoolexecutor)
  - [8.5. Parallelizing Recursive Algorithms](#85-parallelizing-recursive-algorithms)
    - [Example: A Puzzle Framework](#example-a-puzzle-framework)

## 8.1. Implicit Couplings Between Tasks and Execution Policies

task들이 일정하게 나뉘면 좋겠지만 몇몇 task들은 딱 떨어지게 나누어지지 않음

- Dependent tasks : 다른 task에 의존하는 task가 있는 경우.
- Tasks that exploit thread confinement : eg. single thread pool일때 synchronization을 걸지 않아도 된다는 사실을 남용하는 경우.
- Response-time-sensitive tasks : GUI 프로그램 같은거는 response time이 길어지면 UX가 개판됨. task가 단순하지 않음.
- Tasks that use ThreadLocal : thread pool안의 thread들은 동적으로 조절됨. 심지어 unchecked exception이 발생하면 새로 만들어짐. task가 thread local에 의존하면 안됨.

### Thread Starvation Deadlock

Thread pool에서 돌아가고 있는 task가 work queue에 들어가 있는 다른 task에 의존하거나 하면 starvation deadlock이 발생할 수 있음. 이런 경우를 주석에 명시하라.

thread pool이 db connection pool에 의존하는 경우 db connection pool size에 암시적으로 의존적임. 이런 경우도 있음.

```java
public class ThreadDeadlock {
  // single threaded
  ExecutorService exec = Executors.newSingleThreadExecutor();
  public class RenderPageTask implements Callable<String> {
    public String call() throws Exception {
      Future<String> header = exec.submit(new LoadFileTask("header.html"));
      Future<String> footer = exec.submit(new LoadFileTask("footer.html"));
      String page = renderBody();
      // Will deadlock -- task waiting for result of subtask
      return header.get() + page + footer.get();
    }
  }
}
```

### Long-running Tasks

Long-running task랑 Short-running task가 같이 있으면 clogging (막힘) 유발 가능. 이를 해결하는 대표적인 방법은 task를 submit할 때 time out을 거는 것.

## 8.2. Sizing Thread Pools

Pool size는 너무 크면 CPU랑 memory를 먹으려고 경쟁이 심함. resource exhaustion도 발생할 수 있음. 그렇다고 너무 작으면 성능이 안나옴.

CPU는 몇개인지? 메모리는 충분한지? I/O를 주로 하는지? Computing을 주로 하는지? 연산이 db connection pool처럼 부족한 resource를 사용하는지? 에 따라 정책이 달라질 수 있음.

- Compute-intensive tasks : 일반적으로 cpu가 놀지 않기 때문에 `1 + n_core`. paging fault같은거 때문에 여분의 thread가 더 필요할 수도 있음.
- I/O intensive tasks : waiting time과 computing time에 따라 다를 수 있음.
  - pool size : n_core * target_cpu_utilication * (1 + Waiting_Time / Computing_Time)

Core수는 이렇게 가져옴 : `Runtime.getRuntime().availableProcessors()`

db connection pool에 크게 의존하는 경우 db connection pool size로 제한하는게 최적일 수 있음.

> DB를 주로 사용하는 enterprise application에서는 pool size가 core수보다 클 수 밖에 없음. N * Runtime.getRuntime().availableProcessors() 에서 N을 늘려가며 benchmark를 해서 적절할 pool size를 정해야 함. linear하게 증가하지 않는 지점을 찾아.

## 8.3. Configuring ThreadPoolExecutor

ThreadPoolExecutor는 Executors framework의 newCachedThreadPool, newFixedThreadPool, newScheduled-ThreadExecutor를 사용할 때 사용됨. But 이를 직접 생성해서 사용할 수 있음.

### Thread Creation and Teardown

```java
/*
  corePoolSize : task가 없더라도 유지하는 size
  maximumPoolSize : task가 늘어났을 때 늘어나는 최대 thread 개수
  keepAliveTime : idle상태로 이 시간보다 오래 존재한 thread는 종료 대상이 됨 (corePoolSize 까지)
  workQueue : pool size가 coreSize랑 같으면 이 queue가 가득 차야 여분의 thread를 만듬 (maximumPoolSize 까지)
*/
public ThreadPoolExecutor(
    int corePoolSize,
    int maximumPoolSize,
    long keepAliveTime,
    TimeUnit unit,
    BlockingQueue<Runnable> workQueue,
    ThreadFactory threadFactory,
    RejectedExecutionHandler handler) { ... }
```

- newFixedThreadPool : core size == max size, timeout == 0ms (infinite)
- newCachedThreadPool : core size == 0, max size == Integer.MAX_VALUE, timeout == 60000ms

### Managing Queued Tasks

Client가 task를 처리하는 속도보다 더 많이 보내면 queue가 가득 차서 OOM 에러가 날 수 있음. 이러면 throttling을 해서 client가 더이상 보내지 않게 해야 함. 이를 위해서는 ThreadPoolExecutor를 만들 때 bounded queue를 지정해주면 됨. Queue의 종류는 다음과 같음.

- ArrayBlockingQueue : FIFO, array로 내부적으로 저장.
- LinkedBlockingQueue : FIFO, list로 내부적으로 저장. capacity를 지정하지 않으면 Integer.MAX_VALUE가 됨 (unbounded 행). newFixedThreadPool이 기본적으로 unbounded를 씀.
- PriorityBlockingQueue : 우선순위 가지고 뺌
- SynchronousQueue : 넣으면 아무나 빼갈 때 까지 기다리는 녀석. 별도의 queue는 없음. 이거 쓰면 pool size가 max가 되고 새로 task를 받으면 reject해버림. 그렇기 때문에 unbounded pool에서만 쓰는게 좋음. newCachedThreadPool이 기본적으로 씀.

### Saturation Policies

`ThreadPoolExecutor`의 bounded queue가 가득차거나 이미 shutdown 된 thread pool에 submit이 발생하는 경우 Saturation Policies (포화 정책)이 필요. `setRejectedExecutionHandler`로 설정 가능.

RejectedExecutionHandler의 구현체들

- AbortPolicy : throws unchecked exception. default policy.
- DiscardPolicy : 그냥 버림 ㅅㄱ
- DiscardOldestPolicy : 오래된 task를 버리고 resubmit. Priority queue일 경우 우선순위가 제일 높은 것을 버리기 때문에 같이 쓰면 안맞음.
- CallerRunsPolicy : caller가 실행
  - WebServer에서 사용하면 thread pool이 가득 찰 경우 request를 받은 main thread가 실행 -> request가 더 안쌓이고 tcp layer에 쌓임.
  - That is, as the server becomes overloaded, the overload is gradually pushed outward - from the pool threads to the work queue to the application to the TCP layer, and eventually to the client - enabling more graceful degradation under load

```java
// setRejectedExecutionHandler usage example
ThreadPoolExecutor executor
    = new ThreadPoolExecutor(N_THREADS, N_THREADS,
          0L, TimeUnit.MILLISECONDS,
          new LinkedBlockingQueue<Runnable>(CAPACITY));
executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
```

```java
// queue가 full일 때 block하는 것처럼 동작할 수 있는 executor 구현체 (그런 미리 정의된 policy가 없음)
// 이거를 쓸 때는 unbounded queue를 사용 (queue size랑 injection rate를 둘다 bound할 이유는 없음)
@ThreadSafe
public class BoundedExecutor {
  private final Executor exec;
  private final Semaphore semaphore;

  // bound size는 thread pool size + 기다릴 수 있는 task수로 하셈
  public BoundedExecutor(Executor exec, int bound) {
    this.exec = exec;
    this.semaphore = new Semaphore(bound);
  }

  public void submitTask(final Runnable command) throws InterruptedException {
    semaphore.acquire();
    try {
      exec.execute(new Runnable() {
        public void run() {
          try {
            command.run();
          } finally {
            semaphore.release();
          }
        }
      });
    } catch (RejectedExecutionException e) {
      semaphore.release();
    }
  }
}
```

### Thread Factories

기본 `ThreadFactory`는 non-daemon에 아무 설정 없는 thread를 만듬. But customizing이 가능.

Custom 설정을 하면 UncaughtExceptionHandler도 설정 가능, debug log를 잘 찍는 custom Thread class 사용 가능, daemon status도 설정 가능, 로깅에서 알아보기 쉽게 이름도 설정 가능함.

`privilegedThreadFactory`를 사용해서 Thread Pool을 생성하면 각 Thread들은 `privilegedThreadFactory`를 호출하는 thread와 동일한 permission, AccessControlContext, contextClassLoader가 설정됨.

```java
// ThreadFactory interface
public interface ThreadFactory {
  Thread newThread(Runnable r);
}
```

```java
// poolName을 지정한 Custom Thread Factory
public class MyThreadFactory implements ThreadFactory {
  private final String poolName;

  public MyThreadFactory(String poolName) {
    this.poolName = poolName;
  }

  public Thread newThread(Runnable runnable) {
    return new MyAppThread(runnable, poolName);
  }
}

public class MyAppThread extends Thread {
  public static final String DEFAULT_NAME = "MyAppThread";
  private static volatile boolean debugLifecycle = false;
  private static final AtomicInteger created = new AtomicInteger();
  private static final AtomicInteger alive = new AtomicInteger();
  private static final Logger log = Logger.getAnonymousLogger();

  public MyAppThread(Runnable r) { this(r, DEFAULT_NAME); }

  public MyAppThread(Runnable runnable, String name) {
    super(runnable, name + "-" + created.incrementAndGet());
    // custom UncaughtExceptionHandler
    setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread t, Throwable e) {
          log.log(Level.SEVERE,
          "UNCAUGHT in thread " + t.getName(), e);
        }
      });
  }

  public void run() {
    // Copy debug flag to ensure consistent value throughout.
    boolean debug = debugLifecycle;

    if (debug) log.log(Level.FINE, "Created " + getName());
    try {
      alive.incrementAndGet();
      super.run();
    } finally {
      alive.decrementAndGet();
      if (debug) log.log(Level.FINE, "Exiting " + getName());
    }
  }

  public static int getThreadsCreated() { return created.get(); }
  public static int getThreadsAlive() { return alive.get(); }
  public static boolean getDebug() { return debugLifecycle; }
  public static void setDebug(boolean b) { debugLifecycle = b; }
}
```

### Customizing ThreadPoolExecutor After Construction

ThreadPoolExecutor로 생성된 값들은 이후에 setter로 설정을 바꿀 수 있음.

```java
// Executors.newXXX로 생성된 것들도 바꿀 수 있음 (newSingleThreadExecutor 제외)
ExecutorService exec = Executors.newCachedThreadPool();
if (exec instanceof ThreadPoolExecutor) {
  ((ThreadPoolExecutor) exec).setCorePoolSize(10);
}
else {
  throw new AssertionError("Oops, bad assumption");
}
```

`Executors.unconfigurableExecutorService(executor)`를 사용하면 executor가 더이상 configuration이 불가능함. `Executors.newSingleThreadExecutor`로 생성된 pool이 이걸로 감싸져 있음. 사실상 이걸 무조건 해주는게 맞는거 같음.

## 8.4. Extending ThreadPoolExecutor

ThreadPoolExecutor는 상속해서 beforeExecute, afterExecute, terminate 같은 method들을 hook하기 쉽게 설계되어 있음.

- beforeExecute : 실행 되기 전에 실행. logging, timing, monitoring, or statistics gathering할때 사용 가능.
- afterExecute : task가 nornally return하거나 Exception을 던지면 호출. Error를 던지는 경우는 안호출.
- terminate : thread pool이 종료될 때 호출. resource를 release하거나 statistics를 finalize할 때 사용 가능.

```java
// custom ThreadPoolExecutor example
public class TimingThreadPool extends ThreadPoolExecutor {
  // beforeExecute에서 설정한 time을 afterExecute에서 가져갈 수 있게 ThreadLocal 사용
  private final ThreadLocal<Long> startTime = new ThreadLocal<Long>();
  private final Logger log = Logger.getLogger("TimingThreadPool");
  private final AtomicLong numTasks = new AtomicLong();
  private final AtomicLong totalTime = new AtomicLong();

  protected void beforeExecute(Thread t, Runnable r) {
    super.beforeExecute(t, r);
    log.fine(String.format("Thread %s: start %s", t, r));
    startTime.set(System.nanoTime());
  }

  protected void afterExecute(Runnable r, Throwable t) {
    try {
      long endTime = System.nanoTime();
      long taskTime = endTime - startTime.get();
      numTasks.incrementAndGet();
      totalTime.addAndGet(taskTime);
      log.fine(String.format("Thread %s: end %s, time=%dns", t, r, taskTime));
    } finally {
      super.afterExecute(r, t);
    }
  }

  protected void terminated() {
    try {
      log.info(String.format("Terminated: avg time=%dns", totalTime.get() / numTasks.get()));
    } finally {
      super.terminated();
    }
  }
}
```

## 8.5. Parallelizing Recursive Algorithms 

iterating하게 실행되고 있는 각 작업이 independent하면 parallelly 실행할 수 있음

```java
// 이거 대신
void processSequentially(List<Element> elements) {
  for (Element e : elements) {
    process(e);
  }
}

// 이거 사용 가능
void processInParallel(Executor exec, List<Element> elements) {
  for (final Element e : elements) {
    exec.execute(new Runnable() {
      public void run() { process(e); }
    });
  }
}
```

마찬가지로 recursivly 실행되는 각 작업이 다른 recursivly 실행되는 작업에 의존적이지 않으면 parallelly 실행할 수 있음

```java
// 이거 대신
public<T> void sequentialRecursive(List<Node<T>> nodes, Collection<T> results) {
  for (Node<T> n : nodes) {
    results.add(n.compute());
    sequentialRecursive(n.getChildren(), results);
  }
}

// 이거 사용 가능
public<T> void parallelRecursive(final Executor exec, List<Node<T>> nodes, final Collection<T> results) {
  for (final Node<T> n : nodes) {
    exec.execute(new Runnable() {
      public void run() {
        results.add(n.compute());
      }
    });
    parallelRecursive(exec, n.getChildren(), results);
  }
}

// 이렇게 실행
public<T> Collection<T> getParallelResults(List<Node<T>> nodes) throws InterruptedException {
  ExecutorService exec = Executors.newCachedThreadPool();
  Queue<T> resultQueue = new ConcurrentLinkedQueue<T>();
  parallelRecursive(exec, nodes, resultQueue);
  // 기다려 (멍멍)
  exec.shutdown();
  exec.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
  return resultQueue;
}
```

### Example: A Puzzle Framework 

todo
