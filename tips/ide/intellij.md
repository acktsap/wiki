# Intellij Settting

## Setting

### Code Style

Editor > Code Style

### Project

- Open File With Single Click
- Always Select Opened File

### Keymap

Eclipse

- Project <-> Editor : Command (or Ctrl) + F7 (need to set in Tool Keymap > Windows > Project)

Custsom

- Go to Implementations : Ctrl + i
- Go to Test : Ctrl + t

### Show whitespace

- Editor > General > Appearance > Show whitespace

### Apperance & Behavior

- Disable auto save : System Settings -> Autosave -> Uncheck all
  - [X] Save fies if the IDE is idle for xx seconds
  - [X] Save files when switching to a different application
  - [ ] Backup files before saving
  - [X] Synchronize external changes when switching to the IDE windows or opening an editor tab

### JDK Version

File > Project Structure > Project > Project SDK, Project language level

### Build and run using

Build, Execution, Deployment > Build Tools > Gradle > Set 'Build and running', 'Run test using' as Intellij

Need rebuild (build -> rebuild)

### Code template

Editor > File and Code Templates > Class

```java
/*
 * @copyright defined in LICENSE.txt
 */

```

### Disable Quick Documentation by Mouse

Editor > Code Editing > Quick Documentation > Uncheck Show quick documentation on mouse move

### VM options

Help -> Edit Custom VM Options (or shift + shift -> enter custom vm options)

```sh
-Xms2g
-Xmx2g
...
-
```

### Wird card import config

- Normal : File > Settings > Project Settings > Code Style > Java > Imports > General > Class count to use import with '*'
- Static : File > Settings > Project Settings > Code Style > Java > Imports > General > Names count to use static import with '*'

## Plugins

- IdeaVim
- Relative Line Numbers -> `:set rnu` via IdeaVim plugin
- Lombok
- CheckStyle-IDEA
- [Save actions](https://plugins.jetbrains.com/plugin/7642-save-actions/versions)
  - Settings > Other settings > Save actions
    - General
      - [X] Activate save actions on save
    - Formatting Actions
      - [X] Optimize imports
      - [X] Reformat file

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

## Tips

### Heap size

Change `-Xms, -Xmx` in `/Users/$USER/Library/Application Support/JetBrains/IdeaIC2020.1/idea.vmoptions`

