#!/bin/bash -e

# Resolve script home
SOURCE="${BASH_SOURCE[0]}"
# resolve $SOURCE until the file is no longer a symlink
while [ -h "$SOURCE" ]; do
  SCRIPT_HOME="$( cd -P "$( dirname "$SOURCE" )" >/dev/null && pwd )"
  SOURCE="$(readlink "$SOURCE")"
  # if $SOURCE was a relative symlink, we need to resolve it relative to the path where the symlink file was located
  [[ $SOURCE != /* ]] && SOURCE="$SCRIPT_HOME/$SOURCE"
done
readonly SCRIPT_HOME="$( cd -P "$( dirname "$SOURCE" )" >/dev/null && pwd )"

readonly SOURCE_PROJECT=application
readonly TARGET_PROJECTS=(
  spring-rest-server
)
readonly TARGET_FILES=(
  build.gradle
  checkstyle.xml
)

main() {
  for project in "${TARGET_PROJECTS[@]}"; do
    for file in "${TARGET_FILES[@]}"; do
      cp "$SCRIPT_HOME/$SOURCE_PROJECT/$file" "$SCRIPT_HOME/$project/$file"
    done
  done
}

main "$@"
