# Memory Management

- [Main Memory](#main-memory)
  - [vs Cache?](#vs-cache)
  - [Address Binding](#address-binding)
  - [Dynamic Loading](#dynamic-loading)
  - [Dynamic Linking and Shared Libraries](#dynamic-linking-and-shared-libraries)
  - [How to seperate memory space between process?](#how-to-seperate-memory-space-between-process)
  - [Logical Address Space vs Physical Address Space](#logical-address-space-vs-physical-address-space)
  - [Swapping](#swapping)
  - [Fragmentation](#fragmentation)
  - [Segmentation](#segmentation)
  - [Paging](#paging)
  - [Hybrid Paging/Segmentation](#hybrid-pagingsegmentation)
  - [TLB (Translation Look-Aside Buffer)](#tlb-translation-look-aside-buffer)
- [Page Table Structure](#page-table-structure)
  - [Hierarchical Paging](#hierarchical-paging)
  - [Hashed Page Tables](#hashed-page-tables)
  - [Inverted Page Tables](#inverted-page-tables)
- [Virtual Memory](#virtual-memory)
  - [Demand Paging](#demand-paging)
  - [Page Fault](#page-fault)
  - [Copy-on-Write Fork](#copy-on-write-fork)
- [Page Replacement](#page-replacement)
  - [Locality of Reference](#locality-of-reference)
  - [Page Replacement Algorithm](#page-replacement-algorithm)
  - [First-In First-Out (FIFO) Page Replacement Algorithm](#first-in-first-out-fifo-page-replacement-algorithm)
  - [Optimal Page Replacement Algorithm](#optimal-page-replacement-algorithm)
  - [Most Frequently Used (MFU) Page Replacement Algorithm](#most-frequently-used-mfu-page-replacement-algorithm)
  - [Least Frequently Used (LFU) Page Replacement Algorithm](#least-frequently-used-lfu-page-replacement-algorithm)
  - [Least Recently Used (LRU) Page Replacement Algorithm](#least-recently-used-lru-page-replacement-algorithm)
  - [Page-Buffering Algorithms](#page-buffering-algorithms)
- [Allocation of Frames](#allocation-of-frames)
  - [Thrashing](#thrashing)
  - [Working-Set Model](#working-set-model)
  - [Page-Fault Frequency](#page-fault-frequency)
- [Memory-Mapped One](#memory-mapped-one)
  - [Memory-Mapped Files](#memory-mapped-files)
  - [Memory-Mapped I/O](#memory-mapped-io)
- [Kernel Memory Paging](#kernel-memory-paging)
- [Example](#example)
- [Reference](#reference)

## Main Memory

- Every instruction has to be fetched from memory before it can be executed, and most instructions involve retrieving data from memory or storing data in memory or both.

### vs Cache?

- Register 는 1 CPU Clock에 접근 가능함.
- Main memory의 접근에는 많은 CPU cycles가 소요될 수 있음. -> processor stall
- Cache는 CPU와 main memory 사이에 추가됨.

### Address Binding

todo

### Dynamic Loading

todo

### Dynamic Linking and Shared Libraries

https://github.com/jhyuk316/study/blob/main/Backend%20Roadmap/02%20General%20Knowledge/02.6%20Main%20Memory%20Management.md#15-dynamic-linking-and-shared-libraries

- todo: 설명
- 장점
  - stub spec이 바뀌지 않으면 변경 시 dll 교체만 하면 됨.
- 단점
  - dll 따로 관리하기 번거로움.

### How to seperate memory space between process?

![base-and-limit-register](./img/memory-management-base-and-limit-register.png)

- Base와 limit registers는, 특별한 privileged instruction을 사용하는 OS에 의해서만 로드될 수 있음.
- User mode에서 프로그램을 실행하여 OS나 다른 users의 memory에 엑세스할 수 없음.
- Base register에 Physical memory address를 담고 limit register에 사이즈를 담아서 구분.

### Logical Address Space vs Physical Address Space

![mmu](./img/memory-management-mmu.png)

- CPU에서는 실제 Logical Address를 사용하고 이를 memory-management unit (MMU)가 relocation register에 기반해서 Physical Address로 바꿈.

### Swapping

![swapping](./img/memory-management-swapping.png)

- 메모리 관리를 위해 Memory에 있는 Process를 Disk로 내보내고 (swap out) 다시 불러오는 (swap in) 작업을 하는 것.

### Fragmentation

- External Fragmentation : Process들이 차지하는 memory영역 사이에 사용하지 않는 틈이 생기는 것.
  - Solution : Compaction
    - 외부 단편화를 해소하기 위해 프로세스가 사용하는 공간들을 한쪽으로 몰아서 자유공간을 확보하는 기법.
    - JVM의 Old Generation이 Object에 대해서 이 작업을 함.
- Internal Fragmentation : Process에 memory를 할당할 때 특정 단위로 할당해서 실제 사용하지 않는 부분이 생겨서 발생.

### Segmentation

![segmentation](./img/memory-management-segmentation.png)

- Process를 논리적인 segment의 단위로 구분해서 메모리에 적재하는 기법.
- 장점
  - 같은 프로그램을 사용하는 프로세스간 메모리의 공유가 가능.
- 단점
  - 외부 단편화.

### Paging

![paging-hardware](./img/memory-management-paging-hardware.png)

![pagging](./img/memory-management-pagging.png)

- Logical memory space를 특정 크기의 page, Physical memory space를 같은 크기의 frame으로 나누어서 page를 서로 연속적이지 않은 frame에 할당하는 방식.
- Page table이란걸 둬서 page를 어떤 frame에 할당했는지를 기록.
- 장점
  - 외부 단편화를 해결.
  - 같은 program이나 같은 shared library를 사용하는 proces간 page의 공유가 가능.
- 단점
  - frame 크기에 따라 내부 단편화가 더 심해질 수 있음.

### Hybrid Paging/Segmentation

- Segmentation한 단위에 대해서 Paging을 하는 방법.
- 장점
  - paging sharing이 쉬움.
  - protection이 쉬움.
- 단점
  - Segmantation Mapping Table도 사용하고 Page Mapping Table도 사용해서 memory에 3번 접근 (table 2번, 실제 memory 1번)

todo: https://velog.io/@monsterkos/TIL2020.09.12 에서 사진

### TLB (Translation Look-Aside Buffer)

- page table에 대한 cache.
- 보통 특정한 h/w를 사용해서 page entry를 병렬적으로 탐색함.

todo: 사진 추가 정리

## Page Table Structure

todo

### Hierarchical Paging

### Hashed Page Tables

### Inverted Page Tables

## Virtual Memory

![virtual-memory](./img/memory-management-virtual-memory.png)

- 실제로 사용 가능한 memory를 추상화 해서 사용자에게 큰 memory로 보게 만드는 기법.

- 가상 주소 공간을 실제 물리 메모리 보다 크게 잡아서 메모리 용량보다 더 많은 process를 올리는 기법
- 프로그램 실행 시작 시에 프로그램 전체를 Memory에 올리지 않고 필요한 것만 올리는 Depend Paging (요구 페이징)을 사용. 그렇기 때문에 필요한 page가 없는 경우 page fault가 발생

### Demand Paging

![demand-pagging](./img/memory-management-demand-pagging.png)

- Process가 사용하는 page를 memory에 올릴 때 (swap in) 전부 올리지 않고 사용하는 page만 올리는 기법.
- Page table에 validity bit를 추가해서 올라와있지 않는 page는 invalid로 표시해 둠.

### Page Fault

- Process가 사용하려는 page가 physical memory에 올라와 있지 않는 경우.

### Copy-on-Write Fork

![copy-on-write](./img/memory-management-copy-on-write.png)

- 한 process가 fork하면 child process가 memory의 값을 바꾸지 않는 이상 parent process와 같은 page를 사용 가능.
- child process가 memory의 값을 변경시 해당 page만 copy해서 write.

## Page Replacement

![page-replacement](./img/memory-management-page-replacement.png)

- Page fault가 발생하면 page를 디스크에서 가져와야함. 이 때 physical memory가 가득 차 있는 경우 frame이 비길 기다려야 하는데 보통 사용하지 않는 page를 swap out하고 요청받은 page를 swap in하는 page replacement를 주로 함.
- Page Replacement를 할 때 변경된 page는 디스크에 write하는 작업을 해줘야 함. 이를 관리하기 위해 dirty bit를 설정해둠.

### Locality of Reference

- Process가 program/data의 특정 영역을 집중적으로 참조하는 현상. Loop structure, Array 등의 구조로 인해서 발생.
- Spatial Locality
  - 참조한 영역과 인접한 영역을 참조.
- Temporal Locality
  - 한번 참조한 영역을 곧 다시 참조.

> Array등을 처리할때 Spatial Locality를 고려.

### Page Replacement Algorithm

- Page Replacement Algorithm의 goal은 page fault를 최소한으로 줄이는 것. Page fault가 일어나면 I/O가 일어나서 비용이 많이 듬.
- 보통 한 Process가 이용가능한 frame의 수가 많아지면 (process에 더 많은 memory를 할당) page fault가 적어짐.

### First-In First-Out (FIFO) Page Replacement Algorithm

- 가장 먼저 메모리에 들어온 (oldest load time) 페이지를 교체.
- 문제점
  - Belady's anomaly (Belady의 모순) : process에 더 많은 frame을 할당해도 page fault가 더 많이 발생하는 모순.

> Belady's anomaly 는 자주 사용되는 page를 swap out시켜서 발생하는 듯.

### Optimal Page Replacement Algorithm

- 앞으로 가장 오랫동안 사용되지 않을 페이지를 찾아 교체.
- 문제점
  - 미래에 언제 어떻게 사용될지 몰라서 이상적으로는 불가능. 실전에서는 적당히 추정해서 victim을 결정.
  - 그래서 실전에서는 다른 algorithm과 비교하는 benchmark 대상으로 사용.

### Most Frequently Used (MFU) Page Replacement Algorithm

- 참조 횟수가 가장 많은 page를 이미 교체. 이미 많이 쓰였으니 미래에 잘 안쓰일거라 가정.

### Least Frequently Used (LFU) Page Replacement Algorithm

- 참조 횟수가 가장 적은 page를 교체. 자주 안쓰이니까 미래에도 잘 안쓰일거라 가정.
- 문제점
  - 초기에 사용되고 이후에 사용이 안되는 녀석을 유지. -> aging으로 해결.

### Least Recently Used (LRU) Page Replacement Algorithm

- 가장 오랫동안 사용되지 않은 (oldset use time) page를 선택하여 교체.
- todo: https://github.com/jhyuk316/study/blob/main/Backend%20Roadmap/02%20General%20Knowledge/02.7%20Virtual%20Memory%20Main%20Management.md#45-lru-approximation-page-replacement 보고 구현체 보완

### Page-Buffering Algorithms

- 성능 향상을 위해 page를 buffering하는 방법.
- free frame의 수를 일정 이상 유지해서 page fault가 나서 frame 요청이 올 경우 바로 free frame을 할당해서 빠르게 응답. background에서 page replacement를 통해 free frame을 다시 채움.
- 장점
  - page replacement를 안하고 바로 응답이 가능.
  - background에서 돌아서 dirty bit가 set된 페이지도 바로 save하는 식으로 하면 됨.
- 단점
  - 적절한 frame size를 유지하기 힘들다.

## Allocation of Frames

- Frame을 process에 어떻게 할당할 것인가.
- 고려사항
  - Minimum Number of Frames : process에 할당도는 최소한의 frame수를 어떻게 할 것인가?
  - Allocation Algorithms : 각 process에 동일한 수의 frame을 할당할 것인가? process의 크기에 비례해서 할당할 것인가?
  - Global vs Local Allocation : Page fault가 발생했을 때 해당 process에서 page victim을 찾을것인가 전체 process에서 page victim을 찾을 것인가?
  - Non-Uniform Memory Access : Multi core에서 memory랑 cpu가 같은 board에 있느냐 아니냐에 따라 속도가 차이나는데 어떻게 할당할 것인가?

### Thrashing

![thrashing](./img/memory-management-thrashing.png)

- 할당된 frame의 수가 사용하는 page의 수보다 적어서 page fault가 자주 일어나게 되서 paging을 하는 시간이 cpu를 사용하는 시간보다 커지는 현상.
- Local page replacement 정책을 쓰면 해당 process에 영향을 한정지을 수 있지만 I/O queue를 여전히 차지해서 느려짐.
- 해결책
  - 한 memory의 근처에 있는 memory를 사용할 확률이 높다는 것 (Locality)를 감안해서 Locality size만큼 frame 수를 주면 page fault를 줄일 수 있음. 다른 Locality를 사용할 때만 page fault가 발생.

### Working-Set Model

![working-set-model](./img/memory-management-working-set-model.png)

![working-set-and-page-fault-rate](./img/memory-management-working-set-and-page-fault-rate.png)

- 최근 사용된 page를 시간순으로 나열하고 working set window 를 관리해서 Locality를 정의.

### Page-Fault Frequency

- Working-Set Model은 overhead가 크므로 설정된 Page Fault Rate보다 높으면 Frame 할당하고 낮으면 Frame 회수하는 방법.
- 프로세스가 새로운 Locality로 이동하는 기간에는 재대로 작동하지 않음.

## Memory-Mapped One

- 모든 종류의 I/O 가 느리니까 memory에 mapping시켜서 I/O 성능을 향상시키는 기법.

### Memory-Mapped Files
  
![memory-mapped-files](./img/memory-management-memory-mapped-files.png)

- Data file을 process file처럼 memory에 올려서 더 빨리 access하는 기법.
- File에 write하는게 바로 disk에 반영되지 않고 `flush()` system call을 하거나 `close()`를 해야 반영됨.

### Memory-Mapped I/O

- 장치에 대한 I/O를 memory를 통해 추상화 함.
- 비디오 카드 같은 장치를 장치의 레지스터를 프로세스의 가상 메모리 공간에 매핑.

## Kernel Memory Paging

- Kernel은 항상 memory에 올라와 있으니까 paging 안당해야 함.

## Example

- [paging](./practice/paging.c) : `./run.sh paging.c 19986`

## Reference

- Operating System Concepts (Operating System Concepts, Ninth Edition)
  - [Main Memory](https://www.cs.uic.edu/~jbell/CourseNotes/OperatingSystems/8_MainMemory.html)
  - [Virtual Memory](https://www.cs.uic.edu/~jbell/CourseNotes/OperatingSystems/9_VirtualMemory.html)
- wiki
  - [Virtual Memory](https://en.wikipedia.org/wiki/Virtual_memory)