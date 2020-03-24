# JVM

- [JVM](#jvm)
  - [Architecture](#architecture)
  - [Heap Structure](#heap-structure)
    - [Constant Pool](#constant-pool)
  - [Garbage Collector](#garbage-collector)
    - [Minor GC, Major GC](#minor-gc-major-gc)
    - [Card Table](#card-table)
    - [Thread-Local Allocation Buffers](#thread-local-allocation-buffers)
    - [Stop the world](#stop-the-world)
  - [Garbage Collectors](#garbage-collectors)
    - [Serial Collector](#serial-collector)
    - [Parallel Collector](#parallel-collector)
    - [Parallel Compacting Collector](#parallel-compacting-collector)
    - [CMS (Concurrent Mark Sweep) Collector](#cms-concurrent-mark-sweep-collector)
    - [G1 Collector](#g1-collector)
  - [GC Tuning](#gc-tuning)
    - [Out Of Memory (OOM) Error](#out-of-memory-oom-error)
    - [JVM Crash](#jvm-crash)
  - [Reference](#reference)

## Architecture

![jvm-architecture](./img/jvm-architecture.png)

- Java Source : *.java
- Java Compiler : *.java -> *.class
- Java Byte Code : *.class
- ClassLoader : Loads *.class to Runtime Data Areas
- Execution Engine : Interpret *.class in a memory
- Runtime Data Area : JVM process가 OS로부터 할당받은 memory area
  - Method Area : 클래스, 변수, Method, static변수, 상수 등이 저장 (shared amoung thread)
  - Heap Area : 객체 instances 저장, GC의 대상
  - Stack Area : Method 내에서 사용되는 값들 (parameter, return local variable)등을 저장. Thread별로 생성
  - PC Registers : Program Counter Registers, Thread별로 다음에 실행될 명령어의 주소를 저장
  - Native Method Stack : c/c++ 등의 native method를 호출하기 위해 할당되는 구역

## Heap Structure

![jvm-heap-structure](./img/jvm-heap-structure.png)

- Young Generation (Minor GC Target)
  - Eden : 최초로 할당되는 영역
  - Survivor : GC 몇번 해도 살아남았을 때 Eden에서 이동.
- Old Generation : Survivor에서 GC몇번 살아남으면 이동 ,Major GC Target
- Perm : Class metadata, method metadata, static 변수 상수 저장
  - 1.8부터 Metaspace로 변경 (더 커짐) : static 변수 상수를 heap으로 이동

### Constant Pool

![constant-pool](img/constant-pool.png)

String같은거는 내부적으로 Pool을 만들어서 활용. 중복 생성을 방지함.

## Garbage Collector

![jvm-heap-thread-method.png](./img/jvm-heap-thread-method.png)

사용되지 않는 Object를 제거하는 과정. RootSet에서 Reference를 가지지 않는 것을 제거. 메모리가 부족할 때 일어남.\
Root Set은 다음과 같다.

- JVM 스택 내의 Local variable, Operend Stack가 참조하는 Object
- Constant Pool이 참조하는 Object
- JNI애 의해 생성된 객체가 참조하는 Object

Root Set에 의해 참조되지 않는 것은 Unreachable Object라고 하고 GC의 대상이 됨

### Minor GC, Major GC

- Minor GC : Young Generation에 대해서 GC를 수행하는 것
- Major GC : Old Generation에 대해 수행하는 것

### Card Table

![card-table](img/card-table.png)

Minor GC를 할 때 Old Generation이 참조하면 GC의 대상이 되서는 안된다.\
그런데 그렇다고 Old Generation을 전부 파보기에는 너무 많다.\
그래서 이를 Mark해두자! 한게 Card Table로 Old Generation 내에 존재하는 메모리 구조임.\
Minor GC를 수행할 때 Card Table만 보면 Young Generation에서 제거해야 할 대상을 빠르게 할 수 있음.

### Thread-Local Allocation Buffers

Multi Thread가 동시에 메모리를 요청하면 Lock을 걸어야 해서 병목이 발생할 수 있음.\
스레드별로 각자 영역을 할당받으면 동시에 할당받는게 가능. 이것이 TLAB.\
Young Generation의 Fast Allocation을 위한 것.

### Stop the world

GC를 하기 위해 JVM이 멈추는 것. 이 시간을 Suspend time이라고 부름.

## Garbage Collectors

### Serial Collector

![jvm-serial-minor-gc1](./img/jvm-serial-minor-gc1.png)
![jvm-serial-minor-gc2](./img/jvm-serial-minor-gc2.png)
![jvm-serial-minor-gc3](./img/jvm-serial-minor-gc3.png)

![jvm-major-mark-sweep-compaction](./img/jvm-major-mark-sweep-compaction.png)

- Young Generation : Serial Copy Algorithm
- Old Generation : Serial Mark-Sweep-Compaction

### Parallel Collector

![jvm-parallel-minor](./img/jvm-parallel-minor.png)

- Young Generation : **Parallel Copy Algolrithm**
  - Multi Thread가 Promition과정 중 Old Generation에 동시에 접근하는 것을 막기 위해서 Thread별로 영역을 할당.\
    이를 PLAB (Parallel Local Allocation Buffer)라고 부름.
- Old Generation : Serial Mark-Sweep-Compaction

![jvm-plab](./img/jvm-plab.png)

### Parallel Compacting Collector

![jvm-parallel-compacting-major1](./img/jvm-parallel-compacting-major1.png)
![jvm-parallel-compacting-major2](./img/jvm-parallel-compacting-major2.png)
![jvm-parallel-compacting-major3](./img/jvm-parallel-compacting-major3.png)

- Young Generation : Parallel Copy Algoritim
- Old Generation : **Parallel Mark-Summary-Compaction**
  - Mark : Multi Thread, Region별로 Live Object를 Marking
  - Summary : Single Thread, Region별로 Live Object의 Density에 따라 Dense Prefix를 설정.
  - Compaction : Multi Thread, Dense Prefix이후의 주소에 있는 객체를 대상으로 Sweep & Compaction한 후 대상의 객체들을 왼편으로 몰아넣음
    - 계속 이러다 보면 주소의 왼쪽으로 갈수록 오래 살아남은 녀석이 있을 확률이 높게됨!!

### CMS (Concurrent Mark Sweep) Collector

![jvm-cms-major](./img/jvm-cms-major.png)

- Young Generation : Parallel Copy Algorithm
- Old Generation : **Concurrent-Mark-Sweep**
  - Initial Mark : Single Thread, Stop-the-world, ClassLoader에서 가장 가까운 객체만 찾음
  - Concurrent Mark : Single Thread, Initial Mark에서 찾은 객체가 Reference하는 것을 찾음. 다른 Thread와 같이 실행.
  - Remark : Multi Thread, Stop-the-world, Concurrent Mark에서 추가되거나 참조가 끊긴 객체를 확인
  - Concurrent Sweep : Single Thread, Unreachable Object를 sweep, No compaction. 다른 Thread와 같이 실행.\
    Compaction이 없기 때문에 Old Area의 빈 공간에 대한 Free List를 관리.\
    Young Generation에서 승격된 것에 대해 Free List를 기반으로 할당.
- Pros
  - Low suspend time
- Cons
  - Memory와 Cpu를 많이 사용함
  - Free List를 기반으로 Young Generation에서 승격된 객체의 할당 위치를 찾는 것이 부하가 있음.\
    (Compaction이 있다면 그냥 Old Generation에 바로 할당하면 됨)
  - Compaction이 기본적으로는 없기 때문에 Compaction 수행 시 stop-the-world가 더 길어질 수 있음

### G1 Collector

![jvm-g1](./img/jvm-g1.png)

- CMS를 대체하기 위해 만들어짐. 물리적 Generation을 없애고 Region단위로 나눔
- Garbage First -> Garbage로 가득 차 있는 Region부터 GC를 수행
- Young Generation : Snapshot-At-The-Beginning
- Old Generation : Snapshot-At-The-Beginning

## GC Tuning

Old area로 넘어가는 Object를 최소화

### Out Of Memory (OOM) Error

heap dump떠서 뭐가 많이 차지하는지 확인

### JVM Crash

JVM이 그냥 죽어버리는 것...

## Reference

GC들

https://blog.embian.com/68

https://d2.naver.com/helloworld/1329

https://www.slideshare.net/ssuserb77a05/garbage-collection-20150213-44717452

Java Reference와 GC

https://d2.naver.com/helloworld/329631