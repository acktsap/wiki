# Browser Extention

WIP

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

- [web-ext-webpack-plugin](https://github.com/hiikezoe/web-ext-webpack-plugin/blob/master/README.md)
- [https://github.com/birtles/rikaichamp](https://github.com/birtles/rikaichamp)
