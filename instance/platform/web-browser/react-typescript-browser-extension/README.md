# Browser Extention

## Build from source

### Prerequisite

- Nvm
  - [Download](https://github.com/nvm-sh/nvm#install--update-script)
  - setup (use version described in `.nvmrc`)
    - `nvm install` (if not installed)
    - `nvm use`
- Yarn
  - Install
    - npm install --global yarn

### Build

- Install dependencies
  - `yarn install`
- Run
  - Firefox
    - `yarn run start:firefox` or `yarn run start`
  - Chrome
    - `yarn run start:chrome`
- Lint
  - `yarn run lint`
- Build
  - `yarn run build`
- Bundle
  - `yarn run package`
- Install to browser
  - [Firefox](https://extensionworkshop.com/documentation/develop/temporary-installation-in-firefox/)
    - about:debugging -> This Firefox -> Load Temporary Add-on -> select 'build'
  - Chrome
    - chrome://extensions/ -> Developer mode -> Load unpacked -> select 'build'

## References

- webpack
  - [webpack-core-concept](https://webpack.js.org/concepts/)
  - [webpack-typescript-loader](https://webpack.js.org/guides/typescript/) // not used
  - css
    - [webpack-css-loader](https://webpack.js.org/loaders/css-loader/)
    - [webpack-style-loader](https://webpack.js.org/loaders/style-loader/)
    - [webpack-sass-loader](https://webpack.js.org/loaders/sass-loader/)
  - web extension
    - [web-ext-webpack-plugin](https://github.com/hiikezoe/web-ext-webpack-plugin/blob/master/README.md)
  - html
    - [copy-webpack-plugin](https://webpack.js.org/plugins/copy-webpack-plugin)
  - eslint
    - [eslint-webpack-plugin](https://webpack.js.org/plugins/eslint-webpack-plugin/)
- [eslint](https://eslint.org/docs/user-guide/getting-started)
- [babel](https://babeljs.io/docs/en/)
- github
  - [https://github.com/birtles/rikaichamp](https://github.com/birtles/rikaichamp)
  - [https://github.com/kryptokinght/react-extension-boilerplate](https://github.com/kryptokinght/react-extension-boilerplate)
- general setting
  - [creating-react-app-with-typescript-eslint-with-webpack5](https://www.carlrippon.com/creating-react-app-with-typescript-eslint-with-webpack5/)
