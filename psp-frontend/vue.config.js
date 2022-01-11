const fs = require('fs');

module.exports = {
  transpileDependencies: [
    'vuetify'
  ],
  devServer: {
    open: process.platform === 'darwin',
    host: '0.0.0.0',
    port: 8096, // CHANGE YOUR PORT HERE!
    https: {
      key: fs.readFileSync('./certs/psp-front.key'),
      cert: fs.readFileSync('./certs/psp-front.crt')
    },
    hotOnly: false,
  }
}
