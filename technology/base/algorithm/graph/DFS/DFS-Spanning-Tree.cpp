
====== DFS-Spanning-Tree ======


    DFS Spanning Tree on directed graph?
        tree edge? forward edge? back edge? cross edge?
    DFS Spanning Tree on undirected graph?
    Distinction of DFS Spanning tree edges[implementation]?
    Existence of a cycle in graph?




 o DFS Spanning Tree on directed graph

    Starts from vertex u(root), make a tree the way dfs(u) searches

    tree edge : u -> v, where u == parent of tree && v == child of tree
                            && visited(v) == false
    forward edge : u -> v, where u == parent of tree && v == child of tree
                            && visited(v) == true
    back edge : v -> u, where v == child of tree && u == parent of tree
    cross edge : u -> v, where visited(u) == true && visited(v) == true
                            && u, v are not a parent or a child of each other


 o DFS Spanning Tree on undirected graph

    No distinction between forward edge and back edge
    No cross edge


 o Distinction of DFS Spanning tree edges[implementation]

    vector< vector<bool> > adjacents;    // adjacent matrix
    vector<int> discovered; // initialized to -1, store the dicovery order
    vector<bool> finished;  // initialized to false
    int counter;    // initialized to 1

    void findDfsSpanningTreeEdges(int here) {
        discovered[here] = counter;
        counter++;

        for( int there = 0; there < adjacents[here].size(); ++there ) {
            if( adjacents[here][there] == true ) {
                cout << "(" << here << "," << there << ") is a ";
                if( discovered[there] == -1 ) {
                    cout << "tree edge" << endl;
                    findDfsSpanningTreeEdges(there);
                } else if( discovered[here] < discovered[there] ) {
                    cout << "forward edge" << endl;
                } else if( finished[there] == false ) {
                    cout << "back edge" << endl;
                } else {    // finished[there] == true
                    cout << "cross edge" << endl;
                }
            }
        }
        finished[here] = true;
    }


 o Existence of a cycle in graph

    Equivalent with existence of back edges
