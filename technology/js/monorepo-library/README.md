# Monorepo library template

Uses [Lerna](https://github.com/lerna/lerna) and [yarn workspace feature](https://yarnpkg.com/lang/en/docs/workspaces/)

## Config

package.json

```json
{
  // ...
  "scripts": {
    "lerna": "lerna",
    "postinstall": "yarn run bootstrap",
    "bootstrap": "lerna bootstrap", // yarn install for all packages
    "clean-modules": "lerna clean -y",
    "lint": "lerna run lint", // trigger yarn run lint for all packages
    "test": "lerna run test",
    "build": "lerna run build",
    "docs": "lerna run docs"
  },
  // ...
}
```

lerna.json

```json
{
  "packages": [
    "packages/*"
  ],
  "npmClient": "yarn", // npm or yarn
  "useWorkspaces": true,
  "version": "0.1.0" // set as independent if you want to use in indepentent mode
}
```

## Usage (For all the packages)

* Install : `yarn install`
* Clean node_modules : `yarn run clean-modules`
* Lint : `yarn run lint`
* Test : `yarn run test`
* Build : `yarn run build`
* Publish : `cd $PACKAGE_TO_PUBLISH; yarn publish --access public`
* Docs : `yarn run docs`

## Config files

* `.yarnrc`
