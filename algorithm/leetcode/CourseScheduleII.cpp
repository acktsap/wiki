#include <iostream>
#include <vector>
#include <tuple>
#include <algorithm>

using namespace std;

/*
 * Descryption
 *
 * https://leetcode.com/problems/course-schedule-ii/
 *
 *
 * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1.
 * You are given an array prerequisites where prerequisites[i] = [a_i, b_i]
 * indicates that you must take course b_i first if you want to take course a_i.
 * 
 * For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
 *
 * Return the ordering of courses you should take to finish all courses.
 * If there are many valid answers, return any of them.
 * If it is impossible to finish all courses, return an empty array.
 *  
 * 
 * Example 1:
 * 
 * Input: numCourses = 2, prerequisites = [[1,0]]
 * Output: [0,1]
 *
 * Explanation
 *
 * There are a total of 2 courses to take.
 * To take course 1 you should have finished course 0. So the correct course order is [0,1].
 *
 * Example 2:
 * 
 * Input: numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]
 * Output: [0,2,1,3]
 *
 * Explanation
 *
 * There are a total of 4 courses to take.
 * To take course 3 you should have finished both courses 1 and 2. Both courses 1 and 2 should be taken after you finished course 0.
 * So one correct course order is [0,1,2,3]. Another correct ordering is [0,2,1,3].
 *
 * Example 3:
 * 
 * Input: numCourses = 1, prerequisites = []
 * Output: [0]
 *  
 * 
 * Constraints:
 * 
 * 1 <= numCourses <= 2000
 * 0 <= prerequisites.length <= numCourses * (numCourses - 1)
 * prerequisites[i].length == 2
 * 0 <= a_i, b_i < numCourses
 * a_i != b_i
 * All the pairs [a_i, b_i] are distinct.
 *
 *
 *
 * Review
 *
 * adjacent matrix로 하면 타임아웃이 나는 것이다..!
 * adjacent list를 쓰자!!
 *
 */
class CourseScheduleII {
public:
  /*
    그냥 위상정렬.. dfs 끝날때 order에 추가하고 마지막으로 reverse
    
    - time: O(V + E)
    - space: O(V)
    
    어 뭐야 cycle이 있을 수 있네? 그렇다면 cycle detection!!
  */
  vector<int> findOrderDfs(int numCourses, vector<vector<int>>& prerequisites) {
    vector<int> order;
    
    vector<vector<int>> adjacents(numCourses, vector<int>());
    for (vector<int>& prerequisite : prerequisites) {
      adjacents[prerequisite[1]].push_back(prerequisite[0]);
    }
    
    vector<int> visited(numCourses, 0);
    for (int i = 0; i < visited.size(); ++i) {
      if (visited[i] == 0) {
        if (dfs(i, adjacents, visited, order)) {
          return { };
        }
      }
    }
    reverse(order.begin(), order.end());
    
    return order;
  }
  
  bool dfs(int node, vector<vector<int>>& adjacents, vector<int>& visited, vector<int>& order) {
    // has cycle
    if (visited[node] == 1) {
      return true;
    }
    
    if (visited[node] == 2) {
      return false;
    }
    
    visited[node] = 1;
    for (int to : adjacents[node]) {
      if (dfs(to, adjacents, visited, order)) {
        return true;
      }
    }
    visited[node] = 2;
    
    order.push_back(node);
    
    return false;
  }
};

int main() {
  vector<tuple<int, vector<vector<int>>, vector<int>>> parameters {
    make_tuple(
        2,
        vector<vector<int>> { { 1, 0 } },
        vector<int> { 0, 1 }
    ),
    make_tuple(
        4,
        vector<vector<int>> { { 1, 0 }, { 2, 0 }, { 3, 1 }, { 3, 2 } },
        vector<int> { 0, 2, 1, 3 }
    ),
    make_tuple(
        1,
        vector<vector<int>> { },
        vector<int> { 0 }
    ),
    make_tuple(
        2,
        vector<vector<int>> { { 1, 0 }, { 0, 1 } },
        vector<int> { }
    )
  };

  CourseScheduleII solution;
  for (auto& parameter : parameters) {
    auto& numCourses = get<0>(parameter);
    auto& prerequisites = get<1>(parameter);
    auto& expected = get<2>(parameter);

    {
      auto actual = solution.findOrderDfs(numCourses, prerequisites);
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

  return 0;
}
