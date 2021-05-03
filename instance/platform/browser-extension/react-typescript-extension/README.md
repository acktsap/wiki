# Browser Extention

todo

- lint
- minimize
- es5 support

## Prerequisite

### Nvm

- [Download](https://github.com/nvm-sh/nvm#install--update-script)
- setup (use version described in `.nvmrc`)
  - `nvm install` (if not installed)
  - `nvm use`

### Yarn

- Install
  - npm install --global yarn

## Build

- Install dependencies
  - `yarn install`
- Run
  - Firefox
    - `yarn run start:firefox` or `yarn run start`
  - Chrome
    - `yarn run start:chrome`
- Build
  - `yarn run build`
- Bundle
  - `yarn run package`
- Install to browser
  - [Firefox](https://extensionworkshop.com/documentation/develop/temporary-installation-in-firefox/)
    - about:debugging -> This Firefox -> Load Temporary Add-on -> select 'src'
  - Chrome
    - chrome://extensions/ -> Developer mode -> Load unpacked -> select 'src'

## References

- webpack
  - [webpack-core-concept](https://webpack.js.org/concepts/)
  - [webpack-typescript-loader](https://webpack.js.org/guides/typescript/)
  - css
    - [webpack-css-loader](https://webpack.js.org/loaders/css-loader/)
    - [webpack-style-loader](https://webpack.js.org/loaders/style-loader/)
    - [webpack-sass-loader](https://webpack.js.org/loaders/sass-loader/)
  - web extension
    - [web-ext-webpack-plugin](https://github.com/hiikezoe/web-ext-webpack-plugin/blob/master/README.md)
  - html
    - [copy-webpack-plugin](https://webpack.js.org/plugins/copy-webpack-plugin)
- github
  - [https://github.com/birtles/rikaichamp](https://github.com/birtles/rikaichamp)
  - [https://github.com/kryptokinght/react-extension-boilerplate](https://github.com/kryptokinght/react-extension-boilerplate)
