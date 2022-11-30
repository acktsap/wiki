# 0. Introduction

- [Forward](#forward)
- [Preface](#preface)
- [About this book](#about-this-book)

## Forward

- Kotlin에 많은 feature를 넣을 예정이지만 Kotlin 내용이 한 권에 담길만큼 적당한 정도를 유지하는게 기본 철학임.
- 이 책은 Java에 이미 친숙한 사람이 대상임.
- Java 생태계에 도입시키기 위해 Java의 상호 운용성을 지키는게 Kotlin의 초석임.
- 지금 책을 쓰는 순간에 Kotlin이 JVM 뿐만 아니라 js, native도 대상으로 될 수 있게 작업중임.

## Preface

- Kotlin에 대한 idea는 2010년 JetBrains 사에서 시작되었다.
- 당시 Java로 Intellij를 개발하고 있었는데 옆팀이 C#같은 좋은 언어로 개발하는게 부러웠음. 그래서 Java의 대체제를 찾으려고 해봤는데 마땅한게 없었음.
- 그런 언어의 조건은
  1. static typed : 이거 없이 multimillion-line를 개발하려면 미침.
  2. 기존 Java code와 완벽한 호환성 : Java로 이미 짜여진 코드가 많은데 그걸 버리고 싶지는 않음.
  3. Tooling quality를 높게 유지하고 싶음 : 개발자의 생산성은 매우 중요함.
  4. 배우기 쉽고 간단한 언어.
- 그런데 이런 고민을 다른 회사에서도 하고 있었음. 그래서 개발 하면 다른 곳에서도 적용 될거라 예상해서 개발 시작.
- kotlin 1.0은 첫 commit 이후로 5년 뒤에 나옴.
- Kotlin은 대부분의 kotlin 개발자 team이 있는 Russia의 St. Petersburg 근처에 있는 섬 이름으로 지음.
- Kotlin 언어 작성하고 나니 Kotlin을 위한 책을 Kotlin 개발자들이 쓰면 좋겠다고 생각해서 작성.

## About this book

- 이 책은 Kotlin을 이용해서 jvm, android 위에서 application을 만들 수 있는 지식을 줌.
- kotlin의 기본 기능에서부터 high-level abstraction인 dsl까지 알려줌.
- Kotlin 1.0을 cover함. 1.1은 책 작성 기준 개발중.
- 이 책을 읽기 위해서는 Java에 대한 지식이 있는게 좋음. Java에서 많은 개념이나 테크닉을 차용함.
- 이 책은 특정 domain에 특화된 책이 아니라서 server-side 개발자나 android, Kotlin을 활용하여 jvm 기반 application을 작성하는데 필요한 지식을 줌.
