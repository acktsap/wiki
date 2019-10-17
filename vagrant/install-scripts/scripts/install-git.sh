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


echo "Installing git..."

sudo ${INSTALL_CMD} -q install git -y > /dev/null 2>&1
sudo ${INSTALL_CMD} -q install tig -y > /dev/null 2>&1

check_install "git --version"
check_install "tig --version"
