# 0. Introduction

- [Forward](#forward)
- [Preface](#preface)
  - [Kubernetes](#kubernetes)
  - [Design Patterns](#design-patterns)
  - [How This Boot Is Structured](#how-this-boot-is-structured)
  - [Who This Boot Is For](#who-this-boot-is-for)
  - [What You Will Learn](#what-you-will-learn)
  - [Using Code Examples](#using-code-examples)

## Forward

- 쿠버가 현대 application에서 이미 자리를 잡았는데 그 방대한 기능을 실제 어떻게 조합해야 하는지 알려주는 패턴이 없음.
- 경험으로 익히는 거도 좋은데 mission critical한 시스템에서 kuber를 익히다 보면 리스크 비용이 너무 큼.
- 이 책은 쿠버에서 제공하는 방대한 기능을 실제 어떻게 활용할 수 있는지 알려줌.
- 이 책을 끝내면 쿠버에서 제공하는 기능이 왜 있고 어떻게 사용할 수 있을지 알게됨.

## Preface

- 현대의 application은 scability, elasticity, failure, speed of change를 최적화 해서 만들어지고 있음.
- 이러한 요구사항을 만족하는 application 을 만들려면 다양한 pattern 이 필요함.

### Kubernetes

- Kubernetes는 container orchestration platform임.
- Kubernetes는 Google internal container orchestration platform이었던 Borg에 기반하여 2014년 오픈소스로 공개함.
- Kubernetes는 2015년 CNCF (Cloud Native Computing Foundation)에 들어감.
- Red Hap OpenShift Paas가 kubernetes 기반으로 만들어짐.

### Design Patterns

History

- design pattern은 1970년에 나온 pattern language에서 처음 등장함.
- 거기서는 건물을 만드는 패턴을이야기 했는데 나중에 발전해서 소프트웨어에도 적용됨.
- 대표적인 예시가 Gang of Four의 Design Pattern.

Concept

- Pattern은 _a repeatable solution to a problem_ 임.
- step-by-step instruction과는 다름. 알려진 문제에 대한 blueprint를 제공한다고 보면 됨.
- Pattern은 언어를 만든다고도 볼 수 있음. 예를 들면 oop에서 factory라고 하면 다들 새 객체 생성하는거로 알아들음.
- Pattern language는 서로 엮여서 많은 문제를 커버할 수 있음.
- Pattern language는 서로 다른 level의 granularity를 가짐. general한 pattern은 광범위한 문제를 cover하지만 어떻게 문제를 풀건지 덜 구체적임. 반면에 granular한 pattern은 구체적인 해결책을 제시하지만 일부 문제에만 적용 가능함.
- 이 책은 두개 패턴 모두 다룸.
- Pattern은 rigid해야 하는데 저자마다 쓰는 방식이 다름. 그래서 [Writing Software Pattern](https://www.martinfowler.com/articles/writingPatterns.html)라는 martin fouler의 글이 있음.

### How This Boot Is Structured

- 이 책은 매우 간단한 pattern을 따름.

Pattern

- Name
- Problem
- Solution 
- Discussion : Advantage & Disadvantage.
- More Information

이 책의 구성

- Part I, Foundational Patterns
  - Kubernetes의 core concept.
  - The underlying principles and practices for building container-based cloud-native applications.
- Part II, Behavioral Patterns
  - Describes patterns that sit on top of the foundational patterns and add finer-grained concepts for managing various types of container and platform interactions.
- Part III, Structural Patterns
  - Kubernetes의 처리 단위인 Pod안에 container를 어떻게 할건지 다룸.
- Part IV, Configuration Patterns
  - The various ways application configuration can be handled in Kubernetes.
  - These are very granular patterns, including concrete recipes for connecting applications to their configuration.
- Part V, Advanced Patterns
  - a collection of advanced concepts, such as how the platform itself can be extended or how to build container images directly within the cluster.

여담

- Pattern은 one catagory에 딱 맞지 않을 수 있음. context에 따라 같은 패턴이 여러 category에 적용될 수도 있음.

> 중요한건 context, 즉 패턴의 의도다.

### Who This Boot Is For

- kubernetes를 어느정도 아는 사람들이 kubernetes를 이용해서 cloud-native application를 만들려는 개발자들이 봐라.
- kubernetes 몰라도 패턴에서 얻을게 있을 것.
- We want to help you create better cloud-native applications - not reinvent the wheel.

> 사실 _not reinvent the wheel_ 이 책을 봐야 하는 이유인 듯..

### What You Will Learn

배울거

- 배울게 많음.
- 일부 패턴을 처음에 보면 그냥 공식 메뉴얼에서 가져온거 같을지 모름. 근데 그렇게 단순한게 아님. 잘 보면 conceptual angle을 볼 수 있을것.
- detailed guideline을 제시하는 pattern도 있음.

이 책이 안 다루는거

- kubernetes cluster setup을 다루지는 않음. 알려면 Managing Kubernetes나 Kubernetes Cookbook같은 책 보셈.
- kubernetes 자체를 설명하지 않음. 자체 말고 feature뒤에 숨은 개념들을 설명함. 쿠버 자체 알려면 Kubernetes in Action 보셈.

여담

- 이 책은 각각 독립적으로 읽어도 되는 essay처럼 쓰임.

> conceptual angle 표현이 좋네.

### Using Code Examples

- [github 예제 참고](https://github.com/k8spatterns).
- More Information에 사이트 링크들도 있음.
- All example code is distributed under the Creative Commons Attribution 4.0 (CC BY
4.0) license. The code is free to use, and you are free to share and adapt it for commercial and noncommercial projects. However, you should give attribution back to this book if you copy or redistribute the material.