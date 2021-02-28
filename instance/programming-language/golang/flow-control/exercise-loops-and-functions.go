package main

import (
	"fmt"
	"math"
)

func Sqrt(x float64) float64 {
	z := 1.0
	for c := 9; c >= 0; c-- {
		z -= (z*z -x) / (2*z)
		fmt.Println("Z is now", z)
	}
	return z
}

func main() {
	result1 := Sqrt(10)
	result2 := math.Sqrt(10)

	fmt.Println("My Sqrt:", result1)
	fmt.Println("Math Sqrt:", result1)
	fmt.Println("Are they equal?", (result1 == result2))
}
