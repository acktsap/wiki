package main

import (
	"fmt"
	"math/rand"
	"time"
)

func main() {
	fmt.Println("My favourite number is", rand.Intn(10))

	rand.Seed(time.Now().UTC().UnixNano())
	fmt.Println("And here is a random number", rand.Intn(10000))
}
