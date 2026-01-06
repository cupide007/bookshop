import { defineStore } from 'pinia'
import axios from 'axios'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: '',
    profile: {
      username: '',
      phone: '',
    },
    userId: null,
    cart: [],
    favorites: [],
    orders: []
  }),
  getters: {
    isLoggedIn: (state) => !!state.token,
    cartCount: (state) => state.cart.reduce((total, item) => total + item.quantity, 0),
    cartTotal: (state) => state.cart.reduce((total, item) => total + item.quantity * (item.price || 0), 0),
    favoriteIds: (state) => state.favorites.map((item) => item.productId || item.id)
  },
  actions: {
    setUser({ token, username, phone, userId }) {
      this.token = token || ''
      this.profile.username = username || ''
      this.profile.phone = phone || ''
      this.userId = userId || null
    },
    logout() {
      this.token = ''
      this.profile = { username: '', phone: '' }
      this.userId = null
      this.cart = []
      this.favorites = []
      this.orders = []
    },
    async fetchCart() {
      if (!this.token) return
      const res = await axios.post('/myCart')
      if (res.data?.success) {
        this.cart = res.data.data || []
      }
    },
    async addToCart(productId, quantity = 1) {
      if (!productId) return
      await axios.get('/addCart', { params: { productId, quantity } })
      await this.fetchCart()
    },
    async updateCart(productId, quantity) {
      if (!productId) return
      await axios.get('/updateCart', { params: { productId, quantity } })
      await this.fetchCart()
    },
    async removeFromCart(productId) {
      if (!productId) return
      await axios.get('/deleteCart', { params: { productId } })
      await this.fetchCart()
    },
    async fetchFavorites() {
      if (!this.token) return
      const res = await axios.get('/myFavorites')
      if (res.data?.success) {
        this.favorites = res.data.data || []
      }
    },
    async toggleFavorite(productId) {
      if (!productId) return
      if (this.favoriteIds.includes(productId)) {
        await axios.get('/removeFavorite', { params: { productId } })
      } else {
        await axios.get('/addFavorite', { params: { productId } })
      }
      await this.fetchFavorites()
    },
    async fetchOrders() {
      if (!this.token) return
      const res = await axios.get('/myOrders')
      if (res.data?.success) {
        this.orders = res.data.data || []
      }
    },
    async checkout(addressId = 1) {
      if (!this.cart.length) return null
      const orderVoList = this.cart.map((item) => ({
        productId: item.productId || item.id,
        quantity: item.quantity
      }))
      const res = await axios.post('/order', orderVoList, { params: { addressId } })
      if (res.data?.success) {
        await this.fetchCart()
        await this.fetchOrders()
        return res.data.data
      }
      return null
    }
  },
  persist: true
})
