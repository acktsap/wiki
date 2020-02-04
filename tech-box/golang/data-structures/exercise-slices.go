package main

// Notice: don't forget to `go get golang.org/x/tour/pic`

import "golang.org/x/tour/pic"

func Pic(dx, dy int) [][]uint8 {
	y := make([][]uint8, dy)
	x := make([]uint8, dx)

	for yCounter := range y {
		for xCounter := range x {
			x[xCounter] = uint8(yCounter ^ xCounter)
		}
		y[yCounter] = x
	}

	return y
}

func main() {
	pic.Show(Pic)
}
