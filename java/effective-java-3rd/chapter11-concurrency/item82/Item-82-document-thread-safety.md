# Item 82: Document thread safety

## Documentation of Thread Safety

```text
If you fail to document this aspect of a class’s
behavior, its users will be forced to make assumptions

The presence of the synchronized modifier in a
method declaration is an implementation detail, not a part of its API.

To enable safe concurrent use, a class must clearly document what level of
thread safety it supports
```

## Level of Thread Safety

- Immutable : No external synchronization is necessary. eg. String, Long, and BigInteger
- Unconditionally thread-safe : Mutable, but the class has sufficient internal synchronization that its instances can be used concurrently without the need for any external synchronization. eg. AtomicLong and ConcurrentHashMap.
- Conditionally thread-safe : Like unconditionally thread-safe, except that some methods require external synchronization for safe concurrent use.
eg. collections by Collections.synchronized wrappers, whose iterators require external synchronization.
- Not thread-safe : To use them concurrently, clients must surround each method invocation with external synchronization. eg. ArrayList and HashMap.
- Thread-hostile : Unsafe for concurrent use even if every method invocation is surrounded by external synchronization. Usually results from modifying static data without synchronization.

## Conditionally Thread-Safe Class

```text
Documenting a conditionally thread-safe class requires care. You must
indicate which invocation sequences require external synchronization, and which
lock (or in rare cases, locks) must be acquired to execute these sequences.
```

eg. Collections.synchronizedMap docs

```java
Map<K, V> m = Collections.synchronizedMap(new HashMap<>());
Set<K> s = m.keySet(); // Needn't be in synchronized block

synchronized(m) { // Synchronizing on m, not s!
  for (K key : s)
    key.f();
}
```

## Using Lock fields

```text
To prevent this denial-of-service attack, you can use a private lock object
instead of using synchronized methods (which imply a publicly accessible lock):

Because the private lock object is inaccessible outside the class, it is
impossible for clients to interfere with the object’s synchronization.
```

```java
// Private lock object idiom - thwarts denial-of-service attack
// Lock fields should always be declared final
private final Object lock = new Object();

public void foo() {
  synchronized(lock) {
  }
}
```

## Summary

```text
To summarize, every class should clearly document its thread safety properties
with a carefully worded prose description or a thread safety annotation. The
synchronized modifier plays no part in this documentation. Conditionally threadsafe
classes must document which method invocation sequences require external
synchronization and which lock to acquire when executing these sequences. If you
write an unconditionally thread-safe class, consider using a private lock object in
place of synchronized methods.
```
