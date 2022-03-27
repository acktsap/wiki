# Item 84: Don't depend on the thread scheduler

## DO NOT depends on system dependent scheduler

```text
Any program that relies on the thread scheduler for correctness or performance is likely to be nonportable.

The best way to write a robust, responsive, portable program is to ensure that
the average number of runnable threads is not significantly greater than the number
of processors. This leaves the thread scheduler with little choice: it simply
runs the runnable threads till they’re no longer runnable
```

## No busy-wait

```text
Threads should not busy-wait, repeatedly checking a shared object waiting for
its state to change. Besides making the program vulnerable to the vagaries of the
thread scheduler, busy-waiting greatly increases the load on the processor, reducing
the amount of useful work that others can accomplish
```

## No Thread.yield for performance

```text
양보

When faced with a program that barely works because some threads aren’t
getting enough CPU time relative to others, resist the temptation to “fix” the
program by putting in calls to Thread.yield.

The same yield invocations that improve performance on one JVM implementation
might make it worse on a second and have no effect on a third. Thread.yield has no testable semantics.
```

## DO NOT depends on thread priorities

```text
Thread priorities are among the least portable features of Java. It is unreasonable to attempt to solve a serious liveness problem by adjusting thread priorities
```

## Summary

```text
In summary, do not depend on the thread scheduler for the correctness of your
program. The resulting program will be neither robust nor portable. As a corollary,
do not rely on Thread.yield or thread priorities.
```
