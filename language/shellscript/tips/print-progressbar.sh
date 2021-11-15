#!/bin/bash

# Resolve script home
SOURCE="${BASH_SOURCE[0]}"
# resolve $SOURCE until the file is no longer a symlink
while [ -h "$SOURCE" ]; do
  SCRIPT_HOME="$( cd -P "$( dirname "$SOURCE" )" >/dev/null && pwd )"
  SOURCE="$(readlink "$SOURCE")"
  # if $SOURCE was a relative symlink, we need to resolve it relative to the path where the symlink file was located
  [[ $SOURCE != /* ]] && SOURCE="$SCRIPT_HOME/$SOURCE"
done
readonly SCRIPT_HOME="$( cd -P "$( dirname "$SOURCE" )" >/dev/null && pwd )"

# now $SCRIPT_HOME holds absolute path of this script
echo "Script home: $SCRIPT_HOME"


readonly GREEN="\\033[01;32m"
readonly NO_COLOR="\\033[00m"

function print_progressbar {
  local has_processed_count="$1"
  local total_count="$2"
  local n_block=${3:-50}

  local percentage=$((100 * has_processed_count / total_count))
  local calculated_n_percentage_block=$((n_block * percentage / 100))

  echo -ne "\\r" # start on a start of the current line
  echo -ne "<"
  for ((i=1; i<=calculated_n_percentage_block; ++i)); do
    echo -ne "${GREEN}=${NO_COLOR}";
  done
  for ((i=calculated_n_percentage_block+1; i<=n_block; ++i)); do
    echo -ne "-";
  done
  echo -ne ">"
  echo -ne " $percentage %"
}

function clear_line {
  echo -ne "\033[2K"; printf "\r"
}

TOTAL_COUNT=10
for i in $(seq 1 ${TOTAL_COUNT}); do
  print_progressbar ${i} ${TOTAL_COUNT}
  sleep 0.2
done
clear_line
