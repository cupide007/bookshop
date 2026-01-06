<template> 
    <div>
        <h2>新书上市</h2>
        <div class="list-item">
        <div class="item" v-for="book in books" :key="book.productId">
            <router-link :to="'/book/'+book.productId">
                <img :src="'/api/res/images/'+book.imageUrl" alt="" width="100" height="150" />
                <p>{{book.title}}</p>
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
onMounted(() => {
    axios
    .get("/listByPage?pageNum=1&pageSize=8")
    .then(res => {
        books.value=res.data?.data?.list || []
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
