#!/bin/bash

echo -e "\n-- Command substituion --"

val=`expr 1 + 1`
echo $val

val=$(expr 1 + 1)
echo $val

unset val


echo -e "\n-- Variable substituion --"

val="hey!!"
echo '$val is not replaced'
echo "$val is replaced"

unset val


echo -e "\n-- Variable substituion (default value with not changing) --"

origin=""
val=${origin:-default}
echo "val: $val" # default
echo "origin: $origin" # ""

origin=123
val=${origin:-default}
echo "val: $val" # 123
echo "origin: $origin" # 123

unset val
unset origin


echo -e "\n-- Variable substituion (default value with changing) --"

origin=""
val=${origin:=default}
echo "val: $val" # default
echo "origin: $origin" # default

origin=123
val=${origin:=default}
echo "val: $val" # 123
echo "origin: $origin" # 123

unset val
unset origin


echo -e "\n-- Variable substituion (replace it to default if present, not changing) --"

origin=""
val=${origin:+default}
echo "val: $val" # ""
echo "origin: $origin" # ""

origin=123
val=${origin:+default}
echo "val: $val" # default
echo "origin: $origin" # 123

unset val
unset origin


echo -e "\n-- Variable substituion (throw error if not present) --"

origin="123"
val=${origin:?should set origin}
echo "val: $val" # 123
echo "origin: $origin" # 123

origin=""
val=${origin:?should set origin} # throw error

unset val
unset origin
