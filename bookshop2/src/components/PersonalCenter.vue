<template>
  <section class="page">
    <header class="page-header">
      <h2>个人中心</h2>
      <p class="subtitle">查看账号信息，并快速跳转常用功能</p>
    </header>

    <div class="profile-card">
      <div class="avatar">{{ displayInitial }}</div>
      <div class="info">
        <p class="name">{{ profile.username || '未设置昵称' }}</p>
        <p class="meta">手机：{{ profile.phone || '未绑定' }}</p>
      </div>
      <button class="btn ghost" @click="logout">退出登录</button>
    </div>

    <div class="quick-grid">
      <router-link to="/orders" class="tile">
        <p class="title">订单中心</p>
        <p class="desc">查看和跟进订单状态</p>
      </router-link>
      <router-link to="/cart" class="tile">
        <p class="title">购物车</p>
        <p class="desc">继续结算或修改数量</p>
      </router-link>
      <router-link to="/favorites" class="tile">
        <p class="title">收藏夹</p>
        <p class="desc">管理关注的书籍</p>
      </router-link>
    </div>
  </section>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

const profile = computed(() => userStore.profile)
const displayInitial = computed(() => (profile.value.username || 'U').slice(0, 1).toUpperCase())

const logout = () => {
  userStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.page {
  padding: 20px;
  background: #fff;
}

.page-header {
  margin-bottom: 16px;
}

.subtitle {
  color: #888;
  margin-top: 6px;
}

.profile-card {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 16px;
  border: 1px solid #eee;
  border-radius: 10px;
  margin-bottom: 16px;
}

.avatar {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background: linear-gradient(135deg, #2563eb, #7c3aed);
  display: inline-flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 22px;
  font-weight: 700;
}

.name {
  margin: 0;
  font-size: 18px;
  font-weight: 700;
}

.meta {
  margin: 4px 0 0;
  color: #888;
}

.btn {
  margin-left: auto;
  border: 1px solid #2563eb;
  background: #2563eb;
  color: #fff;
  padding: 8px 14px;
  border-radius: 8px;
  cursor: pointer;
}

.btn.ghost {
  background: #fff;
  color: #2563eb;
}

.quick-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 12px;
}

.tile {
  display: block;
  padding: 14px;
  border: 1px solid #eee;
  border-radius: 10px;
  text-decoration: none;
  color: #333;
  transition: all 0.2s ease;
}

.tile:hover {
  border-color: #2563eb;
  box-shadow: 0 4px 14px rgba(37, 99, 235, 0.12);
}

.title {
  margin: 0;
  font-weight: 700;
}

.desc {
  margin: 6px 0 0;
  color: #666;
}
</style>
