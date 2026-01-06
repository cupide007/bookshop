<template>
  <section class="page">
    <header class="page-header">
      <h2>视频资源</h2>
      <p class="subtitle">观看图书导读、上新介绍等短视频</p>
    </header>

    <div class="video-grid">
      <article class="video-card" v-for="item in videos" :key="item.id">
        <div class="thumb" :style="{ backgroundImage: `url(${item.cover})` }">
          <span class="duration">{{ item.duration }}</span>
        </div>
        <h3>{{ item.title }}</h3>
        <p class="desc">{{ item.description }}</p>
      </article>
    </div>
  </section>
</template>

<script setup>
import { ref } from 'vue'

const videos = ref([
  {
    id: 1,
    title: '《活着》导读',
    duration: '04:32',
    description: '快速了解余华经典作品的背景与主旨',
    cover: 'https://via.placeholder.com/320x180?text=Video'
  },
  {
    id: 2,
    title: '2024 春季新书速览',
    duration: '03:11',
    description: '本月上新 10 本人气书籍合集',
    cover: 'https://via.placeholder.com/320x180?text=Book+List'
  },
  {
    id: 3,
    title: '阅读习惯提升指南',
    duration: '05:20',
    description: '专业编辑分享提升阅读效率的 5 个技巧',
    cover: 'https://via.placeholder.com/320x180?text=Tips'
  }
])
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

.video-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
  gap: 14px;
}

.video-card {
  border: 1px solid #eee;
  border-radius: 10px;
  padding: 12px;
  background: #fafafa;
}

.thumb {
  width: 100%;
  height: 150px;
  background-size: cover;
  background-position: center;
  border-radius: 8px;
  position: relative;
}

.duration {
  position: absolute;
  right: 8px;
  bottom: 8px;
  background: rgba(0, 0, 0, 0.6);
  color: #fff;
  padding: 2px 8px;
  border-radius: 10px;
  font-size: 12px;
}

.desc {
  color: #666;
}
</style>
