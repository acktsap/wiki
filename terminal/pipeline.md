# Pipeline

- [Concept](#concept)
- [See also](#see-also)

## Concept

![pipeline](./pipeline.md)

- 사실 IPC (Inter-process communication)인데 standard stream으로 여러 process가 message를 주고받으면서 process가 chained되는거임.
- Anonymous pipes 라고도 부름. 왜 anonymous pipe냐면 그냥 pipe (named)와는 다르게 이름이 없고 한 프로세스가 읽은 데이터를 os가 버퍼해뒀다가 다 처리하면 pipe 제거해버림.

Usage

```sh
command1 | command2 | command3
```

Example

```sh
ls -l | grep key | less
```

## See also

- [Pipeline (Unix)](https://en.wikipedia.org/wiki/Pipeline_(Unix))