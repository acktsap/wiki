# Api Design

## Common

Keep it simple stupid

Make it consistant (convention over configuration)

Throw exception when and only when function preconditions are broken (eg. i/o, network)

Write test case for it. You have to be a first user of api.

## Api

api는 정보의 비대칭을 일관적이고 명백한 인터페이스로 메꿔주는것

사용자가 domain지식을 다 알면 잘 쓰지만 보통은 잘 모름

그쪽 언어로 말해야해

일관적인 design

## Java

Return nullable or Optional for no corresponding value.

## See also

[when-to-throw-an-exception](https://stackoverflow.com/questions/77127/when-to-throw-an-exception)
