<template>
    <div>
        <h2>热门推荐</h2>
        <p v-for="book in books" :key="book.productId">
            <router-link :to="`/book/${book.productId}`" >{{book.name}} ￥{{book.price}}</router-link>
        </p>
    </div>
</template>
<script setup>
import axios from "axios";
import { onMounted, ref } from "vue";

const books=ref([])
onMounted(() => {
    axios.get('/products/list',{params:{page:1,pageSize:20}})
    .then(res=>{
        const list = res.data?.data || []
        books.value=list.slice(0,6)
    })
    .catch(err=>{
        console.log(err)
    })
})
</script>
