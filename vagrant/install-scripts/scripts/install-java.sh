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


echo "Installing java..."

INSTALL_JAVA=""

if [[ "$OS" =~ "CentOS" ]]; then
  INSTALL_JAVA="java-1.8.0-openjdk java-1.8.0-openjdk-devel"
elif [[ "$OS" =~ "Ubuntu" ]]; then
  INSTALL_JAVA="openjdk-8-jdk"
  sudo add-apt-repository ppa:openjdk-r/ppa > /dev/null 2>&1
  sudo apt-get update > /dev/null 2>&1
fi

sudo $INSTALL_CMD -q install $INSTALL_JAVA -y > /dev/null 2>&1

check_install "java -version"
