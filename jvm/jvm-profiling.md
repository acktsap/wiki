# Jvm Profiling

- [jdk tools](#jdk-tools)
- [Thread dump](#thread-dump)
- [Heap Profiling](#heap-profiling)
- [Show class file compiler version](#show-class-file-compiler-version)

## jdk tools

- jcmd : jdk7 or higher, integrated one (jstack, jinfo, jmap, etc..), use it
- jhat : jdk6 or higher, analyze heap dump file
- jmap : jdk6 or higher, dump heap
- jinfo : jdk5 or higher, provide jvm system property info
- jstack : jdk5 or higher, dump stack
- jstat : jdk6 or higher, monitoring gc, classloading status, jvm status, etc..
- jps : show jvm process

jcmd show all commands

```sh
jcmd <pid> help
```

## Thread dump

```sh
jcmd <pid> Thread.print
```

## Heap Profiling

Terms

- Shallow Heap : A memory directly used by a object
- Retained Heap : Shallow Heap + A sum of memory of objects only referred by a single referee object
- Deep Heap : Retained Heap + A sum of memory of objects referred by a referee object
- Dominator tree : A tree of object reference

```sh
# dump after full gc (reachable object only)
jcmd <pid> GC.heap_dump $PWD/dump.hprof

# dump without full gc (includes unreachable object)
jcmd <pid> GC.heap_dump $PWD/alldump.hprof -all
```

View

- MAT : Eclipse Memory Analyzer, A dump file analyzer
  - Install : marketplace -> Find 'memory analyzer' -> Restart -> Find **Memory Analysis** in the perspective
  - Usage : File -> Open Heap Dump -> Select dump binary
  - heap size 단위는 **byte**

## Show class file compiler version

- 45.3: Java 1.1
- 46: Java 1.2
- 47: Java 1.3
- 48: Java 1.4
- 49: Java 5
- 50: Java 6
- 51: Java 7
- 52: Java 8
- 53: Java 9
- 54: Java 10
- 55: Java 11
- 56: Java 12
- 57: Java 13
- 58: Java 14

```sh
# unpack jar file
jar xf test-x.x.x.jar

# check version
javap -v A.class | grep major
```
