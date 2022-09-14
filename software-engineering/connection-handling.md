# Connection Handling

- [Connection Pool](#connection-pool)
- [Timeout 종류](#timeout-종류)
- [Reference](#reference)

## Connection Pool

- tcp/ip 3 way handshaking 이런거로 connection 맺는게 느림. 그래서 미리 연결을 맺어두고 이걸 재활용하자!
- 근데 이게 1개만 있으면 한번에 한개밖에 못하잖아. 그래서 여러개를 가지고 있는거지 그래서 pool.
- 여기서 고려할점이.. 몇개를 할건가? (max size) 한 ip에 최대 몇개의 connection을 할당할건가? (max size per route)idle connection은 얼마 뒤에 죽일건가? (idle timeout) connection이 가득찰 경우 어떻게 처리할건가? 기다리면 얼마나 기다릴건가? (connection request timeout)등을 고려해야 함.

## Timeout 종류

- Timeout이란 기본적으로 어떤 요청을 했는데 이거 이상 기다리지 않겠다는 것.
- tcp/ip에서는 connection을 맺으려면 우선 3-way handshaking을 해야함. 이 때 packet을 주고받는데 이게 너무 느려서 못기다리겠다? -> Connection timeout.
- 3 way handshaking으로 connection은 맺어졌어. 이제 데이터를 주고 받아야하지? 근데 이게 packet이 너무 늦게 오가는거야 -> Socket timeout. socket timeout이 1초면 전체가 3초 걸리더라도 각각 packet이 1초 이내에 도달하면 timeout 안남.
- 근데 packet 단위로 데이터 주고 받는데 이게 전체 받는게 너무 느려. 1bytes씩 처리하는데 1mb를 받아야해. 이거 못기다리면 -> Read timeout.

## Reference

- Timeout
  - [ConnectionTimeout versus SocketTimeout](https://stackoverflow.com/questions/7360520/connectiontimeout-versus-sockettimeout)