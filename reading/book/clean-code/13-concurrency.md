# 13. Concurrency

- [13. Concurrency](#13-concurrency)
  - [Introduction](#introduction)
  - [Why Concurrency?](#why-concurrency)
    - [Myths and Misconceptions](#myths-and-misconceptions)
  - [Challenges](#challenges)
  - [Concurrency Defense Principles](#concurrency-defense-principles)
    - [Single Responsibility Principle](#single-responsibility-principle)
    - [Corollary: Limit the Scope of Data](#corollary-limit-the-scope-of-data)
    - [Corollary: Use Copies of Data](#corollary-use-copies-of-data)
    - [Corollary: Threads Should Be as Independent as Possible](#corollary-threads-should-be-as-independent-as-possible)
  - [Know Your Library](#know-your-library)
    - [Thread-Safe Collections](#thread-safe-collections)
  - [Know Your Execution Models](#know-your-execution-models)
    - [Producer-Consumer](#producer-consumer)
    - [Readers-Writers](#readers-writers)
    - [Dining Philosophers](#dining-philosophers)
  - [Beware Dependencies Between Synchronized Methods](#beware-dependencies-between-synchronized-methods)
  - [Keep Synchronized Sections Small](#keep-synchronized-sections-small)
  - [Writing Correct Shut-Down Code Is Hard](#writing-correct-shut-down-code-is-hard)
  - [Testing Threaded Code](#testing-threaded-code)
    - [Treat Spurious Failures as Candidate Threading Issues](#treat-spurious-failures-as-candidate-threading-issues)
    - [Get Your Nonthreaded Code Working First](#get-your-nonthreaded-code-working-first)
    - [Make Your Threaded Code Pluggable](#make-your-threaded-code-pluggable)
    - [Make Your Threaded Code Tunable](#make-your-threaded-code-tunable)
    - [Run with More Threads Than Processors](#run-with-more-threads-than-processors)
    - [Run on Different Platforms](#run-on-different-platforms)
    - [Instrument Your Code to Try and Force Failures](#instrument-your-code-to-try-and-force-failures)
    - [Hand-Coded](#hand-coded)
    - [Automated](#automated)
  - [Conclusion](#conclusion)

## Introduction

- 객체들이 처리에 대한 추상화라면 Thread는 스케줄링에 대한 추상화다.
- Single thread로 처리하는 프로그램은 만들기 쉬움. Multi thread로 잘 돌아가는것 처럼 보이지만 부하가 많아지면 오동작하는 프로그램도 짜기 쉬움.

## Why Concurrency?

- Concurrency는 무엇을 하느냐랑 언제 완료시키냐를 분리시키는 decoupling strategy임.
- 분리하면 한개의 big one loop도는게 아니라 여러 작은 컴퓨터끼리 상호작용하는 프로그램을 짜는 것.

### Myths and Misconceptions

미신들

- Concurrency는 항상 성능향상을 가져온다 -> 여러 thread간에 대기시간을 공유할 수 있는 경우에만 성능 향상이 가능한데 이런 경우는 드뭄.
- Concurrency를 해도 시스템 design 바뀌지 않는다 -> what을 처리하느냐랑 when 처리하는걸 decoupling하는 작업은 design을 바꿈.
- Concurrency 문제를 EJB container같은걸 사용할때는 몰라도 된다 -> container에서 어떤 식으로 병렬처리를 하는지 알아야 문제를 해결가능.

진실

- Concurrency 작성은 성능 및 코드 작성에 오버헤드를 추가한다.
- Concurrency 작성은 단순한 문제에서도 복잡함.
- Concurrency 버그는 보통 재현하기 힘들어서 보통 one-off (한번만 일어나는) 걸로 취급하고 무시하기도 한다.
- Concurrency 작성은 보통 근본적인 design change가 필요함.

## Challenges

- Example
  ```java
  public class X {
    private int lastIdUsed;

    public int getNextId() {
      return ++lastIdUsed;
    }
  }
  ```
- 2개의 thread로 실행했을 때 가능한 결과들
  - t1 get 43, t2 get 44, lastIdUsed is 44
  - t1 get 44, t2 get 43, lastIdUsed is 44
  - t1 get 43, t2 get 43, lastIdUsed is 44 // 두 thread가 서로 간섭했을 때 일어남

## Concurrency Defense Principles

### Single Responsibility Principle

- Concurrency 자체도 충분히 복잡해서 main logic과 별개로 변경되어야 한다.
  - Concurrency-related code 는 자체적인 development, change, tuning lifecycle을 따름.
  - Concurrency-related code 는 자체적인 어려움이 있음.
  - 잘못짜여진 concurrency 관련 코드는 여러 문제를 발생시킬 수 있으며 이는 추가적인 코드 없이 해결하기 힘듬.

### Corollary: Limit the Scope of Data

### Corollary: Use Copies of Data

### Corollary: Threads Should Be as Independent as Possible

## Know Your Library

### Thread-Safe Collections

## Know Your Execution Models

### Producer-Consumer

### Readers-Writers

### Dining Philosophers

## Beware Dependencies Between Synchronized Methods

## Keep Synchronized Sections Small

## Writing Correct Shut-Down Code Is Hard

## Testing Threaded Code

### Treat Spurious Failures as Candidate Threading Issues

### Get Your Nonthreaded Code Working First

### Make Your Threaded Code Pluggable

### Make Your Threaded Code Tunable

### Run with More Threads Than Processors

### Run on Different Platforms

### Instrument Your Code to Try and Force Failures

### Hand-Coded

### Automated

## Conclusion