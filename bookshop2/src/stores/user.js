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
    orders: [],
    lastOrderNumber: ''
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
      this.lastOrderNumber = ''
    },
    async fetchCart() {
      if (!this.token) return
      try {
        const res = await axios.post('/myCart')
        if (res.data?.success) {
          this.cart = res.data.data || []
        } else {
          alert(res.data?.message || '获取购物车失败')
        }
      } catch (e) {
        alert('获取购物车失败')
      }
    },
    async addToCart(productId, quantity = 1) {
      if (!productId) return
      try {
        const res = await axios.get('/addCart', { params: { productId, quantity } })
        if (res.data?.success) {
          alert(res.data?.message || '已添加购物车')
          await this.fetchCart()
          return true
        }
        alert(res.data?.message || '添加购物车失败')
        return false
      } catch (e) {
        alert('添加购物车失败')
        return false
      }
    },
    async updateCart(productId, quantity) {
      if (!productId) return
      try {
        const res = await axios.get('/updateCart', { params: { productId, quantity } })
        if (res.data?.success) {
          alert(res.data?.message || '数量更新成功')
          await this.fetchCart()
          return true
        }
        alert(res.data?.message || '更新数量失败')
      } catch (e) {
        alert('更新数量失败')
      }
    },
    async removeFromCart(productId) {
      if (!productId) return
      try {
        const res = await axios.get('/deleteCart', { params: { productId } })
        if (res.data?.success) {
          alert(res.data?.message || '商品已删除')
          await this.fetchCart()
          return true
        }
        alert(res.data?.message || '删除失败')
      } catch (e) {
        alert('删除失败')
      }
    },
    async fetchFavorites() {
      if (!this.token) return
      try {
        const res = await axios.get('/myFavorites')
        if (res.data?.success) {
          this.favorites = res.data.data || []
        } else {
          alert(res.data?.message || '获取收藏失败')
        }
      } catch (e) {
        alert('获取收藏失败')
      }
    },
    async toggleFavorite(productId) {
      if (!productId) return
      try {
        let res
        if (this.favoriteIds.includes(productId)) {
          res = await axios.get('/removeFavorite', { params: { productId } })
        } else {
          res = await axios.get('/addFavorite', { params: { productId } })
        }
        if (res?.data?.success) {
          alert(res.data?.message || '操作成功')
          await this.fetchFavorites()
        } else {
          alert(res?.data?.message || '操作失败')
        }
      } catch (e) {
        alert('操作失败')
      }
    },
    async fetchOrders() {
      if (!this.token) return
      try {
        const res = await axios.get('/myOrders')
        if (res.data?.success) {
          this.orders = res.data.data || []
        } else {
          alert(res.data?.message || '获取订单失败')
        }
      } catch (e) {
        alert('获取订单失败')
      }
    },
    async checkout(addressId = 1) {
      if (!this.cart.length) return null
      const orderVoList = this.cart.map((item) => ({
        productId: item.productId || item.id,
        quantity: item.quantity
      }))
      try {
        const res = await axios.post('/order', orderVoList, { params: { addressId } })
        if (res.data?.success) {
          this.lastOrderNumber = res.data.data || ''
          alert(res.data?.message || '下单成功')
          await this.fetchCart()
          await this.fetchOrders()
          return res.data.data
        }
        alert(res.data?.message || '下单失败')
      } catch (e) {
        alert('下单失败')
      }
      return null
    },
    async payOrder(orderNumber) {
      if (!orderNumber) return null
      try {
        const res = await axios.post('/payOrder', null, { params: { orderNumber } })
        if (res.data?.success) {
          alert(res.data?.message || '支付成功')
          await this.fetchOrders()
        } else {
          alert(res.data?.message || '支付失败')
        }
        return res.data
      } catch (e) {
        alert('支付失败')
        return null
      }
    }
  },
  persist: true
})
