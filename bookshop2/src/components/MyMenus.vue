<template>
  <!-- 导航容器 - 居中对齐 -->
  <nav class="nav-container">
    <!-- 导航列表 - 宽度100%，子项平均分配 -->
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
import { ref } from 'vue'
import { useRouter } from 'vue-router'

// 导航菜单数据
const navItems = ref([
  { path: '/', name: '主页' },
  { path: '/new-books', name: '新书上市' },
  { path: '/hot-recommend', name: '热门推荐' },
  { path: '/video-resources', name: '视频资源' },
  { path: '/personal-center', name: '个人中心' }
])

// 当前激活的菜单项路径
const activePath = ref('/')
const router = useRouter()

// 导航点击事件处理
const handleNavClick = (path) => {
  activePath.value = path
  // 如需路由跳转，可添加以下代码（需先引入vue-router）
  
  router.push({
      path:activePath.value
  })
  
  // 模拟跳转日志
  
}
</script>

<style scoped>
/* 基础重置 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

ul {
  list-style: none;
}

/* 导航容器 - 居中对齐，限制最大宽度更美观 */
.nav-container {
  width: 100%;
  background-color: #3404a5;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  padding: 0 20px;
}

/* 导航列表 - 水平居中，子项平均分配宽度 */
.nav-list {
  display: flex;
  justify-content: center; /* 整体居中 */
  align-items: center;
  max-width: 1200px; /* 可选：限制最大宽度，适配大屏 */
  margin: 0 auto;    /* 容器水平居中 */
  height: 70px;      /* 导航栏高度 */
}

/* 导航菜单项 - 平均分配宽度（flex: 1） */
.nav-item {
  flex: 1;            /* 核心：平均分配宽度 */
  height: 100%;
  display: flex;
  justify-content: center; /* 文字水平居中 */
  align-items: center;     /* 文字垂直居中 */
  cursor: pointer;
  position: relative;
  transition: all 0.3s ease;
  color: #fcfafa;
}

/* 鼠标悬浮效果 */
.nav-item:hover {
  color: #165DFF;
  background-color: #f5f7fa;
}

/* 激活状态样式 */
.nav-item.active {
  color: #165DFF;
  font-weight: 600;
}

/* 激活状态底部高亮线 */
.nav-item.active::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 60%;         /* 高亮线宽度（可调整） */
  height: 3px;
  background-color: #165DFF;
  border-radius: 3px;
}

/* 菜单项文字样式 */
.nav-text {
  font-size: 16px;
  white-space: nowrap; /* 防止文字换行 */
}

/* 响应式适配 - 小屏幕优化 */
@media (max-width: 768px) {
  .nav-list {
    height: 60px;
  }
  
  .nav-text {
    font-size: 14px;
  }
  
  /* 超小屏幕下强制文字不换行，保证平均宽度 */
  .nav-item {
    overflow: hidden;
    text-overflow: ellipsis;
    padding: 0 5px;
  }
}

/* 极窄屏幕适配 */
@media (max-width: 480px) {
  .nav-text {
    font-size: 12px;
  }
}
</style>