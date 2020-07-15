#!/bin/bash

function variable() {
  echo "-- variable --"

  # By convention, all the name is in UPPERCASE

  function global_variable() {
    # declare variable
    # globally accessable
    VAL="global_variable"
    echo "$VAL" # print "global_variable"

    unset VAL
    echo "$VAL" # print ""
  }

  global_variable

  function local_variable() {
    # declare local variable
    # only accessable within function
    local LOCAL_VAL="local_variable"
    echo "$LOCAL_VAL" # print "local_variable"

    unset LOCAL_VAL
    echo "$LOCAL_VAL" # print ""
  }

  local_variable

  function enviroment_variable() {
    echo "Absolute path of home Directory: $HOME"
    echo "User name: $USER"
    echo "Present working directory: $PWD"
    echo "Old PWD: $OLDPWD"
    echo "Current function name: $FUNCNAME"
    echo "Parent process id: $PPID"
    echo "A script runs: $SECONDS"
  }

  enviroment_variable

  function special_variabke() {
    echo "Filename of the script: $0"
    echo "1th args: $1, 2nd args: $2"
    echo "A number of argument: $#"
    echo "All the arguments which are totally double quoted: $*"
    echo "All the arguments which are individually double quoted: $@"
    echo "Exit status of the last command: $?"  # 0: fine, else: error
    echo "Pid of the current shell: $$"
    echo "Pid of the last background command: $!"

    # exit on failure
    if [ $? != 0 ]; then exit -1; fi
  }

  special_variabke "arg1" "arg2"

  function global_constant() {
    # global constant
    readonly CONSTANT="global_constant"
    echo "$CONSTANT" # print "global_constant"

    # cause error
    # CONSTANT="change"

    # cause error
    # unset CONSTANT
  }

  global_constant

  function local_constant() {
    local -r LOCAL_CONSTANT="local_constant"
    echo "$LOCAL_CONSTANT" # print "local_constant"

    # cause error
    # LOCAL_CONSTANT="change"

    # cause error
    # unset LOCAL_CONSTANT
  }

  local_constant
}

function decision_making() {
  echo "-- decision_making --"

  function if_else_statement() {
    local a="$1"
    local b="$2"
    if [ $a -eq $b ]; then
      echo "a == b"
    elif [ $a -gt $b ]; then
      echo "a > b"
    else
      echo "a < b"
    fi
  }

  if_else_statement 1 1
  if_else_statement 1 2
  if_else_statement 2 1

  function case_statement() {
    local -r WORD="$1"
    case $WORD in
      "hello")
        echo "hello"
        ;;
      "fuck" | "suck")
        echo "fuck off"
        ;;
      "hey")
        echo "hey"
        ;;
      *) # default
        echo "sorry?"
        ;;
    esac
  }

  case_statement hello
  case_statement fuck
  case_statement hey
  case_statement what

  # short circuit evaluation
  [[ $? -eq 0 ]] || exit -1
}

function loop() {
  echo "-- loop --"

  function while_loop() {
    # print 0-4
    a=0
    while [ $a -lt 5 ]; do
      echo $a
      a=$(($a + 1))
    done
  }

  while_loop

  function while_infinity() {
    echo "Press 0 to stop infinity loop"
    while true; do
      echo -n "> "
      read var
      if [ "$var" -eq 0 ]; then
        break
      fi
      echo "$var is not 0 loop again"
    done
  }

  while_infinity

  function for_loop() {
    # print 0-4
    for var in $(seq 0 4); do
      echo $var
    done

    # same base as previous one
    for var in elem1 elem2; do
      echo $var
    done

    # print 0-4
    for ((i=0; i<5; ++i)); do
      echo $i
    done

    # print all the files starting with .bash in $HOME?
    for file in $HOME/.bash*; do
      echo $file
    done

    # print odd number only
    for var in $(seq 0 4); do
      if [ $((var % 2)) -eq 0 ]; then
        continue
      fi
      echo $var
    done
  }

  for_loop

  function until_loop() {
    # print 0-4
    a=0
    until [ $a -ge 5 ]; do
      echo $a
      a=$((a + 1))
    done
  }

  until_loop

}

function func() {
  echo "-- function --"

  # declare
  function hello() {
    # $1: first arg, $2: second arg
    echo "Hello $1 $2"

    # $@: is is all the arguments with individually double quoted
    echo "Arguments: $@"

    # $#: a number of arguments
    echo "Argument number: $#"

    # return 0 if previous command successes
    # but it's good to return 0 explicitly
    return 0
  }

  # call in current shell
  hello test1 test2

  # unset a function named 'hello' 
  unset hello

  # cause error
  # hello test1 test2
}

variable
decision_making
loop
func