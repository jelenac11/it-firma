const fs = require('fs');

module.exports = {
  transpileDependencies: [
    'vuetify'
  ],
  devServer: {
    open: process.platform === 'darwin',
    host: '0.0.0.0',
    port: 8081, // CHANGE YOUR PORT HERE!
    https: {
      key: fs.readFileSync('./certs/shop-front.key'),
      cert: fs.readFileSync('./certs/shop-front.crt')
    },
    hotOnly: false,
  }
}
