# Arrays and Strings

## Two Pointer

- One slow-runner and the other fast-runner.
  ```java
  // find first i
  int i = 0;
  while (i < arr.length && {some_condition}) {
    ++i;
  }

  // do logic
  int j = i + 1;
  while (j < arr.length) {
    // do something
    // no loop here if possible
  }
  ```
- One pointer starts from the beginning while the other pointer starts from the end.
  ```java
  int i = 0;
  int j = arr.length - 1;
  while (i < j) {
    // do something
    // no loop here if possible
  }
  ```

Check out the loop invariant based on i, j

### Example 1 : Two sum

```cpp
/**
 * @param numbers Sorted positive values
 * @param target
 * @return List of index pair whose sum of each value == target
 */
vector<int> twoSum(vector<int>& numbers, int target) {
  int i = 0
  int j = number.size() - 1;
  while (i < j) {
    if (numbers[i] + numbers[j] < target) --j;
    else if (numbers[i] + numbers[j] > target) ++i;
    else return vector<int>{ i, j };
  }

  return vector<int>();
}
```

## Sliding window 

- Window size : [i, j) (i: inclusive, j: exclusive)
- When iterating elements, do not waste already processed one.
- 매번 처리되는 중복된 요소를 버리지 않고 재사용함으로써 낭비되는 계산을 하지 않음으로써 효율적으로 처리하는 방법.
- When to stretch window? When to shrink window?

### Example 1 : Max subarray of length 3 

```java
int sum = 0;

int i = 0;
int j = 0;
while (j < arr.length && (j - i) < 3) {
  sum += arr[j];
  ++j;
}

while (j < arr.length) {
  int next = sum + arr[j] - arr[i];
  sum = Math.max(sum, next);
  ++i;
  ++j;
}
```

## Greedy

Define a greedy algorithm which staisfies.

- Greedy choice property : A global optimal solution can be reached by choosing the optimal solution of each subproblem.
- Optimal Substructure : An optimal solution to the problem contains optimal solutions to the sub-problems.

Two pointer is greedy??

### vs Brute force? vs Dynamic programming?

- Same on dividing & solving sub-problem
- Difference : Bruce force, dynamic programing consider entire selection. But Greedy-Algorithm consider only current stage.
- Greedy also can be solved using dynamic programming or brute force.

```cpp
/*
    Activity selection problem

        One meeting room, n team, n differene meeting time
        How can be maximize the # of team which uses meeting room

    Approach

      Greedy algorithm : early ending meeting first

    Proof

      Let S_0 be a first ending meeting and S be an any optimal solution.
      Let S_1 be a first ending meeting in the S.
      Replace S_1 with S_0. Then the new solution is also an optimal solution

    begin : stores meeting begin time
    end : stores meeting end time
*/
int findBestSchedule(vector<int>& begin, vector<int>& end) {
    vector <pair<int, int>> order(begin.size());
    for (int i = 0; i < order.size(); ++i) {
        order.push_back(make_pair(end[i], begin[i]);
    }

    // sorting, for easy greedy selection, takes O(nlog(n))
    sort(order.begin(), order.end());

    int earliest = 0, selected = 0;
    for( pair<int, int>& meeting : order ) {
        int& meetingBegin = meeting.second;
        int& meetingEnd = meeting.first;

        // select next early ending meeting
        if( earliest <= meetingBegin ) {
            earlist = meetingEnd;
            ++selected;
        }
    }

    return selected;
}
```

## Tips

Always check IndexOutOfBoundException before getting it.

```java
while (index < s.length() && s.charAt(index) == ' ') {
  ++index;
}
```

Sorting is the great way to select greedily easily

## References

- [Greedy Algorithm (wiki)](https://en.wikipedia.org/wiki/Greedy_algorithm)
- [[알고리즘] 슬라이딩 윈도우 알고리즘](https://blog.fakecoding.com/archives/algorithm-slidingwindow/)

