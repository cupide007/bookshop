<template>
    <div>
        <h2>热门推荐</h2>
        <p v-for="book in books" :key="book.id">
            <router-link to="" >{{book.title}} ￥{{book.price}}</router-link>
        </p>
    </div>
</template>
<script setup>
import axios from "axios";
import { onMounted, ref } from "vue";

const books=ref("")
onMounted(() => {
    axios.get('/book/hot')
    .then(res=>{
        console.log("hot:",res);
        books.value=res.data
    })
    .catch(err=>{
        console.log(err)
    })
})
</script>