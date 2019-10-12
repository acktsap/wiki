#!/bin/bash -e

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

function get_next() {
  local message=${1:-"Enter next"}
  echo -n "$message > "
  read -r NEXT
}

function main() {
  get_next "Enter arg1"
  if [ ${NEXT} = "arg1" ]; then
    echo "You typed arg1 correctely"
  else
    echo "You have to type 'arg1'"
    get_next "Enter arg1 (last chance)"

    if [ ${NEXT} != "arg1" ]; then
      echo "I warned you"
      exit -1
    fi

    echo "Good boy"
  fi
}

main $@
