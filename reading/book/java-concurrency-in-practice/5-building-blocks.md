# 5. Building Blocks

- [5. Building Blocks](#5-building-blocks)
  - [5.1. Synchronized Collections](#51-synchronized-collections)
    - [Problems with Synchronized Collections](#problems-with-synchronized-collections)
    - [Iterators and ConcurrentModificationException](#iterators-and-concurrentmodificationexception)
    - [Hidden Iterators](#hidden-iterators)
  - [5.2. Concurrent Collections](#52-concurrent-collections)
    - [ConcurrentHashMap](#concurrenthashmap)
    - [Additional Atomic Map Operations](#additional-atomic-map-operations)
    - [CopyOnWriteArrayList](#copyonwritearraylist)
  - [5.3. Blocking Queues and the Producer-consumer Pattern](#53-blocking-queues-and-the-producer-consumer-pattern)
    - [Example: Desktop Search](#example-desktop-search)
    - [Serial Thread Confinement](#serial-thread-confinement)
    - [Deques and Work Stealing](#deques-and-work-stealing)
  - [5.4. Blocking and Interruptible Methods](#54-blocking-and-interruptible-methods)
  - [5.5. Synchronizers](#55-synchronizers)
    - [Latches](#latches)
    - [FutureTask](#futuretask)
    - [Semaphores](#semaphores)
    - [Barriers](#barriers)
  - [5.6. Building an Efficient, Scalable Result Cache](#56-building-an-efficient-scalable-result-cache)

## 5.1. Synchronized Collections

### Problems with Synchronized Collections

아래 코드는 문제를 발생시킬 수 있음.

예를 들면 getLast와 deleteLast에서 동시에 size check -> deleteLast에서 lastIndex지움 -> getLast에서 get으로 lastIndex가져옴 -> ArrayIndexOutOfBoundsException 발생

```java
public static Object getLast(Vector list) {
  int lastIndex = list.size() - 1;
  return list.get(lastIndex);
}

public static void deleteLast(Vector list) {
  int lastIndex = list.size() - 1;
  list.remove(lastIndex);
}
```

이를 해결하려면 list전체에 lock을 거는 방법이 있음.

```java
public static Object getLast(Vector list) {
  synchronized (list) {
    int lastIndex = list.size() - 1;
    return list.get(lastIndex);
  }
}
public static void deleteLast(Vector list) {
  synchronized (list) {
    int lastIndex = list.size() - 1;
    list.remove(lastIndex);
  }
}
```

마찬가지로 아래 코드도 size check를 하고 iterating을 하는 사이 list가 변경될 수 있음.

```java
for (int i = 0; i < vector.size(); i++)
  doSomething(vector.get(i));
```

역시 list 전체에 lock을 걸면 해결 할 수 있음.

```java
synchronized (vector) {
  for (int i = 0; i < vector.size(); i++)
    doSomething(vector.get(i));
}
```

하지만 이렇게 lock을 많이 걸게 되면 lock이 너무 많이 걸려서 병렬 프로그래밍의 이점을 잃어버릴 수 있음.

### Iterators and ConcurrentModificationException

iterator는 concurrent modification를 방지하게 설계되어 있지는 않음. concurrent modification이 발생하는 경우 fail-fast (iterating이 시작할 때의 객체 상태랑 iterating 진행중의 객체 상태가 다른 경우 바로 ConcurrentModificationException를 던져버림).

```java
List<Widget> widgetList = Collections.synchronizedList(new ArrayList<Widget>());
...
// Compiled to use Iterator
// May throw ConcurrentModificationException
for (Widget w : widgetList)
  doSomething(w);
```

이를 방지하기 위해서는 iterating에 lock을 걸거나 clone해서 사용하는 방법이 있다. 물론 clone해서 사용하는 방법에는 clone하는 동안은 lock을 걸어야 함.

### Hidden Iterators

Iterator는 숨어있을 가능성이 있음. 그리고 개발자가 객체의 상태와 상태에 대한 lock이 떨어져 있을 수록 lock을 해야 하는지 잊기 쉬움. 그래서 가능하면 동기화 기법을 객체 내부적으로 캡슐화 해놓는게 좋음.

```java
public class HiddenIterator {
  @GuardedBy("this")
  private final Set<Integer> set = new HashSet<Integer>();

  public synchronized void add(Integer i) { set.add(i); }
  public synchronized void remove(Integer i) { set.remove(i); }

  public void addTenThings() {
    Random r = new Random();
    for (int i = 0; i < 10; i++)
      add(r.nextInt());

    // compiled into StringBuilder.append(Object) and invoking toString of set
    // which iterates set object
    // which makes this statement may throw ConcurrentModificationException
    System.out.println("DEBUG: added ten elements to " + set);
  }
}
```

위 코드보다는 이렇게 하는게 더 좋음.

```java
public class HiddenIterator {
  private final Set<Integer> set = Collections.synchronizedSet(new HashSet<Integer>());

  public void add(Integer i) { set.add(i); }
  public void remove(Integer i) { set.remove(i); }

  public void addTenThings() {
    Random r = new Random();
    for (int i = 0; i < 10; i++)
      add(r.nextInt());

    // compiled into StringBuilder.append(Object) and invoking toString of set
    // since "set" is synchronized, it doesn't throw ConcurrentModificationException
    System.out.println("DEBUG: added ten elements to " + set);
  }
}
```

## 5.2. Concurrent Collections

### ConcurrentHashMap

ConcurrentHashMap는 lock striping이라는 기법을 사용해서 제한된 갯수 만큼 동시에 처리할 수 있음. 기존의 HashTable이나 synchronizedMap에 비해 성능상 이점이 좋음

> ConcurrentHashMap는 hashcode해서 나온 bucket자체만 lock을 걸어서 다른 bucket끼리는 동시에 처리할 수 있음. But HashTable이나 synchronizedMap의 경우 해당 객체 자체에 synchronized를 걸어버려서 해당 객체 자체를 점유하게 됨.

### Additional Atomic Map Operations

ConcurrentHashMap가 구현하고 있는 `ConcurrentMap`에는 put-if-absent, remove-if-equal,  and replace-if-equal과 같은 atomic  operations들이 구현되어 있음.

```java
public interface ConcurrentMap<K,V> extends Map<K,V> {
  // Insert into map only if no value is mapped from K
  V putIfAbsent(K key, V value);

  // Remove only if K is mapped to V
  boolean remove(K key, V value);

  // Replace value only if K is mapped to oldValue
  boolean replace(K key, V oldValue, V newValue);

  // Replace value only if K is mapped to some value
  V replace(K key, V newValue);
}
```

### CopyOnWriteArrayList

CopyOnWriteArrayList를 사용하면 값을 추가할 때 마다 새로운 복사본을 집어넣기 때문에 iterator를 뽑아내서 사용하는 경우 별도의 lock이 필요하지 않고 ConcurrentModificationException이 발생하지 않음.

값을 집어넣을 때 마다 새로운 복사본을 집어넣는 과정은 비용이 크기 때문에 iterating이 modification보다 잦을 때 사용하는게 좋음. 대표적인 예시가 event listener를 처리할 때임. event listener iterating하면서 호출하는 부분이 새로운 event listener를 넣는 경우보다 잦음.

## 5.3. Blocking Queues and the Producer-consumer Pattern

Producer-consumer를 할 때 중간에 blocking queue를 둘 수 있음. Producer가 queue에 task를 쌓아두면 consumer는 producer랑 상관 없이 queue에서 task를 뽑아두는 식임.

- BlockingQueue : blocking queue core interface
  - LinkedBlockingQueue : FIFO, 내부적으로 Linked list로 관리
  - ArrayBlockingQueue : FIFO, 내부적으로 Array로 관리
  - PriorityBlockingQueue : Not FIFO, element들간 order
  - SynchronousQueue : No storage, enqueue or dequeue될 때 까지 기다림. producer랑 consumer가 많은 경우 좋음

### Example: Desktop Search

producer랑 consumer가 쓰는 자원이 다른 경우 (하나는 CPU, 하나는 I/O) 이 작업들은 동시에 실행될 수 있어서 성능상 이점을 얻을 수 있음.

```java
public class FileCrawler implements Runnable {
  private final BlockingQueue<File> fileQueue;
  private final FileFilter fileFilter;
  private final File root;

  ...

  public void run() {
    try {
      crawl(root);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  private void crawl(File root) throws InterruptedException {
    File[] entries = root.listFiles(fileFilter);
    if (entries != null) {
      for (File entry : entries)
        if (entry.isDirectory())
          crawl(entry);
        else if (!alreadyIndexed(entry))
          fileQueue.put(entry);
    }
  }
}

public class Indexer implements Runnable {
  private final BlockingQueue<File> queue;

  public Indexer(BlockingQueue<File> queue) {
    this.queue = queue;
  }

  public void run() {
    try {
      while (true)
        indexFile(queue.take());
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}

// run indexing
public static void startIndexing(File[] roots) {
  BlockingQueue<File> queue = new LinkedBlockingQueue<File>(BOUND);
  FileFilter filter = new FileFilter() {
    public boolean accept(File file) { return true; }
  };

  for (File root : roots)
    new Thread(new FileCrawler(queue, filter, root)).start();
  for (int i = 0; i < N_CONSUMERS; i++)
    new Thread(new Indexer(queue)).start();
}
```

### Serial Thread Confinement

Blocking queue에서 element에 대한 ownership관리가 중요함. producer가 consumer에게 소유권을 넘고 producer가 element를 수정하지 않아야 함. 이를 위해 Serial Thread Confinement라는 기법을 통해 producer가 해당thread에 confinement된 객체를 consumer에 confinement되게 소유권을 이전함.

### Deques and Work Stealing

- BlockingDeque : blocking deque core interface
  - ArrayDeque : 내부적으로 Array로 관리
  - LinkedBlockingDeque : 내부적으로 Linked list로 관리

BlockingDeque는 work-stealing pattern에 적용할 수 있음. producer-consumer pattern에서 consumer가 각각의 deque를 가지고 자신의 일을 다 끝낸 consumer가 다른 consumer의 deque에서 제일 뒤에 있는 작업을 가져와서 처리함. work-stealing pattern은 consumer가 또한 작업을 생성하는 producer일 때 사용하면 병렬처리를 잘 할 수 있음.

## 5.4. Blocking and Interruptible Methods

Thread가 I/O를 기다리거나 Thread.sleep 등을 하면 BLOCKED, WAITING, TIMED_WAITING의 상태가 됨. 완료되었다는 신호를 받아야 Runnable상태로 돌아가서 다시 됨.

BlockingQueue::put, BlockingQueue::take, Thread.sleep 등의 Blocking method들은 InterruptedException을 발생시킬 수 있음. `Thread.currentThread().interrupt()`를 해도 InterruptedException를 발생시킬 수 있음.

InterruptedException이 발생하면 이를 그냥 던지거나 복구를 한 뒤 던지는 방법이 있음.

```java
public class TaskRunnable implements Runnable {
  BlockingQueue<Task> queue;
  ...
  public void run() {
    try {
      processTask(queue.take());
    } catch (InterruptedException e) {
      // restore interrupted status
      // ...
      Thread.currentThread().interrupt(); // throws exception
    }
  }
}
```

## 5.5. Synchronizers

### Latches

- Latch
  - Latch는 스스로가 terminal state가 되기 전까지 thread의 동작을 늦춤
  - 마치 문처럼 terminal state가 되기 전까지는 thread들이 동작을 하지 않으나 문이 열린 뒤로는 동작을 시작함.
  - But 한번 열린 문은 닫히지 않음
  - 그래서 one-time precondition이 완료되기 전까지 동작을 멈추는데 사용됨
- CountDownLatch
  - 특정 값으로 초기화 되었다가 event가 발생 하면 count를 내림 (countDown)
  - 사용하는 쪽에서는 `countDownLatch.await()`을 하면 count가 0이 될 때 까지 기다림

```java
public class TestHarness {
  public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
    final CountDownLatch startGate = new CountDownLatch(1);
    final CountDownLatch endGate = new CountDownLatch(nThreads);
    for (int i = 0; i < nThreads; i++) {
      Thread t = new Thread() {
        public void run() {
          try {
            startGate.await();
            try {
              task.run();
            } finally {
              endGate.countDown();
            }
          } catch (InterruptedException ignored) { }
        }
      };
      t.start();
    }

    long start = System.nanoTime();
    startGate.countDown(); // starts all thread
    endGate.await(); // waits for all thread to be completed
    long end = System.nanoTime();

    return end-start;
  }
}
```

### FutureTask

Future, Callable을 상속하며 3가지 상태가 있음 (waiting-to-run, running, completed)

`futureTask.get()`했을 때 값이 있으면 바로 리턴, 없으면 완료 될 때 까지 기다림. 한번 완료되면 계속 기다림

Executor Framework에서 async task를 표현하기 위해서 사용. 보통 오래 걸리는 작업을 해당 결과값을 요청받기 전에 미리 끝내놓을 때 사용

```java
public class Preloader {
  private final FutureTask<ProductInfo> future =
    new FutureTask<ProductInfo>(new Callable<ProductInfo>() {
      public ProductInfo call() throws DataLoadException {
        return loadProductInfo();
      }
    });

  private final Thread thread = new Thread(future);

  // 끝내는 방법을 제공. 생성자에서 자동으로 알아서 실행하면 여전히 로딩시간이 느려짐
  public void start() { thread.start(); }
  
  // start를 미리 해두고 get을 함
  public ProductInfo get() throws DataLoadException, InterruptedException {
    try {
      return future.get();
    } catch (ExecutionException e) { // future.get() throws ExecutionException
      Throwable cause = e.getCause();
      if (cause instanceof DataLoadException)
        throw (DataLoadException) cause;
      else
        throw launderThrowable(cause);
    }
  }

 /**
  * To throw as RuntimeException since ExecutionException.getCause returns Throwable
  *
  * If the Throwable is an Error, throw it; if it is a
  * RuntimeException return it, otherwise throw IllegalStateException
  */
  public static RuntimeException launderThrowable(Throwable t) {
    if (t instanceof RuntimeException)
      return (RuntimeException) t;
    else if (t instanceof Error)
      throw (Error) t;
    else
      throw new IllegalStateException("Not unchecked", t);
  }
}
```

### Semaphores

Semaphore은 공유 자원에 동시에 몇개가 접근할 수 있는지를 제어함.

Semaphore는 가상 permit을 관리함. Thread가 Semaphore에 요청을 하면 permit을 받아서 작업하고 완료 후 permit을 반납하는 것처럼 동작함. 실제로는 아무나 acquire/release를 할 수 있음.

한정된 자원에 요청을 했을 때 자원이 없는 경우 blocking상태로 만들 고 싶을 때 좋음. 예시로 db connection pool이 있음. BlockingQueue로 그럴 때 사용할 수 있음.

Semaphore는 또한 일단적인 Collection을 blocking bounded상태로 만들 때 사용할 수 있음.

```java
public class BoundedHashSet<T> {
  private final Set<T> set;
  private final Semaphore sem;

  // collection 크기 제한
  public BoundedHashSet(int bound) {
    this.set = Collections.synchronizedSet(new HashSet<T>());
    this.sem = new Semaphore(bound);
  }

  public boolean add(T o) throws InterruptedException {
    sem.acquire();
    boolean wasAdded = false;
    try {
      wasAdded = set.add(o);
      return wasAdded;
    } finally {
      if (!wasAdded)
        sem.release();
    }
  }

  public boolean remove(Object o) {
    boolean wasRemoved = set.remove(o);
    if (wasRemoved)
      sem.release();
    return wasRemoved;
  }
}
```

### Barriers

모든 thread가 barrier 위치에 도착해야 관문이 열림. Latch가 event를 위한 동기화 클래스라면 barrier는 thread를 위한 동기화 클래스임.

CyclicBarrier를 이용하면 thread들이 특정한 배리어 포인트에서 반복적으로 만나는 기능을 모니터링 할 수 있음.

Exchanger라는 객체도 있는데 이건 2개의 thread가 연결되는 barrier임. Barrier point에 도달하면 서로 가지고 있는 값을 교환하는 식으로 함. 이건 양쪽 thread가 서로 대칭되는 작업을 할 때 좋음 (producer <-> consumer)

```java
public class CellularAutomata {
  private final Board mainBoard;
  private final CyclicBarrier barrier;
  private final Worker[] workers;

  public CellularAutomata(Board board) {
    this.mainBoard = board;

    int count = Runtime.getRuntime().availableProcessors();
    // count 개수만큼의 thread가 barrier point에 도달하면
    // mainBoard.commitNewValues()의 action을 취함 그리고 다시 barrier point를 준비
    // 즉. 여기서는 영역을 나눠서 board의 상태르 변경하고 barrier point에
    // 모든 thread들이 도달하면 종합함
    this.barrier = new CyclicBarrier(count, () -> mainBoard.commitNewValues());

    // core 개수만큼 board 영역을 나눠서 할당
    this.workers = new Worker[count];
    for (int i = 0; i < count; i++)
      workers[i] = new Worker(mainBoard.getSubBoard(count, i));
  }

  private class Worker implements Runnable {
    private final Board board;

    public Worker(Board board) { this.board = board; }

    public void run() {
      while (!board.hasConverged()) {
        for (int x = 0; x < board.getMaxX(); x++)
        for (int y = 0; y < board.getMaxY(); y++)
        board.setNewValue(x, y, computeValue(x, y));
        try {
          // barrier point
          barrier.await();
        } catch (InterruptedException ex) {
          return;
        } catch (BrokenBarrierException ex) {
          return;
        }
      }
    }
  }

  public void start() {
    for (int i = 0; i < workers.length; i++)
      new Thread(workers[i]).start();
    mainBoard.waitForConvergence();
  }
}
```

## 5.6. Building an Efficient, Scalable Result Cache

```java
public interface Computable<A, V> {
  V compute(A arg) throws InterruptedException;
}

public class ExpensiveFunction implements Computable<String, BigInteger> {
  public BigInteger compute(String arg) {
    // after deep thought...
    return new BigInteger(arg);
  }
}

// 이러면 여러 Thread 접근할 때 단 하나만 접근이 가능해서 더 느려질 수 있음.
public class Memorizer1<A, V> implements Computable<A, V> {
  @GuardedBy("this")
  private final Map<A, V> cache = new HashMap<A, V>();
  private final Computable<A, V> c;

  public Memorizer1(Computable<A, V> c) {
    this.c = c;
  }

  public synchronized V compute(A arg) throws InterruptedException {
    V result = cache.get(arg);
    if (result == null) {
      result = c.compute(arg);
      cache.put(arg, result);
    }
    return result;
  }
}

// ConcurrentHashMap를 써서 동시성 문제를 크게 개선함
// But 동시에 compute를 접근해서 같은 연산을 반복할 수 있음
public class Memorizer2<A, V> implements Computable<A, V> {
  private final Map<A, V> cache = new ConcurrentHashMap<A, V>();
  private final Computable<A, V> c;

  public Memorizer2(Computable<A, V> c) { this.c = c; }

  public V compute(A arg) throws InterruptedException {
    V result = cache.get(arg);
    if (result == null) {
      // 여기에 동시에 같은 값으로 접근할 수 있음
      result = c.compute(arg);
      cache.put(arg, result);
    }
    return result;
  }
}

// FutureTask를 사용해서 이미 연산중인게 있으면 그 값을 기다렸다가 return함
// 연산이 오래걸리는 경우 그 사이 다른 요청이 오면 cache에 값이 있기 때문에 기다림
// But 여전히 동시에 compute를 접근해서 future를 생성하는 작업을 같이 할 수 있음
// 그러면 연산도 같이 함
public class Memorizer3<A, V> implements Computable<A, V> {
  private final Map<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
  private final Computable<A, V> c;

  public Memorizer3(Computable<A, V> c) { this.c = c; }

  public V compute(final A arg) throws InterruptedException {
    Future<V> f = cache.get(arg);
    if (f == null) {
      Callable<V> eval = new Callable<V>() {
        public V call() throws InterruptedException {
          return c.compute(arg);
        }
      };
      FutureTask<V> ft = new FutureTask<V>(eval);
      f = ft;
      cache.put(arg, ft);
      ft.run(); // call to c.compute happens here
    }

    try {
      return f.get();
    } catch (ExecutionException e) {
      throw launderThrowable(e.getCause());
    }
  }
}

// 최종 버전
public class Memorizer<A, V> implements Computable<A, V> {
  private final ConcurrentMap<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
  private final Computable<A, V> c;

  public Memorizer(Computable<A, V> c) { this.c = c; }

  public V compute(final A arg) throws InterruptedException {
    while (true) {
      Future<V> f = cache.get(arg);
      if (f == null) {
        Callable<V> eval = new Callable<V>() {
          public V call() throws InterruptedException {
            return c.compute(arg);
          }
        };

        FutureTask<V> ft = new FutureTask<V>(eval);
        // 여기서 cache에 두번 넣을 수 있는 경우를 방지함
        // 결과적으로 계산도 한번만 일어남
        f = cache.putIfAbsent(arg, ft);

        if (f == null) { f = ft; ft.run(); }
      }

      try {
        return f.get();
      } catch (CancellationException e) {
        cache.remove(arg, f);
      } catch (ExecutionException e) {
        throw launderThrowable(e.getCause());
      }
    }
  }
}
```
