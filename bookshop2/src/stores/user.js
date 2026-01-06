import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: '',
    profile: {
      username: '',
      phone: '',
    },
    cart: [],
    favorites: [],
    orders: []
  }),
  getters: {
    isLoggedIn: (state) => !!state.token,
    cartCount: (state) => state.cart.reduce((total, item) => total + item.quantity, 0),
    cartTotal: (state) => state.cart.reduce((total, item) => total + item.quantity * (item.price || 0), 0),
    favoriteIds: (state) => state.favorites.map((item) => item.id)
  },
  actions: {
    setUser({ token, username, phone }) {
      this.token = token || ''
      this.profile.username = username || ''
      this.profile.phone = phone || ''
    },
    logout() {
      this.token = ''
      this.profile = { username: '', phone: '' }
      this.cart = []
      this.favorites = []
    },
    addToCart(product) {
      if (!product?.id) {
        return
      }
      const existIndex = this.cart.findIndex((item) => item.id === product.id)
      if (existIndex > -1) {
        this.cart[existIndex].quantity += product.quantity || 1
      } else {
        this.cart.push({
          id: product.id,
          name: product.name,
          price: product.price || 0,
          imageUrl: product.imageUrl || '',
          quantity: product.quantity || 1
        })
      }
    },
    removeFromCart(productId) {
      this.cart = this.cart.filter((item) => item.id !== productId)
    },
    toggleFavorite(product) {
      if (!product?.id) {
        return
      }
      const existIndex = this.favorites.findIndex((item) => item.id === product.id)
      if (existIndex > -1) {
        this.favorites.splice(existIndex, 1)
      } else {
        this.favorites.push({
          id: product.id,
          name: product.name,
          price: product.price || 0,
          imageUrl: product.imageUrl || ''
        })
      }
    },
    checkout() {
      if (!this.cart.length) return
      const newOrder = {
        id: Date.now(),
        items: JSON.parse(JSON.stringify(this.cart)),
        total: this.cart.reduce((total, item) => total + item.price * item.quantity, 0),
        createdAt: new Date().toLocaleString()
      }
      this.orders.unshift(newOrder)
      this.cart = []
      return newOrder
    }
  },
  persist: true
})
