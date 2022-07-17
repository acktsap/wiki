# Complexity

- [What is complexity](#what-is-complexity)
- [Big O, Big Omega, Big Theta](#big-o-big-omega-big-theta)
- [Big O notation in computer science](#big-o-notation-in-computer-science)
- [Base-case complexity](#base-case-complexity)
- [Average-case complexity](#average-case-complexity)
- [Worst-case complexity](#worst-case-complexity)
- [Amortized analysis](#amortized-analysis)
- [See also](#see-also)

## What is complexity

- algorithm의 resource usage를 측정하는 것.
- algorithm의 running time, memory를 측정함.

## Big O, Big Omega, Big Theta

- Big O
  - f(n) = O(g(n)).
  - 상한선.
  - f(n) = n^2 + 2n 일 경우 O(n^2), O(n^3) 라고 표현해도 문제될건 없음.
- Big Ω
  - f(n) = Ω(g(n)).
  - 하한선.
  - f(n) = n^2 + 2n 일 경우 Ω(n^2), Ω(n), Ω(1) 라고 표현해도 문제될건 없음.
- Big θ
  - f(n) = θ(g(n)).
  - Big O와 Big Ω를 모두 만족하는 경우.

## Big O notation in computer science

- 사실상 Big O를 Big θ처럼 씀.

## Base-case complexity

- best input n을 받았을 때의 복잡도.

## Average-case complexity

- 통계적으로 평균적인 input n을 받았을 때의 복잡도.

## Worst-case complexity

- 최악의 input n을 받았을 때의 복잡도.

## Amortized analysis

- worst-case completixy가 너무 pessimistic함. worst-case operation은 실제로 잘 일어나지 않으므로 그 비용을 "amortizing" 하자.
- 한개의 연산만 보지 말고 최악의 경우가 발생하도록 n개의 연산을 수행하고 그 n개의 연산 자체에 대한 수행시간을 분석.
- eg. Dynamic array
  - Java ArrayList같은 dynamic array의 경우 보통은 O(1)으로 되는데 가득 찼을 때만 O(n)의 연산이 필요함.
  - n + 1개의 item을 n개의 item에 넣는 경우 (n*O(1) + O(n)) / n + 1 = O(1)의 연산이 필요.
  - 즉, Dynamiy Array의 insert operation은 **Amortized O(1)**
    > 잘 이해가 안되는데 컨셉만 이해하고 있자. 한개만 보지 말고 최악의 경우가 발생하도록 n개의 연산을 수행 후 그 연산 자체만 보는 이라고..

## See also

- [Computational complexity theory (wiki)](https://en.wikipedia.org/wiki/Computational_complexity_theory)
- [Big O notation](https://en.wikipedia.org/wiki/Big_O_notation#Use_in_computer_science)
- [시간 복잡도 (big-O, big-Ω, big-θ) (velog, wan088)](https://velog.io/@wan088/%EC%8B%9C%EA%B0%84-%EB%B3%B5%EC%9E%A1%EB%8F%84-big-O-big-big-)
- [Average-case complexity (wiki)](https://en.wikipedia.org/wiki/Average-case_complexity)
- [Worst-case complexity (wiki)](https://en.wikipedia.org/wiki/Worst-case_complexity)
- [Amortized analysis (wiki)](https://en.wikipedia.org/wiki/Amortized_analysis)
- [Lecture 20: Amortized Analysis (cornell edu)](http://www.cs.cornell.edu/courses/cs3110/2011sp/Lectures/lec20-amortized/amortized.htm)