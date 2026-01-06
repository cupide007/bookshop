<template>
    <div class="search">
        <input
          class="search-value"
          type="text"
          v-model="keyword"
          placeholder="搜索书名、作者"
          @keyup.enter="triggerSearch"
        >
        <button class="bt-search" @click="triggerSearch">搜索</button>
    </div>
</template>

<script setup>
/* global defineEmits */
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const keyword = ref('')
const emit = defineEmits(['search'])

const triggerSearch = () => {
  const wd = keyword.value.trim()
  emit('search', wd)
  router.push({
    path: '/search',
    query: { wd }
  })
}
</script>

<style scoped>
.search{
    display: inline-flex;
    gap: 8px;
    width: 100%;
}
.search-value{
    flex: 1;
    height: 36px;
    padding: 0 10px;
    border-radius: 8px;
    border: 1px solid #dcdfe6;
}
.bt-search{
    background-color: #2563eb;
    color: white;
    height: 36px;
    border: #2563eb solid 1px;
    width: 90px;
    border-radius: 8px;
    cursor: pointer;
}
</style>
