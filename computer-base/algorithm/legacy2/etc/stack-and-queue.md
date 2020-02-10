Stack & Queue
=============


## Examples

### Example1 : check not well-defined bracket

```cpp
bool isGoodBrackets(const string& input) {
	string opening("({[");
	string closing(")}]");

	stack<int> stk;
	for( int i = 0; i < input.size(); ++i ) {
		if( opening.find(input[i]) != string::npos ) {
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
```

### Example2 : infix to postfix

```cpp
string infixToPostfix(const string& expr) {
  string postfix;

  string operators = "+-*/";
  map<char, int> precedence;
  precedence['('] = 1;
  precedence['+'] = 2; precedence['-'] = 2;
  precedence['*'] = 3; precedence['/'] = 3;

  stack<char> operatorStk;
  for( int i = 0; i < expr.size(); ++i ) {
    if( operators.find(expr[i]) != string::npos ) {
      // if operatorStk is not empty, pop it
      // >= : if same precedence, left to right rule
      while( !operatorStk.empty() && precedence[operatorStk.top()] >= precedence[expr[i]] ) {
        postfix += operatorStk.top();
        operatorStk.pop();
      }
      operatorStk.push(expr[i]);  // wait for second operand
    } else if( expr[i] == '(' ) {
      operatorStk.push(expr[i]);
    } else if( expr[i] == ')' ) {
      while( operatorStk.top() != '(') {
        postfix += operatorStk.top();
        operatorStk.pop();
      }
      operatorStk.pop();   // pop '('
    } else if( '0' <= expr[i] && expr[i] <= '9' ) {
      postfix += expr[i];
    }
  }

  while( !operatorStk.empty() ) {
    postfix += operatorStk.top();
    operatorStk.pop();
  }

  return postfix;
}
```
