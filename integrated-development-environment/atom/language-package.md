# Language package

## Grammar package

By convention, grammar packages should start with 'language-'

Role : syntax highlighting and snippets

### Tree-sitter

New feature, preferred way for new programming language  
Write a parser and maps each node to specific highlighting scope

Basic Structure

```text
language-mylanguage
├── LICENSE
├── README.md
├── grammars
│   └── mylanguage.cson // here, define scope for highlighting
└── package.json
```

Writing a parser step

```sh
mkdir tree-sitter-${YOUR_LANGUAGE_NAME}
cd tree-sitter-${YOUR_LANGUAGE_NAME}

# This will prompt you for input
npm init

npm install --save nan
npm install --save-dev tree-sitter-cli

cat << EOF >> grammar.js
module.exports = grammar({
  name: 'the_language_name',

  // here, define a Confext Free Grammar
  rules: {
    // The production rules of the context-free grammar
    source_file: $ => 'hello'
  }
});
EOF

# generate src/xxx
./node_modules/.bin/tree-sitter generate

# test parser
./node_modules/.bin/tree-sitter parse hello.lua
```

see also
* [creating grammar](https://flight-manual.atom.io/hacking-atom/sections/creating-a-grammar)
* [writing parser](http://tree-sitter.github.io/tree-sitter)  

### Textmate

Legacy feature, based on regex, supported by several popular text editors

[creating textmate grammar](https://flight-manual.atom.io/hacking-atom/sections/creating-a-legacy-textmate-grammar/)


## Autocomplete (snippets)

Use 'autocomplete plugin', define a provider and register in a reserved way

[writing custom provider](https://github.com/atom/autocomplete-plus/wiki/Provider-API)


## Linter

Use 'linter plugin', define a provider and register in a reserved way

3 type of Linter Provider

* Indie Provider v2 (push-style)
* Standard Provider v2 (pull-style)
* UI Provider v1

[atom linter documentation](https://github.com/steelbrain/linter/tree/master/docs)
