import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import VueAxios from 'vue-axios'
import { useUserStore } from './stores/user'
import { toast } from './utils/feedback'

const app = createApp(App)
const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)

app.use(router)
app.use(pinia)
axios.defaults.baseURL = "/api"

const userStore = useUserStore(pinia)
let handlingUnauthorized = false

const handleUnauthorized = (message) => {
  if (handlingUnauthorized) return
  handlingUnauthorized = true
  userStore.logout()
  toast(message || '请先登录', 'error')
  if (router.currentRoute.value.name !== 'login') {
    router.push({ path: '/login', query: { redirect: router.currentRoute.value.fullPath } })
  }
  setTimeout(() => {
    handlingUnauthorized = false
  }, 800)
}

axios.interceptors.request.use((config) => {
  if (userStore.token) {
    config.headers.Authorization = userStore.token
  }
  return config
})

axios.interceptors.response.use(
  (response) => {
    if (response?.data?.status === 401) {
      handleUnauthorized(response?.data?.message)
      return Promise.reject(response?.data || response)
    }
    return response
  },
  (error) => {
    const status = error?.response?.status
    if (status === 401) {
      handleUnauthorized(error?.response?.data?.message)
    }
    return Promise.reject(error)
  }
)

app.use(VueAxios, axios)
app.mount('#app')
