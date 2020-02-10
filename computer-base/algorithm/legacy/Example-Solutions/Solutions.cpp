// Chapter 1	록 페스티벌	FESTIVAL

const double WORST_COST = 100001.0;

double bestAverageCost(const vector<int>& costs, const int rentedTeam) {
	double bestAvgCost = WORST_COST;

	vector<int> subsum(costs.size(), 0);
	for( int i = 0; i < costs.size(); ++i ) {
		subsum[i] += costs[i];
		if( i > 0 ) {
			subsum[i] += subsum[i - 1];
		}
	}

	for( int i = 0; i < costs.size() - rentedTeam; ++i ) {
		int startIndex = i + rentedTeam - 1;
		double bestAvgCostCandidate = (double)(subsum[startIndex] - subsum[i]) / (startIndex - i + 1);

		if( bestAvgCostCandidate < bestAvgCost ) {
			bestAvgCost = bestAvgCostCandidate;

			int diff = 1;
			while( startIndex + diff < costs.size() ) {
				double candidate = (double)(subsum[startIndex + diff] - subsum[i]) / (startIndex + diff - i + 1);

				if( candidate < bestAvgCost ) {
					bestAvgCost = candidate;
				} else {
					break;
				}

				diff++;
			}
		}

		cout << bestAvgCost << endl;
	}

	return bestAvgCost;
}



// Chapter 6	소풍 PICNIC

int calculatePossiblePairs(const vector< vector<bool> >& relation, vector<bool>& pairedState) {

	int notMatched = -1;

	// find not matched person
	for( int i = 0; i < pairedState.size(); ++i ) {
		if( pairedState[i] == false ) {
			notMatched = i;
			break;
		}
	}

	// base case
	if( notMatched == -1 ) return 1;

	int ret = 0;

	// force the order
	for( int i = notMatched + 1; i < pairedState.size(); ++i ) {
		if( pairedState[i] == false && relation[notMatched][i] == true ) {
			pairedState[notMatched] = pairedState[i] = true;
			ret += calculatePossiblePairs(relation, pairedState);
			pairedState[notMatched] = pairedState[i] = false;
		}
	}

	return ret;
}



// Chapter 8	외발뛰기 JUMPGAME

int cache[100][100];

bool pathExists(const vector< vector<int> >& grid, int x, int y) {
	int n = grid.size();

	// base case(out of grid)
	if( x < 0 || y < 0 || x >= n || y >= n ) return false;

	// base case(final destination)
	if( x == n - 1 && y == n - 1 ) return true;

	// use dp cache
	int& ret = cache[x][y];
	if( ret != -1 ) return ret;

	int nextJump = grid[x][y];
	ret = pathExists(grid, x + nextJump, y) || pathExists(grid, x, y + nextJump);

	return ret;
}



// Chapter 19	짝이 맞지 않는 괄호	BRACKETS2

bool isGoodBrackets(const string& input) {
	string opening("({[");
	string closing(")}]");

	stack<int> stk;
	for( int i = 0; i < input.size(); ++i ) {
		if( opening.find(input[i]) != -1 ) {
			stk.push(input[i]);
		} else {
			if( stk.empty() )
				return false;

			if( opening.find(stk.top()) != closing.find(input[i]) )
				return false;

			stk.pop();
		}
	}

	return stk.empty();
}
