#!/bin/bash -e
#
# Copying & Restoring Browser Bookmark 

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
}

#######################################
# Params:
#   1: a browser type
# Outputs:
#   BOOKMARK_PATH a path where chrome bookmark is located
#######################################
function find_bookmark() {
  local browser=${1:?Target browser should be present}

  local os="$OSTYPE"
  if [[ "$os" = darwin* ]]; then
    if [[ "$browser" == "chrome" || "$browser" == "Chrome" ]]; then
      readonly BOOKMARK_PATH="/Users/$USER/Library/Application Support/Google/Chrome/Default/Bookmarks"
    elif [[ "$browser" == "whale" || "$browser" == "Whale" ]]; then 
      readonly BOOKMARK_PATH="/Users/$USER/Library/Application Support/Naver/Whale/Default/Bookmarks"
    elif [[ "$browser" == "brave" || "$browser" == "Brave" ]]; then 
      readonly BOOKMARK_PATH="/Users/$USER/Library/Application Support/BraveSoftware/Brave-Browser/Default/Bookmarks"
    else
      # see 'Note' on http://kb.mozillazine.org/Export_bookmarks
      echo "Unsupported browser type $browser"
      exit -1
    fi
  else 
    echo "Unsupported type $os"
    exit -1
  fi
}

#######################################
# Params:
#   1: browser bookmark storage path
#   2: backup path
#######################################
function backup-bookmark() {
  local browser_storage=$1
  local backup=$2
  cp "$browser_storage" "$backup"
}

#######################################
# Params:
#   1: browser bookmark storage path
#   2: backup path
#######################################
function restore-bookmark() {
  local browser_storage=$1
  local backup=$2
  cp "$backup" "$browser_storage" 
}

function main() {
  set_script_home

  local browser=${1:?Usage: ./bookmark.sh [browser] [restore|backup] [backup file path]}
  local operation=${2:?Usage: ./bookmark.sh [browser] [restore|backup] [backup file path]}
  local backup=${3:?Usage: ./bookmark.sh [browser] [restore|backup] [backup file path]}

  find_bookmark "${browser}"

  "${operation}-bookmark" "${BOOKMARK_PATH}" "${backup}"
}

main "$@"

