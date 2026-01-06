<template>
    <h3>图书评论</h3>
    <div v-for="(item,index) in comments" :key="index" class="comment">
        <p>{{item.username}} <span style="margin-left:50px">{{item.commentDate}}</span></p>
        <p>{{item.content}}</p>
    </div>
</template>
<script setup>
import axios from "axios";
import { onMounted,ref} from "vue";
import {useRoute} from "vue-router";

const route=useRoute()
const comments=ref("")
onMounted(() => {
    console.log("route2:",route.fullPath);
    axios.get(route.fullPath+'/comment')
    .then(res=>{
        console.log("comments:",res);
        comments.value=res.data
    })
    .catch(err=>{
        console.log(err);
    })
})
</script>
<style>
.comment{
    text-align: left;
    border-bottom: solid 1px white ;
}
</style>