package main

import "fmt"

func main() {
	var a [2]string
	a[0] = "POT"
	a[1] = "ATO"
	fmt.Println(a[0], a[1])
	fmt.Println(a)

	primes := [6]int{2, 3, 5, 7, 11, 13}
	fmt.Println(primes)
}
