<template>
  <div class="layout">
    <header class="site-header">
      <div class="brand">
        <img alt="网站 logo" src="./assets/book.png" height="54">
        <div>
          <h1>书城商城</h1>
          <p class="subtitle">发现好书 · 畅读经典</p>
        </div>
      </div>

      <HeaderCom @search="handleSearch" />

      <div class="user-zone">
        <template v-if="!isLoggedIn">
          <router-link to="/login" class="link">登录</router-link>
          <router-link to="/register" class="link">注册</router-link>
        </template>
        <template v-else>
          <span class="welcome">欢迎，{{ username }}</span>
          <button class="link ghost" @click="logout">退出</button>
        </template>
        <router-link to="/cart" class="cart-link">购物车（{{ cartCount }}）</router-link>
      </div>
    </header>

    <MyMens />

    <main class="page-container">
      <router-view></router-view>
    </main>

    <footer class="site-footer">
      <p>© 2024 书城商城 | 提供图书检索、购物车、订单、收藏等功能</p>
    </footer>
  </div>
</template>

<script setup>
import { computed, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import HeaderCom from "@/components/HeaderCom.vue";
import MyMens from "@/components/MyMenus.vue"
import { useUserStore } from './stores/user'

const router = useRouter()
const userStore = useUserStore()

const isLoggedIn = computed(() => userStore.isLoggedIn)
const username = computed(() => userStore.profile.username || '用户')
const cartCount = computed(() => userStore.cartCount)

const preloadUserData = () => {
  if (!userStore.isLoggedIn) return
  userStore.fetchCart()
  userStore.fetchFavorites()
  userStore.fetchOrders()
}

const handleSearch = (keyword) => {
  router.push({
    path: '/search',
    query: { wd: keyword }
  })
}

const logout = () => {
  userStore.logout()
  router.push('/login')
}

onMounted(() => {
  preloadUserData()
})

watch(() => userStore.token, () => {
  preloadUserData()
})
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  background: #f6f7fb;
  min-height: 100vh;
}

.layout {
  max-width: 1280px;
  margin: 0 auto;
  padding: 10px 20px 30px;
}

.site-header {
  display: grid;
  grid-template-columns: 2fr 3fr 2fr;
  align-items: center;
  gap: 20px;
  background: #fff;
  padding: 12px 16px;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.04);
}

.brand {
  display: flex;
  align-items: center;
  gap: 10px;
}

.subtitle {
  margin: 0;
  color: #888;
}

.user-zone {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 12px;
}

.link {
  color: #2563eb;
  text-decoration: none;
  border: 1px solid #2563eb;
  padding: 6px 10px;
  border-radius: 8px;
}

.link.ghost {
  background: #fff;
}

.welcome {
  color: #333;
}

.cart-link {
  text-decoration: none;
  color: #fff;
  background: #16a34a;
  padding: 8px 12px;
  border-radius: 8px;
}

.page-container {
  margin-top: 16px;
}

.site-footer {
  margin-top: 24px;
  color: #666;
  font-size: 14px;
}
</style>
