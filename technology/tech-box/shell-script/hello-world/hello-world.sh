#!/bin/bash
# previous line : shebang, alert the system that shell script is being started

<< COMMENT

## Intention

An interface to the Unix System.

There are a lot of shell script.  
We focus on posix shell script for portability.  
And there may be a little bash script syntax sugar for convenience

see also

http://pubs.opengroup.org/onlinepubs/9699919799/
https://pubs.opengroup.org/onlinepubs/9699919799.2018edition/utilities/V3_chap02.html

## Paradigm

- Imperative

## Feature

- Interaction with linux/unit commands
- Interpreted

There are a lot of shell. We only focus on posix shell standard

## Install

Automatically installed on most of the unix/linux based operating system.

## Execute

Change mode to run

> chmod +x hello.sh

Run on same process

> ./hello.sh
> . hello.sh

Run by forking

> ./hello.sh &

## Debug

Show all the command

#!/bin/bash -x
  or
set -x

Exit on failure

#!/bin/bash -e
  or
set -e

Show all the command and exit on failure

#!/bin/bash -xe
  or
set -xe

COMMENT

# print hello world
echo "Hello world"