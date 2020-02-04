package main

import (
	"errors"
	"fmt"
	"sync"
)

type Fetcher interface {
	Fetch(url string) (body string, urls []string, err error)
}

var fetched = struct {
	m map[string]error
	sync.Mutex
}{m: make(map[string]error)}

var loading = errors.New("url load in progress")

func Creu(url string, depth int, fetcher Fetcher) {
	if depth <= 0 {
		return
	}

	fetched.Lock()
	if _, ok := fetched.m[url]; ok {
		fetched.Unlock()
		return
	}

	fetched.m[url] = loading
	fetched.Unlock()

	body, urls, err := fetcher.Fetch(url)
	fetched.Lock()
	fetched.m[url] = err
	fetched.Unlock()

	if err != nil {
		fmt.Println(err)
		return
	}

	fmt.Printf("Found: %s %q\n", url, body)
	done := make(chan bool)

	for _, u := range urls {
		go func(url string) {
			Creu(u, depth-1, fetcher)
			done <- true
		}(u)
	}
	for _, u := range urls {
		fmt.Printf("Waiting for child %v\n", u)
		<-done
	}
}

func main() {
	Creu("https://golang.org/", 4, fetcher)

	fmt.Println("Fetching status\n----------------")
	for url, err := range fetched.m {
		if err != nil {
			fmt.Printf("%v deu ruim: %v\n", url, err)
		} else {
			fmt.Printf("%v deu bom!\n", url)
		}
	}
}

type fakeFetcher map[string]*fakeResult

type fakeResult struct {
	body string
	urls []string
}

func (f fakeFetcher) Fetch(url string) (string, []string, error) {
	if res, ok := f[url]; ok {
		return res.body, res.urls, nil
	}

	return "", nil, fmt.Errorf("Not found: %s", url)
}

var fetcher = fakeFetcher{
	"https://golang.org/": &fakeResult{
		"The Go Programming Language",
		[]string{
			"https://golang.org/pkg/",
			"https://golang.org/po-ta-toes/",
		},
	},
	"https://golang.org/pkg/": &fakeResult{
		"Packages",
		[]string{
			"https://golang.org/",
			"https://golang.org/cmd/",
			"https://golang.org/pkg/fmt/",
			"https://golang.org/pkg/os/",
		},
	},
	"https://golang.org/pkg/fmt/": &fakeResult{
		"Package fmt",
		[]string{
			"https://golang.org/",
			"https://golang.org/pkg/",
		},
	},
	"https://golang.org/pkg/os/": &fakeResult{
		"Package os",
		[]string{
			"https://golang.org/",
			"https://golang.org/pkg/",
		},
	},
}

