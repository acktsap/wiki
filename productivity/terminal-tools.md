# Tools

## direnv

### Pattern

```sh
# Assume .envrc file on current directory
> direnv allow .
```

## find

### Pattern

Find directories from current with max depth 2 except '.git' directory and execute on those.

```sh
find . -type d -maxdepth 2 ! -name ".git" -exec direnv allow {} > /dev/null 2>&1 \; 
```