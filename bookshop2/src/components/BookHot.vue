<template>
    <div>
        <h2>热门推荐</h2>
        <p v-for="book in books" :key="book.id || book.productId">
            <router-link :to="`/book/${book.productId || book.id}`" >{{book.title || book.name}} ￥{{book.price}}</router-link>
        </p>
    </div>
</template>
<script setup>
import axios from "axios";
import { onMounted, ref } from "vue";

const books=ref([])
onMounted(() => {
    axios.get('/book/hot')
    .then(res=>{
        books.value=res.data || []
    })
    .catch(err=>{
        console.log(err)
    })
})
</script>
