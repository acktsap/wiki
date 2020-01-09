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
  CANONICAL_FILE_PATH="$SCRIPT_HOME/$FILE"
  DEST=""
  case $FILE in
    *.vim)
      DEST="$HOME/.vim/colors"
      ;;
    config)
      DEST="$HOME/.ssh"
      ;;
  esac
  mkdir -p "$DEST"
  if [[ ! -f "$DEST/$FILE" ]]; then
    echo "Copy to $DEST"
    cp "$CANONICAL_FILE_PATH" "$DEST/$FILE"
  else
    echo "Not copy to '$DEST/$FILE'. It already exists"
  fi
done
