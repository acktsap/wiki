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

AVAILABLE_PLATFORMS=(ubuntu centos)

function print_help() {
  echo "Options"
  echo "    -p : platform (availables: '${AVAILABLE_PLATFORMS[@]}')"
  echo "    -d : directory"
  echo "    -h : print help"
  echo
}

function validate_args() {
  local found="false"
  for p in ${AVAILABLE_PLATFORMS[*]}; do if [ ${p} = ${PLATFORM} ]; then found="true"; break; fi done
  [ ${found} != "true" ] && return 1

  [ -z ${DIRETORY} ] && return 1

  return 0
}

function copy_targets() {
  local -r target_directory="$DIRETORY"
  mkdir -p ${target_directory}
  cp Vagrantfile_${PLATFORM} ${target_directory}/Vagrantfile
  cp -R ${SCRIPT_HOME}/install-scripts/* ${target_directory}
}

function main() {
  [[ $# -eq 0 ]] && print_help && exit 1
  while getopts "p:d:h" FLAG; do
    case $FLAG in
      p) PLATFORM=$OPTARG ;;
      d) DIRETORY=$OPTARG ;;
      h|\?) print_help ; exit 1 ;; # no argument value
    esac
  done

  validate_args 
  [[ $? -gt 0 ]] && echo -e "\\033[01;31mInvalid argument\\033[00m" && exit 1

  copy_targets
}

main $@
