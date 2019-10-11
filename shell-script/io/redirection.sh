#!/bin/bash

function redirection() {
  echo "-- redirection --"

  # output redirection to a variable
  result=$(ls -l | grep iranalyzer)

  # input redirection from a file?
  # print the number of word in user file
  wc -l < users

  # discard the console output?
  # /def/null is a special file which automatically discards all its input
  echo "fuck" > /dev/null

  # merge input from stream 1 (stdout) with stream 2(stderr)
  ./runner < users 1 <& 2

  # merge output from stream 1 (stdout) with stream 2 (stderr)
  ./runner > /dev/null 1 >& 2

  # discard stdout (1) & stderr (2)
  command > /dev/null 2>&1
}

redirection