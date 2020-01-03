# Markdown

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

## See also

- [markdown chearsheat](https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet)
- [markdown tutorial](https://commonmark.org/help/tutorial/index.html)
