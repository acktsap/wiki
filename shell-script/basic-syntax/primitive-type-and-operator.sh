#!/bin/bash

<< COMMENT

## Primitive type in shell script

No real distinction the way the variable is stored.  
Utilities require a distinction - when a numeric value is expected, non-numeric values are treated as zero.

## Order vs Modern shell

Modern shell supports square brackets [[ ]] while order one doesn't.

COMMENT

function assignment_operator() {
  echo "-- assignment_operator --"

  VAL1="string"
  VAL2=123
  VAL3="$OTHER_VAL"

  function func() {
    local val=123
  }
}

function aritimetic_operator() {
  echo "-- aritimetic_operator --"

  # In traditional shell, we have to use external program (awk or expr)
  # But in modern shell, use `$((expression))`

  local a=10
  local b=3

  # addition
  echo "a + b: $((a + b))"

  # subtraction
  echo "a - b: $((a - b))"

  # multiplication
  echo "a * b: $((a * b))"

  # division
  echo "a / b: $((a / b))"

  # modulas
  echo "a % b: $((a % b))"
}

function relational_operator() {
  echo "-- relational_operator --"

  # Expressions should be placed inside `[ ]` or `[[ ]]` with space around them

  function number_relation() {
    local a="$1"
    local b="$2"

    # Equality : traditional case
    if [ "$a" -eq "$b" ]; then
      echo "A and b are same"
    fi

    # Equality : modern case
    if [[ "$a" == "$b" ]]; then
      echo "A and b are same"
    fi

    # Non equality : traditional case
    if [ "$a" -ne "$b" ]; then
      echo "A and b are not same"
    fi

    # Non equality : modern case
    if [[ "$a" != "$b" ]]; then
      echo "A and b are not same"
    fi

    # Greater Than
    if [ "$a" -gt "$b" ]; then
      echo "a > b"
    fi

    # Greater than or Equal to
    if [ "$a" -ge "$b" ]; then
      echo "a >= b"
    fi

    # Less Then
    if [ "$a" -lt "$b" ]; then
      echo "a < b"
    fi

    # Less Then or Equal to
    if [ "$a" -le "$b" ]; then
      echo "a <= b"
    fi
  }

  number_relation 2 5
  number_relation 2 5
  number_relation 5 5

  function string_relation() {
    local a="$1"
    local b="$2"

    # Equal
    if [ "$a" = "$b" ]; then
      echo "string a and b are equals"
    fi

    # Not Equal
    if [ "$a" != "$b" ]; then
      echo "string a and b are not equals"
    fi

    # Zero size
    if [ -z "$a" ]; then
      echo "string a is empty"
    fi

    # Non-Zero size
    if [ -n "$a" ]; then
      echo "string a is not empty"
    fi
  }

  string_relation "str1" "str2"
  string_relation "str1" "str1"
  string_relation "" "str2"
}

function logical_operator() {
  echo "-- logical_operator --"

  function negation() {
    if [ !false ]; then
      echo "This will be printed"
    fi
  }

  negation

  function or() {
    local a="$1"
    local b="$2"

    # or : traditional case
    if [ "$a" -gt 20 -o "$b" -lt 100 ]; then
      echo "20 < a or b < 100"
    fi

    # or : modern case
    if [[ 20 < "$a" || "$b" < 100 ]]; then
      echo "20 < a or b < 100"
    fi
  }

  or 25 110

  function and() {
    local a="$1"
    local b="$2"

    # and : traditional case
    if [ "$a" -gt 20 -a "$b" -lt 100 ]; then
      echo "20 < a and b < 100"
    fi

    # and : modern case
    if [[ 20 < "$a" && "$b" < 100 ]]; then
      echo "20 < a and b < 100"
    fi
  }

  and 25 90
}

function file_operator() {
  echo "-- file_operator --"

  local file=$(basename "$0")

  function type_check() {
    # block special file
    if [ -b ${file} ]; then
      echo "File $file is block special file"
    fi

    # character special file
    if [ -c ${file} ]; then
      echo "File $file is block special file"
    fi

    # file check
    if [ -f ${file} ]; then
      echo "$file is ordinary file"
    fi

    # directory check
    if [ -d ${file} ]; then
      echo "$file is directory"
    fi

    # named pipe check
    if [ -p ${file} ]; then
      echo "$file is named pipe"
    fi
  }

  type_check

  function id_check() {
    # A group ID[SGID] bit is check
    if [ -g ${file} ]; then
      echo "A group id git is set"
    fi
    # User ID[SUID] bit check?
    if [ -u ${file} ]; then
      echo "A user id git is set"
    fi
  }

  id_check

  function mode_check() {
    # readable
    if [ -r ${file} ]; then
      echo "File is readable"
    fi

    # writeable
    if [ -w ${file} ]; then
      echo "File is writeable"
    fi

    # executable
    if [ -r ${file} ]; then
      echo "File is executable"
    fi
  }

  mode_check

  function size_check() {
    # file size > 0
    if [ -s ${file} ]; then
      echo "File size > 0"
    fi

    # existance
    if [ -e ${file} ]; then
      echo "File $file exists"
    fi
  }

  size_check
}

assignment_operator
aritimetic_operator
relational_operator
logical_operator
file_operator