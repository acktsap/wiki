# Notice

## Used utils

This template is based on `rollup`.  
Javascript module bundlers are in chaos there are too many module bunder.

* Browserify
* Webpack
* Rollup

Do not spend a lot of time in setting js package. There is no ultimate solution yet. Just use it.

## Compatibility

Before ECMAScript2015 (ES6), there was no module in standard. So there was several ways to make a module

* Asynchronous Module Definition (AMD) : Use `require.js`
* CommonJS : Used in `node.js`
* Univerasl Module Definition (UMD) : Combination of `AMD` and `CommonJS`

Now we have es6 module. But still many platform doesn't support es6 module.  
So we need to convert es6 written module to legacy compatible module format.

## Note

Files added to .gitignore will be ignored when deploying to npm. So, you should not ignore dist directory. Also, make sure that you've create dist directory by run `yarn run build`.
