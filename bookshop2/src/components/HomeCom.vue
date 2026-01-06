<template>
    <div class="home">
        <aside class="side">
            <BookCategory/>
        </aside>
    </div>
</template>
<script setup>
import BookCategory from './BookCategory.vue'
</script>
<style>
.home{
    width: 100%;
    margin-top: 30px;
}
.side{
    background: #fff;
    padding: 12px;
    border-radius: 10px;
    box-shadow: 0 2px 10px rgba(0,0,0,0.03);
}
</style>
