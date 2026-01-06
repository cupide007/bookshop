<template>
    <div class="home">
        <aside class="side">
            <BookCategory/>
        </aside>
        <section class="main">
            <BookHot class="block"/>
            <BooksNew class="block" />
        </section>
    </div>
</template>
<script setup>
import BookCategory from './BookCategory.vue'
import BookHot from './BookHot.vue'
import BooksNew from './BooksNew.vue'
</script>
<style>
.home{
    width: 100%;
    display: grid;
    grid-template-columns: 1fr 2fr;
    gap: 20px;
    margin-top: 30px;
}
.side{
    background: #fff;
    padding: 12px;
    border-radius: 10px;
    box-shadow: 0 2px 10px rgba(0,0,0,0.03);
}
.main{
    display: flex;
    flex-direction: column;
    gap: 16px;
}
.block{
    background: #fff;
    padding: 12px;
    border-radius: 10px;
    box-shadow: 0 2px 10px rgba(0,0,0,0.03);
}
</style>
