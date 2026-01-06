import { createRouter, createWebHashHistory } from "vue-router"

const routes=[
    {path:"/",component:()=>import("@/components/HomeCom.vue"),name:"home",meta:{title:"首页"}},
    {path:"/login",component:()=>import("@/components/UserLogin.vue"),name:"login",meta:{title:"登录"}},
    {path:"/register",component:()=>import("@/components/UserRegister.vue"),name:"register",meta:{title:"注册"}},
    {path:"/book/:id",component:()=>import("@/components/BookDetail.vue"),name:"book",meta:{title:"图书详情"}},
    
]
const router=createRouter({
    history:createWebHashHistory(),
    routes
})

export default router