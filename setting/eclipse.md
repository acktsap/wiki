# Eclipse setting

## Instruction

- Formatter
  - Preference -> Java -> Code style -> Formatter [google style guide github](https://github.com/google/styleguide)
  - Usage : command + shift + f (osx) or alt + shift + f (windows)
  - Customizing
    - Line Wrapping -> Never join already wrapped lines
- Code template
  - Copyright comment new file : Preference -> Java -> Code Templates -> Comments -> Files & Check automatically add comments

    ```java
    /*
     * @copyright defined in LICENSE.txt
     */

    ```

- Static import
  - Java -> Editor -> Content Assist -> Favorites

    ```java
    java.util.UUID.*        // randomUUID()
    java.util.Objects.*     // requireNonNull
    org.slf4j.LoggerFactory.* // logback
    ```

- Highlight Current Variable : Java -> Editor -> Mark occurrences
- Font size : General -> Appearance -> Colors and Fonts -> Java
- Import order : Java -> Code Style -> Organized Imports
- Checkstyle warning color : General -> Editors -> Text Editors -> Annotations -> Checkstyle warning

## Plugins

- Vim
- Relative Line Number Ruler
- Infinitest
- [Lombok](https://projectlombok.org/download)
- CheckStyle

## View

- Maximize / unmaximize
  - Ctrl + M
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

