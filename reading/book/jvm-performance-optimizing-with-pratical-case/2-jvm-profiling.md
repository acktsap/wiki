# Jvm profiling

## 2.1. Overview

### JDK official tools

Cli tools

- jcmd : jdk7 or higher, integrated one (jstack, jinfo, jmap, etc..), **use it**
- jhat : jdk6 or higher, analyze heap dump file
- jmap : jdk6 or higher, dump heap
- jinfo : jdk5 or higher, provide jvm system property info
- jstack : jdk5 or higher, dump stack
- jstat : jdk6 or higher, monitoring gc, classloading status, jvm status, etc..
- jps : show jvm process

Gui tools

- jconsole : jdk4 or higher
- jvirualvm (VisualVM): jdk6 or higher
- jmc (Java Mission Control) : jdk7 or higher

### 3rd party tools

- [Thread Dump Analyzer (TDA)](https://github.com/irockel/tda) : free
- [Eclipse Memory Analyzer Tool](https://www.eclipse.org/mat/) : free
- IBM HeapAnalyzer : ???
- [NetBeans Profiler](https://profiler.netbeans.org/) : free
- [JProfiler](https://www.ej-technologies.com/products/jprofiler/overview.html) : ej-technologies
- [GC Viewer](https://github.com/chewiebug/GCViewer) : free
- [Java Performance Analysis Tool (Patty)](https://sourceforge.net/projects/patty/) : free
- [Java Interactive Profiler (JIP)](https://sourceforge.net/projects/jiprof/) : free
- [Profiler4J](http://profiler4j.sourceforge.net/) : free

## 2.2. JVM Thread, Memory profiling

### Thread dump

[see](./1.3-jvm-synchronization.md)

### Heap dump

Running (very heavy, be careful on running application)

`jcmd <pid> GC.heap_dump $PWD/dump.hprof`

Jvm flags to dump on OOM (Out of Memory)

`-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=oom_heap_dump`

Run `MemoryPig.java`. Dump it with two method.

## 2.3. JCMD

TODO

## 2.4. JMC

Java Mission Control, A monitoring tool

### Install

Eclipse marketplace -> Find 'java mission control' -> Restart -> Find icon in toolbar\
Free in development

### Usage

Run `Simulation.java`

Click icon of open **Mission Control** perspective

Create a new connection or select existing one

Select any of those

- JMX Console (real-time monitoring)
- Flight Recording... (monitoring in specific term)

Enjoy monitoring

### Terms

- MBean : a Java object that represents a manageable resource (eg. application, component)

## 2.9. MAT

Eclipse Memory Analyzer, A dump file analyzer

### Install

Eclipse marketplace -> Find 'memory analyzer' -> Restart -> Find **Memory Analysis** in the perspective

### Usage

`jcmd <pid> GC.heap_dump $PWD/dump.hprof`

File -> Open Heap Dump -> Select dump binary

Enjoy dump views

### Terms

- Shallow Heap : A memory directly used by a object
- Retained Heap : Shallow Heap + A sum of memory of objects only referred by a single referee object
- Deep Heap : Retained Heap + A sum of memory of objects referred by a referee object
- Dominator tree : A tree of object reference
