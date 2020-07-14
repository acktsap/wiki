package main

// Don't forget to `go get golang.org/x/tour/reader` ;)
import (
	"golang.org/x/tour/reader"
	"fmt"
)

type MyReader struct{}

func (m MyReader) Read(b []byte) (int, error) {
	if len(b) == 0 {
		return 0, fmt.Errorf("The End has come!")
	}

	for i, _ := range b {
		b[i] = 'A'
	}
	return 1, nil
}

func main() {
	reader.Validate(MyReader{})
}
