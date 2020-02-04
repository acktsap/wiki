#!/bin/bash

function array() {
  echo "-- array --"

  # declare array
  IP_LIST=("localhost" "127.0.0.1")

  # print "localhost"
  echo ${IP_LIST[0]}

  # iterating with *
  for IP in ${IP_LIST[*]}; do
    echo $IP
  done

  # iterating with @
  for IP in ${IP_LIST[@]}; do
    echo $IP
  done

  # undeclare array
  unset IP_LIST
}

array