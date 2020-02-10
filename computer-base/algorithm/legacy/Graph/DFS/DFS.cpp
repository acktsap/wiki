
====== DFS ======


    DFS?
    By recursion[adjacent matrix]?
    By recursion2[map searching]?
    By stack[adjacent matrix]?
    Time Complexity of DFS?
    Usage of DFS[feel it]



 o DFS[Depth First Search]

    Search more deeper vertex first
    If no deeper vertex, go back to a previous vertex
    Implementation : by recursion | by stack


 o By recursion[adjacent matrix]

    vector< vector<bool> > adjacents;	// adjacent matrix
    vector<bool> visited;	// whether visited or not for each vertex

    void dfs(int from) {
        // 1. mark from as visited
        visited[from] = true;

        // 2. process current vertex
        ...

        // 3. search next vertex
        for( int to = 0; to < adjacents[from].size(); ++to ) {
            if( adjacents[from][to] == true && !visited[to] ) {
                dfs(to);
            }
        }

        // exit function, go back to previous vertex
    }

    void dfsAll() {
        visited = vector<bool>(adjacents.size(), false);  // initialize to false
        for( int i = 0; i < visited.size(); ++i ) {
            if( !visited[i] ) {
                dfs(i);
            }
        }
    }

    /*
        Why dfsAll?
        In mose case, dfs is used to reveal overall structore of the graph
        So, we need to put dfs for all vertices
        ex)
        A — B	D — E
        |
        C
        If dfs is excuted only in A, righter graph can't be searched
    */


 o By recursion2[map searching]

    const int DIRECTION_NUM = 4;
    const int da[] = { 1, -1, 0, 0 };
    const int db[] = { 0, 0, -1, 1 };

    vector< vector<int> > map;	// map, consist of 1, 0
    vector< vector<bool> > visited;	// whether visited or not for each map entry

    bool isRightCoordinate(int a, int b) {
        return 0 <= a && b <= map.size() - 1 && 0 <= b && b <= map[a].size() - 1;
    }

    void dfs(int a, int b) {
        // 1. mark from as visited
        visited[a][b] = true;

        // 2. process current vertex
        ...

        // 3. search next vertex
        for (int i = 0; i < DIRECTION_NUM; ++i) {
            int nextA = a + da[i];
            int nextB = b + db[i];

            if ( isRightCoordinate(nextA, nextB) &
                    map[nextX][nextY] == 1 && !visited[nextX][nextY]) {
                dfs(nextX, nextY);
            }
        }

        // do something
        // exit function, go back to previous vertex
    }

    void dfsAll() {
        visited = vector< vector<bool> >(map.size(), vector<bool>(map.size(), false));  // initialize to false
        for (int a = 0; a < visited.size(); ++a) {
            for (int b = 0; b < visited[a].size(); ++b) {
                if (map[a][b] == 1 && !visited[a][b]) {
                    dfs(a, b);
                    // do something
                }
            }
        }
    }


 o By stack[adjacent matrix]

    void dfs(int start) {
        stack<int> stk;
        stk.push(start);

        while( !stk.empty() ) {
            int from = stk.top(); stk.pop();

            if( !visited[from] ) {
                // mark from as visited
                visited[from] = true;

                // process current vertex
                ...

                // search next vertex
                for( int to = 0; to < adjacents[from].size(); ++to ) {
                    if( adjacents[from][to] == true && !visited[to] ) {
                        stk.push(to);
                    }
                }
            }
        }
    }


 o Time Complexity of DFS

    Check all edges of all vertices

  - adjacent list[by recursion] : O(|V|+|E|);
  - adjacent list[by stack] : O(|E|) why?
  - adjacent matrix : O(|V|^2);	// adjacent matrix is a V*V matrix


 o Usage of DFS

  - Is two vertices u, v connected?
        Execute dfs(u), if visited[v] = true, u and v are connected;

  - Find # of connected components

        int dfsAll() {
            int count = 0;

            for( int a = 0; a < map.size(); ++a ) {
                for( int b = 0; b < map[0].size(); ++b ) {
                    if( !visited[a][b]) {
                        dfs(a, b);
                        count++;
                    }
                }
            }

            return count;
        }

  - Find # of elements of connected components

        // Check # of call of dfs() in dfsAll();
        // returns # of all the subsequente dfs calls
        int dfs(int from) {
            int connectedCount = 1; // default connected count is 1

            visited[from] = true;
            for( int to = 0; to < map[from].size(); ++to ) {
                if( map[from][to] == 1 && !visited[to] )
                    connectedCount += dfs(to);
            }

            return connectedCount;
        }
