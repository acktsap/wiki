#!/bin/bash

function std_input_output() {
  echo "-- std_input_output --"

  function input() {
    echo -n "Enter anything: "
    read input
    echo "$input"
  }

  input

  function output() {
    # no new line
    echo -n "message"

    # enable interpretation of backslash
    echo -e "message1\nmessage2"

    # both
    echo -ne "message1\nmessage2"
    echo # need this for new line
  }

  output
}

function output_coloring() {
  echo "-- std_input_output --"

  RED=${RED:-\\033[01;31m}
  GREEN=${GREEN:-\\033[01;32m}
  YELLOW=${YELLOW:-\\033[01;33m}
  NO_COLOR=${NO_COLOR:-\\033[00m}

  # need -e option(enable)
  echo -e "${RED}RED${NO_COLOR}"
  echo -e "${GREEN}GREEN${NO_COLOR}"
  echo -e "${YELLOW}YELLOW${NO_COLOR}"
}

std_input_output
output_coloring