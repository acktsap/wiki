#include <iostream>
#include <vector>
#include <tuple>
#include <algorithm>

using namespace std;

/*
 * Descryption
 *
 * https://leetcode.com/problems/partition-labels/
 *
 * You are given a string s.
 * We want to partition the string into as many parts as possible so that each letter appears in at most one part.
 * 
 * Note that the partition is done so that after concatenating all the parts in order,
 * the resultant string should be s.
 * 
 * Return a list of integers representing the size of these parts.
 * 
 * 
 * Example 1:
 * 
 * Input: s = "ababcbacadefegdehijhklij"
 * Output: [9,7,8]
 *
 * Explanation:
 *
 * The partition is "ababcbaca", "defegde", "hijhklij".
 * This is a partition so that each letter appears in at most one part.
 * A partition like "ababcbacadefegde", "hijhklij" is incorrect, because it splits s into less parts.
 *
 *
 * Example 2:
 * 
 * Input: s = "eccbbbbdec"
 * Output: [10]
 *  
 * 
 * Constraints:
 * 
 * - 1 <= s.length <= 500
 * - s consists of lowercase English letters.
 *
 *
 *
 * Review
 *
 * 이건 brute force로 어케 풀지?
 *
 *
 */
class PartitionLabels {
public:
  /*
    greedy? 같은 알파벳은 같은 partition에 있어야 함. 
    알파벳별로 score (last index) 주고 greedy하게 partition의 score가 가장 크게 잡기?
    partition 우선 한개 잡고 그 partition 안에서 score 큰게 있으면 그걸로 partition 확장
    그렇게 계속 갱신하다가 더 없으면 끝내기
    
    Loop invariant
    
    - partitionIndexes : holds indexes
    
    Completixy
    
    - time : O(n)
    - space: O(1)
  */
  vector<int> partitionLabelsGreedyHumanLike(string s) {
    vector<int> partitionIndexes;
    
    int score[26];
    for (int i = 0; i < s.size(); ++i) {
      score[s[i] - 'a'] = i;
    }
    
    int i = 0;
    while (i < s.size()) {
      int candidate = score[s[i] - 'a'];
      int j = i + 1;
      while (j <= candidate) {
        int maybe = score[s[j] - 'a'];
        if (candidate < maybe) {
          candidate = maybe;
        }
        ++j;
      }
      
      partitionIndexes.push_back(candidate - i + 1);
      i = candidate + 1;
    }
    
    return partitionIndexes;
  }

    /*
    greedy? 같은 알파벳은 같은 partition에 있어야 함. 
    알파벳별로 score (last index) 주고 greedy하게 partition의 score가 가장 크게 잡기?
    partition 우선 한개 잡고 그 partition 안에서 score 큰게 있으면 그걸로 partition 확장
    그렇게 계속 갱신하다가 더 없으면 끝내기
    candidate 계속 갱신하다가 지가 제일 큰거같으면 넣기
    
    Loop invariant
    
    - partitionIndexes : holds indexes
    - candidate : next partition last 후보
    - lastPartitionIndex : 마지막 partition index
    
    Completixy
    
    - time : O(n)
    - space: O(1)
  */
  vector<int> partitionLabelsGreedyFancy(string s) {
    vector<int> partitionIndexes;
    
    int score[26];
    for (int i = 0; i < s.size(); ++i) {
      score[s[i] - 'a'] = i;
    }
    
    int candidate = 0;
    int lastPartitionIndex = 0;
    for (int i = 0; i < s.size(); ++i) {
      candidate = max(candidate, score[s[i] - 'a']);
      if (candidate == i) {
        partitionIndexes.push_back(candidate - lastPartitionIndex + 1);
        lastPartitionIndex = candidate + 1;
      }
    }
    return partitionIndexes;
  }
};

int main() {
  vector<tuple<string, vector<int>>> parameters {
    make_tuple(
        "ababcbacadefegdehijhklij",
        vector<int> { 9, 7, 8 }
    ),
    make_tuple(
        "a",
        vector<int> { 1 }
    ),
    make_tuple(
        "ab",
        vector<int> { 1, 1 }
    ),
    make_tuple(
        "eccbbbbdec",
        vector<int> { 10 }
    )
  };

  PartitionLabels solution;
  for (auto& parameter : parameters) {
    auto& s = get<0>(parameter);
    auto& expected = get<1>(parameter);

    {
      auto actual = solution.partitionLabelsGreedyHumanLike(s);
      if (actual != expected) {
        cout << "Expected: ";
        copy(expected.begin(), expected.end(), ostream_iterator<int>(cout, ", "));
        cout << " but was: ";
        copy(actual.begin(), actual.end(), ostream_iterator<int>(cout, ", "));
        cout << endl;
        assert (false);
      }
    }

    {
      auto actual = solution.partitionLabelsGreedyFancy(s);
      if (actual != expected) {
        cout << "Expected: ";
        copy(expected.begin(), expected.end(), ostream_iterator<int>(cout, ", "));
        cout << " but was: ";
        copy(actual.begin(), actual.end(), ostream_iterator<int>(cout, ", "));
        cout << endl;
        assert (false);
      }
    }
  }
}
