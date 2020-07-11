#!/bin/bash -e

# identify source directory
SOURCE="${BASH_SOURCE[0]}"
# resolve $SOURCE until the file is no longer a symlink
while [ -h "$SOURCE" ]; do
  SCRIPT_HOME="$( cd -P "$( dirname "$SOURCE" )" >/dev/null && pwd )"
  SOURCE="$(readlink "$SOURCE")"
  # if $SOURCE was a relative symlink, we need to resolve it relative to the path where the symlink file was located
  [[ $SOURCE != /* ]] && SOURCE="$SCRIPT_HOME/$SOURCE"
done
SCRIPT_HOME="$( cd -P "$( dirname "$SOURCE" )" >/dev/null && pwd )"
cd "$SCRIPT_HOME"

function check_precondition() {
  echo "Do you want to setup settings? Make sure you've run 'install.sh'. [y|n]"
  echo -n "> "
  read -r ANSWER
  if [[ "$ANSWER" != "y" ]]; then
    exit -1
  fi
}

function main() {
  # link and copy
  ${SCRIPT_HOME}/link/link.sh
  ${SCRIPT_HOME}/copy/copy.sh
}

check_precondition
main "$@"

