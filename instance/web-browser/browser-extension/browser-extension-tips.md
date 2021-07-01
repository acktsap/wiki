# Browser Extension Tips

## Publish

### Firefox

- debuging : type `about:debugging` in firefox address bar
- [temporary installation](https://extensionworkshop.com/documentation/develop/temporary-installation-in-firefox/)
- [publish](https://extensionworkshop.com/documentation/publish/)

### Chrome, Whale

- chrome://extensions/ (or whale://extensions) -> Developer mode -> Load unpacked

## Cors Request

cors request를 위해서는 mainfest에 match pattern에 맞는 permission을 넣어줘야 한다.

eg. `https://developer.mozilla.org/test`로 요청해야 하면 `"*://developer.mozilla.org/*"`를 넣는 식.

- [content script - xhr and Fetch](https://developer.mozilla.org/en-US/docs/Mozilla/Add-ons/WebExtensions/Content_scripts#xhr_and_fetch)
- [manifest post permissions](https://developer.mozilla.org/en-US/docs/Mozilla/Add-ons/WebExtensions/manifest.json/permissions#host_permissions)

## Cross Browsing

### Api usage

> Firefox also supports callbacks for the APIs that support the chrome.* namespace. However, using promises (and the browser.* namespace) is recommended.

-> Use webextension-polyfill to use `browser` api in chrome.

manifest.json

```js
{
  // ...

  "background": {
    "scripts": [
      "browser-polyfill.js",
      "background.js"
    ]
  },

  "content_scripts": [{
    // ...
    "js": [
      "browser-polyfill.js",
      "content.js"
    ]
  }]
}
```

webpack.config.js

```js
const CopyWebpackPlugin = require('copy-webpack-plugin');

module.exports = {
  /* Your regular webpack config, probably including something like this:
  output: {
    path: path.join(__dirname, 'distribution'),
    filename: '[name].js'
  },
  */
  plugins: [
    new CopyWebpackPlugin({
      patterns: [{
        from: 'node_modules/webextension-polyfill/dist/browser-polyfill.js',
      }],
    })
  ]
}
```

popup.html

```js
<!DOCTYPE html>
<html>
  <head>
    <!-- ... -->
    <script type="module" src="browser-polyfill.js"></script>
    <!-- ... -->
  </head>
  <!-- ... -->
</html>
```

- [build a cross browser extension](https://developer.mozilla.org/en-US/docs/Mozilla/Add-ons/WebExtensions/Build_a_cross_browser_extension)
- [webextension polyfill](https://github.com/mozilla/webextension-polyfill/)

### Debugging

- chrome extension console log : open extension -> mouse right button -> inspect

## why no both browser action & page action in chrome extension

https://stackoverflow.com/questions/7888915/why-i-cannot-use-two-or-more-browser-action-page-action-or-app-together