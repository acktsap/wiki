
====== ETC ======

    Check all of it


 o to_string in c++ 11

    #include <sstream>

    namespace tostr {
        string to_string(const int& n) {
            std::ostringstream stm;
            stm << n;
            return stm.str();
        }
    }


 o string to integer [since c++ 11]

    stoi("123");    // return 123


 o getchar

    int n, k;
    scanf("%d", &n);
    scanf("%d", &k);

    getchar();	// remove \n
    while( k-- ) {
     	char ch;
        ch = getchar();
    }


 o cin functions

    - std::getline
        string input;
        while( std::getline(cin, input) ) {
          std::cout << input << std::endl;
        }


 o C++ std::cin speed up

	cin.sync_with_stdio(false);   // off synchronization with <cstdio>


 o std toupper tolower

    #include <cctype>
    
    int toupper(int ch);
    int tolower(int ch);
