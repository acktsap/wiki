# Item 78: Synchronize access to shared mutable data

## Synchronization

Not only does synchronization prevent threads from observing an object
in an inconsistent state (mutual exclusion), but it ensures that each thread
entering a synchronized method or block sees the effects
of all previous modifications that were guarded by the same lock

> 동기화는 mutual exclusion를 만족시킬 뿐만 아니라 happen-before condition도 보장시키는데 사용됨.

## Synchronization on primitive type

The language specification guarantees that reading or writing a variable is
atomic unless the variable is of type long or double [JLS, 17.4, 17.7].

While the language specification guarantees that a thread will not see an
arbitrary value when reading a field, it does not guarantee that a value written by
one thread will be visible to another. Synchronization is required for reliable
communication between threads as well as for mutual exclusion.

This is due to a part of the language specification known as the memory model,
which specifies when and how changes made by one thread become visible to others.

> Java에서는 long, double을 제외한 primitive type에는 operation이 atomic함.\
> But cache때문에 한 Thread가 변경한 내용이 다른 Thread에 보이지 않을 수가 있음. Primitive type이라도 그럼 ㅇㅇ\
> volatile keyword를 쓰면 cache를 안보고 memory를 바로 보기 때문에 이를 해결할 수 있음.

## Stopping thread

The libraries provide the Thread.stop method,
but this method was deprecated long ago because it is inherently unsafe—its use
can result in data corruption. Do not use Thread.stop. A recommended way to
stop one thread from another is to have the first thread poll a boolean field that is
initially false but can be set to true by the second thread to indicate that the first
thread is to stop itself.

> Thread를 멈출 때는 Thread.stop을 쓰지 말고 pool을 끝내는 boolean타입 변수를 사용.\
> 이 경우 volatile을 쓰거나 그 변수를 멈추는 부분에 synchronized를 걸어야 함. 안그러면 컴파일러 최적화때문에 그 부분이 묻힘...

## Synchronization & Read/Write

It is not sufficient to synchronize only the write method!
Synchronization is not guaranteed to work unless both read and
write operations are synchronized

> write랑 read둘다 synchronized 걸어야 happen-before condition을 만족시킬 수 있음.

## Avoid Synchronication

The best way to avoid the problems discussed in this item is not to share
mutable data. Either share immutable data (Item 17) or don’t share at all. In other
words, confine mutable data to a single thread

> 동시성 문제를 제거하는 방법은 객체를 immutable하게 만드는 것. 그러면 happen-before이 무조건 만족됨. mutable 객체는 한 thread에서만 사용.

## Summary

when multiple threads share mutable data, each thread that
reads or writes the data must perform synchronization. In the absence of
synchronization, there is no guarantee that one thread’s changes will be visible to
another thread. The penalties for failing to synchronize shared mutable data are
liveness and safety failures

If you need only inter-thread communication,
and not mutual exclusion, the volatile modifier is an acceptable form of
synchronization, but it can be tricky to use correctly
