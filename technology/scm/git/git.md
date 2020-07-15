# Git

- [Git](#git)
  - [Basic](#basic)
  - [Repository handling](#repository-handling)
    - [clone](#clone)
    - [init](#init)
  - [Manipulating change](#manipulating-change)
    - [add](#add)
    - [mv](#mv)
    - [reset](#reset)
    - [rm](#rm)
  - [Examination](#examination)
    - [bisect](#bisect)
    - [grep](#grep)
    - [log](#log)
    - [show](#show)
    - [status](#status)
  - [Marking change](#marking-change)
    - [branch](#branch)
    - [checkout](#checkout)
    - [commit](#commit)
    - [diff](#diff)
    - [merge](#merge)
    - [rebase](#rebase)
    - [tag](#tag)
  - [Collaboration](#collaboration)
    - [fetch](#fetch)
    - [pull](#pull)
    - [push](#push)
  - [Config](#config)
  - [Practice](#practice)
  - [Tips](#tips)
    - [Pull Request](#pull-request)
    - [checkout to the remote branch](#checkout-to-the-remote-branch)
    - [discard change](#discard-change)
    - [remove untrackted files](#remove-untrackted-files)
    - [.gitignore renew](#gitignore-renew)
    - [git rebase](#git-rebase)
    - [User.name, user.email](#username-useremail)

## Basic

Git is just a combination of fetch & merge.

Consist of 3 part

- HEAD (`@`) : A snapshot of last commit
- Index : A snapshot of next commit
- Working directory : A sandbox

## Repository handling

### clone

Clone a repository into a new directory.

```sh
git clone [-b BRANCH_NAME] REMOTE_REPO [DIRECTORY_NAME]
```

```sh
# clone and indicating default head
git clone https://github.com/aergoio/heraj

# clone single branch
git clone -b feature/abi --single-branch https://github.com/aergoio/heraj

# clone all the branch and checkout to feature/abc
git clone -b feature/abi https://github.com/aergoio/heraj
```

### init

Create an empty Git repository or reinitialize an existing one.

```sh
git init [DIRECTORY_NAME]. if ∃ DIRECTORY_NAME -> init on DIRECTORY_NAME
```

```sh
# init on currnet directory
git init

# init on directory 'test'
git init test
```

## Manipulating change

### add

Add file contents to the index.

```sh
git add [--verbose | -v] [--dry-run | -n] [--force | -f] [--interactive | -i] FILE_PATH
```

```sh
# add file 'test.lua'
git add fuck.lua

# add file of current directory recursively
git add .

# add file of directory 'test' recursively
git add test
```

### mv

Move or rename a file, a directory, or a symlink. Git cannot recognize the renaming of a file directly.

```sh
git mv [--verbose | -v] [--dry-run | -n] [--force | -f] SOURCE DESTINATION
```

```sh
# rename already staged file 'test.lua' to 'test2.lua'
git mv test.lua test2.lua
```

### reset

Reset current HEAD to the specified state.

```sh
git reset [--soft | --mixed | --hard] @~N (N : the number of previous) [ --mixed : default option ]
git reset [--mixed | --hard] @~N FILE_PATH (N : the number of previous) [ --mixed : default option ]
```

```sh
# reset current head to previous 2 commit
git reset --soft @~2

# reset current head and update index to previous 2 commit
git reset @~2

# reset current head and update index & wd to previous 2 commit
# WARNING : this is very dangerous
git reset --hard @~2

# reset current index to HEAD version from the current directory recursively
# this is reverse operation of 'git add .'
git reset @ .

# reset current index & wd to HEAD version from the current directory recursively
# WARNING : this is very dangerous
git reset --hard @ .
```

### rm

Remove files from the working tree and from the index.

```sh
git rm [-f | --force] [-n] [-r] FILE_PATH
```

```sh
# remove already tracked file 'test.lua'
git rm test.lua
```

## Examination

### bisect

Use binary search to find the commit that introduced a bug.

```sh
# TODO
```

### grep

Look for specified patterns in the tracked files in the work tree.

```sh
git grep [options] PATTERN
```

```sh
# find pattern 'function' in the tracked files
git grep function
```

### log

Show commit logs.

```sh
git log [options] [FILE_PATH]
```

```sh
# show log of current directory
git log

# show log of 'test.lua'
git log test.lua
```

### show

Show various types of objects.

### status

Show the working tree status.

## Marking change

### branch

List, create, or delete branches.

### checkout

Switch branches or restore working tree files.

### commit

Record changes to the repository.

### diff

Show changes between commits, commit and working tree, etc.

### merge

Join two or more development histories together.

### rebase

Reapply commits on top of another base tip.

### tag

Create, list, delete or verify a tag object signed with GPG.

## Collaboration

### fetch

Download objects and refs from another repository.

### pull

Fetch from and integrate with another repository or a local branch.

### push

Update remote refs along with associated objects.

## Config

Configure `.gitconfig` file

```sh
git config [--global] [--get] [-l, --list] [--unset]
```

```sh
# set user.name locally
git config user.name myname

# set user.name globally
git config --global user.name myname

# get user.name config
git config --get user.name

# list all the config
git config --list

# unset user.name
git config --unset user.name
```

## Practice

Questions

```sh
1. Make a git repository in the current folder. What is created?
2. Make an new file "new.txt". Is is tracked? How to check it?
3. Make it tracked[3 ways. just by file name, file extension, all file]?
4. Now, type git status. is the new file tracked? staged?
5. Make an new file "ignore.log" file. Make it ignored to add command.
6. Commit the current staged files[2 ways. by vim, just by commit command]?
7. How to check a commit log?
8. Make a new branch NewBranch, TrashBranch.
9. List all the branches.
10. Remove branch TrashBranch.
11. Checkout to the NewBranch.
12. Make a new file branchNew.txt & commit it with message "branch commit"
13. Checkout to master[basic branch]. Is the commit "branch commit" applied?
14. Apply changes in the branch NewBranch to the master.
```

Answers

```sh
1. Make a git repository in the current folder. What is created?
  -> git init; .git/ file is created. Which store a lot of git informations.

2. Make an new file "new.txt". Is is tracked? How to check it?
  -> No; git status

3. Make it tracked[3 ways. just by file name, file extension, all file]?
  -> 1) git add new.txt; 2) git add *.txt; 3) git add .

4. Now, type git status. Is the new file tracked? staged?
  -> Yes; Yes

5. Make an new file "ignore.log" file. Make it ignored to add command.
  -> touch .gitignore; add "*.log"

6. Commit the current staged files[2 ways. by vim, just by commit command]?
  -> 1) git commit; type commit message; :wq; 2) git commit -m "my_message"

7. How to check a commit log?
  -> git log

8. Make a new branch NewBranch
  -> git branch NewBranch

9. Check the branch status
  -> git branch -a

10. Remove branch TrashBranch.
  -> git branch -d TrashBranch

11. Checkout to the NewBranch.
  -> git checkout NewBranch

12. Make a new file branchNew.txt & commit it with message "branch commit"
  -> git commit -m "branch commit"

13. Checkout to master[basic branch]. Is the commit "branch commit" applied?
  -> git checkout master; No

14. Apply changes in the branch NewBranch to the master.
  -> git merge NewBranch
```

## Tips

### Pull Request

```sh
git fetch origin pull/ID/head:MY_BRANCHNAME
ex) git fetch origin pull/385/head:llvm3.8-Support
    -> pull request of ID #385 will be fetched to llvm3.8-Support
```

github에서 pull request에 commit추가하려면 그냥 그 브랜치에 커밋 추가 하면 됨. 남의 것도 그 repository 받아서 추가하면 됨

### checkout to the remote branch

```sh
git checkout -b feature/abc origin/feature/abc
```

### discard change

```sh
git checkout path/to/the/file.txt
```

### remove untrackted files

```sh
git clean -id .
git clean -n . (show what would be done)
```

### .gitignore renew

```sh
git rm -r --cached .
git add .
git commit -m "fixed untracked files"
```

### git rebase

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

### User.name, user.email

user.name, user.email should be setted

```sh
git config --global user.name Taeik Lim
git config --global user.email sibera21@gmail.com
```

### Sparse checkout

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
