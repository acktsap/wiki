package main

import (
	"fmt"
	"strings"
	"strconv"
)

type IPAddr [4]byte

func (ip IPAddr) String() string {
	ipAddress := make([]string, len(ip))
	for index, value := range ip {
		ipAddress[index] = strconv.Itoa(int(value))
	}
	return strings.Join(ipAddress, ".")
}

func main() {
	hosts := map[string]IPAddr{
		"loopback": {127, 0, 0, 1},
		"googleDNS": {8, 8, 8, 8},
	}

	for name, ip := range hosts {
		fmt.Printf("%v: %v\n", name, ip)
	}
}
