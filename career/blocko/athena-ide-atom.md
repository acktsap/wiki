# Athena IDE Atom

아르고 스마트 컨트랙트 개발을 위한 IDE

https://github.com/aergoio/athena-ide-atom

- [Athena IDE Atom](#athena-ide-atom)
  - [What techs](#what-techs)
    - [Atom](#atom)
    - [Static analysis](#static-analysis)
    - [JavaScript, TypeScript](#javascript-typescript)
    - [Less](#less)
    - [React.js](#reactjs)
    - [Mobx](#mobx)
    - [Atomic Design](#atomic-design)
  - [Architecture?](#architecture)
  - [What you did?](#what-you-did)
  - [What you got?](#what-you-got)

## What techs

### Atom

### Static analysis

- symbol table

### JavaScript, TypeScript

### Less

atom package에서 css를 위해 기본으로 처리하는게 less여서 씀

### React.js

Vue vs React 고민했으나 Vue는 Framework이고 React는 라이브러리임. 그래서 React를 선택. 벤치마크한 이더리움 Atom IDE도 React쓰기도 했음. 그리고 React가 코드가 더 깔끔해보임

### Mobx

Redux를 쓰려고 했으나 Boilerplace code가 너무 많음. 작은 규모의 어플리케이션에서는 Mobx를 써도 될거라고 판단. Store를 View가 Observe해서 변경되면 알아서 바뀌는 식으로 동작. Store에 state 변경 operation도 같이 있음. 개인적으로는 마음에 안드나 (분리되어 있어야 한다고 봄) 시간관계상 그냥 사용함.

### Atomic Design

## Architecture?

View                     Athena (static analysis tools)

Store

Service

## What you did?

- Intellisence
- UI

## What you got?

- Designer와의 협업
- 필요한 것은 진행해야 한다 (필요했음, 당시는 반대들 함)
