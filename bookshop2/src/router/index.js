import { createRouter, createWebHashHistory } from "vue-router"
import { useUserStore } from "@/stores/user"

const routes = [
    { path: "/", component: () => import("@/components/HomeCom.vue"), name: "home", meta: { title: "首页" } },
    { path: "/login", component: () => import("@/components/UserLogin.vue"), name: "login", meta: { title: "登录" } },
    { path: "/register", component: () => import("@/components/UserRegister.vue"), name: "register", meta: { title: "注册" } },
    { path: "/book/:id", component: () => import("@/components/BookDetail.vue"), name: "book", meta: { title: "图书详情" } },
    { path: "/search", component: () => import("@/components/SearchBook.vue"), name: "search", meta: { title: "搜索" } },
    { path: "/new-books", component: () => import("@/components/BooksNew.vue"), name: "newBooks", meta: { title: "新书上市" } },
    { path: "/hot-recommend", component: () => import("@/components/BookHot.vue"), name: "hotRecommend", meta: { title: "热门推荐" } },
    { path: "/video-resources", component: () => import("@/components/VideoResource.vue"), name: "videos", meta: { title: "视频资源" } },
    { path: "/personal-center", component: () => import("@/components/PersonalCenter.vue"), name: "personalCenter", meta: { title: "个人中心", requiresAuth: true } },
    { path: "/cart", component: () => import("@/components/CartPage.vue"), name: "cart", meta: { title: "购物车", requiresAuth: true } },
    { path: "/orders", component: () => import("@/components/OrdersPage.vue"), name: "orders", meta: { title: "订单中心", requiresAuth: true } },
    { path: "/favorites", component: () => import("@/components/FavoritesPage.vue"), name: "favorites", meta: { title: "收藏夹", requiresAuth: true } },
    { path: "/pay", component: () => import("@/components/PaymentPage.vue"), name: "pay", meta: { title: "支付订单", requiresAuth: true } },
]
const router = createRouter({
    history: createWebHashHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    const userStore = useUserStore()
    if (to.meta.requiresAuth && !userStore.isLoggedIn) {
        next({ path: "/login", query: { redirect: to.fullPath } })
        return
    }
    next()
})

export default router
