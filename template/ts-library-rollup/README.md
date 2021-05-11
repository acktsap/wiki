# Javascript library template

Single project template for javascript library.

## Usage

* Test : `yarn run test` -> coverage : `$PROJECT_HOME/coverage`
* Build : `yarn run build`
* Install
```sh
# install globally (check /usr/local/lib/node_modules)
> yarn link
# link /usr/local/lib/node_modules/ts-library-project to $REFERENCING_PROJECT/node_modules
> cd $REFERENCING_PROJECT; yarn link ts-library-project
```
* Publish
```sh
# create an account in https://www.npmjs.com & make a token
> yarn publish --access public
```
* Docs : `yarn run docs` -> genertated on `$PROJECT_HOME/docs`

## Dev dependencies

### Rollup

Module bundler

* [rollup](https://rollupjs.org/guide/en) : Module bundler. default config : `{$PROJECT_HOME}/rollup.config.js`
* [rollup-plugin-babel](https://rollupjs.org/guide/en#babel) : Babel converts latest js feature to an older one. need `@babel/core` to use it
* [rollup-plugin-commonjs](https://github.com/rollup/rollup-plugin-commonjs) : Convert commonjs modules to es6, so they can be included in a rollup bundle
* [rollup-plugin-node-resolve](https://github.com/rollup/rollup-plugin-node-resolve) : Locate modules using the Node resolution algorithm.

### Babel

* [@babel/core](https://github.com/babel/babel/tree/master/packages/babel-core) : A babel compiler core
* [@babel/register](https://github.com/babel/babel/tree/master/packages/babel-register) : A babel require hook
* [@babel/preset-env](https://github.com/babel/babel/tree/master/packages/babel-preset-env) : A babel presets (a set of particular language support)
* [@babel/preset-env-typescript](https://github.com/babel/babel/tree/master/packages/babel-preset-typescript) : A typescript babel preset
* [babel-register-ts](https://github.com/deepsweet/babel-register-ts) : A `@babel/register` awrapper with `.ts` and `.tsx`

### Lint, Test

* [eslint](https://eslint.org/docs/user-guide/getting-started) : Linter, uses [typescript-eslint](https://github.com/typescript-eslint/typescript-eslint/tree/master/packages/eslint-plugin)
* [mocha](https://mochajs.org/#assertions) : Unit test library, use [chai](https://www.chaijs.com/guide/) for assertion, need `@babel/register` to run es6 written test code
* [istanbul (nyc)](https://istanbul.js.org/docs/tutorials/mocha/) : Code coverage tool used with `mocha`.

## Add new dependency

Add runtime dependency

```sh
> $ yarn add ${dependency}
```

Add dev dependency

```sh
> $ yarn add -D ${dependency}
```

## Config files

* `.babelrc`
* `.eslintrc`
* `.nycrc`
