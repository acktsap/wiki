
====== Eulerian-path ======


    Hamiltonian path?
    Eulerian path[Eulerian trail]? Eulerian circuit?
    Degree of the vertex : degree? inDegree? outDegree?
    Existence of Eulerian path[Implementation]?
    Existence of Eulerian circuit[Implementation]?
    Find the Eulerian path by the Eulerian circuit from u to v?
    Algorithm for finding the Eulerian path?
        Implementation[by dfs]? Time Complexity?
    Example[feel it]




 o Hamiltonian path

    A path which visited every vertex exactly once
    No answer yet


 o Eulerian path[Eulerian trail]

    A path which visits every edge exactly once

  - Eulerian circuit
        Eulerian path && starting vertex == ending vertex


 o Degree of the vertex

  - degree(v) : # of edges from or to a vertex v
  - inDegree(v) : # of edges to v
  - outDegree(v) : # of edges from v


 o Existence of Eulerian Path[implementation, feel it]

    /*
        ∃! u ∈ V, inDegree(u) + 1 == outDegree(u) &&    // starting vertex
        ∃! v ∈ V, inDegree(v) == outDegree(v) + 1 &&    // ending vertex

        if( Directed case )
            ∀ z ∈ V(z != u, v), inDegree(z) == outDegree(z)
        else    // Undirected case
            ∀ z ∈ V(z != u, v), degree(z) % 2 == 0
    */
    bool hasEulerPath(vector<int>& inDegree, vector<int>& outDegree) {
        int absOut = 0;
        int absIn = 0;
        for( int i = 0; i < inDegree.size(); ++i ) {
            int outMinusIn = outDegree[i] - inDegree[i];
            if( outMinusIn < -1 || 1 < outMinusIn ) return false;   // not exists
            if( outMinusIn == 1 ) absOut++;
            if( outMinusIn == -1 ) absIn++;
        }
        if( absOut == 1 && absIn == 1 ) return true;   // Euler path
        if( absOut == 0 && absIn == 0 ) return true;  // Euler circuit
        return false;
    }


 o Existence of Eulerian circuit[implementation, feel it]

    /*
        Undirected graph : ∀ v ∈ V, degree(v) % 2 == 0
        Directed graph : ∀ v ∈ V, inDegree(v) == outDegree(v)
    */
    bool hasEularCircuit(const vector< vector<int> >& graph) {
        vector<int> degree(graph.size(), 0);    // undirected case
        // directed case
        // vector<int> inDegree(graph.size(), 0);
        // vector<int> outDegree(graph.size(), 0);
        for( int i = 0; i < graph.size(); ++i ) {
            for( int j = 0; j < graph[i].size(); ++j ) {
                degree[i] += graph[i][j];    // undirected case
                // directed case
                // outDegree[i] = graph[i][j];
                // inDegree[i] = graph[j][i];
            }
        }

        for( int i = 0; i < degree.size(); ++i ) {
            if( degree[i] % 2 != 0 ) return false;    // undirected case
            // directed case
            // if( inDegree[i] != outDegree[i] ) return false;
        }

        return true;
    }


 o Find the Eulerian path from u to v by the Eulerian circuit

    Add edge v -> u and find the Eulerian circuit
    Remove v -> u from the Eulerian circuit


 o Algorithm for finding the Eulerian path

  - Algorithm

    Let u be any vertex.
    1. findEulerPath(u)
         : starting from u, search adjacent edges until it reaches u;
    2. If ∃ vertex v which has edge not searched
         findEulerPath(v) and connect the circuit with the previous path;
       Else, return;

  - Implementation[undirected graph, by dfs]

    /*
        O(|E||V|)
        |E| -> call getEulerPath every time it visits edges
        |V| -> search all edges of the from vertex
    */

    // adjacent graph, graph[i][j] : # of edges from i to j
    vector< vector<int> > graph;

    void getEulerPath(int from, vector<int>& path) {
        for( int to = 0; to < graph[from].size(); ++to ) {
            while( graph[from][to] > 0 ) {
                // remove both part
                graph[from][to]--;
                graph[to][from]--;

                // search next edge
                getEulerPath(to, path);
            }
        }

        // make a path from the end
        // so, easy to insert sub-circuit
        path.push_back(from);
    }

    int main(void) {
        ...
        /* finally, reverse the result */
        reverse(path.begin(), path.end());
    }


 o Example[feel it]

    Statement
    WordChain(끝말잇기), A list of words;
    Can we make a word chain without using duplicate words?
    If it is possible, show any of the answer;

    Input
    1 <= C <= 50 : # of test case;
    1 <= n <= 100 : # of words for each test case;
    n words(2 to 10 length, lower case, no duplication) for each test case;

    Output
    If no possible word chain, "IMPOSSIBLE";
    else, print any of the possible wordchain;


    ans)

    /* O(|V||E|) */

    const int NUM_OF_CHAR = 26;

    vector<string> words;
    vector< vector<int> > adjacents;
    vector<int> inDegree;
    vector<int> outDegree;
    vector<string> edges[NUM_OF_CHAR][NUM_OF_CHAR]; // make each word as an edge

    void makeGraph() {
        // clear adjacents informations
        adjacents = vector< vector<int> >(NUM_OF_CHAR, vector<int>(26, 0));
        inDegree = vector<int>(NUM_OF_CHAR, 0);
        outDegree = vector<int>(NUM_OF_CHAR, 0);

        // clear edges
        for( int i = 0; i < NUM_OF_CHAR; ++i ) {
            for( int j = 0; j < NUM_OF_CHAR; ++j ) {
                edges[i][j].clear();
            }
        }

        for( int i = 0; i < words.size(); ++i ) {
            const int startChar = words[i][0] - 'a';
            const int endChar = words[i][words[i].size() - 1] - 'a';

            adjacents[startChar][endChar]++;
            inDegree[startChar]++;
            outDegree[endChar]++;
            edges[startChar][endChar].push_back(words[i]);
        }
    }

    bool hasEulerPath() {
        int out = 0;
        int in = 0;
        for( int i = 0; i < NUM_OF_CHAR; ++i ) {
            int outMinusIn = outDegree[i] - inDegree[i];
            if( outMinusIn < -1 || 1 < outMinusIn ) return false;
            if( outMinusIn == 1 ) out++;
            if( outMinusIn == -1 ) in++;
        }
        if( out == 1 && in == 1 ) return true;   // in case of Euler path
        if( out == 0 && in == 0 ) return true;  // in case of Euler circuit
        return false;
    }

    void getEulerPath(int here, vector<int>& path) {
        for( int there = 0; there < adjacents.size(); ++there ) {
            while( adjacents[here][there] > 0 ) {
                adjacents[here][there]--;
                getEulerPath(there, path);
            }
        }
        path.push_back(here);
    }

    string makeWordChain(const vector<int>& eulerPath) {
        string wordChain = "";
        for( int i = 1; i < eulerPath.size(); ++i ) {
            int startChar = eulerPath[i - 1];
            int endChar = eulerPath[i];
            if( wordChain.size() != 0 ) wordChain += " ";
            wordChain += edges[startChar][endChar].back();
            edges[startChar][endChar].pop_back();
        }
        return wordChain;
    }

    string calculateWordChain() {
        if( !hasEulerPath() ) return "IMPOSSIBLE";

        vector<int> eulerPath;
        for( int i = 0; i < NUM_OF_CHAR; ++i ) {
            // Euler path case
            if( outDegree[i] == (inDegree[i] + 1) ) {
                getEulerPath(i, eulerPath);
                break;
            }

            // Euler circuit case
            if( outDegree[i] > 0 ) {
                getEulerPath(i, eulerPath);
                break;
            }
        }

        // check whether all the vertices are searched or not
        if( eulerPath.size() != words.size() + 1 ) return "IMPOSSIBLE";

        reverse(eulerPath.begin(), eulerPath.end());

        return makeWordChain(eulerPath);
    }

    int main()
    {
        ifstream cin("input.inp");
        int c;
        cin >> c;

        while( c-- ) {
            int n;
            cin >> n;

            words = vector<string>(n);
            for( int i = 0; i < n; ++i ) {
                cin >> words[i];
            }
            makeGraph();
            cout << calculateWordChain() << endl;
        }

        return 0;
    }
