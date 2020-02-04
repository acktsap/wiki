package main

import "fmt"

type Person struct {
	Name string
	Age int
}

func (p Person) String() string {
	return fmt.Sprintf("%v (%v years)", p.Name, p.Age)
}

func main() {
	a := Person{"Ziggy Stardust", 17}
	z := Person{"José das Couves", 666}
	fmt.Println(a, z)
}
