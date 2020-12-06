# Git Hook

## Config

`.git/hooks`에 보면 git이 sample로 넣어준 hooks들이 있음

git 2.29 버전부터 다음의 명령어로 githook directory를 설정가능해서 repository에 `.githooks` directory를 만들어서 설정해주면 좋음

`git config core.hookspath .githooks`

## Commit

### pre-commit

commit 전에. lint같은거를 실행

```sh
#!/bin/sh
#
# Called by "git commit" with no arguments.  The hook should
# exit with non-zero status after issuing an appropriate message if
# it wants to stop the commit.
#
# To enable this hook, rename this file to "pre-commit".

./gradlew clean check
```

### prepare-commit-msg

commit message 만들기 전에. commit mesasge에 prefix같은거 붙임

```sh
#!/bin/sh
#
# Called by "git commit" with the name of the file that has the
# commit message, followed by the description of the commit
# message's source.  The hook's purpose is to edit the commit
# message file.  If the hook fails with a non-zero status,
# the commit is aborted.

COMMIT_MSG_FILE=$1
COMMIT_SOURCE=$2
SHA1=$3  # if amend

# not for amend
if [[ ! -z ${SHA1} ]]; then
  exit 0
fi

RECENT_MESSAGE=$(git log -1 --format=%s)

# parse commit message starting with "[#"
PREFIX=$(echo $RECENT_MESSAGE | grep "^\[\#" | cut -d" " -f1)

if [[ ! -z ${PREFIX} ]]; then
  echo "$PREFIX $(cat $COMMIT_MSG_FILE)" > $COMMIT_MSG_FILE
fi
```

### commit-msg

commit message를 작성 후 commit 직전에. commit message 검증같은거 함.

```sh
#!/bin/sh
#
# Called by "git commit" with one argument, the name of the file
# that has the commit message.  The hook should exit with non-zero
# status after issuing an appropriate message if it wants to stop the
# commit.  The hook is allowed to edit the commit message file.

# commit message file
MSG_FILE=$1

COMMIT_MSG="$(cat $MSG_FILE)"

# Initialize constants here
REGEX='^(\[#[0-9]+\] [a-zA-Z1-9]+|Merge)'
MSG_FORMAT="\"[#issue_no] message\""

if [[ $COMMIT_MSG =~ $REGEX ]]; then
  exit 0
else
  echo "Bad commit message (expected: $MSG_FORMAT)"
  echo "\n$COMMIT_MSG\n"
  exit 1
fi
```

post-commit : commit이 완료된 후. 알리는 용도로 사용.

```sh
#!/bin/sh

GIT_HASH=$(git rev-parse HEAD)

echo "$GIT_HASH\n"
```

## Etc

이외에도 `.git/hooks`에 보면 다른 hook들이 많음. 필요 시 참고할 것.

## References

https://git-scm.com/book/ko/v2/Git%EB%A7%9E%EC%B6%A4-Git-Hooks