const { defineConfig } = require('@vue/cli-service')
const path = require("path");

module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,
  devServer: {
    proxy: {
      '/resources/\\d*/js/(main|vendors).js': {
        target: 'http://127.0.0.1:8081',
        pathRewrite: {'/resources/\\d*' : ''}
      },
      '/api': {
        target: 'http://127.0.0.1:8080',
        changeOrigin: true,
      },
      // '**': 'http://127.0.0.1:8080'
    }
  },
  outputDir: path.resolve(__dirname, "../src/main/resources/static"),
  indexPath: path.resolve(__dirname, "../src/main/resources/static/index.html"),
})
