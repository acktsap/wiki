package main

import "fmt"

func main() {
	var i interface{} = "because the internet"

	s := i.(string)
	fmt.Println(s)

	s, ok := i.(string)
	fmt.Println(s, ok)

	f, ok := i.(float64) // Safe!
	fmt.Println(f, ok)

	f = i.(float64) // Bang!
	fmt.Println(f)
}
