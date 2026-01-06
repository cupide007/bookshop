<template>
    <div>
        <h2>图书分类</h2>
        <div v-for="book in books" :key="book.id">
            <h5>{{book.name}}</h5>
            <div class="item" v-for="item in book.children" :key="item.id">
                <router-link to="">{{item.name}}</router-link>
            </div>
        </div>
    </div>
</template>
<script setup>
import axios from "axios";
import { onMounted, ref } from "vue";

const books=ref("")


onMounted(()=>{
    axios.get("/category")
    .then(res=>{
        console.log(res);
        books.value=res.data
        console.log(books);
    })
    .catch(err=>{
        console.log(err);
    })
})
</script>
<style scoped>
.item {
  margin:20px;
}

    
</style>