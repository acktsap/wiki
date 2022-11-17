#!/bin/bash

# /dev/null : trash output file

BASIC_TRASH="basic_trash"
HERE_TRASH="here_trash"

function file_input_output() {
  echo "-- file_input_output --"

  local file=$(basename "$0")

  function input() {
    # read a file line-by-line
    while read -r line; do
      echo $line
    done < ${file}
  }

  input

  function output() {
    # output redirection (make a new file)
    echo "Wow" > ${BASIC_TRASH}

    # Output redirection (append to a file)
    echo "Wow" >> ${BASIC_TRASH}
  }

  output
}

function here_document() {
  echo "-- here_document --"

  # make a file contaiing 'fuck\ndamn' using here document (make a new file)
  cat << EOF >> ${HERE_TRASH}
  fuck
  damn
EOF

  # make a file contaiing 'fuck\ndamn' using here document? (append to a file)
  cat << EOF >> ${HERE_TRASH}
  fuck
  damn
EOF
}

file_input_output
here_document