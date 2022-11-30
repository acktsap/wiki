# 1. Introduction

- [1. Introduction](#1-introduction)
  - [1.1. Brief History of Concurrency](#11-brief-history-of-concurrency)
  - [1.2. Benefits of Threads](#12-benefits-of-threads)
    - [Exploiting Multiple Processors](#exploiting-multiple-processors)
    - [Simplicity of Modeling](#simplicity-of-modeling)
    - [Simplified Handling of Asynchronous Events](#simplified-handling-of-asynchronous-events)
    - [More Responsive User Interfaces](#more-responsive-user-interfaces)
  - [1.3. Risks of Threads](#13-risks-of-threads)
    - [Safety Hazards](#safety-hazards)
    - [Liveness Hazards](#liveness-hazards)
    - [Performance Hazards](#performance-hazards)
  - [1.4. Threads are Everywhere](#14-threads-are-everywhere)

## 1.1. Brief History of Concurrency

In the ancient past, computers didn't have operating systems; they executed a single program from beginning to end, and that program had direct access to all the resources of the machine

Operating systems evolved to allow more than one program to run at once, running individual programs in processes: isolated, independently executing programs to which the operating system allocates resources such as memory, file handles, and security credentials

Several motivating factors led to the development of operating systems that allowed multiple programs to execute simultaneously:

Resource utilization. Programs sometimes have to wait for external operations such as input or output, and while waiting can do no useful work. It is more efficient to use that wait time to let another program run.

Fairness. Multiple users and programs may have equal claims on the machine's resources. It is preferable to let them share the computer via finer-grained time slicing than to let one program run to completion and then start another.

Convenience. It is often easier or more desirable to write several programs that each perform a single task and have them coordinate with each other as necessary than to write a single program that performs all the tasks.

The same concerns (resource utilization, fairness, and convenience) that motivated the development of processes also motivated the development of threads. Threads allow multiple streams of program control flow to coexist within a process. They share process-wide resources such as memory and file handles, but each thread has its own program counter, stack, and local variables.

Threads are sometimes called lightweight processes, and most modern operating systems treat threads, not processes, as the basic units of scheduling

Since threads share the memory address space of their owning process, all threads within a process have access to the same variables and allocate objects from the same heap, which allows finer-grained data sharing than inter-process mechanisms. But without explicit synchronization to coordinate access to shared data, a thread may modify variables that another thread is in the middle of using, with unpredictable results

> OS는 여러 개의 일을 동시에 처리하고 싶음. 그래서 만든 작업의 단위가 process. But OS의 관점에서 이것들은 분리되어 관리되어야 함. memory, file handle등을 OS가 분리해서 관리해야 해서 그럼. But process만으로는 Resource utilizaiton, Fairness, Converience 등의 문제를 효과적으로 해결할 수 없음. 그래서. Thread가 나오게 됨. 이는 lightweight process라고도 불리고 scheduling의 기본 단위임. process내의 자원을 공유하기 때문에 inter-process communication보다 효율적이나 동기화를 해야 하는 문제가 발생할 수 있음.

## 1.2. Benefits of Threads

### Exploiting Multiple Processors

Since the basic unit of scheduling is the thread, a program with only one thread can run on at most one processor at a time. So, programs with multiple active threads can execute simultaneously on multiple processors

Using multiple threads can also help achieve better throughput on single-processor systems. If a program is single-threaded, the processor remains idle while it waits for a synchronous I/O operation to complete. In a multithreaded program, another thread can still run while the first thread is waiting for the I/O to complete, allowing the application to still make progress during the blocking I/O

> Multicore에서 multithread가 코어 각자에서 실행되니까 빠름. I/O할때도 그 thread가 blocking되어 있는 동안 다른 thread가 일을 할 수 있어서 좋음.

### Simplicity of Modeling

It is often easier to manage your time when you have only one type of task to perform (fix these twelve bugs) than when you have several.

The same is true for software: a program that processes one type of task sequentially is simpler to write, less error-prone, and easier to test than one managing multiple different types of tasks at once. A complicated, asynchronous workflow can be decomposed into a number of simpler, synchronous workflows each running in a separate thread, interacting only with each other at specific synchronization points.

This benefit is often exploited by frameworks such as servlets or RMI (Remote Method Invocation). Servlet writers do not need to worry about how many other requests are being processed at the same time or whether the socket input and output streams block

> 한개의 복잡한 작업을 하는 것 보다 여러 개의 간단한 작업을 하는 식으로 하는게 모델링이 쉬움. Servlet container 같은거에서는 doService등이 single thread가 하는 것 처럼 구현할 수 있음. 그렇게 하면 Servlet Container가 multi thread처리를 해줌.

### Simplified Handling of Asynchronous Events

A server application that accepts socket connections from multiple remote clients may be easier to develop when each connection is allocated its own thread and allowed to use synchronous I/O

If an application goes to read from a socket when no data is available, read blocks until some data is available. In a single-threaded application, this means that not only does processing the corresponding request stall, but processing of all requests stalls while the single thread is blocked. To avoid this problem, single-threaded server applications are forced to use non-blocking I/O, which is far more complicated and error-prone than synchronous I/O. However, if each request has its own thread, then blocking does not affect the processing of other requests

> Single Thread라면 Asynchronous하게 I/O를 처리해야 하는데 이는 복잡함. Multi Thread가 이를 각자 blocking한 형태로 처리하게 하는게 더 쉬움

### More Responsive User Interfaces

Modern GUI frameworks, such as the AWT and Swing toolkits, replace the main event loop with an event dispatch thread (EDT). When a user interface event such as a button press occurs, application-defined event handlers are called in the event thread.

> GUI는 Single Thread로 두고 button클릭 시 일을 하는 녀석을 따로 둬서 사용자 경험을 증가시킴

## 1.3. Risks of Threads

### Safety Hazards

In the absence of synchronization, the compiler, hardware, and runtime are allowed to take substantial liberties with the timing and ordering of actions, such as caching variables in registers or processor􀍲local caches where they are temporarily (or even permanently) invisible to other thread.

```java
@NotThreadSafe
public class UnsafeSequence {
  private int value;
  // The problem with UnsafeSequence  is that with some unlucky timing
  // two threads could call getNext and receive the same value
  public int getNext() {
    return value++;
  }
}

@ThreadSafe
public class Sequence {
  @GuardedBy("this") private int nextValue;
  public synchronized int getNext() {
    return nextValue++;
  }
}
```

> 적절한 동기화를 하지 않으면 한 스레드가 한 작업이 다른 스레드에 보이지 않는 Safety Harards문제를 만날 수 있음.

### Liveness Hazards

While safety means "nothing bad ever happens", liveness concerns the complementary goal that "something good eventually happens". A liveness failure occurs when an activity gets into a state such that it is permanently unable to make forward progress. One form of liveness failure that can occur in sequential programs is an inadvertent infinite loop, where the code that follows the loop never gets executed. The use of threads introduces additional liveness risks. For example, if thread A is waiting for a resource that thread B holds exclusively, and B never releases it, A will wait forever.

> 동기화를 이상하게 하면 다음 작업을 수행하지 못하는 Liveness Hazards를 만날 수 있음. Liveness는 스레드에 있는 문제의 카테고리임. Liveness 문제는 deadlock, live lock, starvation이 있음

### Performance Hazards

In well designed concurrent applications the use of threads is a net performance gain, but threads nevertheless carry some degree of runtime overhead. Context switches - when the scheduler suspends the active thread temporarily so another thread can run - are more frequent in applications with many threads, and have significant costs: saving and restoring execution context, loss of locality, and CPU time spent scheduling threads instead of running them. When threads share data, they must use synchronization mechanisms that can inhibit compiler optimizations.

> Thread간 Context Switching비용이나 동기화 하는 비용이 Performance Hazards를 낳을 수 있음

## 1.4. Threads are Everywhere

Every Java application uses threads. When the JVM starts, it creates threads for JVM housekeeping tasks (garbage collection, finalization) and a main thread for running the main method

> jvm만 실행해도 Thread가 있음. 여기저기에서 쓰임 잘 알아야 함
