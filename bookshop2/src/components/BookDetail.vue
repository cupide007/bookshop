<template>
    <div class="book-detail">
        <div class="book-left">
            <img :src="'/api/res/images/'+book.imageUrl" alt="" width="300" height="400">
        </div>
        <div class="book-right">
            <h2>{{book.name}}</h2>
            <p>
                <span style="color:red"><strong>${{book.price}}</strong></span>
                <span style="margin-left:20px;color:red">{{10}}折</span>
                <span style="margin-left:50px">[定价：<span>￥{{book.price}}</span>]</span>
            </p>
        </div>
    </div>
    <div class="addcart">
        <button @click="count--">-</button>
        <input type="text" style="with:30px" v-model="count">
        <button @click="count++">+</button>
        <button class="bt-add">购物车</button>
    </div>
    <div>
        <div v-for="(image,index) in imageList" :key="index">
            <img :src="'/api/res/images/'+image">
        </div>
    </div>
    <BookTab :introduce="book.detail"/>
</template>
<script setup>
import qs from 'qs';
import axios from "axios";
import { onMounted,ref } from "vue";
import { useRoute } from "vue-router";
import BookTab from "./BookTab.vue";


const route=useRoute();
const book =ref("");
const count =ref(1);
const imageList=ref([]);
onMounted(() => {
    // console.log(route);
    // axios.get(route.fullPath)
    // .then(res=>{
    //     console.log("book:",res);
    //     book.value=res.data
    // })
    // .catch(err=>{
    //     console.log(err)
    // })
    axios.get("/productDetail?" + qs.stringify ({ id: route.params.id }))
        .then(response => {

          console.log (response.data) ;

          book.value = response.data.data;
          imageList.value=response.data.data.description.split(";")

        })
        .catch(error => {
          this.msg = error.message;

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
.book-price{
    color: rgb(114, 111, 111);
    text-decoration: line-through;
}
.addcart{
    margin-top: 20px;
}
.bt-add{
    color: rgb(252, 248, 248);
    background-color: aqua;
    width: 80px;
    border: solid 1px rebeccapurple; 
    height: 30px;
    margin-left: 20px;
}

</style>