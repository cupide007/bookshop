<template>
    <div style="text-align:left">
        <button :class="[{btactive:currentIndex==index},{basestyle:true}]" v-for="(tab,index) in tabs" :key="index"
        @click="currentIndex=index">
            {{tab.name}}
        </button>
        <component :is="CurrentComponent[currentIndex]" :detail="introduce"></component>
    </div>
</template>
<script setup>
import BookIntroduce from "./BookIntroduce1.vue"
import BookComment from "./BookComment.vue"
import BookQuestion from "./BookQuestion.vue"
import { reactive,ref,defineProps } from "vue";

const introduce=defineProps(["introduce"])
const tabs=reactive([
    {title:"Introduce",name:"图书介绍"},
    {title:"Comment",name:"图书评论"},
    {title:"Question",name:"图书问答"},

])
const currentIndex=ref(0)
const CurrentComponent=ref([
    BookIntroduce,BookComment,BookQuestion
])
</script>
<style>
.btactive{
    background-color: rgb(77, 173, 211);
    border: solid 1px rgb(77, 173, 211);
    color:white;
    height: 30px;
}
.basestyle{
    height: 30px;
}
</style>
