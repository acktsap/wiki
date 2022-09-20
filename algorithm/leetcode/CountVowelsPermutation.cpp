#include <iostream>
#include <vector>
#include <tuple>
#include <algorithm>

using namespace std;

template <typename T>
std::ostream& operator <<(std::ostream& os, const vector<T>& elements);

template <typename T>
std::ostream& operator <<(std::ostream& os, const vector<vector<T>>& grid);

/*
 * Descryption
 *
 * https://leetcode.com/problems/count-vowels-permutation/
 *
 * Given an integer n, your task is to count how many strings of length n can be formed under the following rules:
 * 
 * - Each character is a lower case vowel ('a', 'e', 'i', 'o', 'u')
 * - Each vowel 'a' may only be followed by an 'e'.
 * - Each vowel 'e' may only be followed by an 'a' or an 'i'.
 * - Each vowel 'i' may not be followed by another 'i'.
 * - Each vowel 'o' may only be followed by an 'i' or a 'u'.
 * - Each vowel 'u' may only be followed by an 'a'.
 *
 * Since the answer may be too large, return it modulo 10^9 + 7.
 *  
 * 
 * Example 1:
 * 
 * Input: n = 1
 * Output: 5
 * Explanation: All possible strings are: "a", "e", "i" , "o" and "u".
 *
 * Example 2:
 * 
 * Input: n = 2
 * Output: 10
 * Explanation: All possible strings are: "ae", "ea", "ei", "ia", "ie", "io", "iu", "oi", "ou" and "ua".
 *
 * Example 3: 
 * 
 * Input: n = 5
 * Output: 68
 *  
 * 
 * Constraints:
 * 
 * - 1 <= n <= 2 * 10^4
 *
 *
 * Review
 *
 *
 */
class CountVowelsPermutation {
public:
  /*
    aeiou
    ae
    ea
    ei
    ia
    ie
    io
    iu
    oi
    ou
    ua
    
    뭔가 overlapping subproblem의 냄새가 나는데..
    optimal structure는 알파벳 별로 해당 알파벳으로 끝나는 단어중 최대를 하면 됨 아마?
    각각 알파벳 별로 dp table 만들어서 추가하면 될듯
    이거 테이블 만들 필요도 없네 그냥 변수 5개
    
    편의상 aeiou를 dp table 이름으로 치면

    a[n] : n번째 index를 a로 끝나는 알파벳의 경우의 수

    n >= 2
    a[n] = e[n - 1] + i[n - 1] + u[n - 1]
    e[n] = a[n - 1] + i[n - 1]
    i[n] = e[n - 1] + o[n - 1]
    o[n] = i[n - 1]
    u[n] = i[n - 1] + o[n - 1]

    n == 1
    a[n], e[n], i[n], o[n], u[n] = 1
    
    - time: O(n)
    - space: O(1)
  */
  int countVowelPermutation(int n) {
    int prime = 1000000007;
    int a = 1;
    int e = 1;
    int i = 1;
    int o = 1;
    int u = 1;
    
    for (int index = 1; index < n; ++index) {
      int tempA = ((e + i) % prime + u) % prime;
      int tempE = (a + i) % prime;
      int tempI = (e + o) % prime;
      int tempO = i;
      int tempU = (i + o) % prime;
        
      a = tempA;
      e = tempE;
      i = tempI;
      o = tempO;
      u = tempU;
    }
    
    return ((((a + e) % prime + i) % prime + o) % prime + u) % prime;
  }
};

int main() {
  vector<tuple<int, int>> parameters {
    make_tuple(
        1,
        5
    ),
    make_tuple(
        2,
        10
    ),
    make_tuple(
        3,
        19
    ),
    make_tuple(
        100,
        173981881
    )
  };

  CountVowelsPermutation solution;
  for (auto& parameter : parameters) {
    auto& n = get<0>(parameter);
    auto& expected = get<1>(parameter);

    {
      auto actual = solution.countVowelPermutation(n);
      if (actual != expected) {
        cout << "Expected" << endl << expected << endl;
        cout << "Actual" << endl << actual << endl;
        assert (false);
      }
    }
  }
}

template <typename T>
std::ostream& operator <<(std::ostream& os, const vector<T>& elements){
  if (elements.size() == 0) {
    return os;
  }

  os << elements[0];
  for (int i = 1; i < elements.size(); i++) {
      os << ", " << elements[i];
  }

  return os;
}

template <typename T>
std::ostream& operator <<(std::ostream& os, const vector<vector<T>>& grid){
  for(int i = 0; i < grid.size(); i++) {
    os << grid[i][0];
    for (int j = 1; j < grid[i].size(); j++) {
        os << ", " << grid[i][j];
    }
    os << endl;
  }

  return os;
}
