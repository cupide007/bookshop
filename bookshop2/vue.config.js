const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer:{
    proxy:{
      '/api':{// /api是后端数据接口的上下文路径，如果接口地址是以/api开头的，则启用下面的代理服务器，并允许跨域
        // target:"http://123.57.150.98",
        target:'http://localhost:8081',
        //设置允许跨域
        changeOrigin:true,
        pathRewrite: {
          '^/api': ''
        }//这里设置为''表示跨域后的网址是不包含/api的
      }
}
  }
})
