Brute-Force
===========


#  Brute-force

  Solve the problem by brute-force(무식하게 풀기)
  [100,000,000 loops takes approximately 1 seconds]
  Also used on Optimization Problem[search all & find the optimization]

##  Exhausive Receipe

  1. deals with base case
  2. solve the problem using a subproblem

##  Tips

  Force the order; eg. first on dictionary
  What to return? What to share?
  Remember, it is just a graph of state


# Examples

## Example 1

  /**
   * select combination of m elements from n elements
   * print it when any combination is done
   * @param n total number of elements
   * @param picked picked numbers
   * @param toPick # of elements to be picked
   */
  void pick(int n, vector<int>& picked, int toPick);

- ans
  // -> it's just like a dfs which makes states by it's own
  void pick(int n, vector<int>& picked, int toPick) {
    // base case
    if( toPick == 0 ) {
      for( int& num : picked ) cout << num << " ";
      cout << endl;
      return;
    }
    // smallest number of element which can be picked
    int start = picked.empty() ? 0 : picked.back() + 1;
    // select one element in this stage
    for( int next = start; next < n; ++next ) {
      picked.push_back(next);
      pick(n, picked, toPick - 1);
      picked.pop_back();
    }
  }

## Example 2

  /**
   * calc a number of counts whose sum of subset[not empty] is equal to finalsum
   * from the vector<int>numbers
   * @param numbers numbers
   * @param picked picked numbers
   * @param cumulativeSum cumulative sum
   * @param finalSum target summation number
   * @return a number of counts
   */
  int calcCounts(vector<int>& numbers, vector<int>& picked, int cumulativeSum, int finalSum);

- ans

  int calcCounts(vector<int>& numbers, vector<int>& picked, int cumulativeSum, int finalSum) {
    int ret = 0;

    // base case
    if( !picked.empty() ) {
      int sum = ;
      ; for( int& index : picked ) sum += numbers[index];
      if( sum == finalSum ) ret += 1;
    }

    int start = picked.empty() ? 0 : picked.back() + 1;
    for( int i = start; i < numbers.size(); ++i ) {
      picked.push_back(i);
      ret += calcCounts(numbers, picked, finalSum);
      picked.pop_back();
    }

    return ret;
  }

  bool findRealDwarfs(const vector<int>& candidates, vector<int>& picked, int currentSum) {
    bool ret = false;

    // base case, target : 100
    if( picked.size() == 7 && currentSum == 100 ) return true

    int start = picked.empty() ? 0 : picked.back() + 1;
    for( int i = start; i < candidates.size(); ++i ) {
      picked.push_back(i);
      ret = findRealDwarfs(candidates, picked, currentSum + candidates[i]);
      if( ret == true ) break;  // once find it, break
      picked.pop_back();
    }

    return ret;
  }


##  Example 3

  /* O(n^2) */
  void findAllPossiblePermutations(const vector<int>& nums, vector<bool>& visited, vector<int>& picked, vector< vector<int> >& result) {
    if( picked.size() == nums.size() ) {
      result.push_back(picked);
      return;
    }

    for( int i = 0; i < nums.size(); ++i ) {
      if( !visited[i] ) {
        visited[i] = true;
        picked.push_back(nums[i]);
        findAllPossiblePermutations(nums, visited, picked, result);
        visited[i] = false;
        picked.pop_back();
      }
    }
  }

  vector<vector<int>> permute(vector<int>& nums) {
    vector< vector<int> > ret;

    vector<bool> visited(nums.size(), false);
    vector<int> picked;
    findAllPossiblePermutations(nums, visited, picked, ret);

    return ret;
  }

  // c++ std::next_permutation
  // Different permutations can be ordered according to
  // how they compare lexicographicaly to each other
  vector<vector<int>> permute(vector<int>& nums) {
    vector< vector<int> > ret;

    sort(nums.begin(), nums.end()); // must be sorted
    do {
      ret.push_back(nums);
    } while( std::next_permutation(nums.begin(), nums.end()));

    return ret;
  }


#  Example3[optimization problem, feel it]

  Traveling Salesman problem
  Travel from current city to every cities and back to current city

  /* O((n-1)!) */

  vector<int> dictances;  // stores distance between cities

  // path, currentLength store already solved information
  // visited == true means already solved information
  // visited == false stores information to be solved
  int shortestPath(vector<int>& path, vecctor<bool>& visited, int currentLength) {
    // base case
    if( path.size() == distances.size() )
      return currentLength + distance[path[0]][path.back()];

    int ret = INF;  // INF : very big number

    // visit all the cities
    for( int next = 0; next < distances.size(); ++next ) {
      if( visited[next] ) continue;

      int here = path.back();

      path.push_back(next);
      visited[next] = true;

      int cand = shortestPath(path, visite, currentLength + distance[here][next]);

      // calc final solution with a subproblem
      ret = min(ret, cand);
      path.pop_back();
      visited[next] = false;
    }

    return ret;
  }
