# Typescript executable template

## Usage

- Test : `yarn run test` -> coverage : `$PROJECT_HOME/coverage`
- Build : `yarn run build`
- Dev (Watch): `yarn run dev`
- Run : `./bin/hello`

## Dev dependencies

- rollup : Module bundler. default config : `{$PROJECT_HOME}/rollup.config.js`

- @babel/core : A babel compiler core
- @babel/register : A babel require hook
- @babel/preset-env : A babel presets (a set of particular language support)
- @babel/preset-env-typescript : A typescript babel preset
- babel-register-ts : A `@babel/register` awrapper with `.ts` and `.tsx`

- eslint : Linter, uses typescript-eslint
- mocha : Unit test library, use chai for assertion, need `@babel/register` to run es6 written test code
- istanbul (nyc) : Code coverage tool used with `mocha`.

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

- `.babelrc`
- `.eslintrc`
- `.nycrc`
