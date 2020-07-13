#!/bin/bash -e
#
# Copy setup files. It exists, doesn't copy file.

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
#   Copy setup files.
#######################################
function copy_file() {
  local current_file=$(basename $0)
  local files=$(find * ! -name "$current_file")
  for file in ${files[@]}; do
    local dest_dir=""
    local dest_file=""
    case $file in
      *.vim)
        dest_dir="$HOME/.vim/colors"
        dest_file="$file"
        ;;
      config)
        dest_dir="$HOME/.ssh"
        dest_file="config"
        ;;
      *rc)
        dest_dir="$HOME"
        dest_file=".$file"
        ;;
    esac

    if [[ ! -d "$dest_dir" ]]; then
      echo "Making directory $dest_dir"
      mkdir -p "$dest_dir"
    fi

    local canonical_file_path="$SCRIPT_HOME/$file"
    local dest="$dest_dir/$dest_file"
    if [[ ! -f "$dest" ]]; then
      echo "Copying $file to $dest"
      cp "$canonical_file_path" "$dest"
    else
      echo "Not copying $file to $dest. It already exists"
    fi
  done
}

function main() {
  set_script_home
  copy_file
}

main "$@"
