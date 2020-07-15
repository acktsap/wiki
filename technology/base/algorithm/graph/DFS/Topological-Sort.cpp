
====== Topological Sort ======


	Topological Sort?
	Implementation?
		proof[1. just by, 2. using DFS spanning tree]?
        code[with adjacent list, feel it]
	Example[feel it]




 o Topological Sort

	Dependency graph represents dependency among vertices
	Holds for DAG

	ex)
		A -> C -> D -> E
		A -> B
		-> No cycle, DAG(Directed Acyclic Graph)

	Topological sort arranges all vertices so that all dependencies are staisfied
	if it is searched from left to right

	ex)
		A -> C -> D -> B -> E  :  O
		A -> 'D -> C' -> B -> E  :  X


 o Implementation

	Mark a vertex of dfs everytime it exit.
	continue.. until dfsAll finishes.
	Reverse the order of marked vertices.
	Then we get a result of topological sort.

  - proof[just]

		Let v, a, b, ..., u be a result of topological sort;
		Then dfs(v) is exited after dfs(u) is exited;
		Assume ∃ u -> v;
		dfs(u) check all of the adjacent edges;
		So, it have to check u -> v;
		At that time, visited[v] == true or false?

		1. visited[v] == true
			dfs(v) must be already called;
			Then, dfs(v) must be running and that means dfs(v) call dfs(u);
			Which means v -> u;
			By assumption, u -> v. That means ∃ cycle in graph;
			Which is contradiction(graph is DAG);

		2. visited[v] == false
			dfs(u) may call dfs(v);
			Therefore, dfs(u) is exited after dfs(v) is exited;
			Which is contradiction;

  - proof[by DFS spanning tree]

		Let v, a, b, ..., u be a result of topological sort;
		Assume ∃ u -> v;
		Which means dfs(v) is exited after dfs(u) is exited;
		There are 4 cases;

		1. u -> v : tree edge or 2. u -> v : forward edge
			If u -> v is a tree edge or a forward edge, dfs(u) is exited after dfs(v) is exited;
			Which is contradiction;

		3. u -> v : back edge
			If u -> v is a back edge, ∃ v -> u and therefore ∃ a cycle in graph;
			Which is contradiction(graph is DAG);

		4. u -> v : cross edge
			If u -> v is a cross edge, dfs(v) is already exited and dfs(u) is just called;
			Which is contradiction;

  - code[with adjacent list, feel it]

    vector< vector<int> > adjacents;  // adjacent list
    vector<bool> visited;
    vector<int> order;

    void dfs(int from) {
        visited[from] = true;

        for( int i = 0; i < adjacents[from].size(); ++i ) {
            int to = adjacents[from][i];
            if( !visited[to] ) dfs(to);
        }
        // save a vertex everytime dfs(from) exits
        order.push_back(from);
    }

    // return topologicalSort order; return vector of size 0 if it's not a DAG
    vector<int> topologicalSort() {
        visited = vector<bool>(adjacents.size(), false);
        order.clear();

        // dfsAll
        for( int i = 0; i < visited.size(); ++i ) {
            if( !visited[i] ) dfs(i);
        }

        // reverses the result
        reverse(order.begin(), order.end());

        // if the graph is not DAG, there must be reverse edge in order
        for( int i = 0; i < order.size(); ++i ) {
            for( int j = i + 1; j < order.size(); ++j ) {
                for( int& to : adjacents[order[j]])
                    if( to == order[i] ) return vector<int>();
            }
        }

        return order;
    }


 o Example[feel it]

	statement
	Dictionray, a list of words, find the order of the alphabet of this language

	Input
	1 <= n <= 200 : # of words in a dictionary

	ex)
	5
	gg
	kia
	lotte
	lg
	hanwha

	Output
	If contradiction, "INVALID HYPOTHESIS"
	else, order of alphabets
	(if more than one order is possible, print any of that)


 	ans)

	/* O(# of words * word.size ) */

	const string ERROR = "INVALID HYPOTHESIS";
	const int NUM_OF_ALPHABET = 26;

	vector< vector<int> > adjacents;
	vector<bool> visited;
	vector<int> order;

	void make_graph(const vector<string>& words) {
		adjacents = vector< vector<int> >(NUM_OF_ALPHABET, vector<int>(NUM_OF_ALPHABET, -1));

		// check all adjacent words
		for( int j = 1; j < words.size(); ++j ) {
			int i = j - 1;	// words[i] : front word, words[j] : next word
			int min_len = min(words[i].size(), words[j].size());

			for( int k = 0; k < min_len; ++k ) {
				if( words[i][k] != words[j][k] ) {
					int a = words[i][k] - 'a';
					int b = words[j][k] - 'a';
					adjacents[a][b] = 1;
					break;
				}
			}
		}
	}

	void dfs(int from) {
		visited[from] = true;
		for(int to = 0; to < adjacents.size(); ++to ) {
			if( adjacents[from][to] != -1 && !visited[to] )
				dfs(to);
		}
		order.push_back(from);  // mark vertex when the dfs exits
	}

    // return topologicalSort order; return vector of size 0 if it's not a DAG
	vector<int> topologicalSort() {
		visited = vector<bool>(adjacents.size(), false);
		order.clear();

		for( int i = 0; i < visited.size(); ++i ) {
			if( !visited[i] ) dfs(i);
		}

		// reverses the result
		reverse(order.begin(), order.end());

		// if the graph is not DAG, there must be reverse edge in order
		for( int i = 0; i < order.size(); ++i ) {
			for( int j = i + 1; j < order.size(); ++j ) {
				if( adjacents[order[j]][order[i]] == 1 )
					return vector<int>();
			}
		}

		return order;
	}

	int main()
	{
		ifstream cin("input.inp");

		int n;
		cin >> n;

		vector<string> words(n);
		for( int i = 0; i < n; ++i ) {
			cin >> words[i];
		}

		make_graph(words);
		vector<int> final_order = topologicalSort();

		if( !final_order.empty() ) {
			for( int i = 0; i < final_order.size(); ++i )
				cout << static_cast<char>(final_order[i] + 'a');
		} else {
			cout << ERROR;
		}
		cout << endl;

		return 0;
	}
