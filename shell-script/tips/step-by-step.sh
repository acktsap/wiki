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
  echo -ne "$message\n> "
  read -r NEXT
}

function assert_next() {
  local args=$@
  local found=false
  for arg in ${args[@]}; do
    if [ ${NEXT} = ${arg} ] ; then
      found=true
      break
    fi
  done

  if [ false = ${found} ]; then
    echo "Next should be one of $args"
    exit -1
  fi
}

function main() {
  get_next "Enter arg1 or arg2"
  assert_next "arg1" "arg2"

  get_next "Enter n1 or n2"
  assert_next "n1" "n2"
}

main $@
