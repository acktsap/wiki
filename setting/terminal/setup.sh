#!/bin/bash -e
#
# Setup terminal utils

#######################################
# Outputs:
#   SCRIPT_HOME an absolute path where current script is located
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
  cd "$SCRIPT_HOME"
}

#######################################
# Prepare install command .
# Outputs:
#   INSTALL_COMMAND variable is set as install command
#######################################
function prepare_install_command() {
  echo "$OSTYPE detected.."
  if [[ "$OSTYPE" = darwin* ]]; then
    INSTALL_COMMAND="brew install"
  elif [[ "$OSTYPE" = linux* ]]; then
    local OS="$(grep . /etc/*-release | head -n 1 | cut -d"=" -f2)"
    if [[ "$OS" = "CentOS" ]]; then
      INSTALL_COMMAND="sudo yum install -y"
    elif [[ "$OS" = "Ubuntu" ]]; then
      INSTALL_COMMAND="sudo apt-get install -y"
    fi
  else
    echo "Unsupported os type"
    exit -1
  fi
  echo "Install with '$INSTALL_COMMAND'"
}

#######################################
# Install tool.
# Globals:
#   INSTALL_COMMAND
# Arguments:
#   1 binary name (required)
#   2 install name (optional)
#######################################
function install() {
  local binary_name="$1"
  local install_name="${2:-$1}"
  if [[ -z $(which "${binary_name}" | grep bin) ]]; then
    echo "Installing ${install_name}.."
    ${INSTALL_COMMAND} "${install_name}"
  fi
}

#######################################
# Link setup files.
#######################################
function setup_link() {
  echo "Linking files.."
  ${SCRIPT_HOME}/link/link.sh
}

#######################################
# Copy setup files.
#######################################
function setup_copy() {
  echo "Copying files.."
  ${SCRIPT_HOME}/copy/copy.sh
}

function main() {
  set_script_home
  prepare_install_command

  install vim
  install nvim neovim
  install direnv
  install tmux
  install tig
  install tree
  install zsh

  # vim vundle plugin
  local vundle="$HOME/.vim/bundle/Vundle.vim"
  if [[ ! -d "$vundle" ]]; then
    echo "Installing Vundle.vim.."
    git clone https://github.com/VundleVim/Vundle.vim.git "$vundle"
  fi

  # set z shell as default shell
  local default_shell=$(echo $SHELL | grep zsh)
  if [[ -z ${default_shell} ]]; then
    local zshell_location=$(which zsh)
    [ -z $(sudo grep ${zshell_location} /etc/shells) ] && sudo bash -c "echo ${zshell_location} >> /etc/shells"
    chsh -s ${zshell_location}
  fi

  # oh my zsh
  local oh_my_zsh_location="$HOME/.oh-my-zsh"
  if [[ ! -d ${oh_my_zsh_location} ]]; then
    echo "Installing oh-my-zsh.."
    # https://github.com/ohmyzsh/ohmyzsh#unattended-install
    sh -c "$(curl -fsSL https://raw.githubusercontent.com/ohmyzsh/ohmyzsh/master/tools/install.sh)" "" --unattended
  fi

  setup_link
  setup_copy
}

main "$@"
