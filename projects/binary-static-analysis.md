# Binary based Security Analysis Automization

바이너리 기반 보안 취약점 탐지 자동화 도구\
eg. malloc한 뒤 free안한다던지.. ip를 hard coding한다던지.. 이런걸 감지

## What techs?

- LLVM (Low Level Virtual Machine)
  - Clang
- rev.ng : binary code -> llvm intermediate code
- Vaadin : Generating war file.
  - Pros : UI using java! Server-side rendering.
  - Cons : Slow
- Tomcat : Web Server for displaying analysis result
- Postgres : Storing analysis information.
- Static Analysis : On llvm intermediate code
  - Symbol table
  - Data Flow Analysis
  - ...

## Architecture?

Web Server   1. Request                                                                     5. Display Result

Analysis Server           2. Accept request                              4. Static Analysis

rev.ng                                        3. Convert to llvm IR Code

Postgres : store datas

## What you did?

- Implement Vaadin UI page
- 동적 분석을 위한 전처리기 구현
- standard library parsing해서 symbol list 만드는 것
- JUnit 기반 스펙 테스트 자동화 (CWE-xxx 테스트 통과하는지를 자동화함)

## What you got (feeling)?

- Developed simple web server
- 정적 분석은 이렇게 하는구나
