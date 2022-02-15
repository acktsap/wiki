# Productivity Tools

## direnv

https://direnv.net/

load new shell feature for current directory

- enable for current directory
  ```sh
  # Assume .envrc file on current directory
  direnv allow .
  ```
- export alias, function 
  ```
  # usage: export_alias zz "ls -la"
  export_alias() {
    local name=$1
    shift
    local alias_dir=$PWD/.direnv/aliases
    local target="$alias_dir/$name"
    mkdir -p "$alias_dir"
    PATH_add "$alias_dir"
    echo "#!/usr/bin/env bash -e" > "$target"
    echo "$@" >> "$target"
    chmod +x "$target"
  }

  # usage: export_function test
  export_function() {
    local name=$1
    local alias_dir=$PWD/.direnv/aliases
    mkdir -p "$alias_dir"
    PATH_add "$alias_dir"
    local target="$alias_dir/$name"
    if declare -f "$name" >/dev/null; then
      echo "#!/usr/bin/env bash" > "$target"
      declare -f "$name" >> "$target" 2>/dev/null
      echo "$name" >> "$target"
      chmod +x "$target"
    fi
  }
  ```
  - see also: https://github.com/direnv/direnv/issues/73

## fzf

https://github.com/junegunn/fzf

command-lind fuzzy finder

- Basic Usage
  ```sh
  # show all git branches
  git branch | fzf
  ```

## Broot

https://github.com/Canop/broot

more than tree

commands

- only folders: `br -f`

in commands

- preview
  - text: `:preview_text`
  - image: `:preview_image`
  - binary: `:preview_binary`
  - open: `:open_preview` (open proper preview)
  - close: `:close_preview`
- crud
  - create: `:create $some_file`
  - edit: `:e`
  - remove current file: `:rm`
- sort
  - sort by size: `:ss`
  - sort by date: `:sd`
  - sort by count: `:sc`
  - no sort: `:ns`
- movement
  - default page up: `ctrl + u`
  - default page down: `ctrl + d`
- toggle
  - toggle showing size: `:sizes`
  - toggle last modified date : `:dates`
  - toggle number of files in directories: `:counts`
  - toggle file permission: `:perm`
- etc
  - change permission: `:ch xxx`
  - open terminal for current directory: `:terminal`
  - quit : `ctrl + c` or `ctrl  or `ctrl + q`
  - help: `?`
