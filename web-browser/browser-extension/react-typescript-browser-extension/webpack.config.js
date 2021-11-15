// path utils
const path = require('path')

// copy plugin to copy *.html, *.css, ...
const CopyWebpackPlugin = require('copy-webpack-plugin');

// eslint
const ESLintPlugin = require('eslint-webpack-plugin');

// integrate the web-ext run and lint commands into the webpack process
const WebExtPlugin = require('web-ext-plugin')

const baseConfigs = {
  entry: {
    content: path.resolve(__dirname, './src/content_scripts'),
    background: path.resolve(__dirname, './src/background_scripts'),
    popup: path.resolve(__dirname, './src/popup'),
    option: path.resolve(__dirname, './src/option'),
  },
  module: {
    rules: [
      {
        test: /\.(ts|js)x?$/i,
        exclude: /node_modules/,
        use: {
          // babel : convert ECMAScript 2015+ code into backword compatible one
          loader: "babel-loader",
          options: {
            presets: [
              "@babel/preset-env",
              "@babel/preset-react",
              "@babel/preset-typescript",
            ],
            plugins: [
              // resolve 'regeneratorRuntime is not defined' when using async/await
              "@babel/transform-runtime",
            ]
          },
        },
      },
      {
        test: /\.css|scss?$/i,
        use: [
          {
            loader: 'style-loader', // creates 'style' nodes
            options: {
              // use single <style> ... </style> tag
              injectType: 'singletonStyleTag',
            }, 
          },
          'css-loader', // translate css int CommonJS
          {
            loader: 'sass-loader', // interprets sass
            options: {
              implementation: require('sass'),
            }, 
          },
        ]
      },
    ],
  },
  resolve: {
    extensions: ['.tsx', '.ts', '.js'],
  },
  output: {
    path: path.resolve(__dirname, 'build'),
    filename: '[name].js',
  },
  plugins: [
    new CopyWebpackPlugin({
      patterns: [
        {
          from: 'src/public',
          globOptions: {
            ignore: ['*.js', '*.ts', '*.tsx'],
          },
        },
      ],
    }),
    new ESLintPlugin({
      extensions: ["js", "jsx", "ts", "tsx"],
    })
  ],
}

const configFirefox = (configs) => {
  const webExtPlugin = new WebExtPlugin({
    sourceDir: path.resolve(__dirname, 'build'), // web-ext --source-dir
    startUrl: "https://duckduckgo.com", // web-ext --start-url
    browserConsole: true, // web-ext --browser-console
    target: "firefox-desktop" // web-ext --target
  });

  configs.plugins.push(webExtPlugin);
}

const configChrome = (configs) => {
  const webExtPlugin = new WebExtPlugin({
    sourceDir: path.resolve(__dirname, 'build'),
    startUrl: "https://duckduckgo.com",
    browserConsole: true,
    target: "chromium"
  });

  configs.plugins.push(webExtPlugin);
}

module.exports = (env, argv) => {
  const configs = baseConfigs;

  if (env.target === 'firefox') {
    configFirefox(configs)
  } else if (env.target === 'chrome') {
    configChrome(configs);
  } else {
    configFirefox(configs)
  }

  return configs;
}