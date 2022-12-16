# Circular Dependency

- [Concept](#concept)
- [See also](#see-also)

## Concept

- 의존성 사이클이 생기면 한쪽의 변화가 다른쪽에 전파가 됨. 근데 이게 웃긴게 서로 의존이라 서로 바꿔야 해서 한 쪽이 변화가 생겨서 다른 쪽을 맞추면 맞추는 변화로 인해 초기 변화가 발생한 객체에 영향을 줌. 즉, A <-> B라고 하면 A의 변화가 B에 영향을 주고 그로 인한 B의 변화가 A에 영향을 주고 이로 인한 A의 변화가...
- 또한 의존성 사이클로 인해 프로젝트 구조를 파악하는데 어려움을 줌. 어디를 봐야 할지 알기 힘듬.
- ~~그냥 안됨. 일단 안됨.~~

## See also