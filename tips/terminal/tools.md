# Terminal Tools

## direnv

### Pattern

```sh
# Assume .envrc file on current directory
> direnv allow .
```

## find

```sh
# find directories from current with max depth 2 except '.git' directory and execute on those.
find . -type d -maxdepth 2 ! -name ".git" -exec direnv allow {} > /dev/null 2>&1 \; 
```

## Openssl

```sh
# sha256 digest input.txt file
openssl dgst -sha256 input.txt

# encrypt input.txt file with aes-256-cbc
openssl aes-256-cbc -a -in input.txt

# encrypt input.txt file with aes-256-cbc with salt
openssl aes-256-cbc -a -salt -in input.txt

# decrypt input.txt file with aes-256-cbc
openssl aes-256-cbc -a -d -in input.txt
```

## Curl

```sh
```

## Date


```sh
# get days 3 days before
date -v -3d

# print today as yyyymmdd
date "+%Y%m%d"
```
