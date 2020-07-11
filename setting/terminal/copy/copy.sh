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


CURRENT_FILE=$(basename $0)
FILES=$(find * ! -name "$CURRENT_FILE")
for FILE in ${FILES[@]}; do
  DEST_DIR=""
  DEST_FILE=""
  case $FILE in
    *.vim)
      DEST_DIR="$HOME/.vim/colors"
      DEST_FILE="$FILE"
      ;;
    config)
      DEST_DIR="$HOME/.ssh"
      DEST_FILE="config"
      ;;
  esac

  if [[ ! -d "$DEST_DIR" ]]; then
    echo "Making directory $DEST_DIR"
    mkdir -p "$DEST_DIR"
  fi

  CANONICAL_FILE_PATH="$SCRIPT_HOME/$FILE"
  DEST="$DEST_DIR/$DEST_FILE"
  if [[ ! -f "$DEST" ]]; then
    echo "Copying $FILE to $DEST"
    cp "$CANONICAL_FILE_PATH" "$DEST"
  else
    echo "Not copying $FILE to $DEST. It already exists"
  fi
done
