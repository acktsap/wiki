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

  while getopts "t:f:brh" FLAG; do
    case $FLAG in
      t) readonly BROWSER=$OPTARG ;;
      f) BOOKMARK_BACKUP_PATH=$OPTARG ;;
      b) readonly OPERATION="backup" ;;
      r) readonly OPERATION="restore" ;;
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

  if [[ -z "$OPERATION" ]]; then
    echo "must set [-b | -r]"
    exit -1
  fi
}

#######################################
# Outputs:
#   Print help
#######################################
function print_help() {
  echo "Options"
  echo "    -t : a browser type (chrome | whale | firefox)"
  echo "    -f : a target file path"
  echo "    -b : backup"
  echo "    -r : restore"
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
      # see 'Note' on http://kb.mozillazine.org/Export_bookmarks
      readonly BOOKMARK_PATH="/Users/$USER/Library/Application Support/Firefox/Profiles/jugaer2x.default-release/bookmarks.html"
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
  if [[ "$OPERATION" == "backup" ]]; then
    local source="$BOOKMARK_PATH"
    local target="$SCRIPT_HOME/$BOOKMARK_BACKUP_PATH"
  elif [[ "$OPERATION" == "restore" ]]; then 
    # firefox doesn't support html with default configuration
    if [[ "$BROWSER" == "firefox" ]]; then
      echo "import firefox bookmark manually (see 'http://kb.mozillazine.org/Import_bookmarks#Import_from_file')"
      exit -1
    fi
    local source="$SCRIPT_HOME/$BOOKMARK_BACKUP_PATH"
    local target="$BOOKMARK_PATH"
  fi

  cp "$source" "$target"
}

function main() {
  set_script_home
  parse_argument "$@"
  find_bookmark
  do_operation
}

main "$@"

