#!/bin/bash

function array() {
  echo "-- array --"

  # declare array
  local ip_list=("localhost" "127.0.0.1")

  # print "localhost"
  echo ${ip_list[0]}

  # iterating with *
  for ip in ${ip_list[*]}; do
    echo "${ip}"
  done

  # iterating with @
  for ip in ${ip_list[@]}; do
    echo "${ip}"
  done
}

array
