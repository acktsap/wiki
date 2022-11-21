# Shell Script Run

- [Run itself (same process)](#run-itself-same-process)
- [Run in another process](#run-in-another-process)

## Run itself (same process)

Run in current shell (if current shell is zsh, run in zsh)

```sh
. ./test.sh
```

- `.` : runs a shell script in the current environment and then returns.

## Run in another process

define test.sh

```sh
#!/bin/bash

echo "abc
```

Run (run in `/bin/bash`)

```sh
./test.sh
```

- `#`이 없이 `./test.sh`를 하면 `/bin/bash`자체가 수행됨. 근데 `#!`로 시작하면 아래 친구들을 `/bin/bash`에 넘겨서 수행하라고 함.
- `#!` 이거는 특수한 주석이라고 보면 됨.