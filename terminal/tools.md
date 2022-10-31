# Terminal Tools

- [find](#find)
- [Curl](#curl)
- [Date](#date)
- [Sort](#sort)
- [Sftp](#sftp)
- [Sed](#sed)
- [Base64](#base64)

## find

```sh
# find directories from current with max depth 2 except '.git' directory and execute on those.
find . -type d -maxdepth 2 ! -name ".git" -exec direnv allow {} > /dev/null 2>&1 \; 
```

## Curl

```sh
# get
curl -X GET www.naver.com
```

## Date

```sh
# get days 3 days before
date -v -3d

# print today as yyyymmdd
date "+%Y%m%d"
```

## Sort

```sh
cat << EOF >> base.txt
1,3,2
2,1,3
3,2,1
EOF

# csv replace result
#
#   2,1,3
#   3,2,1
#   1,3,2
sort --field-separator=',' --key=2 base.txt
```

## Sftp

```sh
# access
sftp -oPort=8000 user@127.0.0.1

# local ls
lls

# remote ls
ls

# local cd
lcd

# remote cd
cd

# download to local
get some_file.txt

# download to local by wildcard
get *.txt

# put file
put some_file.txt

# put file by wildcard
put *.txt
```

## Sed

```sh
# replacement
echo "target_test" | sed -e "s/target/new/" # print new_test
```

## Base64

```sh
# decode
echo 324324234 | base64 --decode

# encode
echo 324324234 | base64

# encode without newline
echo 324324234 | base64 -w 0
```
