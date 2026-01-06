<template>
  <section class="page">
    <header class="page-header">
      <h2>订单中心</h2>
      <p class="subtitle">查看已提交的订单，掌握订单状态</p>
    </header>

    <div v-if="!orders.length" class="empty">
      <p>暂无订单记录</p>
      <router-link class="link" to="/">去首页逛逛</router-link>
    </div>

    <div v-else class="order-list">
      <article class="order-card" v-for="order in orders" :key="order.id">
        <header class="order-head">
          <div>订单号：{{ order.id }}</div>
          <div class="meta">{{ order.createdAt }}</div>
        </header>
        <div class="order-items">
          <div class="item" v-for="item in order.items" :key="item.id">
            <img :src="resolveImage(item.imageUrl)" alt="" class="thumb">
            <div class="info">
              <p class="name">{{ item.name }}</p>
              <p class="meta">数量：{{ item.quantity }}</p>
            </div>
            <p class="price">￥{{ item.price }}</p>
          </div>
        </div>
        <footer class="order-foot">
          <span>共 {{ order.items.length }} 件商品</span>
          <span class="total">实付：￥{{ order.total.toFixed(2) }}</span>
        </footer>
      </article>
    </div>
  </section>
</template>

<script setup>
import { computed } from 'vue'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
const orders = computed(() => userStore.orders)

const resolveImage = (src) => {
  if (!src) return 'https://via.placeholder.com/60x80?text=Book'
  return src.startsWith('http') ? src : `/api/res/images/${src}`
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

.empty {
  text-align: center;
  padding: 40px 0;
  color: #666;
}

.link {
  color: #2563eb;
  text-decoration: none;
}

.order-list {
  display: grid;
  gap: 12px;
}

.order-card {
  border: 1px solid #eee;
  border-radius: 8px;
  padding: 12px;
}

.order-head,
.order-foot {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.meta {
  color: #888;
  font-size: 13px;
}

.order-items {
  margin: 10px 0;
  border-top: 1px dashed #f0f0f0;
  border-bottom: 1px dashed #f0f0f0;
}

.item {
  display: flex;
  align-items: center;
  padding: 8px 0;
}

.thumb {
  width: 60px;
  height: 80px;
  object-fit: cover;
  border-radius: 4px;
  margin-right: 14px;
}

.info .name {
  margin: 0;
  font-weight: 600;
}

.price {
  margin-left: auto;
  color: #e43f3b;
}

.total {
  font-weight: 700;
  color: #e43f3b;
}
</style>
