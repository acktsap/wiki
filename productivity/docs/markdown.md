# Markdown

- [Markdown](#markdown)
  - [1. Header](#1-header)
    - [Title](#title)
    - [Subtitle](#subtitle)
    - [Headers](#headers)
    - [Horizontal line](#horizontal-line)
  - [2. Lists](#2-lists)
    - [Ordered list](#ordered-list)
    - [Unordered list](#unordered-list)
    - [Nested list](#nested-list)
  - [3. Font](#3-font)
    - [Italics](#italics)
    - [Bold](#bold)
    - [Both italics and bold](#both-italics-and-bold)
    - [Cancelline](#cancelline)
    - [Blockquotes](#blockquotes)
  - [4. Etc](#4-etc)
    - [Line ending (ending with 2 spaces)](#line-ending-ending-with-2-spaces)
    - [Inline code](#inline-code)
    - [Code block with syntax highlighting](#code-block-with-syntax-highlighting)
    - [Link](#link)
    - [Image](#image)
    - [Table](#table)
    - [Collapsible](#collapsible)
  - [See also](#see-also)

## 1. Header

### Title

```markdown
This is a title
=============
```

### Subtitle

```markdown
This is an subtitle
-------------
```

### Headers

```markdown
# This is an H1
## This is an H2
### This is an H3
#### This is an H4
##### This is an H5
###### This is an H6
```

### Horizontal line

```markdown
***
* * *
*****
---
- - -
-------
```

-----------------------------------------------------

## 2. Lists

### Ordered list

```markdown
1. First
2. Second
3. Third
```

1. First
2. Second
3. Third

### Unordered list

```markdown
* First
* Second

+ First
+ Second

- First
- Second
```

- First
- Second

### Nested list

4 space for ordered nested list

2 space for unordered nested list

4 space for ordered/unordered mixed list

```markdown
1. First
    1. First.firstChild
    2. First.secondChild

- First
  - First.firstChild
  - First.secondChild

1. First
    - First.firstChild
    - First.secondChild
2. Second
    - Second.firstChild
    - Second.secondChild

- First
    1. First.firstChild
    2. First.secondChild
- Second
    1. Second.firstChild
    2. Second.secondChild
```

1. First
    1. First.firstChild
    2. First.secondChild

- First
  - First.firstChild
  - First.secondChild

1. First
    - First.firstChild
    - First.secondChild
2. Second
    - Second.firstChild
    - Second.secondChild

- First
    1. First.firstChild
    2. First.secondChild
- Second
    1. Second.firstChild
    2. Second.secondChild

-----------------------------------------------------

## 3. Font

### Italics

```markdown
_This is an italics with underscore_
*This is an italics with asterisks*
```

_This is an italics with underscore_  
*This is an italics with asterisks*

### Bold

```markdown
__This is a bold with underscore__
**This is a bold with asterisks**
```

__This is a bold with underscore__  
**This is a bold with asterisks**

### Both italics and bold

```markdown
**_This is both italics and bold_**
```

**_This is both italics and bold_**

### Cancelline

```markdown
~~This is a cancelline~~
```

~~This is a cancelline~~

### Blockquotes

```markdown
> This is blockquotes
```

> This is blockquotes

-----------------------------------------------------

## 4. Etc

### Line ending (ending with 2 spaces)

```markdown
First line  
Second line
```

First line  
Second line

### Inline code

```markdown
`inline code`
```

This is an `inline code`

### Code block with syntax highlighting

```markdown
"```cpp"
...
"```"
```

```cpp
int main() {
  // this is cpp code
}
```

### Link

```markdown
[Google](www.google.com)
```

[Google](www.google.com)

### Image

```markdown
![Alternative text](/path/to/img.jpg)
```

![Alternative text](/path/to/img.jpg)

### Table

```markdown
Header 1 | Header 2
-------- | --------
cell 1   | cell 2
column 1 | column 2
```

Header 1 | Header 2
-------- | --------
cell 1   | cell 2
column 1 | column 2

### Collapsible

<details><summary>Click Me</summary>
<p>

Hidden contents

</p>
</details>

```html
<details><summary>Click Me</summary>
<p>

Hidden contents

</p>
</details>
```

## See also

- [markdown chearsheat](https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet)
- [markdown tutorial](https://commonmark.org/help/tutorial/index.html)
