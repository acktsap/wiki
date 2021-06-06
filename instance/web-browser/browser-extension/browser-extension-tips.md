# Browser Extension Tips

## Publish

### Firefox

- debuging : type `about:debugging` in firefox address bar
- [temporary installation](https://extensionworkshop.com/documentation/develop/temporary-installation-in-firefox/)
- [publish](https://extensionworkshop.com/documentation/publish/)

## Cors Request

cors request를 위해서는 mainfest에 match pattern에 맞는 permission을 넣어줘야 한다.

eg. `https://developer.mozilla.org/test`로 요청해야 하면 `"*://developer.mozilla.org/*"`를 넣는 식.

- [content script - xhr and Fetch](https://developer.mozilla.org/en-US/docs/Mozilla/Add-ons/WebExtensions/Content_scripts#xhr_and_fetch)
- [manifest post permissions](https://developer.mozilla.org/en-US/docs/Mozilla/Add-ons/WebExtensions/manifest.json/permissions#host_permissions)

## why no both browser action & page action in chrome extension

https://stackoverflow.com/questions/7888915/why-i-cannot-use-two-or-more-browser-action-page-action-or-app-together