# Browser Extention

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
- Run on default browser
  - `yarn run start` (`yarn run start -- --help` for help)
- Bundle
  - `yarn run build` (`yarn run build -- --help` for help)
- Install to browser
  - [Firefox](https://extensionworkshop.com/documentation/develop/temporary-installation-in-firefox/)
    - about:debugging -> This Firefox -> Load Temporary Add-on -> select 'src'
  - Chrome
    - chrome://extensions/ -> Developer mode -> Load unpacked -> select 'src'

## References

- [MDN your second extension](https://developer.mozilla.org/en-US/docs/Mozilla/Add-ons/WebExtensions/Your_second_WebExtension)
