package main

import "fmt"

func main() {
	i, j := 42, 2701

	// `p` points to `i`.
	p := &i
	// Read `i` through the `p` pointer.
	fmt.Println(*p)
	// Assign 21 to `i` through the 'p` pointer.
	*p = 21
	fmt.Println(i)

	// Now, `p` points to `j`.
	p = &j
	// Divides 37 with `j` through `p` pointer;
	// Then, assign the result to `j` through the `p` pointer.
	*p = *p / 37
	fmt.Println(j)
}
