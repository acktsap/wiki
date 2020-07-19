# Intellij settting

## Setting

- Code Style : Editor > Code Style
- Project
  - Open File With Single Click
  - Always Select Opened File
- Keymap: Eclipse
  - Project <-> Editor : Command (or Ctrl) + F7 (need to set in Tool Keymap > Windows > Project)
- Show whitespace : Editor > General > Appearance > Show whitespace
- Disable auto save: System Settings -> Synchronization -> Uncheck second and third one
- JDK Version : File > Project Structure > Project > Project SDK, Project language level
- Build and run using : Build, Execution, Deployment > Build Tools > Gradle > Set 'Build and running', 'Run test using' as Intellij
- Code template
  - Editor > File and Code Templates > Class

    ```java
    /*
     * @copyright defined in LICENSE.txt
     */

    ```
- VM options : $IDEA_HOME/bin/idea.vmoptions
- Wird card import config
  - Normal : File > Settings > Project Settings > Code Style > Java > Imports > General > Class count to use import with '*'
  - Static : File > Settings > Project Settings > Code Style > Java > Imports > General > Names count to use static import with '*'

## Plugins

- IdeaVim
- Relative Line Numbers
- Lombok
- CheckStyle-IDEA

## View

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

## File

- New : Ctrl + N (in project explorer)
- Move
- Remove : Delete

## Edit

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

## Search

- Resources : Command + Shift + R
- Classes : Command + Shift + T
- Class, function
- Go to definition : F3
- Referer : ?
- Call Hierarchy : Command + Alt + H

## Run, Debug

- Run current file : Command + F11
- Debug current file
- Mark break point : Command + Shift + B
- Step into
- Step over
- Resume
