# Greedy

## Greedy Algorithm

Define a greedy algorithm which staisfies.

- Greedy choice property : A global optimal solution can be reached by choosing the optimal solution of each subproblem.
  -> mostly obvious
  - 즉, partially optimal이 global optimal인지 증명해야 함.
- Optimal Substructure : An optimal solution to the problem contains optimal solutions to the sub-problems.

## Two pointer is greedy?

- Greedy한 속성이 있으면 greedy하다고 볼 수 있음.

## vs Brute force? vs Dynamic programming?

- Same on dividing & solving sub-problem
- Difference : Bruce force, dynamic programing consider entire selection. But Greedy-Algorithm consider only current stage.
- Greedy sometimes can be solved using dynamic programming or brute force.

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

## Reference

- [Greedy Algorithm (wiki)](https://en.wikipedia.org/wiki/Greedy_algorithm)
