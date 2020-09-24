# Firefox Extention

## Build From source

### Prerequisite

- [nvm](https://github.com/nvm-sh/nvm#install--update-script)

### Build

- Nvm setup
  - `nvm install` (if not installed)
  - `nvm use`
- Install dependencies
  - `npm install`
- Run
  - `npm run start` (`npm run start -- --help` for help)
- Bundle
  - `npm run build` (`npm run build -- --help` for help)
- Install Locally
  - https://extensionworkshop.com/documentation/develop/temporary-installation-in-firefox/
  - Load `dist/naver_search-x.x.x.zip` file

## References

https://extensionworkshop.com/documentation/develop/browser-extension-development-tools/

- [Boilerplate code generation](https://webextensions.in/)
- [Examples](https://developer.mozilla.org/en-US/docs/Mozilla/Add-ons/WebExtensions/Examples)
- [Web ext: a cli tool for web extension](https://extensionworkshop.com/documentation/develop/getting-started-with-web-ext/)
- debuging : type `about:debugging` in firefox address bar

https://extensionworkshop.com/documentation/develop/temporary-installation-in-firefox/

- temporary installation

https://developer.mozilla.org/en-US/docs/Mozilla/Add-ons/WebExtensions/manifest.json

- `manifest.json` info
  - chrome_settings_overrides

https://extensionworkshop.com/documentation/publish/

- publish
