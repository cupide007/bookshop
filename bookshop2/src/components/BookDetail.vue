<template>
    <div class="book-detail">
        <div class="book-left">
            <img :src="resolveImage(book.imageUrl)" alt="" width="300" height="400">
        </div>
        <div class="book-right">
            <h2>{{book.name}}</h2>
            <p>
                <span style="color:red"><strong>￥{{book.price}}</strong></span>
                <span style="margin-left:20px;color:red">{{10}}折</span>
                <span style="margin-left:50px">[定价：<span>￥{{book.price}}</span>]</span>
            </p>
            <p class="desc">{{ book.detail?.introduce || '图书简介加载中...' }}</p>
            <div class="action-row">
                <button @click="decrease">-</button>
                <input type="text" class="quantity" v-model.number="count">
                <button @click="increase">+</button>
                <button class="bt-add" @click="addCart">加入购物车</button>
                <button class="bt-secondary" @click="toggleFav">{{ favoriteText }}</button>
            </div>
        </div>
    </div>
    <div class="gallery">
        <div v-for="(image,index) in imageList" :key="index">
            <img :src="resolveImage(image)">
        </div>
    </div>
    <BookTab :introduce="book.detail"/>
</template>
<script setup>
import qs from 'qs';
import axios from "axios";
import { computed, onMounted,ref } from "vue";
import { useRoute } from "vue-router";
import BookTab from "./BookTab.vue";
import { useUserStore } from "@/stores/user";


const route=useRoute();
const userStore = useUserStore()
const book =ref({});
const count =ref(1);
const imageList=ref([]);
const favoriteText = computed(() => userStore.favoriteIds.includes(book.value.id || book.value.productId) ? '取消收藏' : '收藏')

const resolveImage = (src) => {
    if (!src) return 'https://via.placeholder.com/300x400?text=Book'
    return src.startsWith('http') ? src : `/api/res/images/${src}`
}

const decrease = () => {
    count.value = Math.max(1, count.value - 1)
}

const increase = () => {
    count.value += 1
}

const addCart = () => {
    const id = book.value.id || book.value.productId
    if (!id) return
    userStore.addToCart({
        id,
        name: book.value.name,
        price: book.value.price,
        imageUrl: book.value.imageUrl,
        quantity: count.value
    })
}

const toggleFav = () => {
    const id = book.value.id || book.value.productId
    if (!id) return
    userStore.toggleFavorite({
        id,
        name: book.value.name,
        price: book.value.price,
        imageUrl: book.value.imageUrl
    })
}

onMounted(() => {
    axios.get("/productDetail?" + qs.stringify ({ id: route.params.id }))
        .then(response => {

          console.log (response.data) ;

          book.value = response.data.data;
          book.value.id = response.data.data?.productId || response.data.data?.id
          imageList.value=response.data.data.description.split(";")

        })
        .catch(error => {
          if (error.response.status == 504)
          {

            alert ("服务器没启动") ;

          }
        })
});
</script>
<style>
.book-detail{
   display: flex;
}
.book-left{
    flex: 1;
}
.book-right{
    flex: 4;
    text-align: left;
    color: rgb(114, 111, 111);
    padding: 10px;
}
.desc{
    margin: 14px 0;
}
.book-price{
    color: rgb(114, 111, 111);
    text-decoration: line-through;
}
.addcart{
    margin-top: 20px;
}
.action-row{
    display: flex;
    align-items: center;
    gap: 8px;
}
.quantity{
    width: 60px;
    text-align: center;
}
.bt-add{
    color: rgb(252, 248, 248);
    background-color: aqua;
    width: 80px;
    border: solid 1px rebeccapurple; 
    height: 30px;
    margin-left: 20px;
}
.bt-secondary{
    border: 1px solid #2563eb;
    color: #2563eb;
    background: #fff;
    height: 30px;
    padding: 0 12px;
}
.gallery{
    display: flex;
    gap: 10px;
    margin: 16px 0;
}
.gallery img{
    width: 120px;
    height: 160px;
    object-fit: cover;
    border-radius: 6px;
}
</style>
