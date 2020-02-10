
====== Sliding-Window ======


 o Problem

    Given array of length N [(a_0) ~ a_(n-1)] and buffer size L
    ∀ i : i - (L - 1) >= 0 print mininum value between a_(i - (L - 1)) ~ a_(i);


 o Solution

    vector<int> maxInWindows(const vector<int>& numbers, int windowSize)
    {
        if( numbers.empty() || windowSize <= 0 || numbers.size() < windowSize )
            return vector<int>();

        // If windowSize == 1 then number itself is the answer
        if( windowSize == 1 ) return numbers;

        vector<int> maxInSlidingWindows;    // result, stores indices of maximum value
        deque<int> window; // window.front() is a index of a max

        for(int i = 1; i < numbers.size(); ++i)
        {
            maxInSlidingWindows.push_back(numbers[window.front()]);

            // If numbers[window.back()] is less than numbers[i],
            // it never be a max on current window
            while( !window.empty() && numbers[i] >= numbers[window.back()] )
                window.pop_back();

            // If current max is out of window, remove it
            // Then automatically, the index of next maximum value is window.front()
            if( i >= windowSize && !window.empty() && window.front() <= i - windowSize )
                window.pop_front();

            window.push_back(i);
        }
        maxInSlidingWindows.push_back(numbers[window.front()]);

        return maxInSlidingWindows;
    }
