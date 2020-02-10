
====== Algorithm Basic ======


# Direction of algorithm document

  Rules for solving problem
  Recyclable general solution(code) for specific algorithmic problem
  A property, implementation of data structures worth organizing


#  Basic Algorithm for solving problem

  1. Understanding a problem
   - Understand everything of a problem, perfectly, precisely

  2. Redefinition and abstraction
   - Make it representable in a computing world

  3. Make a plan
   - What data structure? What algorithm?

  4. Verify the plan
   - Is it covers every case? It can be executed in time limit?

  5. Execute the plan
   - Implementation

  6. Retrospect(회고하기)
   - What algorithm is used? Is it can be mproved? What mistake i took?


#  Strategy for solving problem

  01. 비슷한 문제를 풀어본 적이 있던가?
  02. 단순한 방법으로 무식하게 풀 수 있는가? 그 방법을 효율적으로 할 수 있는가?
  03. 문제를 푸는 과정을 손으로 적어서 표현이 가능한가?
  04. *단순화 할 수 있는가?*
  05. 그림으로 그려볼 수 있는가?
  06. 수학적 기호를 이용해서 수식의 형태로 표현이 가능한가?
  07. *각각의 문제들을 쉬운 형태로 변경할 수 있을까?*
  08. 결과로부터 시작해서 문제를 풀 수 있을까? ex) 사다리타기
  09. *순서를 강제하여 경우의 수를 줄일 수 있는가?*
  10. *특정 형태의 답만을 고려할 수 있을까?*


#  Split data with code

  - eg1. month

  from
    string getMonthName(int month) {
      if( month == 1 ) return "January";
      if( month == 2 ) return "February";
      ...
      return "December";
    }

  to
    const string monthName[] { "january", "Febrary", ... , "December" };

  - eg2. direction

    // east, west, south, north resp.
    const int dx[4] = { 1, -1, 0, 0 };
    const int dy[4] = { 0, 0, -1, 1 };


#  No Floating point# peration[because it is slow]

  ex) distance
    sqrt(x*x + y*y) == r;    // no

    x*x + y*y == r*r;   // yes, if no# verflow
