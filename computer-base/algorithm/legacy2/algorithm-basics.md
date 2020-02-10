Algorithm Basic
===============


## Direction of algorithm document

Rules for solving problem  
Recyclable general solution(code) for specific algorithmic problem  
A property, implementation of data structures worth organizing


## Basic Algorithm for solving problem

1. Understand a problem perfectly, precisely

2. Redefinition and abstraction : Make it representable in a computing world

3. Make a plan : What data structure? What algorithm?

4. Verify the plan : Is it covers every case? It can be executed in time limit?

5. Execute the plan : Implementation

6. Retrospect : What algorithm? No improvement? No mistake?


## Strategy for solving problem

1. 비슷한 문제를 풀어본 적이 있던가?
2. 단순한 방법으로 무식하게 풀 수 있는가? 그 방법을 효적으로 할 수 있는가?
3. 문제를 푸는 과정을 손으로 적어서 표현이 가능한가?
4. *단순화 할 수 있는가?*
5. 그림으로 그려볼 수 있는가?
6. 수학적 기호를 이용해서 수식의 형태로 표현이 가능한가?
7. *각각의 문제들을 쉬운 형태로 변경할 수 있을까?*
8. 결과로부터 시작해서 문제를 풀 수 있을까? ex) 사다리타기
9. *순서를 강제하여 경우의 수를 줄일 수 있는가?*
10. *특정 형태의 답만을 고려할 수 있을까?*


## Tips

### Split data with code

```cpp
string getMonthName(int month) {
  if( month == 1 ) return "Jan";
  if( month == 2 ) return "Feb";
  ...
  return "Dec";
}

->

const string monthName[] { "dummy", "jan", "Feb", ... , "Dec" };
```

```cpp
// east, west, south, north resp.
const int dx[4] = { 1, -1, 0, 0 };
const int dy[4] = { 0, 0, -1, 1 };
```

### No Floating point operation
```cpp
// no, it's slow
sqrt(x*x + y*y) == r;

// yes, if not overflow
x*x + y*y == r*r;
```

### C++ tips

to_string in c++ 11
```cpp
#include <sstream>

namespace tostr {
  string to_string(const int& n) {
    std::ostringstream stm;
    stm << n;
    return stm.str();
  }
}
```

string to integer [since c++ 11]
```cpp
  stoi("123");    // return 123
```

getchar
```cpp
  int n, k;
  scanf("%d", &n);
  scanf("%d", &k);

  getchar();	// remove \n
  while( k-- ) {
    char ch;
    ch = getchar();
  }
```

cin getline
```cpp
  string input;
  while( std::getline(cin, input) ) {
    std::cout << input << std::endl;
  }
```

cpp std::cin speed up
```cpp
  // off synchronization with <cstdio>
  cin.sync_with_stdio(false);
```

std toupper tolower
```cpp
#include <cctype>

int toupper(int ch);
int tolower(int ch);
```
