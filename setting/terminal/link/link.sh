#!/bin/bash -e
#
# Link setup files.

#######################################
# Prepare absolute path of current script.
# Outputs:
#   SCRIPT_HOME an absolute path where current script is located.
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
# Globals:
#   SCRIPT_HOME
# Outputs:
#   Link setup files.
#######################################
function link_file() {
  # add parent directories
  local dirs=(
    $HOME/.config/nvim
  )
  for dir in ${dirs[@]}; do
    if [[ ! -d ${dir} ]]; then
      mkdir -p ${dir}
    fi
  done

  # link
  local current_file=$(basename $0)
  FILES=$(find * ! -name "$current_file")

  for file in ${FILES[@]}; do
    local canonical_file_path="$SCRIPT_HOME/$file"
    dest=""
    case $file in
      *vim)
        dest="$HOME/.config/nvim/$file"
        ;;
      *)
        dest="$HOME/.$file"
        ;;
    esac
    echo "Link to '$dest'"
    ln -sf "$canonical_file_path" "$dest"
  done
}

function main() {
  set_script_home
  link_file
}

main "$@"
