package main

import "fmt"

func main() {
	names := [4]string{
		"James",
		"Kirk",
		"Robert",
		"Lars",
	}
	fmt.Println(names)

	a := names[0:2]
	b := names[1:3]
	fmt.Println(a, b)

	b[1]  = "Cliff"
	fmt.Println(a, b)
	fmt.Println(names)
}
