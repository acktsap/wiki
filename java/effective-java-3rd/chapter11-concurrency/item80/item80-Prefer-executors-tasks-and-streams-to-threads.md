# Item 80: Prefer executors, tasks, and streams to threads

## ExecutorService

You can do many more things with an executor service.

For example

You can wait for a particular task to complete (with the `get` method)
You can wait for any or all of a collection of tasks to complete (using the `invokeAny` or `invokeAll` methods)
You can wait for the executor service to terminate (using the `awaitTermination` method)
You can retrieve the results of tasks one by one as they complete (using an `ExecutorCompletionService`)
You can schedule tasks to run at a particular time or to run periodically (using a
`ScheduledThreadPoolExecutor`)

## Choosing Thread Pool

If you want more than one thread to process requests from the queue, simply
call a different static factory that creates a different kind of executor service called a thread pool.
The java.util.concurrent.Executors class contains static factories
that provide most of the executors you’ll ever need.

For a small program, or a lightly loaded server, Executors.newCachedThreadPool is
generally a good choice because it demands no configuration and generally “does
the right thing.” But a cached thread pool is not a good choice for a heavily loaded
production server!

If a server is so heavily loaded that all of its CPUs are fully
utilized and more tasks arrive, more threads will be created, which will only make
matters worse. Therefore, in a heavily loaded production server, you are much
better off using Executors.newFixedThreadPool

> Executors.newXXX를 활용해서 thread pool을 생성하셈. 부하가 작은 서버에서는 cached pool이 ㄱㅊ으나 부하가 큰 서버에서는 fixed pool이 더 좋을 수 있음.

## High Level Abstraction : Runnable, Callable, ExecutorService

The key abstraction is the unit of work, which is the task.
There are two kinds of tasks: Runnable and its close cousin, Callable (which is
like Runnable, except that it returns a value and can throw arbitrary exceptions).
The general mechanism for executing tasks is the executor service.

> Runnable, Callable라는 task에 대한 추상화와, 실행에 대한 ExecutorService라는 추상화가 있음.

## ForkJoinPool

In Java 7, the Executor Framework was extended to support fork-join tasks,
which are run by a special kind of executor service known as a fork-join pool. A
fork-join task, represented by a ForkJoinTask instance, may be split up into
smaller subtasks, and the threads comprising a ForkJoinPool not only process
these tasks but “steal” tasks from one another to ensure that all threads remain
busy, resulting in higher CPU utilization, higher throughput, and lower latency

Parallel streams (Item 48) are written atop fork join pools and allow you
to take advantage of their performance benefits with little effort.

> java 7부터 자기 queue에 일이 없으면 다른 queue로부터 일을 뺏어오는 fork-join pool이 나옴. 이는 CPU utilization에서 좋음. java 8의 parallelstream을 쓰면 내부적으로 이걸 씀.
