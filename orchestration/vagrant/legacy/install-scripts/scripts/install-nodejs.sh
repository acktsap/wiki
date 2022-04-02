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
SCRIPT_HOME="$( cd -P "$( dirname "$SOURCE" )" >/dev/null && pwd )"

# define common variables
. ${SCRIPT_HOME}/common


echo "Install nodejs..."

NODE_VERSION=10.16.3

sudo -u ${USER} curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.34.0/install.sh | sh
. "$HOME/.profile"
check_install "nvm --version"

nvm install ${NODE_VERSION}
nvm use ${NODE_VERSION}
check_install "node --version"
