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
- Check out the loop invariant based on i, j

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

- Window size : [i, j]
- Greedy하게 매번 처리되는 중복된 요소를 버리지 않고 재사용함으로써 낭비되는 계산을 하지 않음으로써 효율적으로 처리하는 방법.

### vs Two Pointer

- Sliding window는 window 안의 모든 element를 가지고 비교하지만 (eg. sum) two pointer는 각각의 pointer가 가리키고 있는 값만 비교함.

### Rule of Thumb

- State: sss
- Stretch window until xxx, change the state
- Shrink window until yyy, change the state

```java
i, j = 0
while j < s.length - 1:
  if next does not duplicate entry:
    ++j  // stretch
  if next duplicates entry && i < j:
    ++i // shrink
  if next duplicates entry && i == j:
    ++i, ++j 
  ...
```

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

## Prefix Sum

- Use cumulative sum of an array.

### Example

```java
int[] nums = new int[] { 1, 0, 3, 2, 5 };
int[] prefixSum = new int[nums.length + 1]; // trick: put 0 to 0-th entry
for (int i = 1; i < prefixSum.length; ++i) {
  prefixSum[i] = nums[i - 1] + prefixSum[i - 1];
}
```

## Greedy

Define a greedy algorithm which staisfies.

- Greedy choice property : A global optimal solution can be reached by choosing the optimal solution of each subproblem.  
  -> mostly obvious
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

