# Intellij Settting

## Setting

### Project

- Open File With Single Click
- Always Select Opened File

### Apperance & Behavior

- Disable auto save : Apperance & Behavior > System Settings > Autosave
  - [X] Save fies if the IDE is idle for xx seconds
  - [X] Save files when switching to a different application
  - [ ] Backup files before saving
  - [X] Synchronize external changes when switching to the IDE windows or opening an editor tab

### Keymap

Eclipse

- Project <-> Editor : Command (or Ctrl) + F7 (need to set in Tool Keymap > Windows > Project)

Custom

- Go to Implementations : Ctrl + i
- Go to Test : Ctrl + t

### Editor

- Show whitespace : Editor > General > Appearance > Show whitespace
- Code template : Editor > File and Code Templates > Class
  ```java
  /*
   * @copyright defined in LICENSE.txt
   */

  ```
- Disable Quick Documentation by Mouse : Editor > Code Editing > Quick Documentation > Uncheck Show quick documentation on mouse move
- Code Style : Editor > Code Style
- Java wird card import : Editor > Code Style > Java > Imports > General
  - Normal : Class count to use import with '*'
  - Static : Names count to use static import with '*'
### Build, Execution, Deployment

- Build and run using intellij : Build Tools > Gradle > Set 'Build and running', 'Run test using' as Intellij
  - Need rebuild (build -> rebuild)

## Plugins

- Ktlint
- IdeaVim
- Relative Line Numbers -> `:set rnu` via IdeaVim plugin
- CheckStyle-IDEA
- [Save actions](https://plugins.jetbrains.com/plugin/7642-save-actions/versions)
  - Settings > Other settings > Save actions
    - General
      - [X] Activate save actions on save
    - Formatting Actions
      - [X] Optimize imports
      - [X] Reformat file
- Bundled
  - Kotlin
  - Lombok

## Etc

### JDK Version

File > Project Structure > Project > Project SDK, Project language level

### VM options

Help -> Edit Custom VM Options (or shift + shift -> enter custom vm options)

```sh
-Xms2g
-Xmx2g
...
-
```

## Shortcuts

Use eclispe keymap.

### View

- Maximize / unmaximize
- Closing
  - Close : Ctrl + W
  - Close All : Ctrl + Shift + W
- Navigating
  - View : Ctrl + F7
  - Method list of current file : Command + O
  - Show document : F2
  - Go to definition : F3
  - Show hierarchy of class : F4

### File

- New : Ctrl + N (in project explorer)
- Move
- Remove : Delete

### Edit

- Vim
  - Insert, copy and paste, delete of
    - Char
    - Word
    - Line
    - Block
- Refactor
  - Rename : Command + Shift + R
  - Extract variable : Command + Shift + L
  - Extract method : Command + Shift + M

### Search

- Resources : Command + Shift + R
- Classes : Command + Shift + T
- Class, function
- Go to definition : F3
- Referer : ?
- Call Hierarchy : Command + Alt + H
- Find in path : Ctrl + H

### Run, Debug

- Run current file : Command + F11
- Debug current file
- Mark break point : Command + Shift + B
- Step into
- Step over
- Resume

