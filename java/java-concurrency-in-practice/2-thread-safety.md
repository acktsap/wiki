# 2. Thread Safety

- [2. Thread Safety](#2-thread-safety)
  - [2.1. What is Thread Safety](#21-what-is-thread-safety)
    - [Correctness](#correctness)
    - [Thread Safe Class](#thread-safe-class)
    - [Thread-safe class](#thread-safe-class-1)
    - [Stateless objects are always thread-safe](#stateless-objects-are-always-thread-safe)
  - [2.2. Atomicity](#22-atomicity)
    - [Race condition](#race-condition)
    - [Atomic operation](#atomic-operation)
    - [Use existing thread-safe objects](#use-existing-thread-safe-objects)
  - [2.3. Locking](#23-locking)
    - [Race condition with atomic object](#race-condition-with-atomic-object)
    - [Intrinsic Locks](#intrinsic-locks)
    - [Lock & Transactional applications](#lock--transactional-applications)
    - [Reentrancy](#reentrancy)
  - [2.4. Guarding State with Locks](#24-guarding-state-with-locks)
  - [2.5. Liveness and Performance](#25-liveness-and-performance)

3 ways to fix thread-safety

If multiple threads access the same mutable state variable without appropriate synchronization, your program is broken. There are three ways to fix it:

- Don't share the state variable across threads;
- Make the state variable immutable; or
- Use synchronization whenever accessing the state variable.

> 자원을 공유하지 않거나, immutable object만 사용하거나, 적절한 동기화를 하면 thread-safety를 지킬 수 있음.

Encapulation & Thread-safety

The less code that has access to a particular variable, the easier it is to ensure that all of it uses the proper synchronization, and the easier it is to reason about the conditions under which a given variable might be accessed.
When designing thread safe classes, good object-oriented techniques encapsulation, immutability, and clear specification of invariants are your best friends.

> Encapulation을 통해 class의 내부 상태를 보여주지 않는 것을 잘 활용하면 thread-safe한 class를 작성하는데 도움을 줄 수 있다.

thread-safe class vs thread-safe program

We've used the terms "thread-safe class" and "thread-safe program" nearly interchangeably
thus far. Is a thread-safe program one that is constructed entirely of thread-safe classes?
Not necessarily - a program that consists entirely of thread-safe classes may not
be thread safe, and a thread-safe program may contain classes that are not thread-safe.

> class를 thread-safe하게 만든다고 program이 thread-safe하지는 않음. 두개는 다른 개념임.

## 2.1. What is Thread Safety

### Correctness

Correctness means that a class conforms to its specification. A good specification defines invariants constraining an object's state and post - conditions describing the effects of its operations. Since we often don't write adequate specifications for our classes, how can we possibly know they are correct? We can't, but that doesn't stop us from using them anyway once we've convinced ourselves that "the code works". This "code confidence" is about as close as many of us get to correctness, so let's just assume that single-threaded correctness is something that "we know it when we see it". Having optimistically defined "correctness" as something that can be recognized, we can now define thread safety in a somewhat less circular way: a class is thread-safe when it continues to behave correctly when accessed from multiple threads.

### Thread Safe Class

A class is thread-safe if it behaves correctly when accessed from multiple threads, regardless of the scheduling or interleaving of the execution of those threads by the runtime environment, and with no additional synchronization or other coordination on the part of the calling code.

> 스케쥴링이나 실행 순서에 관계 없이 멀티스레드에서 동시성 제어 코드 없이 잘 동작하면 해당 클래스는 thread-safe하다.

### Thread-safe class

Thread-safe classes encapsulate any needed synchronization so that clients need not provide their own

> Thread-safe class는 synchronization 관련 처리를 내부적으로 해줌

### Stateless objects are always thread-safe

```java
@ThreadSafe
public class StatelessFactorizer implements Servlet {
  // no fields and references no fields from other classes
  public void service(ServletRequest req, ServletResponse resp) {
    BigInteger i = extractFromRequest(req);
    BigInteger[] factors = factor(i);
    encodeIntoResponse(resp, factors);
  }
}
```

> Stateless object는 항상 thread-safe함. (eg. Service in Spring Framework)

## 2.2. Atomicity

### Race condition

```java
@NotThreadSafe
public class UnsafeCountingFactorizer implements Servlet {
  private long count = 0;
  public long getCount() { return count; }
    public void service(ServletRequest req, ServletResponse resp) {
    BigInteger i = extractFromRequest(req);
    BigInteger[] factors = factor(i);
    // 3 operations -> Not thread-safe
    // fetch the current value, add one to it, and write the new value back.
    // race condition
    ++count;
    encodeIntoResponse(resp, factors);
  }
}
```

UnsafeCountingFactorizer has several race conditions that make its results unreliable. A race condition occurs when the correctness of a computation depends on the relative timing or interleaving of multiple threads by the runtime; in other words, when getting the right answer relies on lucky timing. The most common type of race condition is check-then-act, where a potentially stale observation is used to make a decision on what to do next.

> Operation의 correctness가 timing에 의존적인 경우 race condition이라고 함. 대표적으로 check-then-act가 있음.

```java
// check-then-act on Lazy Initialization
@NotThreadSafe
public class LazyInitRace {
  private ExpensiveObject instance = null;
  public ExpensiveObject getInstance() {
    if (instance == null) {
      // can be called same time
      // race condition
      instance = new ExpensiveObject();
    }
    return instance;
  }
}
```

### Atomic operation

Operations A and B are atomic with respect to each other if, from the perspective of a thread executing A, when another thread executes B, either all of B has executed or none of it has. An atomic operation is one that is atomic with respect to all operations, including itself, that operate on the same state.

> Operation A의 관점에서 Operation B가 실행되거나 전혀 실행되지 않으면 Atomic하다고 함

### Use existing thread-safe objects

```java
@ThreadSafe
public class CountingFactorizer implements Servlet {
  // see also java.util.concurrent.atomic
  private final AtomicLong count = new AtomicLong(0);

  public long getCount() { return count.get(); }
    public void service(ServletRequest req, ServletResponse resp) {
    BigInteger i = extractFromRequest(req);
    BigInteger[] factors = factor(i);
    count.incrementAndGet();
    encodeIntoResponse(resp, factors);
  }
}
```

Where practical, use existing thread-safe objects, like AtomicLong, to manage your class's state. It is simpler to reason about the possible states and state transitions for existing thread-safe objects than it is for arbitrary state variables, and this makes it easier to maintain and verify thread safety.

> 가능한한 AtomicXXX 같은 이미 존재하는 Object를 사용해라

## 2.3. Locking

### Race condition with atomic object

```java
@NotThreadSafe
public class UnsafeCachingFactorizer implements Servlet {
  // lastNumber and lastFactors are in race conditions
  // To preserve state consistency, update related state variables
  // in a single atomic operation.
  private final AtomicReference<BigInteger> lastNumber
      = new AtomicReference<BigInteger>();
  private final AtomicReference<BigInteger[]> lastFactors
      = new AtomicReference<BigInteger[]>();
  public void service(ServletRequest req, ServletResponse resp) {
    BigInteger i = extractFromRequest(req);
    // if cached, return value. but can return wrong value on unlucky timing
    if (i.equals(lastNumber.get()))
      encodeIntoResponse(resp, lastFactors.get() );
    else {
      BigInteger[] factors = factor(i);
      lastNumber.set(i);
      lastFactors.set(factors);
      encodeIntoResponse(resp, factors);
    }
  }
}
```

> Atomic Object를 사용하더라도 race condition이 발생할 수 있음

### Intrinsic Locks

```java
synchronized (object) {
  // lock the object
}

public synchronized void method() {
  // method (lock the object where method defined)
}
```

Every Java object can implicitly act as a lock for purposes of synchronization; these built-in locks are called intrinsic locks or monitor locks. The lock is automatically acquired by the executing thread before entering a synchronized block and automatically released when control exits the synchronized block, whether by the normal control path or by throwing an exception out of the block

> `synchronized` keyword는 java intrinsic lock (or monitor lock)을 걸음. `synchronized` block에 들어가면 해당 lock을 획득하고 나오면 해당 lock을 놓음

### Lock & Transactional applications

```java
@ThreadSafe
public class SynchronizedFactorizer implements Servlet {
  @GuardedBy("this") private BigInteger lastNumber;
  @GuardedBy("this") private BigInteger[] lastFactors;

  // thread-safe, but performance issue
  public synchronized void service(ServletRequest req,
    ServletResponse resp) {
    BigInteger i = extractFromRequest(req);
    if (i.equals(lastNumber))
      encodeIntoResponse(resp, lastFactors);
    else {
      BigInteger[] factors = factor(i);
      lastNumber = i;
      lastFactors = factors;
      encodeIntoResponse(resp, factors);
    }
  }
}
```

Since only one thread at a time can execute a block of code guarded by a given lock, the synchronized blocks guarded by the same lock execute atomically with respect to one another. In the context of concurrency, atomicity means the same thing as it does in transactional applications - that a group of statements appear to execute as a single, indivisible unit.

> block단위로 lock을 거는 것은 그 부분에 대한 transactional을 보장해줌

### Reentrancy

```java
public class Widget {
  public synchronized void doSomething() {
  }
}

public class LoggingWidget extends Widget {
  public synchronized void doSomething() {
    System.out.println(toString() + ": calling doSomething");
    // make deadlock without reentrant lock
    super.doSomething();
  }
}
```

When a thread requests a lock that is already held by another thread, the requesting thread blocks. But because intrinsic locks are reentrant, if a thread tries to acquire a lock that it already holds, the request succeeds. Reentrancy means that locks are acquired on a per-thread rather than per-invocation basis (differ from POSIX threads mutexes, which are granted on a per-invocation basis).

Reentrancy is implemented by associating with each lock an acquisition count and an owning thread. When the count is zero, the lock is considered unheld. When a thread acquires a previously unheld lock, the JVM records the owner and sets the acquisition count to one. If that same thread acquires the lock again, the count is incremented, and when the owning thread exits the synchronized block, the count is decremented. When the count reaches zero, the lock is released.

> java는 monitor lock을 획득한 녀석이 monitor lock을 다시 획득하는 것을 허용해줌. 이를 Reentrancy라고 하는데 monitor lock을 다시 획득할 때 마다 count를 증가키시고 최종적으로 놓으면 0으로 바꾸는 식으로 구현해놓음. Java에서는 Thread단위로 lock을 획득해서 이것이 가능. 이는  invocation단위로 lock을 획득하는 POSIX mutex하고는 다름.

## 2.4. Guarding State with Locks

For each mutable state variable that may be accessed by more than one thread, all accesses to that variable must be performed with the same lock held. In this case, we say that the variable is guarded by that lock.

Every shared, mutable variable should be guarded by exactly one lock. Make it clear to maintainers which lock that is.

For every invariant that involves more than one variable, all the variables involved in that invariant must be guarded by the same lock.

If synchronization is the cure for race conditions, why not just declare every method synchronized? It turns out that such indiscriminate application of synchronized might be either too much or too little synchronization. Merely synchronizing every method, as Vector does, is not enough to render compound actions on a Vector atomic

```java
// This attempt at a put-if-absent operation has a race condition,
// even though both contains and add are atomic.
if (!vector.contains(element)) {
  vector.add(element);
}
```

> mutable한 모든 변수의 R/W에 대해 lock을 걸어야 함\
> 그 lock은 하나여야함\
> invarient가 여러 변수에 관계되어 있는 경우 그 변수들은 같은 lock에 감싸져 있어야함\
> 과도한 synchronization을 피하도록 하라. vector처럼 각자의 operation이 atomic하더라도 그것을 사용하는 operation block에 대한 동시성이 보장되지는 않음\

## 2.5. Liveness and Performance

synchronization should be "short enough"

There is frequently a tension between simplicity and performance. When implementing a synchronization policy, resist the temptation to prematurely sacrifice simplicity (potentially compromising safety) for the sake of performance.

Avoid holding locks during lengthy computations or operations at risk of not completing quickly such as network or console I/O.

```java
// performance issue fixed one with hit ratio
@ThreadSafe
public class CachedFactorizer implements Servlet {
  @GuardedBy("this") private BigInteger lastNumber;
  @GuardedBy("this") private BigInteger[] lastFactors;
  @GuardedBy("this") private long hits;
  @GuardedBy("this") private long cacheHits;

  public synchronized long getHits() { return hits; }
  public synchronized double getCacheHitRatio() {
    return (double) cacheHits / (double) hits;
  }

  public void service(ServletRequest req, ServletResponse resp) {
    BigInteger i = extractFromRequest(req);
    BigInteger[] factors = null;
    synchronized (this) {
      ++hits;
      if (i.equals(lastNumber)) {
        ++cacheHits;
        factors = lastFactors.clone();
      }
    }
    if (factors == null) {
      factors = factor(i);
      synchronized (this) {
        lastNumber = i;
        lastFactors = factors.clone();
      }
    }
    encodeIntoResponse(resp, factors);
  }
}
```

> synchronization은 최소한만 걸어야 함. 아니면 성능 이슈가 생길 수 있음.
