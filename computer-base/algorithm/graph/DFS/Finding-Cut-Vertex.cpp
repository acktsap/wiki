
====== Finding-Cut-Vertex ======


    Cut Vertex[undirected graph case]?
        Biconnected component?
    Algorithm for finding cut vertex[undirected graph case]?
    Implementation?
    Finding bridge of graph?




 o Cut Vertex[undirected graph case]

    remove of u -> # of compoment of graph + 1 ⇒ u is cut vertex

  - Biconnected component : A graph which has no cut vertex


 o Algorithm[undirected graph case]

    Make a DFS Spanning tree
    Since undirected graph has no cross edge,
    u is a cut vertex ⇔ root(u) and children(u) are not connected

    1. Get the minimum depth of vertices accessable from the children of u,
    2. If minimum depth of those >= depth(u) [which means deeper], u is a cut vertex;


 o Implementation

    vector< vector<bool> > adjacents;    // adjacent matrix
    vector<int> discovered;    // initialized to -1, store the dicovery order
    vector<bool> isCutVertex;    // initalized to false
    int counter;    // initialized to 1

    int findCutVertex(int here, bool isRoot) {
        discovered[here] = counter;
        ++counter;

        int minDepth = discovered[here];
        int children = 0;
        for( int there = 0; there < adjacents[here].size(); ++there ) {
            if( adjacents[here][there] == true ) {
                if( discovered[there] == -1) {
                    ++children;

                    int minDepthOfChild = findCutVertex(there, false);
                    if( !isRoot && minDepthOfChild >= discovered[here] ) {
                        isCutVertex[here] = true;
                    }
                    minDepth = min(minDepth, minDepthOfChild);
                } else {
                    minDepth = min(minDepth, discovered[there]);
                }
            }
        }

        // in case of root(more than 2 child means cut vertex)
        if( isRoot && children >= 2) {
            isCutVertex[here] = true;
        }

        return minDepth;
    }


 o Finding bridge of graph

    remove of edges e -> # of component of graph + 1 ⇒ e is bridge;
