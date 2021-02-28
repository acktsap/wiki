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


echo "Installing python..."

INSTALL_PYTHON=""

if [[ "$OS" =~ "CentOS" ]]; then
  sudo yum install https://centos7.iuscommunity.org/ius-release.rpm
  INSTALL_PYTHON="python36u"
elif [[ "$OS" =~ "Ubuntu" ]]; then
  INSTALL_PYTHON="python3.6"
  sudo add-apt-repository ppa:jonathonf/python-3.6 > /dev/null 2>&1
  sudo apt-get update > /dev/null 2>&1
fi

sudo $INSTALL_CMD -q install $INSTALL_PYTHON -y > /dev/null 2>&1
sudo $INSTALL_CMD -q install python-pip -y > /dev/null 2>&1

check_install "python3 --version"
