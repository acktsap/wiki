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
# Outputs:
#   BROWSER a browser type
#   BOOKMARK_BACKUP_PATH a browser bookmark backup file path
#   OPERATION an operation
#######################################
function parse_argument() {
  [[ $# -eq 0 ]] && print_help && exit 1

  while getopts "b:f:o:h" FLAG; do
    case $FLAG in
      b) readonly BROWSER=$OPTARG ;;
      f) BOOKMARK_BACKUP_PATH=$OPTARG ;;
      o) readonly OPERATION=$OPTARG ;;
      h|\?) print_help ; exit 1 ;;
    esac
  done

  # default path is current directory
  BOOKMARK_BACKUP_PATH=${BOOKMARK_BACKUP_PATH:-.}

  if [[ $BROWSER != "chrome" \
      && $BROWSER != "whale" \
      && $BROWSER != "firefox" ]]; then
        echo -e "Invalid browser type"
    exit -1
  fi

  if [[ $OPERATION != "backup" && $OPERATION != "restore" ]]; then
    echo -e "Invalid operation type"
    exit -1
  fi

  echo "  browser : $BROWSER"
  echo "  bookmark file path : $BOOKMARK_BACKUP_PATH"
  echo "  operation : $OPERATION"
}

#######################################
# Outputs:
#   Print help
#######################################
function print_help() {
  echo "Options"
  echo "    -b : a browser type (chrome | whale | firefox)"
  echo "    -f : a target file path"
  echo "    -o : an operation (backup | copy)"
  echo "    -h : print help"
}

#######################################
# Globals:
#   BROWSER a browser type
# Outputs:
#   BOOKMARK_PATH a path where chrome bookmark is located
#######################################
function find_bookmark() {
  readonly local os="$OSTYPE"
  if [[ "$os" = darwin* ]]; then
    if [[ "$BROWSER" == "chrome" ]]; then
      readonly BOOKMARK_PATH="/Users/$USER/Library/Application Support/Google/Chrome/Default/Bookmarks"
    elif [[ "$BROWSER" == "whale" ]]; then 
      readonly BOOKMARK_PATH="/Users/$USER/Library/Application Support/Naver/Whale/Default/Bookmarks"
    elif [[ "$BROWSER" == "firefox" ]]; then 
      # TODO
    fi
  else 
    echo "Unsupported type $os"
    exit -1
  fi
}

#######################################
# Globals:
#   OPERATION an operation
#   BOOKMARK_PATH a path where browser bookmark file is located
#   BOOKMARK_BACKUP_PATH a path where browser bookmark backup file is located
#######################################
function do_operation() {
  # todo
  local source="$BOOKMARK_PATH"
  local target="$BOOKMARK_BACKUP_PATH"
  echo "$source"
  echo "$target"
}

function main() {
  set_script_home
  parse_argument "$@"
  find_bookmark
  do_operation
}

main "$@"

