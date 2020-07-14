package main

import (
	// Don't forget to `go get golang.org/x/tour/pic`
	"golang.org/x/tour/pic"
	"image/color"
	"image"
)

type Image struct {
	Height, Width int
}

func (img Image) ColorModel() color.Model {
	return color.RGBAModel
}

func (img Image) Bounds() image.Rectangle {
	return image.Rect(0, 0, img.Height, img.Width)
}

func (img Image) At(x, y int) color.Color {
	m := uint8(x ^y)
	return color.RGBA{m, m, 255, 255}
}

func main() {
	m := Image{256, 256}
	pic.ShowImage(m)
}
