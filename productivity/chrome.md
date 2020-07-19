# Chrome

## Setting

동기화 항목

- 앱
- 북마크
- 확장 프로그램

## Bookmark

Backup

```sh
# backup
cp "/Users/${USER}/Library/Application Support/Google/Chrome/Default/Bookmarks" ${path_to_backup}

# restore
cp ${path_to_backup} "/Users/${USER}/Library/Application Support/Google/Chrome/Default/Bookmarks"
```

