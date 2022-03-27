# 3. Sharing Objects

- [3. Sharing Objects](#3-sharing-objects)
  - [3.1. Visibility](#31-visibility)
    - [Visibility](#visibility)
    - [Stale data](#stale-data)
    - [Non-atomic 64-bit Operations](#non-atomic-64-bit-operations)
    - [Locking and Visibility](#locking-and-visibility)
    - [Volatile Variables](#volatile-variables)
  - [3.2. Publication and Escape](#32-publication-and-escape)
  - [3.3. Thread Confinement](#33-thread-confinement)
    - [Thread Confinement (제한)](#thread-confinement-%ec%a0%9c%ed%95%9c)
    - [Ad-hoc Thread Confinement](#ad-hoc-thread-confinement)
    - [Stack Confinement](#stack-confinement)
    - [Thread Local](#thread-local)
  - [3.4. Immutability](#34-immutability)
    - [Remove mutability](#remove-mutability)
    - [Condition of immutability](#condition-of-immutability)
    - [Immutabliity & Update, Allocation](#immutabliity--update-allocation)
    - [Final fields](#final-fields)
    - [Race condition & Immutability](#race-condition--immutability)
  - [3.5. Safe Publication](#35-safe-publication)
    - [Improper Publication: When Good Objects Go Bad](#improper-publication-when-good-objects-go-bad)
    - [Immutable Objects and Initialization Safety](#immutable-objects-and-initialization-safety)
    - [Safe Publication Idioms](#safe-publication-idioms)
    - [Effectively Immutable Objects](#effectively-immutable-objects)
    - [Safe publication](#safe-publication)
    - [Documentation for thread-safety](#documentation-for-thread-safety)

## 3.1. Visibility

### Visibility

NoVisibility could loop forever because the value of ready might never become visible to the reader thread. Even more strangely, NoVisibility could print zero because the write to ready might be made visible to the reader thread before the write to number, a phenomenon known as reordering

This may all sound a little scary, and it should. Fortunately, there's an easy way to avoid these complex issues:
always use the proper synchronization whenever data is shared across threads.

```java
public class NoVisibility {
  private static boolean ready;
  private static int number;
  private static class ReaderThread extends Thread {
    public void run() {
      while (!ready)
        Thread.yield();
      System.out.println(number); // may print 0
    }
  }
  public static void main(String[] args) {
    new ReaderThread().start();
    number = 42;
    ready = true;
  }
}
```

> Multiprocessor의 cache를 잘 사용하기 위해서 jvm이 reordering을 함.\
> 이로 인해 한 Thread에서 한 작업이 다른 Thread에서 보이지 않을 수가 있음.\
> 이를 해결하기 위해서는 thread간 데이터의 공유가 발행하면 무조건 synchronization을 하면 됨.

### Stale data

When the reader thread examines ready, it may see an out-of-date value

```java
@NotThreadSafe
public class MutableInteger {
  private int value;
  public int get() { return value; } // can see stale value
  public void set(int value) { this.value = value; }
}

@ThreadSafe
public class SynchronizedInteger {
  @GuardedBy("this") private int value;
  public synchronized int get() { return value; } // no stale value
  public synchronized void set(int value) { this.value = value; }
}
```

> 적절한 동기화를 하지 않으면 (get, set 모두) get이 stale data를 볼 수 있음 (stale: 신선하지 않은)

### Non-atomic 64-bit Operations

When a thread reads a variable without synchronization, it may see a stale value, but at least it sees a value that was actually placed there by some thread rather than some random value. This safety guarantee is called out-of-thin-air safety.

Out-of-thin-air safety applies to all variables, with one exception: 64-bit numeric variables (double and long) that are not declared volatile (see Section 3.1.4). The Java Memory Model requires fetch and store operations to be atomic, but for nonvolatile long and double variables, the JVM is permitted to treat a 64-bit read or write as two separate 32-
bit operations.

> jvm은 64-bit 데이터에 대한 연산을 2개의 32-bit에 대한 연산으로 나눠서 실행함.\
> 그래서 64 bit primitive type (long, double)에 대해서는 동기화를 하지 않으면 2개의 32-bit operation중 일부만 적용될 수 있기 때문에 이상한 random값이 보일 수 있음.

### Locking and Visibility

Locking is not just about mutual exclusion; it is also about memory visibility. To ensure that all threads see the most up-to-date values of shared mutable variables, the reading and writing threads must synchronize on a common lock.

> Lock은 mutual exclusion 뿐만 아니라 memory visibility에도 사용될 수 있음. shared mutable variable에 대해서는 R/W lock을 걸어라

### Volatile Variables

When a field is declared volatile, the compiler and runtime are put on notice that this variable is shared and that operations on it should not be reordered with other memory operations. Volatile variables are not cached in registers or in caches where they are hidden from other processors, so a read of a volatile variable always returns the most recent write by any thread.

Use volatile variables only when they simplify implementing and verifying your synchronization policy; avoid using volatile variables when verifying correctness would require subtle reasoning about visibility. Good uses of volatile variables include ensuring the visibility of their own state, that of the object they refer to, or indicating that an important lifecycle event (such as initialization or shutdown) has occurred.

Locking can guarantee both visibility and atomicity; volatile variables can only guarantee visibility.

```java
volatile boolean asleep;
while (!asleep)
  countSomeSheep();
```

> multi-process에서의 reordering (processor내의 register에 cache하는거)을 방지하기 위해서는 volatile keyword를 사용. But 그리고 이건 visbility만 보장함. 복잡한 작업의 경우 보장하지 않음. Lifecycle event같은거에서만 사용하는 것이 좋음.

## 3.2. Publication and Escape

An object that is published when it should not have been is said to have escaped.

```java
// publish
public static Set<Secret> knownSecrets;
public void initialize() {
  knownSecrets = new HashSet<Secret>();
}

class UnsafeStates {
  private String[] states = new String[] {
    "AK", "AL" ...
  };
  // 'states' is escaped
  public String[] getStates() { return states; }
}

public class ThisEscape {
  // impliclitly allowing ThisEscape scope to escape (accessable inside source)
  // since inner class instances contain a hidden reference to the enclosing instance
  public ThisEscape(EventSource source) {
    source.registerListener(
      new EventListener() {
        public void onEvent(Event e) {
          doSomething(e);
        }
    });
  }
}
```

> java의 object는 reference로 움직이기 때문에 return 잘못하면 내부 상태에 대한 변경을 escape시킬 수 있음

```java
public class SafeListener {
private final EventListener listener;
private SafeListener() {
  listener = new EventListener() {
    public void onEvent(Event e) {
      doSomething(e);
    }
  };
}
public static SafeListener newInstance(EventSource source) {
  SafeListener safe = new SafeListener();
    source.registerListener(safe.listener);
    return safe;
  }
}
```

> 수틀리면 factory method를 사용하는 것도 하나의 방법임

## 3.3. Thread Confinement

### Thread Confinement (제한)

Accessing shared, mutable data requires using synchronization; one way to avoid this requirement is to not share. If data is only accessed from a single thread, no synchronization is needed. This technique, thread confinement, is one of the simplest ways to achieve thread safety. When an object is confined to a thread, such usage is automatically thread-safe even if the confined object itself is not

Another common application of thread confinement is the use of pooled JDBC (Java Database Connectivity) Connection objects. The JDBC specification does not require that Connection objects be thread-safe.

> Thread safe를 만족하는 가장 좋은 방법은 한 thread만 사용 가능하게 제한하는 것. JDBC Connection Pool이 이런 느낌임 (필요한 thread가 꺼내서 사용하고 반환함)

### Ad-hoc Thread Confinement

Ad-hoc thread confinement describes when the responsibility for maintaining thread confinement falls entirely on the implementation. Ad-hoc thread confinement can be fragile because none of the language features, such as visibility modifiers or local variables, helps confine the object to the target thread.

A special case of thread confinement applies to volatile variables. It is safe to perform read-modify-write operations on shared volatile variables as long as you ensure that the volatile variable is only written from a single thread. In this case, you are confining the modification to a single thread to prevent race conditions, and the visibility guarantees for volatile variables ensure that other threads see the most up-to-date value

> Ad-hoc thread confinement란 thread confinement를 지키려고 구현 단에서 하는게 아니라 개발자의 노력으로 이루어 지는 것을 말함. 예로는 volatile 변수에서 write는 single thread에서만 이루어 지게 설계하는 것이 있음. 그러면 다른 thread들은 최신의 값을 볼 수 있음.

https://stackoverflow.com/questions/9039503/example-of-ad-hoc-thread-confinement-in-java

### Stack Confinement

Stack confinement is a special case of thread confinement in which an object can only be reached through local variables. Just as encapsulation can make it easier to preserve invariants, local variables can make it easier to confine objects to a thread. Local variables are intrinsically confined to the executing thread; they exist on the executing thread's stack, which is not accessible to other threads

For primitively typed local variables, such as numPairs in loadTheArk in Listing 3.9, you cannot violate stack confinement even if you tried

Maintaining stack confinement for object references requires a little more assistance from the programmer to ensure that the referent does not escape

> Stack에서 생성한 변수는 기본적으로 thread안의 stack에 있기 때문에 thread confinement를 만족함. But object reference를 local variable에서 쓸 때는 escape하지 않게 조심할 필요가 있음

### Thread Local

A more formal means of maintaining thread confinement is ThreadLocal, which allows you to associate a per-thread value with a value-holding object. Thread-Local provides get and set accessor methods that maintain a separate copy of the value for each thread that uses it, so a get returns the most recent value passed to set from the currently executing thread.

The thread-specific values are stored in the Thread object itself; when the thread terminates, the thread-specific values can be garbage collected.

ThreadLocal is widely used in implementing application frameworks. For example, J2EE containers associate a transaction context with an executing thread for the duration of an EJB call. This is easily implemented using a static Thread-Local holding the transaction context.

```java
// jdbc connection is not thread-safe. use it within thread local
private static ThreadLocal<Connection> connectionHolder
    = new ThreadLocal<Connection>() {
      public Connection initialValue() {
        return DriverManager.getConnection(DB_URL);
      }
    };
public static Connection getConnection() {
  return connectionHolder.get();
}
```

> java차원에서 제공되는 ThreadLocal을 쓰면 thread confinement를 쉽게 만족시킬 수 있음. 서버가 요청을 처리할 때 해당 transaction에 대한 context를 thread local에 저장하곤함 (보통 한개의 thread가 처리). Thread local은 Thread object 자체에 저장됨.

## 3.4. Immutability

### Remove mutability

Nearly all the atomicity and visibility hazards we've described so far, such as seeing stale values, losing updates, or observing an object to be in an inconsistent state, have to do with the vagaries of multiple threads trying to access the same mutable state at the same time. If an object's state cannot be modified, these risks and complexities simply go away

> mutability가 문제니 이걸 제거하면 여기서 야기되는 문제들을 피할 수 있음!

### Condition of immutability

Neither the Java Language Specification nor the Java Memory Model formally defines immutability, but immutability is not equivalent to simply declaring all fields of an object final. An object whose fields are all final may still be mutable, since final fields can hold references to mutable objects.

- Its state cannot be modified after construction;
- All its fields are final;[12] and
- It is properly constructed (the this reference does not escape during construction).

> 생성 후 변경할 수 없으면 immutable한 object임. All field를 final로 선언하는게 충분하진 않음. reference에 대해서는 값을 여전히 변경할 수 있음. 그래서 이걸 잘 감싸줘야함 (eg. Collections.immutableList())

### Immutabliity & Update, Allocation

Because program state changes all the time, you might be tempted to think that immutable objects are of limited use, but this is not the case. There is a difference between an object being immutable and the reference to it being immutable. Program state stored in immutable objects can still be updated by "replacing" immutable objects with a new instance holding new state.

Allocation is cheaper than you might think, and immutable objects offer additional performance advantages such as reduced need for locking or defensive copies and reduced impact on generational garbage collection.

> Mutable에 대한 대체로는 매번 새로운 객체를 만드는 방법이 있음. 그리고 이거는 생각보다 비용이 얼마 안함. 오히려 이것으로 인해 얻는 이점이 더 큼. 굳이 mutable하게 할 이유가 없으면 immutable하게 하는게 좋음

### Final fields

Just as it is a good practice to make all fields private unless they need greater visibility [EJ Item 12], it is a good practice to make all fields final unless they need to be mutable

> 가능하면 field를 final로 만드는게 좋은 습관임

### Race condition & Immutability

Race conditions in accessing or updating multiple related variables can be eliminated by using an immutable object to hold all the variables. With a mutable holder object, you would have to use locking to ensure atomicity; with an immutable one, once a thread acquires a reference to it, it need never worry about another thread modifying its state

```java
// Example: Using Volatile to Publish Immutable Objects
@Immutable
class OneValueCache {
  private final BigInteger lastNumber;
  private final BigInteger[] lastFactors;
  public OneValueCache(BigInteger i, BigInteger[] factors) {
    lastNumber = i;
    lastFactors = Arrays.copyOf(factors, factors.length);
  }
  public BigInteger[] getFactors(BigInteger i) {
    if (lastNumber == null || !lastNumber.equals(i))
      return null;
    else
      return Arrays.copyOf(lastFactors, lastFactors.length);
  }
}

@ThreadSafe
public class VolatileCachedFactorizer implements Servlet {
  private volatile OneValueCache cache = new OneValueCache(null, null);
  public void service(ServletRequest req, ServletResponse resp) {
    BigInteger i = extractFromRequest(req);
    BigInteger[] factors = cache.getFactors(i);
    if (factors == null) {
      factors = factor(i);
      cache = new OneValueCache(i, factors);
    }
    encodeIntoResponse(resp, factors);
  }
}
```

> Immutable object에서는 race condition이 존재하지 않음.

## 3.5. Safe Publication

### Improper Publication: When Good Objects Go Bad

```java
public class Holder {
  private int n;
  public Holder(int n) { this.n = n; }
  // may throw exception
  public void assertSanity() {
    if (n != n)
      throw new AssertionError("This statement is false.");
  }
}
```

> 생성자가 기본값을 할당한 뒤 인자로 받은 값을 할당하기 때문에 assertSanity가 에러날 수 있음..

### Immutable Objects and Initialization Safety

Immutable objects, on the other hand, can be safely accessed even when synchronization is not used to publish the object reference. For this guarantee of initialization safety to hold, all of the requirements for immutability must be met: unmodifiable state, all fields are final, and proper construction

> Immutable object는 Initialization Safety를 만족시킬 수 있음.

### Safe Publication Idioms

To publish an object safely, both the reference to the object and the object's state must be made visible to other threads at the same time. A properly constructed object can be safely published by:

- Initializing an object reference from a static initializer;
- Storing a reference to it into a volatile field or AtomicReference;
- Storing a reference to it into a final field of a properly constructed object; or
- Storing a reference to it into a field that is properly guarded by a lock.

Static initializers are executed by the JVM at class initialization time; because of internal synchronization in the JVM, this mechanism is guaranteed to safely publish any objects initialized in this way.

> safe하게 publishment를 하려면 여러 방법이 있음 (위에 봐 귀찮아 적기..)

### Effectively Immutable Objects

Objects that are not technically immutable, but whose state will not be modified after publication, are called effectively immutable. They do not need to meet the strict definition of immutability

```java
public Map<String, Date> lastLogin =
    Collections.synchronizedMap(new HashMap<String, Date>());
```

If the Date values are not modified after they are placed in the Map, then the synchronization in the synchronizedMap implementation  is  sufficient  to  publish  the  Date  values  safely,  and  no  additional  synchronization  is  needed  when accessing them.

> 기술적으로 immutable하지는 않지만 실질적으로 immutable하게 사용할 수 있으면 effectively immutable이라고 함

### Safe publication

The publication requirements for an object depend on its mutability:

- Immutable objects can be published through any mechanism;
- Effectively immutable objects must be safely published;
- Mutable objects must be safely published, and must be either thread-safe or guarded by a lock.

> Immutable object는 그냥 있는 그대로, effectively immutable object는 잘 publish, mutable object는 thread-safe하게 lock을 걸든 해서 잘 처리를 해둬야함

### Documentation for thread-safety

Thread-confined : A thread-confined object is owned exclusively by and confined to one thread, and can be modified by its owning thread.

Shared read-only : A shared read-only object can be accessed concurrently by multiple threads without additional synchronization, but cannot be modified by any thread. Shared read-only objects include immutable and effectively immutable objects.

Shared thread-safe : A thread-safe object performs synchronization internally, so multiple threads can freely access it through its public interface without further synchronization.

Guarded : A guarded object can be accessed only with a specific lock held. Guarded objects include those that are encapsulated within other thread-safe objects and published objects that are known to be guarded by a specific lock

> Thread-safecy에 대한 documentation은 4개가 있음 (위에 참고)
