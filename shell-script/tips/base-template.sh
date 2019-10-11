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
SCRIPT_HOME="$( cd -P "$( dirname "$SOURCE" )" >/dev/null && pwd )"

# now $SCRIPT_HOME holds absolute path of this script
echo "Script home: $SCRIPT_HOME"

function print_help() {
  echo "Options"
  echo "    -e : endpoint"
  echo "    -p : port"
  echo "    -c : enable client"
  echo "    -u : enable user"
  echo "    -n : number of make"
  echo "    -h : print help"
}

function some_function() {
  [[ $# -eq 0 ]] && print_help
  while getopts "e:p:cun:h" FLAG; do
    case $FLAG in
      e) ENDPOINT=$OPTARG ;;
      p) PORT=$OPTARG ;;
      c) ENABLE_RUN_MAKE_CLIENT=1 ;; # no argument value
      u) ENABLE_RUN_MAKE_USER=1 ;; # no argument value
      n) N_MAKE=$OPTARG ;;
      h|\?) help ; exit 1 ;; # no argument value
    esac
  done
  [[ $# -gt 0 ]] && echo -e "\\033[01;31mInvalid argument\\033[00m" && exit 1
}

some_function $@