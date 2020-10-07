# Git Tips

## Commit message

https://blog.ull.im/engineering/2019/03/10/logs-on-git.html

## Before making pull request

- rebase해서 최신 commit하고 맞추셈
- 이왕이면 squash해서 하나의 commit으로 하는게 좋음

## Pull Request

```sh
git fetch origin pull/ID/head:MY_BRANCHNAME
ex) git fetch origin pull/385/head:llvm3.8-Support
    -> pull request of ID #385 will be fetched to llvm3.8-Support
```

github에서 pull request에 commit추가하려면 그냥 그 브랜치에 커밋 추가 하면 됨. 남의 것도 그 repository 받아서 추가하면 됨

## checkout to the remote branch

```sh
git checkout -b feature/abc origin/feature/abc
```

## discard change

```sh
git checkout path/to/the/file.txt
```

## remove untracked files

```sh
git clean -id .
git clean -n . (show what would be done)
```

## .gitignore renew

```sh
git rm -r --cached .
git add .
git commit -m "fixed untracked files"
```

## git rebase

1. git rebase -i @~2
2. enter command
    pick 9e67594 commit 1
    pick e5af3cb commit 2 -> squash e5af3cb commit 2
3. enter commit message

    ```sh
    # This is a combination of 2 commits.
    # The first commit message is:

    commit 1

    # This is the 2nd commit message:

    commit 2
    ```

## User.name, user.email

user.name, user.email should be setted

```sh
git config --global user.name Taeik Lim
git config --global user.email sibera21@gmail.com
```

## Sparse checkout

```sh
# init
git init ${SOME_PROJECT}
cd ${SOME_PROJECT}

# config sparse checkout
git config core.sparseCheckout true

# add remote
git remote add -f origin <REMOTE_URL>

# set sparse target into git/info/sparse-checkout
echo "script/sys-script" >> .git/info/sparse-checkout
echo "script/user-script/user1" >> .git/info/sparse-checkout

# pull
git pull origin master
```

## Reference

Sparse checkout

https://www.lesstif.com/gitbook/git-clone-20776761.html
