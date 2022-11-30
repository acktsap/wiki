# 4. Composing Objects

- [4. Composing Objects](#4-composing-objects)
  - [4.1. Designing a Thread-safe Class](#41-designing-a-thread-safe-class)
    - [Designing a thread-safe class](#designing-a-thread-safe-class)
    - [State composition](#state-composition)
    - [Gathering Synchronization Requirements](#gathering-synchronization-requirements)
    - [State-dependent Operations](#state-dependent-operations)
    - [State Ownership](#state-ownership)
  - [4.2. Instance Confinement](#42-instance-confinement)
    - [A class confined data](#a-class-confined-data)
    - [Java Monitor Pattern](#java-monitor-pattern)
  - [4.3. Delegating Thread Safety](#43-delegating-thread-safety)
    - [Independent State Variables](#independent-state-variables)
    - [When delegation fails](#when-delegation-fails)
    - [Publishing Underlying State Variables](#publishing-underlying-state-variables)
  - [4.4. Adding Functionality to Existing Thread-safe Classes](#44-adding-functionality-to-existing-thread-safe-classes)
    - [By extension](#by-extension)
    - [By client-side locking](#by-client-side-locking)
    - [By composision](#by-composision)
  - [4.5. Documenting Synchronization Policies](#45-documenting-synchronization-policies)
    - [Interpreting Vague Documentation](#interpreting-vague-documentation)

## 4.1. Designing a Thread-safe Class

### Designing a thread-safe class

The design process for a thread-safe class should include these three basic elements:

- Identify the variables that form the object's state;
- Identify the invariants that constrain the state variables;
- Establish a policy for managing concurrent access to the object's state.

> thread-safe class를 만들기 위해서는:\
> object의 state를 가리키는 variables들을 식별,\
> state에 대한 invariant(불변조건)을 식별,\
> object의 state에 대한 동시성을 보장하기 위한 정책 수립이 있다.

### State composition

An object's state starts with its fields. If they are all of primitive type, the fields comprise the entire state. The state of an object with n primitive fields is just the n-tuple of its field values; the state of a 2D Point is its (x, y) value. If the object has fields that are references to other objects, its state will encompass fields from the referenced objects as well. LinkedList includes the state of all the link node objects belonging to the list.

> Class의 state는 primitive type들의 tuple로 정의됨. n개라면 (n_1, n_2, ... n_n) 이런 식임.

### Gathering Synchronization Requirements

Making a class thread-safe means ensuring that its invariants hold under concurrent access; this requires reasoning about its state. Objects and variables have a state space: the range of possible states they can take on. The smaller this state space, the easier it is to reason about. By using final fields wherever practical, you make it simpler to analyze the possible states an object can be in. (In the extreme case, immutable objects can only be in a single state.).

Constraints placed on states or state transitions by invariants and post-conditions create additional synchronization or encapsulation requirements. If certain states are invalid, then the underlying state variables must be encapsulated, otherwise client code could put the object into an invalid state. If an operation has invalid state transitions, it must be made atomic. On the other hand, if the class does not impose any such constraints, we may be able to relax encapsulation or serialization requirements to obtain greater flexibility or better performance

You cannot ensure thread safety without understanding an object's invariants and post-conditions. Constraints on the valid values or state transitions for state variables can create atomicity and encapsulation requirements

> object의 invariant랑 post-condition을 모르고는 thread-safety를 말할 수 없음. invariant를 지킬 수 있게 synchronized를 걸어야 함. 가능하면 state를 줄이는 것이 좋음. 그래서 final keyword를 최대한 쓰는게 좋음. 그래서 immutable object가 제일 좋음.

### State-dependent Operations

Some objects also have methods with state-based preconditions. For example, you cannot remove an item from an empty queue; a queue must be in the "nonempty" state before you can remove an element. Operations with state-based preconditions are called state-dependent [CPJ 3].

In a single-threaded program, if a precondition does not hold, the operation has no choice but to fail. But in a concurrent program, the precondition may become true later due to the action of another thread.

The built-in mechanisms for efficiently waiting for a condition to become true - wait and notify - are tightly bound to intrinsic locking, and can be difficult to use correctly. To create operations that wait for a precondition to become true before proceeding, it is often easier to use existing library classes, such as blocking queues or semaphores, to provide the desired state-dependent behavior.

> concurrent program에서는 state-dependent operation에 대한 precondition이 다른 thread에 의해 만족될 수 있음. 이거를 만족시키는 방법은 wait-notify의 low-level api를 사용하는 것 또는 blocking queue, semephore등 high-level api를 사용하는 것이 있다. wait-notify의 low-level api는 느리고 어렵기 때문에 high-level api를 사용하는 것을 추천.

### State Ownership

TODO

## 4.2. Instance Confinement

### A class confined data

Encapsulating data within an object confines access to the data to the object's methods, making it easier to ensure that the data is always accessed with the appropriate lock held.

Confined objects must not escape their intended scope.
Instance confinement also allows different state variables to be guarded by different locks.

```java
@ThreadSafe
public class PersonSet {
  // mySet isn't thread-safe. But since it's confined to a PersonSet instance
  // and add, contains operations are guared by PersonSet object,
  // it's thread safe.
  @GuardedBy("this")
  private final Set<Person> mySet = new HashSet<Person>();
    public synchronized void addPerson(Person p) {
    mySet.add(p);
  }
  public synchronized boolean containsPerson(Person p) {
    return mySet.contains(p);
  }
}
```

> 자체로는 thread-safe하지 않으나 thread encapsulation에 의해 class에 confined된 객체는 thread-safe하게 동작하게 하기 쉽다. 다른 state variable에 대해서 다른 lock을 걸어서 더 효율적으로 lock을 걸수도 있음.

### Java Monitor Pattern

The Java monitor pattern is inspired by Hoare's work on monitors (Hoare, 1974), though there are significant differences between this pattern and a true monitor. The bytecode instructions for entering and exiting a synchronized block are even called monitorenter and monitorexit, and Java's built-in (intrinsic) locks are sometimes called monitor locks or monitors.

Following the principle of instance confinement to its logical conclusion leads you to the Java monitor pattern. An object following the Java monitor pattern encapsulates all its mutable state and guards it with the object's own intrinsic lock.

The Java monitor pattern is merely a convention; any lock object could be used to guard an object's state so long as it is used consistently.

```java
public class PrivateLock {
  private final Object myLock = new Object();
  @GuardedBy("myLock") Widget widget;
  void someMethod() {
    synchronized(myLock) {
      // Access or modify the state of widget
    }
  }
}
```

> Java monitor pattern은 mutable state의 concurrency를 object's intrinsic lock으로 막아서 보장. Instance confinement에 대한 조건을 만족시키는 방법중 하나임. 어떠한 lock을 사용해도 ㄱㅊ음.\
> 실제 Java monitor는 monitor에 들어갈 때 monitorenter를 실행하고 나갈 때 monitorexit을 실행시킴.

## 4.3. Delegating Thread Safety

Monitor-based class to thread safety delegation using thread-safe class.

```java
@ThreadSafe
public class MonitorVehicleTracker {
  @GuardedBy("this")
  private final Map<String, MutablePoint> locations;

  public MonitorVehicleTracker(Map<String, MutablePoint> locations) {
    this.locations = deepCopy(locations);
  }

  public synchronized Map<String, MutablePoint> getLocations() {
    return deepCopy(locations);
  }

  // returns snapshot
  public synchronized MutablePoint getLocation(String id) {
    MutablePoint loc = locations.get(id);
    return loc == null ? null : new MutablePoint(loc);
  }

  public synchronized void setLocation(String id, int x, int y) {
    MutablePoint loc = locations.get(id);
    if (loc == null)
      throw new IllegalArgumentException("No such ID: " + id);
    loc.x = x;
    loc.y = y;
  }

  private static Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> m) {
    Map<String, MutablePoint> result = new HashMap<>();
    for (String id : m.keySet()) {
      result.put(id, new MutablePoint(m.get(id)));
    }
    return Collections.unmodifiableMap(result);
  }
}

@NotThreadSafe
public class MutablePoint {
  public int x, y;

  public MutablePoint() { x = 0; y = 0; }

  public MutablePoint(MutablePoint p) {
    this.x = p.x;
    this.y = p.y;
  }
}
```

```java
@ThreadSafe
public class DelegatingVehicleTracker {
  // thread safety delegated to ConcurrentHashMap
  private final ConcurrentMap<String, Point> locations;
  private final Map<String, Point> unmodifiableMap;

  public DelegatingVehicleTracker(Map<String, Point> points) {
    locations = new ConcurrentHashMap<String, Point>(points);
    unmodifiableMap = Collections.unmodifiableMap(locations);
  }

  // returns live view (can view recent version)
  // since unmodifiableMap is made of this.locations and setLocation() modifies it
  public Map<String, Point> getLocations() {
    return unmodifiableMap;
  }

  // returns unchanging view
  public Map<String, Point> getLocations2() {
    return Collections.unmodifiableMap(new HashMap<String, Point>(locations));
  }

  public Point getLocation(String id) {
    return locations.get(id);
  }

  public void setLocation(String id, int x, int y) {
    if (locations.replace(id, new Point(x, y)) == null)
      throw new IllegalArgumentException("invalid vehicle name: " + id);
  }
}

@Immutable
public class Point {
  public final int x, y;
  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }
}
```

> thread-safe class를 잘 쓰면 monitor pattern을 쓰지 않고도 thread-safe를 만족시킬 수 있음. 그리고 이게 권장된다고 생각됨 개인적으로.

### Independent State Variables

We can also delegate thread safety to more than one underlying state variable as long as those underlying state variables are independent, meaning that the composite class does not impose any invariants involving the multiple state variables.

```java
public class VisualComponent {
  // keyListeners and mouseListeners are independent state
  // so, delegate its thread safety obligations to two underlying thread-safe lists
  private final List<KeyListener> keyListeners = new CopyOnWriteArrayList<KeyListener>();
  private final List<MouseListener> mouseListeners = new CopyOnWriteArrayList<MouseListener>();

  public void addKeyListener(KeyListener listener) {
    keyListeners.add(listener);
  }

  public void addMouseListener(MouseListener listener) {
    mouseListeners.add(listener);
  }

  public void removeKeyListener(KeyListener listener) {
    keyListeners.remove(listener);
  }

  public void removeMouseListener(MouseListener listener) {
    mouseListeners.remove(listener);
  }
}
```

> state variable들이 한 클래스 내에 독립적인 경우가 있으면 그것들 두개의 lock으로 각각 처리할 수 있음. 마찬가지로 delegating하는 경우에도 독립적으로 delegation시킬 수 있음.

### When delegation fails

If a class is composed of multiple independent thread-safe state variables and has no operations that have any invalid state transitions, then it can delegate thread safety to the underlying state variables.

```java
public class NumberRange {
  // INVARIANT: lower <= upper
  // But it's not thread-safe. setLower, setUpper can be (5, 4) on unlucky timing
  // NumberRange could be made thread-safe by using locking to maintain its invariants,
  // such as guarding lower and upper with a common lock.
  private final AtomicInteger lower = new AtomicInteger(0);
  private final AtomicInteger upper = new AtomicInteger(0);

  public void setLower(int i) {
    // Warning -- unsafe check-then-act
    if (i > upper.get())
      throw new IllegalArgumentException("can't set lower to " + i + " > upper");
    lower.set(i);
  }

  public void setUpper(int i) {
    // Warning -- unsafe check-then-act
    if (i < lower.get())
      throw new IllegalArgumentException("can't set upper to " + i + " < lower");
    upper.set(i);
  }

  public boolean isInRange(int i) {
    return (i >= lower.get() && i <= upper.get());
  }
}
```

> state variable들이 독립적이지 않은 경우 thread-safety를 thread-safe class로 delegation 시킬 수 없음.

### Publishing Underlying State Variables

If a state variable is thread-safe, does not participate in any invariants that constrain its value, and has no prohibited state transitions for any of its operations, then it can safely be published.

> object의 invariant에 관계되지 않는 객체들만 publishing이 가능하게 하셈.\
> Immutable object로 만들면 에초에 문제 없을듯.

## 4.4. Adding Functionality to Existing Thread-safe Classes

### By extension

Extension is more fragile than adding code directly to a class, because the implementation of the synchronization policy is now distributed over multiple, separately maintained source files

```java
@ThreadSafe
public class BetterVector<E> extends Vector<E> {
  public synchronized boolean putIfAbsent(E x) {
    boolean absent = !contains(x);
    if (absent)
      add(x);
    return absent;
  }
}
```

> 상속을 통해 기능을 확장할 수도 있으나 너무 위험함. 상위 클래스의 구현이 바뀌게 되면 어떻게 될지 모름.

### By client-side locking

Client-side locking entails guarding client code that uses some object X with the lock X uses to guard its own state. In order to use client-side locking, you must know what lock X uses.

Client-side locking has a lot in common with class extension - they both couple the behavior of the derived class to the implementation of the base class

```java
@ThreadSafe
public class ListHelper<E> {
  // Collections.synchronizedList use list object itself as lock
  public List<E> list = Collections.synchronizedList(new ArrayList<E>());
  public boolean putIfAbsent(E x) {
    // here, sync
    synchronized (list) {
      boolean absent = !list.contains(x);
      if (absent)
        list.add(x);
      return absent;
    }
  }
}
```

> Client side에서 thread-safe object가 어떤 것으로 lock하는지 알아내서 이것으로 lock을 하는 방법이 있음. But 상속처럼 base class의 구현에 종속되는 단점이 있음.

### By composision

ImprovedList adds an additional level of locking using its own intrinsic lock. It does not care whether the underlying List is thread-safe, because it provides its own consistent locking that provides thread safety even if the List is not thread-safe or changes its locking implementation.

While the extra layer of synchronization may add some small performance penalty, the implementation in ImprovedList is less fragile than attempting to mimic the locking strategy of another object. The penalty will be small because the synchronization on the underlying List is guaranteed to be uncontended and therefore fast;

```java
@ThreadSafe
public class ImprovedList<T> implements List<T> {
  private final List<T> list;

  public ImprovedList(List<T> list) { this.list = list; }

  public synchronized boolean putIfAbsent(T x) {
    boolean contains = list.contains(x);
    if (contains)
      list.add(x);
    return !contains;
  }

  public synchronized void clear() { list.clear(); }

  // ... similarly delegate other List methods
}
```

> 기존의 객체가 thread-safe하든 말든 composition을 해서 원하는 기능을 추가하는 방법이 있음. 추가적인 lock이 더 발생할 수 있어서 performance penalty가 있을 수 있으나 미미함. 추천되는 방법.

## 4.5. Documenting Synchronization Policies

Document a class's thread safety guarantees for its clients; document its synchronization policy for its maintainers. Of course, the best time to document design decisions is at design time. Weeks or months later, the details may be a blur - so write it down before you forget.

Crafting a synchronization policy requires a number of decisions: which variables to make volatile, which variables to guard with locks, which lock(s) guard which variables, which variables to make immutable or confine to a thread, which operations must be atomic, etc. Some of these are strictly implementation details and should be documented for the sake of future maintainers, but some affect the publicly observable locking behavior of your class and should be documented as part of its specification.

> thread-safey에 대해 user나 maintainer 모두를 위해 작성해라. 특히 설계하고 까먹기 전에 작성해라 그때가 최적의 타이밍이다.

### Interpreting Vague Documentation

You are going to have to guess. One way to improve the quality of your guess is to interpret the specification from the perspective of someone who will implement it (such as a container or database vendor), as opposed to someone who will merely use it.

Servlets are always called from a container􀍲managed thread, and it is safe to assume that if there is more than one such thread, the container knows this. The servlet container makes available certain objects that provide service to multiple servlets, such as HttpSession or ServletContext. So the servlet container should expect to have these objects accessed concurrently, since it has created multiple threads and called methods like Servlet.service from them that could reasonably be expected to access the ServletContext.

> 애매한 thread-safety에 대한 docs에 대해서는 그 인터페이스의 구현자라고 생각해라.\
> 는 잘 모르겠음 걍 개소리 같고 소스를 까보거나 아예 가정을 하지 않는게 최고 아닐까?
