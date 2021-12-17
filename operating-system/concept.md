# Operating System

- [Operating System](#operating-system)
  - [Storage Management](#storage-management)
    - [Cache Locality](#cache-locality)
  - [Etc](#etc)
    - [Blocking / Non-Blocking I/O, Synchronous / Asynchronous Programming](#blocking--non-blocking-io-synchronous--asynchronous-programming)
  - [References](#references)

## Storage Management

### Cache Locality

- Cache의 적중률을 극대화 시키기 위해 사용되는 원리
  - Temporal Locality (시간 지역성) : 최근에 참조된 주소는 조만간 다시 참조되는 특성
  - Spatical Locality (공간 지역성) : 참조된 주소와 인접한 주소의 내용이 다시 참조되는 특성
    - eg. array

## Etc

### Blocking / Non-Blocking I/O, Synchronous / Asynchronous Programming

![blocking-io](./img/blocking-io.png)
![non-blocking-io](./img/non-blocking-io.png)
![asynchronous-io](./img/asynchronous-io.png)

- Blocking I/O vs Non-Blocking I/O
  - Kernal에 I/O를 요청한 후 ready queue에 들어가서 기다리면 blocking, 안들어가고 바로 응답을 보내면 non-blocking
- Synchronous vs Asynchronous
  - return시간과 요청 결과를 얻는 시간이 같으면 synchronous, 다르면 asynchronous
- Non-Blocking I/O vs Asynchronous
  - 관점이 다를 뿐임. Asynchronous를 위한 재료로써 Non-blocking I/O가 활용될 수 있으나 필수조건은 아님. I/O를 다른 Thread에 위임하고 그 thread는 blocking하고 있어도 Asynchronous 한거임

[위로](#Operating-System)

---

## References

Basic

https://github.com/JaeYeopHan/Interview_Question_for_Beginner/tree/master/OS

OS book

http://os-book.com/OS9/slide-dir/index.html

blocking, non-blocking, synchronous, Asynchronous

http://asfirstalways.tistory.com/348

https://tech.peoplefund.co.kr/2017/08/02/non-blocking-asynchronous-concurrency.html