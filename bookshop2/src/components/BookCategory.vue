<template>
  <div class="category-layout">
    <div class="category-list">
      <div
        v-for="category in categories"
        :key="category.categoryId"
        class="category-item"
        :class="{ active: category.categoryId === activeCategoryId }"
        @click="selectCategory(category.categoryId)"
      >
        <span class="name">{{ category.name }}</span>
        <span class="badge">{{ category.products.length }}</span>
      </div>
    </div>
    <div class="products-panel">
      <div class="panel-header">
        <div class="title">{{ activeCategory?.name || '商品' }}</div>
        <div class="hint">共 {{ displayedProducts.length }} 件商品</div>
      </div>
      <div v-if="displayedProducts.length" class="product-grid">
        <div v-for="item in displayedProducts" :key="item.productId" class="product-card">
          <img :src="resolveImage(item.imageUrl)" alt="">
          <div class="product-info">
            <router-link class="name" :to="{ path: '/book/'+item.productId }">{{ item.name }}</router-link>
            <div class="price">￥{{ item.price }}</div>
          </div>
        </div>
      </div>
      <div v-else class="empty">暂无商品</div>
    </div>
  </div>
</template>
<script setup>
import axios from "axios";
import { computed, onMounted, ref, watch } from "vue";

const rawProducts = ref([])
const activeCategoryId = ref(null)
const categoryMap = {
  1: '电子产品',
  2: '服装',
  3: '家居用品',
  4: '食品'
}

const categories = computed(() => {
  const grouped = Object.keys(categoryMap).map((id) => ({
    categoryId: Number(id),
    name: categoryMap[id],
    products: []
  }))

  rawProducts.value.forEach((p) => {
    const cat = grouped.find((g) => g.categoryId === p.categoryId)
    if (cat) {
      cat.products.push(p)
    }
  })
  return grouped
})

const activeCategory = computed(() => categories.value.find((c) => c.categoryId === activeCategoryId.value))
const displayedProducts = computed(() => activeCategory.value?.products || [])

const resolveImage = (src) => {
  if (!src) return 'https://via.placeholder.com/240x240?text=Goods'
  return src.startsWith('http') ? src : `/api/res/images/${src}`
}

const selectCategory = (id) => {
  activeCategoryId.value = id
}

watch(categories, (list) => {
  if (activeCategoryId.value) return
  const first = list.find((c) => c.products.length) || list[0]
  if (first) {
    activeCategoryId.value = first.categoryId
  }
})

onMounted(() => {
  axios.get("/products/list", { params: { page: 1, pageSize: 50 } })
    .then(res => {
      rawProducts.value = res.data?.data || []
    })
    .catch(err => {
      console.log(err);
    })
})
</script>
<style scoped>
.category-layout {
  display: grid;
  grid-template-columns: 240px 1fr;
  gap: 16px;
}
.category-list {
  background: #fff;
  border-radius: 12px;
  padding: 12px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.04);
  display: flex;
  flex-direction: column;
  gap: 8px;
}
.category-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 12px;
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.2s ease;
}
.category-item:hover {
  background: #f3f4f6;
}
.category-item.active {
  background: #2563eb;
  color: #fff;
}
.category-item.active .badge {
  background: rgba(255,255,255,0.25);
  color: #fff;
}
.name {
  font-size: 15px;
}
.badge {
  background: #eef2ff;
  color: #2563eb;
  padding: 4px 8px;
  border-radius: 999px;
  font-size: 12px;
}
.products-panel {
  background: #fff;
  border-radius: 12px;
  padding: 16px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.04);
  min-height: 320px;
  display: flex;
  flex-direction: column;
}
.panel-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 12px;
}
.title {
  font-size: 18px;
  font-weight: 600;
}
.hint {
  color: #6b7280;
  font-size: 13px;
}
.product-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
  gap: 12px;
}
.product-card {
  border: 1px solid #e5e7eb;
  border-radius: 10px;
  padding: 12px;
  display: flex;
  flex-direction: column;
  gap: 8px;
  transition: box-shadow 0.2s ease, transform 0.2s ease;
}
.product-card:hover {
  box-shadow: 0 6px 20px rgba(0,0,0,0.06);
  transform: translateY(-2px);
}
.product-card img {
  width: 100%;
  height: 160px;
  object-fit: cover;
  border-radius: 8px;
  background: #f9fafb;
}
.product-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}
.product-info .name {
  font-weight: 600;
  color: #111827;
}
.product-info .price {
  color: #ef4444;
  font-weight: 600;
}
.empty {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #6b7280;
  background: #f9fafb;
  border-radius: 10px;
}
</style>
