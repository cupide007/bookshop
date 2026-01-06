<template>
    <div>
        <h2>商品分类</h2>
        <div v-for="category in categories" :key="category.categoryId">
            <h5>{{ category.name }}</h5>
            <div class="item" v-for="item in category.products" :key="item.productId">
                <router-link :to="{ path: '/book/'+item.productId }">{{ item.name }}</router-link>
            </div>
        </div>
    </div>
</template>
<script setup>
import axios from "axios";
import { computed, onMounted, ref } from "vue";

const rawProducts = ref([])
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
    if (cat && cat.products.length < 4) {
      cat.products.push(p)
    }
  })
  return grouped
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
.item {
  margin:20px;
}

    
</style>
