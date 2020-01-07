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

function main() {
  echo "$OSTYPE detected.."
  if [[ "$OSTYPE" = darwin* ]]; then
    COMMAND="brew install"
  elif [[ "$OSTYPE" = linux* ]]; then
    local OS="$(grep . /etc/*-release | head -n 1 | cut -d"=" -f2)"
    if [[ "$OS" = "CentOS" ]]; then
      COMMAND="sudo yum install -y"
    elif [[ "$OS" = "Ubuntu" ]]; then
      COMMAND="sudo apt-get install -y"
    fi
  else
    echo "Unsupported os type"
    exit -1
  fi
  echo "install with '$COMMAND'"

  # vim
  local vundle="$HOME/.vim/bundle/Vundle.vim"
  if [[ ! -d "$vundle" ]]; then
    git clone https://github.com/VundleVim/Vundle.vim.git "$vundle"
  fi

  # z shell
  local zshell_location=$(which zsh)
  if [[ -z ${zshell_location} ]]; then
    ${COMMAND} zsh
    zshell_location=$(which zsh)
    [ -z $(sudo grep ${zshell_location} /etc/shells) ] && sudo bash -c "echo ${zshell_location} >> /etc/shells"
    chsh -s ${zshell_location}
    sh -c "$(curl -fsSL https://raw.githubusercontent.com/ohmyzsh/ohmyzsh/master/tools/install.sh)"
  fi

  # direnv
  if [[ -z $(which direnv) ]]; then
    ${COMMAND} direnv
  fi
}

main "$@"
