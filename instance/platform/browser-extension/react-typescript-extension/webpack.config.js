// path utils
const path = require('path')

// copy plugin to copy *.html, *.css, ...
const CopyWebpackPlugin = require('copy-webpack-plugin');

// eslint
const ESLintPlugin = require('eslint-webpack-plugin');

// integrate the web-ext run and lint commands into the webpack process
const WebExtPlugin = require('web-ext-plugin')

const commonConfig = {
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
}

const firefoxConfig = {
  ...commonConfig,
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
    }),
    new WebExtPlugin({
      sourceDir: path.resolve(__dirname, 'build'), // web-ext --source-dir
      startUrl: "https://duckduckgo.com", // web-ext --start-url
      browserConsole: true, // web-ext --browser-console
      target: "firefox-desktop" // web-ext --target
    }),
  ],
}

const chromeConfig = {
  ...commonConfig,
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
    }),
    new WebExtPlugin({
      sourceDir: path.resolve(__dirname, 'build'),
      startUrl: "https://duckduckgo.com",
      browserConsole: true,
      target: "chromium"
    }),
  ],
}

module.exports = (env, argv) => {
  let configs = [];
  if (env.target === 'firefox') {
    configs.push({ ...firefoxConfig });
  } else if (env.target === 'chrome') {
    configs.push({ ...chromeConfig });
  } else {
    configs.push({ ...firefoxConfig }); // default : firefox
  }

  return configs;
}