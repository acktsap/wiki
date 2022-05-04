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
