# Brave Setting

## Download

https://brave.com

## Extensions

- Dark Reader
  - Drak
    - Brightness : off
    - Contrast : -10
    - Sepia : +35
    - Grayscale : off
- LastPass
- Google Translate
  - Extension Options
    - My primary language: Korean
- Octotree : GitHub code tree

## Settings

### Securiy and Privacy

- [ ] Allow privacy-preserving product analytics (P3A)
- [ ] Automatically send daily usage ping to Brave
- Clear browsing data -> On exit
  - Check except
    - Cached images and files
    - Hosted app data

### Search engine

- Other search engines
  - DuckDuckGo (Default)
    - :d, :ㅇ
    - https://duckduckgo.com/?q=%s
  - Google
    - :g, :ㅎ
    - https://www.google.com/search?q=%s
  - Brave
    - :b, :ㅠ
    - https://search.brave.com/search?q=%s
  - Naver
    - :n, :ㅜ
    - https://search.naver.com/search.naver?sm=top_hty&fbm=1&ie=utf8&query=%s
  - YouTube
    - :y, :ㅛ
    - https://www.youtube.com/results?search_query=%s
  - Dict
    - :c, :ㅊ
    - https://en.dict.naver.com/#/search?query=%s


## Additional Settings

### Autofill

- [ ] Offer to save passwords
 
## Bookmark

Backup

```sh
cp "/Users/${USER}/Library/Application Support/BraveSoftware/Brave-Browser/Default/Bookmarks" ${path_to_backup}
```
Restore

```sh
cp ${path_to_backup} "/Users/${USER}/Library/Application Support/BraveSoftware/Brave-Browser/Default/Bookmarks"
```

