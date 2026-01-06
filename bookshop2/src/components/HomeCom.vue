<template>
  <div class="home">
    <div class="content">
      <BookCategory/>
    </div>
  </div>
</template>
<script setup>
import BookCategory from './BookCategory.vue'
</script>
<style>
.home{
  width: 100%;
  margin-top: 30px;
  display: flex;
  justify-content: center;
}
.content{
  width: 1200px;
}
</style>
