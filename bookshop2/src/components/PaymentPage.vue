<template>
  <section class="page">
    <header class="page-header">
      <h2>支付订单</h2>
      <p class="subtitle">确认订单并完成支付</p>
    </header>

    <div class="form-card" v-if="orderNumber">
      <p class="order-label">待支付订单号</p>
      <p class="order-number">{{ orderNumber }}</p>
      <button class="action primary" @click="handlePay">立即支付</button>
      <p v-if="message" class="info">{{ message }}</p>
      <router-link class="link" to="/orders">返回订单列表</router-link>
    </div>
    <div v-else class="form-card">
      <p>暂无待支付订单</p>
      <router-link class="link" to="/orders">返回订单列表</router-link>
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'

const route = useRoute()
const userStore = useUserStore()

const orderNumber = ref('')
const message = ref('')

const handlePay = async () => {
  if (!orderNumber.value) return
  const res = await userStore.payOrder(orderNumber.value)
  if (res?.success) {
    message.value = '支付成功'
  } else {
    message.value = res?.message || '支付失败'
  }
}

onMounted(() => {
  if (route.query.orderNumber) {
    orderNumber.value = route.query.orderNumber
  } else if (userStore.lastOrderNumber) {
    orderNumber.value = userStore.lastOrderNumber
  }
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
  color: #666;
}

.form-card {
  border: 1px solid #eee;
  border-radius: 10px;
  padding: 16px;
  max-width: 420px;
  background: #fafafa;
}

.field {
  display: flex;
  flex-direction: column;
  gap: 6px;
  margin-bottom: 12px;
}

.field input {
  height: 38px;
  padding: 0 10px;
  border: 1px solid #dcdfe6;
  border-radius: 8px;
}

.order-label {
  margin: 0;
  color: #666;
}

.order-number {
  font-size: 18px;
  font-weight: 700;
  margin: 6px 0 14px;
}

.action.primary {
  background: #2563eb;
  color: #fff;
  border: 1px solid #2563eb;
  padding: 10px 14px;
  border-radius: 8px;
  cursor: pointer;
}

.action.primary:disabled {
  background: #9eb8f8;
  cursor: not-allowed;
}

.info {
  margin-top: 10px;
  color: #2563eb;
}

.link {
  display: inline-block;
  margin-top: 10px;
  color: #2563eb;
  text-decoration: none;
}
</style>
