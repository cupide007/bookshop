 <!--
<template>
    <div>搜索结果</div>
</template>
<script setup>


import axios from "axios";
import { onMounted } from "vue";
import { useRouter } from "vue-router";

const route=useRouter()
onMounted(()=>{
    console.log(route);
    console.log("search?wd="+route.query.wd+"&pageNum=1&pageSize=5");
    axios.get("/search?"+route.query.wd+"&pageNum=1&pageSize=5")
    .then(res=>{
        console.log(res);
    })
    .catch(err=>{
        console.log(err);
    })
})
</script>
-->
<template>
    <div>搜索结果</div>
    <div class="book-list-container">
    <!-- 书籍列表项循环渲染 -->
    <div class="book-item" v-for="book in books" :key="book.productId">
      <router-link :to="'/book/'+book.productId">
        <img :src="'/api/res/images'+book.imageUrl" alt="" />
        <p>{{ book.name}}</p>
      </router-link>
       <p>{{ ¥book.name}}</p>


      
    </div>
  </div>
    
</template>
<script setup>
import axios from "axios";
import { onMounted } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();

onMounted(()=>{
    console.log("当前路由信息：", route); // 现在能看到 query 里的 wd 参数了
    
    // 3. 安全读取 wd 参数，添加可选链和默认值，避免 undefined 报错
    const wd = route.query?.wd || '';
    // 4. 规范拼接参数，空值时也不会导致 URL 格式错误
    const requestUrl = `/search?wd=${wd}&pageNum=1&pageSize=5`;
    console.log("请求URL：", requestUrl);
    
    axios.get(requestUrl)
    .then(res=>{
        console.log("请求成功：", res);
    })
    .catch(err=>{
        console.log("请求失败：", err);
    })
})
</script>
