# Compiler

- [Phases](#phases)
- [Bootstrapping](#bootstrapping)
- [Transpiler](#transpiler)
- [References](#references)

## Phases

![compiler-phases](./img/compiler-phases.jpg)

## Bootstrapping

개념

- self-compiling compiler.
- 컴파일 하고자 하는 언어에 대한 compiler를 해당 언어 자체로 만드는 것.
- chickenn-or-egg-problem이 날거 같은에 어떻게 하냐? 처음에는 다른 언어로 (eg. assembly) compiler를 만들었다가 컴파일 대상 언어로 교체해서 작성하는 식으로 함.

장점

- 작성하는 언어에 대한 dogfooding이 가능.
- 컴파일러 개발자가 컴파일 대상 언어만 알면 됨.
- 컴파일러 개선을 하면 컴파일러 작성 자체에 대한 개선도 일어남.

## Transpiler

- source to source compiler. 주로 신규 소스 코드를 이전 버전에 호환되는 소스 코드로 만들어주는 친구.
- eg. babel

https://en.wikipedia.org/wiki/Source-to-source_compiler

## References

- [Phases of a Compiler](https://www.geeksforgeeks.org/phases-of-a-compiler/)
- [Bootstrapping (compilers) (wiki)](https://en.wikipedia.org/wiki/Bootstrapping_(compilers))