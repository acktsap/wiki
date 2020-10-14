# Chrome

## Setting

동기화 항목

- 앱
- 북마크
- 확장 프로그램

## Run in incognito mode

https://www.techwalla.com/articles/how-to-always-launch-chrome-in-incognito-on-a-mac

Save as Application

```sh
do shell script "open -a Google\\ Chrome --new --args -incognito"
```

## Bookmark

Backup

```sh
# backup
cp "/Users/${USER}/Library/Application Support/Google/Chrome/Default/Bookmarks" ${path_to_backup}

# restore
cp ${path_to_backup} "/Users/${USER}/Library/Application Support/Google/Chrome/Default/Bookmarks"
```

