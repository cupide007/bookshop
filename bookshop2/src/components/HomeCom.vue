<template>
    <div class="home">
        <BookCategory class="home-left"/>
        <BookSwiper class="home-mid"/>
        <BookHot class="home-right"/>
        <BooksNew class="book-new" />
    </div>
</template>
<script setup>
import BookCategory from './BookCategory.vue'
import BookSwiper from './BookSwiper.vue'
import BookHot from './BookHot.vue'
import BooksNew from './BooksNew.vue'
</script>
<style>
.home{
    width: 100%;
    position: relative;
    margin-top: 30px;
    
}
.home-left,.home-right{
    float: left;
    width: 25%;
    
}
.home-mid{
    float: left;
    width: 50%;
}
.book-new{
    width: 100%;
    overflow: hidden;
}
</style>