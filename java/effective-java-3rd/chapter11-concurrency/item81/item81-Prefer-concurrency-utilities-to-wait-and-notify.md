# Item 81: Prefer concurrency utilities to wait and notify

## No Wait and Notify if possible

```text
Given the difficulty of using wait and notify
correctly, you should use the higher-level concurrency utilities instead.

The higher-level utilities in java.util.concurrent fall into three categories:
the Executor Framework, concurrent collections; and synchronizers
```

## Concurrent Collections

```text
The concurrent collections are high-performance concurrent implementations
of standard collection interfaces such as List, Queue, and Map. To provide high
concurrency, these implementations manage their own synchronization internally
```

State-dependent modify operation

```text
Because you can’t exclude concurrent activity on concurrent collections, you
can’t atomically compose method invocations on them either. Therefore, concurrent
collection interfaces were outfitted with state-dependent modify operations,
which combine several primitives into a single atomic operation

For example, Map’s putIfAbsent(key, value) method inserts a mapping for
a key if none was present and returns the previous value associated with the key,
or null if there was none
```

CoucurrentHashMap

```text
Use ConcurrentHashMap in preference to Collections.synchronizedMap.
Since Collections.synchronizedMap is just a wrapper for map whereas
ConcurrentHashMap handles synchronization more carefully (eg. single bucket lock)
```

BlockingOperations

```text
Some of the collection interfaces were extended with blocking operations,
which wait (or block) until they can be successfully performed. For example,
BlockingQueue extends Queue and adds several methods, including take, which
removes and returns the head element from the queue, waiting if the queue is
empty. This allows blocking queues to be used for work queues (also known as
producer-consumer queues).

Most ExecutorService implementations,
including ThreadPoolExecutor, use a BlockingQueue
```

## Synchronizers

```text
Synchronizers are objects that enable threads to wait for one another, allowing
them to coordinate their activities.

The most commonly used synchronizers are CountDownLatch and Semaphore.
Less commonly used are CyclicBarrier and Exchanger.
The most powerful synchronizer is Phaser.
```

## Always use wait loop idiom

```text
The wait method is used to make a thread wait for some condition. It must be
invoked inside a synchronized region that locks the object on which it is invoked.

Always use the wait loop idiom to invoke the wait method; never invoke it
outside of a loop.
```

```java
// The standard idiom for using the wait method
synchronized (obj) {
  while (<condition does not hold>)
    obj.wait(); // (Releases lock, and reacquires on wakeup)
  ... // Perform action appropriate to condition
}
```

Why `white(condition) { obj.wait(); }`

```text
Testing the condition before waiting and skipping the wait if the condition
already holds are necessary to ensure liveness. If the condition already holds and
the notify (or notifyAll) method has already been invoked before a thread
waits, there is no guarantee that the thread will ever wake from the wait.

Testing the condition after waiting and waiting again if the condition does not
hold are necessary to ensure safety. If the thread proceeds with the action when
the condition does not hold, it can destroy the invariant guarded by the lock.
```

Prefer `notifyAll` over `notify`

```text
 It is sometimes said that you should always use notifyAll
(Recall that notify wakes a single waiting thread, assuming such a
thread exists, and notifyAll wakes all waiting threads.).
It will always yield correct results because it guarantees that
you’ll wake the threads that need to be awakened
```

## Summary

```text
In summary, using wait and notify directly is like programming in concurrency
assembly language.

There is seldom, if ever, a reason to use wait and
notify in new code. If you maintain code that uses wait and notify, make sure
that it always invokes wait from within a while loop using the standard idiom.

The notifyAll method should generally be used in preference to notify. If
notify is used, great care must be taken to ensure liveness.
```
