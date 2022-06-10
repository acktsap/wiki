# VIM Basic Usage

- [Movement [direction]](#movement-direction)
    - [By character](#by-character)
  - [By word](#by-word)
  - [By line](#by-line)
  - [By sentence](#by-sentence)
  - [By paragraph](#by-paragraph)
  - [By screen](#by-screen)
  - [By page](#by-page)
- [Edit [starts from the cursor]](#edit-starts-from-the-cursor)
  - [Basic form](#basic-form)
  - [Delete](#delete)
  - [Change](#change)
  - [Copy [yank]](#copy-yank)
  - [Undo, Redo](#undo-redo)
  - [Replacement](#replacement)
  - [Etc](#etc)
- [Vim mode](#vim-mode)
  - [Visual](#visual)
  - [Shell prompt type](#shell-prompt-type)
- [File management](#file-management)
- [Split](#split)
- [Etc](#etc-1)
- [Vim Setting](#vim-setting)

![vim_cheat_sheet_for_programmers](./img/vim_cheat_sheet_for_programmers.png)

## Movement [direction]

#### By character

- left, down, up, right : `h, j, k, l`

### By word

- to the next start of the word : `w`  // forWord
- to the 3rd next start of the word : `3w`
- to the next end of the word : `e`    // end
- to the 2nd next end of the word : `2e`
- to the back of the previous word : `b`    // back
- to the back of the 3rd previous word : `3b`
- to the start of the line(1st column) : `0`
- to the start of the line(1st non-blank) : `^`
- to the end of the line : `$`

### By line

- to the line before : `-`
- to the line after : `+`
- to the first-line : `gg` or `ctrl + home`
- to the end-line : `G` or `ctrl + end`
- to the n-th line : `n + G`
- to the n-th forward / back relative to current line : `n + '+/-'`

### By sentence

- to the sentence before : `(`
- to the sentence next : `)`

### By paragraph

- to the paragraph before : `{`
- to the paragraph next : `}`

### By screen

- to the start of the screen: `H`
- to the end of the screen: `L`

### By page

- to the page after : `ctrl + f`
- to the page before : `ctrl + b`


## Edit [starts from the cursor]

### Basic form

```sh
operator    [number]    motion
    operator : what to do?
    [number] : what times?
    motion   : what direction?
```

### Delete

- delete one character : `x`
- delete operator : `d`
- delete to the next start of the word : `dw`
- delete to the next end of the word : `de`
- delete to the start of the line(1st column) : `d0`
- delete to the start of the line(1st non-blank) : `d^`
- delete to the end of the line : `d$`
- delete current line : `dd`
- delete 3 line including the current line : `3dd`

### Change

- change operator : `c`
- change to the next start of the word : `cw`
- change to the next end of the word : `ce`
- change to the start of the line(1st column) : `c0`
- change to the start of the line(1st non-blank) : `c^`
- change to the end of the line : `c$``

### Copy [yank]

- copy operator : `y`
- copy to the next start of the word : `yw`
- copy to the next end of the word : `ye`
- copy current line : `yy`
- copy 5 lines : `5yy`
- paste after : `p`
- paste before : `P`

### Undo, Redo

- undo previous action : `u`
- undo all the changes on a line : `U`
- redo : `ctrl + r`

### Replacement

- replace on all line : `:%s/old/new/g`
- replace on 5 ~ 14 line `:5,14s/old/new/g`

### Etc

- upper <-> lower case : (to the blocked one) `~`
- auto formatting : (to the blocked one) `=`
- join line (current + next) : `J`
- repeat : `.`
- indent : `>`
- un-indent : `<`


## Vim mode

### Visual

- go to the visual mode : `v`
- command mode on the blocked one : `v + select block + ':'`

### Shell prompt type

- insert before the cursor : `i`    // <-
- insert from the start of the line : `I`   // <-
- insert after the cursor : `a`     // ->
- insert from the end of the line : `A`     // ->
- insert from the next line : `o`
- insert from the above line : `O`
- substitute the current character : `s`
- substitute the current line : `S`
- to normal mode : `<ESC>` or `Ctrl + [` or `Ctrl + c`
- to normal mode and back to insert mode just after one move : `Ctrl + o`


## File management

- write : `:w`
- quit : `:q`
- write & quit : `:wq`
- trash all changes : `:q!`


## Split

- split[horitonzally] : `:sp`
- vertical split : `:vs`
- vertical split with file : `:vs new_file.cpp`
- move by order : `ctrl + w + w`
- move by direction : `ctrl + w + direction[hjkl]`

## Etc

- present working directory : `:pwd`
- linux function manual : `shift + k`
- run shell command :! ${command}

## Vim Setting

http://blog.joncairns.com/2014/03/using-vim-as-a-multi-language-ide/
