# Greedy

## Strategy

1. Divide into sub-problems
2. Define a greedy algorithm which staisfies
    1. Greedy choice property : ∃ optimal solution which contains our solution on sub-problem\
       Assume ∃ other optimal solution, prove that we can modify it to our solution
    2. Optimal Substructure : ∑ optimal solution of subproblem -> Optimal final solution; Mostly obvious

- Tips : Sorting is the great way to select greedily easily


## VS bruce force? VS dynamic programming?

- Same on dividing & solving sub-problem
- Difference : Bruce force, dynamic programing consider entire selection. But Greedy-Algorithm consider only current stage.

```cpp
/*
    Activity selection problem

        One meeting room, n team, n differene meeting time
        How can be maximize the # of team which uses meeting room

    Approach

      Greedy algorithm : early ending meeting first

    Proof

      Let S_0 be an first ending meeting and S be an any optimal solution
      Let S_1 be an first ending meeting on the S
      Now, replace S_1 with S_0 then new solution S` is also an optimal solution

    begin : stores meeting begin time
    end : stores meeting end time
*/
int findBestSchedule(vector<int>& begin, vector<int>& end) {
    vector< pair<int, int> > order(begin.size());
    for( int i = 0; i < order.size(); ++i ) {
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
