<template>
  <section class="page">
    <header class="page-header">
      <h2>支付订单</h2>
      <p class="subtitle">请输入或确认订单号后完成支付</p>
    </header>

    <div class="form-card">
      <label class="field">
        <span>订单号</span>
        <input v-model="orderNumber" type="text" placeholder="请输入订单号" />
      </label>
      <button class="action primary" :disabled="!orderNumber" @click="handlePay">立即支付</button>
      <p v-if="message" class="info">{{ message }}</p>
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
