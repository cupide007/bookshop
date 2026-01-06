import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import VueAxios from 'vue-axios'
import { useUserStore } from './stores/user'

const app = createApp(App)
const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)

app.use(router)
app.use(pinia)
axios.defaults.baseURL = "/api"

const userStore = useUserStore(pinia)
axios.interceptors.request.use((config) => {
  if (userStore.token) {
    config.headers.Authorization = userStore.token
  }
  return config
})

app.use(VueAxios, axios)
app.mount('#app')
