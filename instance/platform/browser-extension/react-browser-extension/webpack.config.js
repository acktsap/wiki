// path utils
const path = require('path')

// integrate the web-ext run and lint commands into the webpack process
const WebExtPlugin = require('web-ext-plugin')

const commonConfig = {
  entry: {
    content: path.resolve(__dirname, 'src/content_scripts'),
    background: path.resolve(__dirname, 'src/background_scripts'),
    popup: path.resolve(__dirname, 'src/popup')
  },
  output: {
    path: path.resolve(__dirname, 'build'),
    filename: '[name].js',
  },
}

const firefoxConfig = {
  ...commonConfig,
  plugins: [
    new WebExtPlugin({
      sourceDir: path.resolve(__dirname, 'build'), // web-ext --source-dir
      startUrl: "https://duckduckgo.com", // web-ext --start-url
      browserConsole: true, // web-ext --browser-console
      target: "firefox-desktop" // web-ext --target
    })
  ],
}

const chromeConfig = {
  ...commonConfig,
  plugins: [
    new WebExtPlugin({
      sourceDir: path.resolve(__dirname, 'build'),
      startUrl: "https://duckduckgo.com",
      browserConsole: true,
      target: "chromium"
    })
  ],
}

module.exports = (env, argv) => {
  let configs = [];
  if (env.target === 'firefox') {
    configs.push({ ...firefoxConfig });
  } else if ('chrome') {
    configs.push({ ...chromeConfig });
  }

  return configs;
}