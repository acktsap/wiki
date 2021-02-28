# Etc

## to_string in c++ 11

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

## string to integer [since c++ 11]

```cpp
stoi("123"); // return 123
```

## getchar

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

## cin getline

```cpp
string input;
while( std::getline(cin, input) ) {
  std::cout << input << std::endl;
}
```

## std::cin speed up

```cpp
cin.sync_with_stdio(false); // off synchronization with <cstdio>
```

## std toupper tolower

```cpp
#include <cctype>

int toupper(int ch);
int tolower(int ch);
```