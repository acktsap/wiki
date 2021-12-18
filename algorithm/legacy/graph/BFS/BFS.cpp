
====== BFS ======


	BFS?
	Implementation by queue[format, adjacent list]?
	Time complexity of BFS?
	BFS Spanning Tree?
	Finding Shortest Path & BFS?




 o BFS[Breadth First Search]

	Search more closer to root vertex

  - three state of vertex
  		1. !visited && !processed
		2. visited && !processed
		3. visited && processed


 o Implementation by queue[format, adjacent list]

	vector< vector<int> > adjacents;	// adjacent list

	void bfs(int start) {
		vector<bool> discovered = vector<bool>(adjacents.size(), false);

		queue<int> qu;
		qu.push(start);
		discovered[start] = true; // mark start as discovered
		while( !qu.empty() ) {
			// process current vertex
			int from = qu.front(); qu.pop();

			for( int i = 0; i < adjacents[from].size(); ++i ) {
				int& to = adjacents[from][i];
				if( !discovered[to] ) {
					qu.push(to);
					discovered[to] = true; // mark 'to' as discovered
				}
			}
		}
		// do something
	}


 o Time Complexity of BFS

	Check all edges of all vertices

  - adjacent list
		O(|V|+|E|);

  - adjacent matrix
		O(|V|^2);	// adjacent matrix is a V*V matrix


 o BFS Spanning Tree[undirected graph]

    Starts from vertex u(root), make a tree the way bfs(u) searches.


 o Finding Shortest Path & BFS?[feel it]

	vector< vector<int> > adjacents;	// adjacent list

	vector<int> bfsSpanningTree(int start) {
		vector<int> distance(adjacents.size(), -1); // distance from start to i
		vector<int> parentOfSpanningTree(adjacents.size(), -1);	// parent[i] : parent of spanning tree i

		queue<int> q;
		distance[start] = 0;
		parentOfSpanningTree[start] = start;
		q.push(start);
		while( !q.empty() ) {
			int here = q.front(); q.pop();

			for( int i = 0; i < adjacents[here].size(); ++i ) {
				int there = adjacents[here][i];
				if( distance[there] == -1 ) {
					q.push(there);
					distance[there] = distance[here] + 1;
					parentOfSpanningTree[there] = here;
				}
			}
		}

		return parentOfSpanningTree;
	}

	// get shortest path from root to v
	vector<int> shortestPath(int vertex, const vector<int>& parentOfSpanningTree) {
		vector<int> path;
		path.push_back(vertex);

		int nextVertex = vertex;
		while( parentOfSpanningTree[nextVertex] != nextVertex ) {	// while !root
			nextVertex = parent[nextVertex];
			path.push_back(nextVertex);
		}
		reverse(path.begin(), path.end());

		return path;
	}
