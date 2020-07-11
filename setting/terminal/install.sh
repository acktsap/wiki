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
  echo "Install with '$COMMAND'"

  # vim
  if [[ -z $(which vim | grep bin) ]]; then
    echo "Installing vim.."
    ${COMMAND} vim
  fi

  # nvim
  if [[ -z $(which nvim) ]]; then
    echo "Installing neovim.."
    ${COMMAND} neovim
  fi

  # vim vundle plugin
  local vundle="$HOME/.vim/bundle/Vundle.vim"
  if [[ ! -d "$vundle" ]]; then
    echo "Installing Vundle.vim.."
    git clone https://github.com/VundleVim/Vundle.vim.git "$vundle"
  fi

  # z shell
  local zshell_location=$(which zsh)
  if [[ -z ${zshell_location} ]]; then
    echo "Installing zsh.."
    ${COMMAND} zsh
    zshell_location=$(which zsh)
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

  # direnv
  if [[ -z $(which direnv | grep bin) ]]; then
    echo "Installing direnv.."
    ${COMMAND} direnv
  fi

  # tmux
  if [[ -z $(which tmux | grep bin) ]]; then
    echo "Installing tmux.."
    ${COMMAND} tmux
  fi

  # git
  if [[ -z $(which tig | grep bin) ]]; then
    echo "Installing tig.."
    ${COMMAND} tig
  fi

  echo -e "\nInstalling is done.. make sure run 'setup-settings.sh'\n"
}

main "$@"
