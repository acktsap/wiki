# Intellij Settting

## Setting

### Project

- Open File With Single Click
- Always Select Opened File

### Apperance & Behavior

- Disable auto save : Apperance & Behavior > System Settings > Autosave
  - [X] Save fies if the IDE is idle for 15 seconds
  - [X] Save files when switching to a different application
  - [ ] Backup files before saving
  - [X] Synchronize external changes when switching to the IDE windows or opening an editor tab

### Keymap

Eclipse (macOS)

- Project <-> Editor : Command (or Ctrl) + F7 (need to set in Tool Keymap > Windows > Project)

Custom

- Go to Implementations : Ctrl + i
- Go to Test : Ctrl + t
- Rename : Command + option + r

### Editor

- Editor
  - General
    - Apperance
      - Show whitespace
  - Code Editing
    - Quick Documentation
      - [ ] Show quick documentation on hover
  - Code Style
    - Java
      - Imports
        - General
          - Class count to use import with '*' : 99
          - Names count to use static import with '*' : 99
    - Schema : ${project_specific_setting}
  - File and Code Templates
    - Class : ${your_setting}
      - eg.
        ```java
        /*
        * @copyright defined in LICENSE.txt
        */
        ```

### Build, Execution, Deployment

- Build Tools
  - Gradle 
    - Gradle projects
      - Build and running : Intellij IDEA
      - Run test using : Intellij IDEA 

## Plugins

- Ktlint
- IdeaVim
- Relative Line Numbers -> `:set rnu` via IdeaVim plugin
- CheckStyle-IDEA
- [Save actions](https://plugins.jetbrains.com/plugin/7642-save-actions/versions)
  - [X] Reformat code
  - [X] Optimize imports
- Infinitest
- Bundled
  - Kotlin
  - Lombok

## Etc

### JDK Version

File > Project Structure > Project > Project SDK, Project language level

### VM options

Help -> Edit Custom VM Options (or shift + shift -> enter custom vm options)

```sh
-Xms4G
-Xmx4G
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
  - Introduce variable : Command + Shift + L
  - Extract method : Command + Shift + M

### Search

- Resources : Command + Shift + R
- Classes : Command + Shift + T
- Class, function
- Go to definition : F3
- Referer : ?
- Call Hierarchy : Command + Alt + H
- Find in files : Ctrl + H

### Run, Debug

- Run current file : Command + F11
- Debug current file
- Mark break point : Command + Shift + B
- Step into
- Step over
- Resume

