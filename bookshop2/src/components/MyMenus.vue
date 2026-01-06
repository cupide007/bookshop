<template>
  <nav class="nav-container">
    <ul class="nav-list">
      <li 
        v-for="item in navItems" 
        :key="item.path"
        class="nav-item"
        :class="{ active: activePath === item.path }"
        @click="handleNavClick(item.path)"
      >
        <span class="nav-text">{{ item.name }}</span>
      </li>
    </ul>
  </nav>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

const navItems = [
  { path: '/', name: '主页' },
  { path: '/new-books', name: '新书上市' },
  { path: '/hot-recommend', name: '热门推荐' },
  { path: '/video-resources', name: '视频资源' },
  { path: '/cart', name: '购物车' },
  { path: '/orders', name: '订单中心' },
  { path: '/favorites', name: '收藏夹' },
  { path: '/personal-center', name: '个人中心' }
]

const activePath = computed(() => {
  const current = route.path
  const hit = navItems.find((item) => current.startsWith(item.path) && item.path !== '/')
  return hit ? hit.path : '/'
})

const handleNavClick = (path) => {
  router.push({ path })
}
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

ul {
  list-style: none;
}

.nav-container {
  width: 100%;
  background-color: #1f2937;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  padding: 0 20px;
  border-radius: 12px;
  margin-top: 12px;
}

.nav-list {
  display: flex;
  justify-content: center;
  align-items: center;
  max-width: 1200px;
  margin: 0 auto;
  height: 64px;
}

.nav-item {
  flex: 1;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  position: relative;
  transition: all 0.3s ease;
  color: #fcfafa;
}

.nav-item:hover {
  color: #165DFF;
  background-color: #f5f7fa;
}

.nav-item.active {
  color: #165DFF;
  font-weight: 600;
}

.nav-item.active::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 60%;
  height: 3px;
  background-color: #165DFF;
  border-radius: 3px;
}

.nav-text {
  font-size: 16px;
  white-space: nowrap;
}

@media (max-width: 768px) {
  .nav-list {
    height: 60px;
  }
  
  .nav-text {
    font-size: 14px;
  }
  
  .nav-item {
    overflow: hidden;
    text-overflow: ellipsis;
    padding: 0 5px;
  }
}

@media (max-width: 480px) {
  .nav-text {
    font-size: 12px;
  }
}
</style>
