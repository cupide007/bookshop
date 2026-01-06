<template>
  <section class="page">
    <header class="page-header">
      <h2>购物车</h2>
      <p class="subtitle">查看已加入的商品，完成下单流程</p>
    </header>

    <div v-if="!cartItems.length" class="empty">
      <p>购物车还没有商品，快去选购吧！</p>
      <router-link to="/" class="link">返回首页</router-link>
    </div>

    <div v-else class="cart-panel">
      <div class="cart-header">
        <span class="col col-product">商品信息</span>
        <span class="col col-price">单价</span>
        <span class="col col-qty">数量</span>
        <span class="col col-op">操作</span>
      </div>
      <div class="cart-item" v-for="item in cartItems" :key="item.productId">
        <div class="col col-product product-cell">
          <img :src="resolveImage(item.imageUrl)" alt="" class="thumb">
          <div>
            <h3>{{ item.name }}</h3>
          </div>
        </div>
        <div class="col col-price price">￥{{ item.price }}</div>
        <div class="col col-qty meta">数量：{{ item.quantity }}</div>
        <div class="col col-op">
          <button class="action danger" @click="remove(item.productId)">移除</button>
        </div>
      </div>

      <footer class="cart-summary">
        <div>
          <p>共 {{ cartCount }} 件商品</p>
          <p class="total">合计：￥{{ cartTotal.toFixed(2) }}</p>
        </div>
        <button class="action primary" @click="submitOrder">提交订单</button>
      </footer>

      <div v-if="newOrderNumber" class="payment-tip">
        <p>订单已生成：{{ newOrderNumber }}</p>
        <button class="action primary" @click="payNow">立即支付</button>
        <router-link class="link" :to="{ path: '/pay', query: { orderNumber: newOrderNumber } }">前往支付页</router-link>
        <router-link class="link" to="/orders">查看订单</router-link>
      </div>
      <p v-if="successMessage" class="success">{{ successMessage }}</p>
    </div>
  </section>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
const successMessage = ref('')
const newOrderNumber = ref('')
const router = useRouter()

const cartItems = computed(() => userStore.cart)
const cartCount = computed(() => userStore.cartCount)
const cartTotal = computed(() => userStore.cartTotal)

const resolveImage = (src) => {
  if (!src) return 'https://via.placeholder.com/80x100?text=Book'
  return src.startsWith('http') ? src : `/api/res/images/${src}`
}

const remove = (id) => {
  userStore.removeFromCart(id)
}

const submitOrder = async () => {
  const orderNumber = await userStore.checkout()
  if (orderNumber) {
    newOrderNumber.value = orderNumber
    router.push({ path: '/pay', query: { orderNumber } })
  }
}

const payNow = async () => {
  if (!newOrderNumber.value) return
  router.push({ path: '/pay', query: { orderNumber: newOrderNumber.value } })
}

onMounted(() => {
  userStore.fetchCart()
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

.cart-panel {
  border: 1px solid #eee;
  border-radius: 8px;
  padding: 12px;
  background: #fff;
}

.cart-header {
  display: grid;
  grid-template-columns: 2fr 1fr 1fr 1fr;
  padding: 10px 0;
  border-bottom: 1px solid #f3f3f3;
  font-weight: 600;
  color: #444;
}

.cart-item {
  display: grid;
  grid-template-columns: 2fr 1fr 1fr 1fr;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid #f3f3f3;
}

.cart-item:last-child {
  border-bottom: none;
}

.thumb {
  width: 80px;
  height: 100px;
  object-fit: cover;
  border-radius: 4px;
  margin-right: 16px;
}

.product-cell {
  display: flex;
  gap: 12px;
  align-items: center;
}

.col {
  padding: 0 10px;
}

.info h3 {
  margin: 0;
  font-size: 16px;
}

.meta {
  color: #888;
}

.price {
  color: #e43f3b;
  font-weight: 600;
}

.action {
  border: 1px solid #2563eb;
  background: #fff;
  color: #2563eb;
  padding: 8px 14px;
  border-radius: 6px;
  cursor: pointer;
  margin-left: auto;
}

.action:hover {
  background: #2563eb;
  color: #fff;
}

.action.danger {
  border-color: #f87171;
  color: #b91c1c;
}

.cart-summary {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 12px;
}

.total {
  font-size: 18px;
  font-weight: 700;
  color: #e43f3b;
}

.action.primary {
  background: #2563eb;
  color: #fff;
}

.cart-header .col,
.cart-item .col {
  text-align: center;
}

.cart-header .col-product,
.cart-item .col-product {
  text-align: left;
}

.payment-tip{
  margin-top: 12px;
  display: flex;
  align-items: center;
  gap: 12px;
  background: #f0f7ff;
  padding: 10px;
  border-radius: 8px;
}

.success {
  color: #16a34a;
  margin-top: 10px;
}
</style>
