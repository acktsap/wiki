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

# link copy
${SCRIPT_HOME}/link/link.sh
${SCRIPT_HOME}/copy/copy.sh

# additioal bashrc
BASHRC_ADD_FILE="bashrc_add"
HOME_BASHRC="$HOME/.bashrc"
if [[ -z $(cat $HOME_BASHRC | grep $BASHRC_ADD_FILE) ]]; then
cat << EOF >> ~/.bashrc

# load additional bashrc setting
source $PWD/$BASHRC_ADD_FILE
EOF
fi
