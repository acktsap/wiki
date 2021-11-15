
====== Finding Shortest Path ======




 o Example

  - statement

	Sorting GameWordChain(끝말잇기);
    Reverse operation[1, 2] on {3, 2, 1, 0} -> {3, 1, 2, 0};
    Find a minimum # of reverse operation to get a sorted array;

  - Input

	1 <= C <= 50 : # of test case;
	1 <= n <= 8 : size of an array;
    n integer(1 <= n <= 1000000);

  - Output

	Print minimun # of reverse operation for each test case;


	ans)

	// n elements, # of ordering : n! ways
	// vertex : any ordered elements, edge : reachable by a reverse operation
	// return minimum # of reverse operation; if fails, return -1
    int getMinimumReverseOperation(const vector<int>& numbers) {
    	map< vector<int>, int > distance;
    	vector<int> sorted = numbers; // final destination
    	sort(sorted.begin(), sorted.end());

    	queue< vector<int> > q;
    	distance[numbers] = 0;
    	q.push(numbers);
    	while (!q.empty()) {
    		vector<int> here = q.front(); q.pop();

    		if (here == sorted) return distance[here];

    		int cost = distance[here];
    		for (int i = 0; i < numbers.size(); ++i) {
    			for (int j = i + 2; j <= numbers.size(); ++j) {  // here.begin(), here.begin() + 2 : [0], [1] of vector
    				reverse(here.begin() + i, here.begin() + j);
    				if (distance.count(here) == 0) {	// if not searched
    					distance[here] = cost + 1;
    					q.push(here);
    				}
    				reverse(here.begin() + i, here.begin() + j);
    			}
    		}
    	}
    	return -1;
    }

    /*
        but it's too slow, it may take 8! = 40320 * 1000 = 40320000 times;
        1. regarding relative size of an array, calculate previously;
        2. starts from the sorted array;
    */
