<template> 
    <div>
        <h2>新品上架</h2>
        <div class="list-item">
        <div class="item" v-for="book in books" :key="book.productId">
            <router-link :to="'/book/'+book.productId">
                <img :src="resolveImage(book.imageUrl)" alt="" width="100" height="150" />
                <p>{{book.name}}</p>
            </router-link>
            <p>￥{{ book.price }}</p>
        </div>
    </div>
    </div>


</template>
<script setup>
import axios from "axios";
import { onMounted, ref } from "vue";

const books=ref([])

const resolveImage = (src) => {
    if (!src) return 'https://via.placeholder.com/120x160?text=Book'
    return src.startsWith('http') ? src : `/api/res/images/${src}`
}

onMounted(() => {
    axios
    .get("/products/list",{params:{page:1,pageSize:20}})
    .then(res => {
        const list = res.data?.data || []
        books.value=list.slice(-8).reverse()
    })
    .catch(err=>{
        console.log(err);
    })
})

</script>
<style>

.list-item{
    display: flex;
    flex-wrap: wrap;
    gap: 12px;
}
.item{
    flex: 1 1 20%;
}

    
</style>
