package main

import (
	"fmt"
	"time"
)

func say(something string) {
	for i := 0; i < 5; i++ {
		time.Sleep(100 * time.Millisecond)
		fmt.Println(something)
	}
}

func falae(oq string) {
	for i := 0; i < 3; i++ {
		fmt.Println(oq, "era pra falar msm? Ah!", i)
	}
}

func main() {
	go say("preula")
	say("Eita")

	falae("Masoq")
	go falae("Mas,")

	go func(message string) {
		fmt.Println(message)
	}("BIRL")

	fmt.Scanln()
	fmt.Println("CABÔ")
}
