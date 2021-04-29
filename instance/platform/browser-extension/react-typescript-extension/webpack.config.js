// path utils
const path = require('path')

// copy plugin to copy *.html, *.css, ...
const CopyWebpackPlugin = require('copy-webpack-plugin');

// integrate the web-ext run and lint commands into the webpack process
const WebExtPlugin = require('web-ext-plugin')

const commonConfig = {
  entry: {
    content: path.resolve(__dirname, './src/content_scripts'),
    background: path.resolve(__dirname, './src/background_scripts'),
    popup: path.resolve(__dirname, './src/popup'),
  },
  module: {
    rules: [
      {
        test: /\.tsx?$/,
        use: 'ts-loader', // typescript webpack loader
        exclude: /node_modules/,
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