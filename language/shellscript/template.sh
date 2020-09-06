#!/bin/bash -e
#
# TODO: this is script template

#######################################
# Prepare absolute path of current script.
# Outputs:
#   SCRIPT_HOME an absolute path where current script is located.
#######################################
function set_script_home() {
  # identify source directory
  local source="${BASH_SOURCE[0]}"
  # resolve $source until the file is no longer a symlink
  while [ -h "$source" ]; do
    SCRIPT_HOME="$( cd -P "$( dirname "$source" )" >/dev/null && pwd )"
    source="$(readlink "$source")"
    # if $source was a relative symlink, we need to resolve it relative to the path where the symlink file was located
    [[ $source != /* ]] && source="$SCRIPT_HOME/$source"
  done
  SCRIPT_HOME="$( cd -P "$( dirname "$source" )" >/dev/null && pwd )"
}

#######################################
# Outputs:
#   Print help
#######################################
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
  [[ $# -eq 0 ]] && print_help && exit 1
  while getopts "e:p:cun:h" FLAG; do
    case $FLAG in
      e) ENDPOINT=$OPTARG ;;
      p) PORT=$OPTARG ;;
      c) ENABLE_RUN_MAKE_CLIENT=1 ;; # no argument value
      u) ENABLE_RUN_MAKE_USER=1 ;; # no argument value
      n) N_MAKE=$OPTARG ;;
      h|\?) print_help ; exit 1 ;; # no argument value
    esac
  done
  echo "  endpoint : $ENDPOINT"
  echo "  port : $PORT"
  echo "  enable client : $ENABLE_RUN_MAKE_CLIENT"
  echo "  enable user : $ENABLE_RUN_MAKE_USER"
  echo "  number of make : $N_MAKE"
}

function main() {
  some_function "$@"
}

main "$@"

