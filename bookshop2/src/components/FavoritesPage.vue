<template>
  <section class="page">
    <header class="page-header">
      <h2>收藏夹</h2>
      <p class="subtitle">查看已收藏的商品，随时加入购物车</p>
    </header>

    <div v-if="!favorites.length" class="empty">
      <p>还没有收藏任何商品</p>
      <router-link class="link" to="/">去首页发现更多</router-link>
    </div>

    <div v-else class="favorite-list">
      <article class="favorite-card" v-for="item in favorites" :key="item.favoriteId">
        <img :src="resolveImage(item.product?.imageUrl)" alt="" class="thumb">
        <div class="info">
          <h3>{{ item.product?.name }}</h3>
          <p class="price">￥{{ item.product?.price }}</p>
          <div class="actions">
            <router-link class="link" :to="`/book/${item.productId}`">查看详情</router-link>
            <button class="btn" @click="addCart(item.productId)">加入购物车</button>
            <button class="btn ghost" @click="remove(item.productId)">取消收藏</button>
          </div>
        </div>
      </article>
    </div>
  </section>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
const favorites = computed(() => userStore.favorites)

const resolveImage = (src) => {
  if (!src) return 'https://via.placeholder.com/80x100?text=Book'
  return src.startsWith('http') ? src : `/api/res/images/${src}`
}

const addCart = (productId) => {
  userStore.addToCart(productId, 1)
}

const remove = (productId) => {
  userStore.toggleFavorite(productId)
}

onMounted(() => {
  userStore.fetchFavorites()
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

.favorite-list {
  display: grid;
  gap: 12px;
}

.favorite-card {
  display: flex;
  align-items: center;
  border: 1px solid #eee;
  border-radius: 8px;
  padding: 12px;
}

.thumb {
  width: 80px;
  height: 100px;
  object-fit: cover;
  border-radius: 6px;
  margin-right: 14px;
}

.info h3 {
  margin: 0;
}

.price {
  color: #e43f3b;
  font-weight: 600;
}

.actions {
  display: flex;
  gap: 10px;
  margin-top: 8px;
}

.link {
  color: #2563eb;
  text-decoration: none;
}

.btn {
  padding: 6px 12px;
  border-radius: 6px;
  border: 1px solid #2563eb;
  background: #2563eb;
  color: #fff;
  cursor: pointer;
}

.btn.ghost {
  background: #fff;
  color: #2563eb;
}
</style>
