<template>
  <section class="detail-page">
    <div class="hero">
      <div class="gallery-main">
        <img :src="resolveImage(book.imageUrl)" alt="" />
      </div>
      <div class="info">
        <div class="info-top">
          <h2 class="title">{{ book.name || '商品加载中' }}</h2>
          <p class="sub">编号：{{ book.productId || '-' }} · 分类：{{ categoryName }}</p>
          <div class="price-row">
            <span class="price">￥{{ book.price || 0 }}</span>
            <span class="tag">库存 {{ book.stock || 0 }}</span>
            <span class="tag neutral">{{ discountLabel }}</span>
          </div>
          <div class="action-row">
            <div class="quantity-box">
              <button @click="decrease">-</button>
              <input type="number" v-model.number="count" min="1" />
              <button @click="increase">+</button>
            </div>
            <button class="bt-primary" @click="addCart">加入购物车</button>
            <button class="bt-secondary" @click="toggleFav">{{ favoriteText }}</button>
          </div>
        </div>
        <div class="thumb-panel" v-if="imageList.length">
          <div v-for="(image,index) in imageList" :key="index" class="thumb-item large">
            <img :src="resolveImage(image)" alt="">
          </div>
        </div>
      </div>
    </div>

    <section class="reviews">
      <header class="reviews-head">
        <div>
          <h3>商品评价</h3>
          <p class="muted">分享你的购买体验，帮助更多用户</p>
        </div>
        <div class="review-form">
          <select v-model.number="rating" class="rating-select">
            <option v-for="n in 5" :key="n" :value="n">{{ n }} 星</option>
          </select>
          <textarea
            v-model="newReview"
            placeholder="写下你的使用感受"
            rows="3"
          ></textarea>
          <div class="uploader">
            <label class="upload-label">
              <span>上传图片</span>
              <input type="file" accept="image/*" @change="handleImageChange">
            </label>
            <div v-if="uploadPreview" class="preview">
              <img :src="uploadPreview" alt="">
              <button type="button" class="clear" @click="clearImage">移除</button>
            </div>
          </div>
          <button class="bt-primary" :disabled="submitting" @click="submitReview">
            {{ submitting ? '提交中...' : '提交评价' }}
          </button>
        </div>
      </header>
      <div v-if="reviews.length" class="review-list">
        <article v-for="item in reviews" :key="item.id || item.commentId" class="review-card">
          <div class="review-meta">
            <span class="name">{{ item.username || '匿名用户' }}</span>
            <span class="stars">{{ renderStars(item.rating) }}</span>
            <span class="time">{{ item.commentDate || item.createdAt || '' }}</span>
          </div>
          <p class="content">{{ item.comment }}</p>
          <div v-if="item.imageUrl" class="review-images">
            <img :src="resolveImage(item.imageUrl)" alt="">
          </div>
        </article>
      </div>
      <div v-else class="empty">还没有评价，快来成为第一个吧</div>
    </section>

    <div class="thumbs" v-if="imageList.length">
      <div v-for="(image,index) in imageList" :key="index" class="thumb-item">
        <img :src="resolveImage(image)" alt="">
      </div>
    </div>
  </section>
</template>
<script setup>
import qs from 'qs'
import axios from "axios"
import { computed, onMounted, ref, watch } from "vue"
import { useRoute, useRouter } from "vue-router"
import { useUserStore } from "@/stores/user"
import { toast } from "@/utils/feedback"

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const book = ref({})
const count = ref(1)
const imageList = ref([])
const reviews = ref([])
const newReview = ref('')
const rating = ref(5)
const submitting = ref(false)
const selectedFile = ref(null)
const uploadPreview = ref('')

const favoriteText = computed(() => userStore.favoriteIds.includes(book.value.productId) ? '取消收藏' : '收藏')

const categoryMap = {
  1: '电子产品',
  2: '服装',
  3: '家居用品',
  4: '食品'
}

const categoryName = computed(() => {
  const id = book.value.categoryId
  return categoryMap[id] || '其他'
})

const discountLabel = computed(() => {
  if (book.value.discount) return `${book.value.discount}折`
  return '热卖'
})

const resolveImage = (src) => {
  if (!src) return 'https://via.placeholder.com/360x440?text=Goods'
  return src.startsWith('http') ? src : `/api/res/images/${src}`
}

const decrease = () => {
  count.value = Math.max(1, count.value - 1)
}

const increase = () => {
  count.value += 1
}

const addCart = () => {
  const id = book.value.productId
  if (!id) return
  userStore.addToCart(id, count.value)
}

const toggleFav = () => {
  const id = book.value.productId
  if (!id) return
  userStore.toggleFavorite(id)
}

const renderStars = (value) => {
  const score = Number(value) || 0
  const full = Math.max(0, Math.min(5, Math.round(score)))
  return '★'.repeat(full) + '☆'.repeat(5 - full)
}

const fetchDetail = async () => {
  const id = route.params.id
  if (!id) return
  try {
    const res = await axios.get("/products/detail?" + qs.stringify({ id }))
    book.value = res.data?.data || {}
    imageList.value = (book.value.imageUrl || "").split(";").filter(Boolean)
    userStore.fetchFavorites()
    fetchReviews()
  } catch (error) {
    toast('商品详情加载失败', 'error')
  }
}

const fetchReviews = async () => {
  const id = route.params.id
  if (!id) return
  try {
    const res = await axios.get('/productReviews', { params: { productId: id } })
    if (res.data?.success) {
      reviews.value = res.data.data || []
    } else {
      reviews.value = []
    }
  } catch (e) {
    reviews.value = []
  }
}

const submitReview = async () => {
  if (!userStore.isLoggedIn) {
    toast('请先登录后再评价', 'error')
    router.push({ path: '/login', query: { redirect: route.fullPath } })
    return
  }
  if (!newReview.value.trim()) {
    toast('请输入评价内容', 'error')
    return
  }
  const id = book.value.productId
  if (!id) return
  submitting.value = true
  let imageUrl = ''
  if (selectedFile.value) {
    try {
      const form = new FormData()
      form.append('file', selectedFile.value)
      const uploadRes = await axios.post('/upload', form, { headers: { 'Content-Type': 'multipart/form-data' } })
      if (uploadRes.data?.success) {
        imageUrl = uploadRes.data.data
      } else {
        toast(uploadRes.data?.message || '图片上传失败', 'error')
      }
    } catch (e) {
      toast('图片上传失败', 'error')
    }
  }
  try {
    const payload = { productId: id, comment: newReview.value.trim(), rating: rating.value }
    if (imageUrl) {
      payload.imageUrl = imageUrl
    }
    const res = await axios.post('/addReview', payload)
    if (res.data?.success) {
      toast(res.data?.message || '评价成功', 'success')
      newReview.value = ''
      resetImage()
      await fetchReviews()
    } else {
      toast(res.data?.message || '评价失败', 'error')
    }
  } catch (e) {
    toast('评价失败', 'error')
  } finally {
    submitting.value = false
  }
}

onMounted(fetchDetail)

const resetImage = () => {
  if (uploadPreview.value) {
    URL.revokeObjectURL(uploadPreview.value)
  }
  selectedFile.value = null
  uploadPreview.value = ''
}

const handleImageChange = (event) => {
  const file = event.target.files?.[0]
  resetImage()
  if (file) {
    selectedFile.value = file
    uploadPreview.value = URL.createObjectURL(file)
  }
}

const clearImage = () => {
  resetImage()
}

watch(() => route.params.id, () => {
  count.value = 1
  fetchDetail()
})
</script>
<style>
.detail-page{
  padding: 20px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 2px 12px rgba(0,0,0,0.04);
}
.hero{
  display: grid;
  grid-template-columns: 360px 1fr;
  gap: 24px;
  align-items: start;
}
.gallery-main img{
  width: 100%;
  height: 440px;
  object-fit: cover;
  border-radius: 12px;
  background: #f8fafc;
}
.info{
  display: flex;
  flex-direction: column;
  gap: 16px;
  color: #374151;
}
.info-top{
  display: flex;
  flex-direction: column;
  gap: 12px;
}
.title{
  margin: 0;
}
.sub{
  margin: 0;
  color: #6b7280;
  font-size: 14px;
}
.price-row{
  display: flex;
  align-items: center;
  gap: 10px;
}
.price{
  color: #ef4444;
  font-size: 28px;
  font-weight: 700;
}
.tag{
  background: #fee2e2;
  color: #b91c1c;
  padding: 4px 10px;
  border-radius: 999px;
  font-size: 13px;
}
.tag.neutral{
  background: #e0e7ff;
  color: #4338ca;
}
.desc{
  line-height: 1.6;
  color: #4b5563;
}
.action-row{
  display: flex;
  align-items: center;
  gap: 10px;
  margin-top: 10px;
}
.quantity-box{
  display: flex;
  align-items: center;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  overflow: hidden;
}
.quantity-box button{
  background: #f3f4f6;
  border: none;
  width: 36px;
  height: 36px;
  cursor: pointer;
}
.quantity-box input{
  width: 60px;
  height: 36px;
  border: none;
  text-align: center;
}
.bt-primary{
  background: #2563eb;
  color: #fff;
  border: none;
  padding: 10px 18px;
  border-radius: 10px;
  cursor: pointer;
}
.bt-secondary{
  border: 1px solid #2563eb;
  color: #2563eb;
  background: #fff;
  height: 40px;
  padding: 0 14px;
  border-radius: 10px;
  cursor: pointer;
}
.thumb-panel{
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(140px, 1fr));
  gap: 12px;
}
.thumb-item.large img{
  width: 100%;
  height: 160px;
  object-fit: cover;
  border-radius: 10px;
  background: #f3f4f6;
}
.thumbs{
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(120px, 1fr));
  gap: 10px;
  margin: 20px 0;
}
.thumb-item img{
  width: 100%;
  height: 120px;
  object-fit: cover;
  border-radius: 8px;
  background: #f9fafb;
}
.reviews{
  margin-top: 20px;
  padding: 16px;
  background: #fafafa;
  border-radius: 12px;
  border: 1px solid #e5e7eb;
}
.reviews-head{
  display: flex;
  justify-content: space-between;
  gap: 12px;
  flex-wrap: wrap;
  align-items: center;
}
.muted{
  color: #6b7280;
  margin: 4px 0 0;
}
.review-form{
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
  align-items: flex-start;
}
.rating-select{
  height: 38px;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  padding: 0 10px;
}
.review-form textarea{
  flex: 1;
  min-width: 240px;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  padding: 8px 10px;
}
.uploader{
  display: flex;
  align-items: center;
  gap: 10px;
}
.upload-label{
  display: inline-flex;
  align-items: center;
  gap: 6px;
  border: 1px dashed #9ca3af;
  padding: 8px 12px;
  border-radius: 10px;
  cursor: pointer;
  color: #374151;
  font-size: 14px;
}
.upload-label input{
  display: none;
}
.preview{
  display: flex;
  align-items: center;
  gap: 8px;
}
.preview img{
  width: 64px;
  height: 64px;
  object-fit: cover;
  border-radius: 8px;
  border: 1px solid #e5e7eb;
}
.clear{
  border: none;
  background: #ef4444;
  color: #fff;
  padding: 6px 10px;
  border-radius: 8px;
  cursor: pointer;
}
.review-list{
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-top: 14px;
}
.review-card{
  border: 1px solid #e5e7eb;
  border-radius: 10px;
  padding: 12px;
  background: #fff;
}
.review-meta{
  display: flex;
  align-items: center;
  gap: 12px;
}
.name{
  font-weight: 600;
}
.stars{
  color: #f59e0b;
  font-size: 14px;
}
.time{
  color: #9ca3af;
  font-size: 13px;
}
.content{
  margin: 8px 0 0;
  color: #4b5563;
}
.review-images{
  margin-top: 8px;
}
.review-images img{
  width: 120px;
  height: 120px;
  object-fit: cover;
  border-radius: 10px;
  border: 1px solid #e5e7eb;
}
.empty{
  margin-top: 12px;
  color: #6b7280;
}
</style>
