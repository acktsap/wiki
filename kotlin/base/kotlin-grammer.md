# Kotlin Grammer

- [Notation](#notation)
- [Root](#root)
- [declaration](#declaration)
- [Bodys](#bodys)
  - [classMemberDeclarations](#classmemberdeclarations)
- [block](#block)
  - [Label](#label)
  - [annotation](#annotation)
  - [assignment](#assignment)
  - [loopStatement](#loopstatement)
  - [expression](#expression)
- [See also](#see-also)

## Notation

https://kotlinlang.org/spec/syntax-and-grammar.html#notation

## Root

https://kotlinlang.org/spec/syntax-and-grammar.html#syntax-grammar

- Kotlinfile
  - packageHeader
  - importList
    - {importHeader}
  - {topLevelObject}
    - [declaration](#declaration)
- KotlinScript
  - todo

## declaration

https://kotlinlang.org/spec/syntax-and-grammar.html#grammar-rule-declaration

- [classDeclaration](https://kotlinlang.org/spec/syntax-and-grammar.html#grammar-rule-classDeclaration)
  - ...
  - classBody | enumClassBody
- [objectDeclaration](https://kotlinlang.org/spec/syntax-and-grammar.html#grammar-rule-objectDeclaration)
  - ...
  - classBody
- [functionDeclaration](https://kotlinlang.org/spec/syntax-and-grammar.html#grammar-rule-functionDeclaration)
  - ...
  - functionBody
- [propertyDeclaration](https://kotlinlang.org/spec/syntax-and-grammar.html#grammar-rule-propertyDeclaration)
- [typeAlias](https://kotlinlang.org/spec/syntax-and-grammar.html#grammar-rule-typeAlias)

## Bodys

- [classBody](https://kotlinlang.org/spec/syntax-and-grammar.html#grammar-rule-classBody)
  - classMemberDeclarations
- [enumClassBody](https://kotlinlang.org/spec/syntax-and-grammar.html#grammar-rule-enumClassBody)
  - ...
  - classMemberDeclarations
- [functionBody](https://kotlinlang.org/spec/syntax-and-grammar.html#grammar-rule-functionBody)
  - [block](#block)

### classMemberDeclarations

https://kotlinlang.org/spec/syntax-and-grammar.html#grammar-rule-classMemberDeclarations

- classMemberDeclaration 
  - declaration | companionObject | anonymousInitializer | secondaryConstructor

## block

https://kotlinlang.org/spec/syntax-and-grammar.html#grammar-rule-block

- [statements](https://kotlinlang.org/spec/syntax-and-grammar.html#grammar-rule-statements)
  - {statement}
    - {[label](#label) | [annotation](#annotation)} ([declaration](#declaration) | [assignment](#assignment) | [loopStatement](#loopstatement) | [expression](#expression))

### Label

https://kotlinlang.org/spec/syntax-and-grammar.html#grammar-rule-label

### annotation

https://kotlinlang.org/spec/syntax-and-grammar.html#grammar-rule-annotation

### assignment

https://kotlinlang.org/spec/syntax-and-grammar.html#grammar-rule-assignment

### loopStatement

https://kotlinlang.org/spec/syntax-and-grammar.html#grammar-rule-loopStatement

- forStatement | whileStatement | doWhileStatement

### expression

https://kotlinlang.org/spec/syntax-and-grammar.html#grammar-rule-expression

- [disjunction](https://kotlinlang.org/spec/syntax-and-grammar.html#grammar-rule-disjunction)

## See also

- [Syntax anl grammar (spec)](https://kotlinlang.org/spec/syntax-and-grammar.html)