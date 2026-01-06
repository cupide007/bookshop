 <!--
<template>
    <div>搜索结果</div>
</template>
<script setup>


import axios from "axios";
import { onMounted } from "vue";
import { useRouter } from "vue-router";

const route=useRouter()
onMounted(()=>{
    console.log(route);
    console.log("search?wd="+route.query.wd+"&pageNum=1&pageSize=5");
    axios.get("/search?"+route.query.wd+"&pageNum=1&pageSize=5")
    .then(res=>{
        console.log(res);
    })
    .catch(err=>{
        console.log(err);
    })
})
</script>
-->
<template>
    <section class="page">
      <header class="page-header">
        <h2>搜索结果</h2>
        <p class="subtitle">关键字：{{ keyword || '全部' }}</p>
      </header>
      <div v-if="loading" class="empty">搜索中...</div>
      <div v-else-if="!books.length" class="empty">暂无匹配的书籍</div>
      <div v-else class="book-list-container">
        <div class="book-item" v-for="book in books" :key="book.productId">
          <router-link :to="`/book/${book.productId}`">
            <img :src="resolveImage(book.imageUrl)" alt="" />
            <p class="name">{{ book.name }}</p>
          </router-link>
          <p class="price">￥{{ book.price || '待定' }}</p>
          <div class="actions">
            <button class="btn" @click="addCart(book)">加入购物车</button>
            <button class="btn ghost" @click="toggleFav(book)">收藏</button>
          </div>
        </div>
      </div>
    </section>
    
</template>
<script setup>
import axios from "axios";
import { onMounted, ref, watch } from "vue";
import { useRoute } from "vue-router";
import { useUserStore } from "@/stores/user";

const route = useRoute();
const userStore = useUserStore()
const keyword = ref('')
const books = ref([])
const loading = ref(false)

const resolveImage = (src) => {
  if (!src) return 'https://via.placeholder.com/160x200?text=Book'
  return src.startsWith('http') ? src : `/api/res/images/${src}`
}

const fetchData = () => {
  loading.value = true
  const wd = route.query?.wd || ''
  keyword.value = wd
  axios.get("/products/list", { params: { page: 1, pageSize: 100 } })
    .then(res => {
      const list = res.data?.data || []
      books.value = list.filter((item) => item.name?.includes(wd))
    })
    .catch(err => {
      console.log("请求失败：", err);
    })
    .finally(() => {
      loading.value = false
    })
}

const addCart = (book) => {
  userStore.addToCart(book.productId, 1)
}

const toggleFav = (book) => {
  userStore.toggleFavorite(book.productId)
}

onMounted(fetchData)
watch(() => route.query?.wd, fetchData)
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

.empty {
  text-align: center;
  padding: 30px 0;
  color: #888;
}

.book-list-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 16px;
}

.book-item {
  background: #fafafa;
  padding: 12px;
  border-radius: 10px;
  border: 1px solid #eee;
}

.book-item img {
  width: 100%;
  height: 220px;
  object-fit: cover;
  border-radius: 8px;
}

.name {
  margin: 8px 0 4px;
  color: #333;
}

.price {
  color: #e43f3b;
  font-weight: 700;
}

.actions {
  display: flex;
  gap: 8px;
  margin-top: 8px;
}

.btn {
  flex: 1;
  padding: 6px 10px;
  border-radius: 8px;
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
