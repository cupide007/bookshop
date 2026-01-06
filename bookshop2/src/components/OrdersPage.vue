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
      <article class="order-card" v-for="order in orders" :key="order.orderId">
        <header class="order-head">
          <div>订单号：{{ order.orderNumber }}</div>
          <div class="meta">{{ order.createdAt }}</div>
        </header>
        <div class="order-items" v-if="order.items && order.items.length">
          <div class="item" v-for="item in order.items" :key="item.itemId">
            <img :src="resolveImage(item.productImage)" alt="" class="thumb">
            <div class="info">
              <p class="name">{{ item.productName }}</p>
              <p class="meta">数量：{{ item.quantity }}</p>
            </div>
            <p class="price">￥{{ item.price }}</p>
          </div>
        </div>
        <footer class="order-foot">
          <span>总额：￥{{ order.totalAmount }}</span>
          <span class="total">状态：{{ order.status }}</span>
          <button
            v-if="order.status === 'pending'"
            class="pay-btn"
            @click="toPay(order.orderNumber)"
          >
            立即支付
          </button>
        </footer>
      </article>
    </div>
  </section>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
const orders = computed(() => userStore.orders)
const router = useRouter()

const resolveImage = (src) => {
  if (!src) return 'https://via.placeholder.com/60x80?text=Book'
  return src.startsWith('http') ? src : `/api/res/images/${src}`
}

const toPay = (orderNumber) => {
  router.push({ path: '/pay', query: { orderNumber } })
}

onMounted(() => {
  userStore.fetchOrders()
})
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

.pay-btn{
  border: 1px solid #2563eb;
  background: #2563eb;
  color: #fff;
  padding: 6px 12px;
  border-radius: 6px;
  cursor: pointer;
}
</style>
