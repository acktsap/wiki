#!/bin/bash

function variable_substitution() {
  echo "-- variable_substitution --"

  local val=1

  # command substitution
  val=`expr $val + 1`
  val=$(expr $val + 1)

  # print $origin wow
  echo '$val wow it is not replaced'

  # print some_value wow
  echo "$val wow it's replaced"

  function substitution_case() {
    echo "--- Try with $@ ---"

    local hostname="$1"

    # show warning when alias isn't set
    local alias=${2:?"Alias must be provided"}

    echo "Origin hostname: $hostname"
    echo "Origin alias: $alias"

    # substitute for hostname as 'localhost' when hostname is unset
    # no change to hostname
    echo "Processed hostname: ${hostname:-${hostname}}"

    # substitute for alias as 'forced_alias' when alias is set
    # no change to alias
    echo "Processed alias: ${alias:+"forced_alias"}"

    echo "Dirty hostname: $hostname"
    echo "Dirty alias: $alias"

    echo
  }

  # print 192.168.0.1, forced_alias
  substitution_case "192.168.0.1" my_alias

  # print localhost, forced_alias
  substitution_case "" my_alias

  # print warning
  substitution_case "192.168.0.1"
}

variable_substitution