const fs = require('fs');

module.exports = {
  transpileDependencies: [
    'vuetify'
  ],
  devServer: {
    open: process.platform === 'darwin',
    host: '0.0.0.0',
    port: 8082, // CHANGE YOUR PORT HERE!
    https: {
      key: fs.readFileSync('./certs/bank-front.key'),
      cert: fs.readFileSync('./certs/bank-front.crt')
    },
    hotOnly: false,
  }
}
