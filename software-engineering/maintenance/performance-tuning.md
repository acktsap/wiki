# Performance Tuning

## Profiling

## Bottle Neck

sequencial하게 실행해서 snapshot 계속 해봐

stack trace에서 자주 보이는 부분이 병목

real에서 요청이 어떤 형식으로 자주 오는지 생각하고 거기 따라 처리를 해

## Tuning

### Thread Pool Size

- 최적의 size는 요구사항에 따라 다름. But 어찌됬건 thread는 core 개수만큼 동시에 (parallel, not concurrency) 돌 수 있음. 중요한 요인은 Operation이 어떤 종류인가임
- CPU Intensive
  - 그냥 computer로 연산만 하는거 (no I/O)
  - Core개수보다 많은거 해봤자 scheduling에 의해 context switching만 일어남. (L1, L2 cache 날라가버림)
  - Pool size : # of core
- I/O Intensive
  - DB, File, Network 등 I/O를 할 때 thread는 waiting set에 들어감. 이 때 다른 thread가 일을 할 수 있음
  - Pool size : # of core * (1 + Wait time / CPU time)
    - Wait time / CPU time : blocking coefficienet
    - Waiting time : time spent waiting for I/O bound (or like monitor lock) tasks to complete (eg. waiting for HTTP response from remote service)
    - CPU time : the time spent being busy (eg. marshaling/unmarshaling)
    - thread 개수 올려가면서 성능 측정 하다가 linear하게 오르지 않는 구간 이전의 thread 개수로 결정
- Pitfalls
  - CPU에 다른 application이 돌고 있지는 않은지? 다른 Thread pool이 있는지?
  - Thread pool이 너무 큰 경우 Context switching으로 인해 L1, L2 cache가 날아가버림

## References

Thread pool size

https://engineering.zalando.com/posts/2019/04/how-to-set-an-ideal-thread-pool-size.html

Scaling web applications

http://venkateshcm.com/2014/05/Architecture-Issues-Scaling-Web-Applications/
