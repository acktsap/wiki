# Memory Management

- [Memory Management](#memory-management)
  - [Main Memory](#main-memory)
    - [Swapping](#swapping)
    - [Fragmentation](#fragmentation)
    - [Paging](#paging)
    - [Segmentation](#segmentation)
  - [Virtual Memory](#virtual-memory)
    - [Demand Paging](#demand-paging)
    - [Page Replacement](#page-replacement)
    - [Frame Allocation](#frame-allocation)
    - [Thrashing](#thrashing)
    - [Memory-Mapped Files](#memory-mapped-files)
  - [Reference](#reference)

## Main Memory

### Swapping

![swapping](./img/memory-management-swapping.png)

- 메모리 관리를 위해 Memory에 있는 Process를 Disk로 내보내고 (swap out) 다시 불러오는 (swap in) 작업을 하는 것

### Fragmentation

- 외부 단편화 : Process들이 차지하는 memory영역 사이에 사용하지 않는 틈이 생기는 것
  - Compaction
    - 외부 단편화를 해소하기 위해 프로세스가 사용하는 공간들을 한쪽으로 몰아서 자유공간을 확보하는 기법
    - JVM의 Old Generation이 Object에 대해서 이 작업을 함
- 내부 단편화 : Process에 memory를 할당할 때 특정 단위로 할당해서 실제 사용하지 않는 부분이 생김. 이 부분을 말함

### Paging

![pagging](./img/memory-management-pagging.png)

- 프로세스를 일정 크기의 page로 나누어서 frame단위로 나눈 메모리에 적재하는 방식. 프로세스가 사용하는 공간이 연속적이지 않아도 됨.
- 장점
  - 외부 단편화를 해결
  - 같은 프로그램을 사용하는 프로세스간 메모리의 공유가 가능
- 단점
  - frame 크기에 따라 내부 단편화가 더 심해질 수 있음

### Segmentation

![segmentation](./img/memory-management-segmentation.png)

- Process를 논리적인 segment의 단위로 구분해서 메모리에 적재하는 기법
  - 장점
    - 같은 프로그램을 사용하는 프로세스간 메모리의 공유가 가능
  - 단점
    - 외부 단편화

## Virtual Memory

- 가상 주소 공간을 실제 물리 메모리 보다 크게 잡아서 메모리 용량보다 더 많은 process를 올리는 기법
- 프로그램 실행 시작 시에 프로그램 전체를 Memory에 올리지 않고 필요한 것만 올리는 Depend Paging (요구 페이징)을 사용. 그렇기 때문에 필요한 page가 없는 경우 page fault가 발생
- Page fault가 발생하면 page를 디스크에서 가져와야함. But 이 때 물리 메모리가 가득 차 있는 경우 page replacement가 이루어져야함
- Page Replacement Algorithm
  - First-In First-Out (FIFO) : 가장 먼저 메모리에 들어온 페이지를 교체
    - 문제점 : `Belady의 모순`: 페이지를 저장할 수 있는 페이지 프레임의 갯수를 늘려도 되려 페이지 부재가 더 많이 발생하는 모순이 발생
  - Optimal : 앞으로 가장 오랫동안 사용되지 않을 페이지를 찾아 교체 (불가능)
  - Least Recently Used (LRU) : 가장 오랫동안 사용되지 않은 페이지를 선택하여 교체
  - Least Frequently Used (LFU) : 참조 횟수가 가장 적은 페이지를 교체
  - Most Frequently Used (MFU) : 참조 횟수가 가장 많은 페이지를 교체

### Demand Paging

### Page Replacement

### Frame Allocation

### Thrashing

### Memory-Mapped Files

## Reference

- Operating System Concepts (Operating System Concepts, Ninth Edition)
  - [Main Memory](https://www.cs.uic.edu/~jbell/CourseNotes/OperatingSystems/8_MainMemory.html)
  - [Virtual Memory](https://www.cs.uic.edu/~jbell/CourseNotes/OperatingSystems/9_VirtualMemory.html)
- wiki
  - none