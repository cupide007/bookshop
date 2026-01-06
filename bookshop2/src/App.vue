<template>
   <div class="content">
     <img alt="Vue logo" src="./assets/book.png" height="60">
    <HeaderCom />
   
    <button class="cart">加入购物车</button>

    <span>请 <router-link to="/login" >登录</router-link> 免费 <router-link to="/register" >注册</router-link></span>
    <span>欢迎您！{{}}</span>
   </div>
    <MyMens />
    <!-- 指定路由目标组件的渲染位置 -->
    <router-view></router-view>
    
</template>

<script>
import HeaderCom from "@/components/HeaderCom.vue";
import MyMens from "@/components/MyMenus.vue"

export default {
  name: "App",
  components: {
    HeaderCom,
    MyMens,
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin: auto;
  width: 1280px;
}
.content{
  display: flex;
  justify-content: center;
}
.cart{
  background-color: green;
  color: white;
  height: 38px;
  border: blue solid 1px;
  width: 120px;
  margin-left: 20px;
}
</style>
