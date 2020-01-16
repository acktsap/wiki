package main

import (
	"golang.org/x/tour/wc"
	"strings"
)

func WordCount(sentence string) map[string]int {
	words := strings.Fields(sentence)
	list := make(map[string]int)

	for _, word := range words {
		_, ok := list[word]
		if ok {
			list[word] += 1
		} else {
			list[word] = 1
		}
	}

	return list
}

func main() {
	wc.Test(WordCount)
}
