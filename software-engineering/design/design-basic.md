# Design Basic

## Seperation of Concerns

Modeling per concerns

- Minimize modification on a spec changes
- Maximize reuseability

It represents as **SOLID** in OOP

- Single Responsibility Principle : A class should only have a single responsibility. One changes to spec affects to an one class.
- Open-Closed Principle : Open for extension and closed for modification
- Liskov Substitution Principle : Objects should be replaceable with instances of their subtypes without modification
- Interface Segregation Principle : Many client-specific interfaces are better than one general-purpose interface
- Dependency Inversion Principle : One should depend upon abstractions not concretions (eg. dependency injection)

## Modeling

- 컴퓨터 내부에서 돌아가는 자체적인 세계를 만드는 것
- 모델링 할 때 뭔가 잘 안풀리고 두개의 객체가 뭔가 꼬여있다 싶으면 잘못나눈거 orthogonal하지 않다는 의미
- 나눌수록 묶기 좋음

## Framework

Making DSL.

## Abstraction

- 빼는 것. 도메인 상에서 필요한거만 집중하고 나머지는 빼는 것.  
- 내가 만들고 싶은 세상에서 펼치고 싶은 의도가 있고 이걸 위해 속성을 추가.
- 현실과 결합하는 과정에서 현실에 있는 것의 속성을 뺌.
- 여기서 추상화 하는 도메인이 서로 겹치면 안된다

## Reference

- [Abstraction (computer_science), wiki](https://en.wikipedia.org/wiki/Abstraction_(computer_science))
