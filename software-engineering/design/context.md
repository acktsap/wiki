# Context

## Consideration

무엇에 대한?

- Execution
  - Pre-processing
  - Failover Strategy
  - Just Like AOP in spring (Spring AOP는 대상, 즉 JointPoint가 Invocation임)
- Client
  - Connection, Connection Pooling
  - Clustering
    - Round-robin (Per request)
    - Round-robin (On fail)
- Request
  - Request Parameter
  - Response

필요한 것, API

- Configuration (pre-defined key-value set)
- Key-Value Storage
- Context Storage
- Strategy
  - Strategy Applier

구현

- Context (interface) : Thread-safety를 만족시키기 위해 Immutable하게
  - EmptyContext `(root context)
  - ContextConc
- Context Storage (interface)
  - ThreadLocalStorage
  - StaticStorage?
- Key<T>
- Value<V>
- KeyValueStorage<T, V>
- API
  - withValue(key: Key<T>, value: V)
