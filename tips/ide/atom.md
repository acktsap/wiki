# Atom

## Package management

* Install : `cmd + ,` & go to the install tab

### Show startup time for packages

`Packages > Timecop > Show`

### Terminal

* Install : `apm install ${package_name}`
* Search  : `apm search ${package_name}`
* View    : `apm view ${package_name}`

## Editor

### Vim

* Install `vim-mode-plus` and `ex-mode`

### Navigating symbols

* Find a definition : `cmd + R`
* List all symbols  : `cmd + T`

### Bookmark

* Make a bookmark on current line : `cmd + f2`
* Move to the next bookmark       : `f2`
* Move to the previous bookmark   : `shirt + f2`
* List all the bookmarks          : `ctrl + f2`

### Folding

* Fold       : `alt + cmd + [`
* Unfold     : `alt + cmd + ]`
* Fold all   : `alt + cmd + shift + [`
* Unfold all : `alt + cmd + shift + ]`

### Grammar

Automatically detected. On malfunction, use `ctrl + shirt + l`

### Etc

* Show command        : `cmd + shift + p`
* Markdown preview    : `ctrl + shift + m`
* Check current scope : `alt + cmd + p`

## Snippets

* Show all available : `command : Snippets: Available`
* Apply              : `enter or tab`
* Custom             : `~/.atom/snippets.cson`

```json
'.source.lua':
  # snippets starting with 'for'
  # previx  : trigger. here, 'for'
  # body    : insert snippet
  # $number : tab stop
  'for i,v in ipairs()':
    'prefix': 'fori'
    'body': 'for ${1:i},${2:v} in ipairs(${3:table_name}) do\n\t${0:-- body...}\nend'
  'for i=1,10':
    'prefix': 'for'
    'body': 'for ${1:i}=${2:1},${3:10} do\n\t${0:-- body...}\nend'
  'for k,v in pairs()':
    'prefix': 'forp'
    'body': 'for ${1:k},${2:v} in pairs(${3:table_name}) do\n\t${0:-- body...}\nend'
```

### Markdown snippets

* table, img

## Customization

* Stylesheet    : `~/.atom/styles.less`
* KeyBinding    : `~/.atom/keymap.cson`
* Global config : `~/.atom/config.cson`

```json
# language specific setting on config.cson
# here, '.source.xxx' is a 'scope' name

'*': # all languages unless overridden
  'editor':
    'softWrap': false
    'tabLength': 8

'.source.gfm': # markdown overrides
  'editor':
    'softWrap': true

'.source.ruby': # ruby overrides
  'editor':
    'tabLength': 2

'.source.python': # python overrides
  'editor':
    'tabLength': 4
```

## UI Tips

- Styleguide : `ctrl + cmd + shift + g` => insert with custom : check content value of icon in `before`
  - Wrap as component and follow it (apply atomic design)
- Components overview : [link](https://blog.eleven-labs.com/en/create-atom-package/)
- Backspace not work in text area : add `native-key-bindings` class to tag
- Custom text copy not work : add `native-key-bindings` class and set attribute `tabindex: -1`
- Image resources : `atom://<package name>/<location>/<file.ext>`

## ETc

Electron Dialog

https://www.brainbell.com/javascript/show-save-dialog.html

Read file from input tag

https://velog.io/@jeong3320/React-input%ED%83%9C%EA%B7%B8%EB%82%B4%EC%97%90%EC%84%9C-file%EC%9D%BD%EA%B8%B0
