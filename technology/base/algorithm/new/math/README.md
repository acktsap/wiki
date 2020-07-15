# Math

## Finding all the divisor

```cpp
/* O(sqrt(num) * log(sqrt(num)) */
vector<int> selfDividingNumbers(int num) {
  vector<int> divisors;

  int range = static_cast<int>(std::sqrt(num));
  for( int i = 1; i <= range; ++i ) {
    if( num % i == 0 ) {
      divisors.push_back(i);
      if( i * i != num ) divisors.push_back(num / i);
    }
  }
  std::sort(divisors.begin(), divisors.end());

  return divisors;
}
```
