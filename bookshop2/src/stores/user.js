import { defineStore } from 'pinia'
import axios from 'axios'
import { toast } from '@/utils/feedback'

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
          toast(res.data?.message || '获取购物车失败', 'error')
        }
      } catch (e) {
        toast('获取购物车失败', 'error')
      }
    },
    async addToCart(productId, quantity = 1) {
      if (!productId) return
      try {
        const res = await axios.get('/addCart', { params: { productId, quantity } })
        if (res.data?.success) {
          const targetQuantity = Math.max(1, quantity)
          await this.fetchCart()
          const current = this.cart.find((item) => item.productId === productId)
          const finalQuantity = current ? current.quantity + (targetQuantity - 1) : targetQuantity
          if (finalQuantity > 1) {
            await this.updateCart(productId, finalQuantity, false)
          }
          toast(res.data?.message || '已添加购物车', 'success')
          return true
        }
        toast(res.data?.message || '添加购物车失败', 'error')
        return false
      } catch (e) {
        toast('添加购物车失败', 'error')
        return false
      }
    },
    async updateCart(productId, quantity, showAlert = true) {
      if (!productId) return
      try {
        const res = await axios.get('/updateCart', { params: { productId, quantity } })
        if (res.data?.success) {
          if (showAlert) {
            toast(res.data?.message || '数量更新成功', 'success')
          }
          await this.fetchCart()
          return true
        }
        if (showAlert) {
          toast(res.data?.message || '更新数量失败', 'error')
        }
      } catch (e) {
        if (showAlert) {
          toast('更新数量失败', 'error')
        }
      }
    },
    async removeFromCart(productId) {
      if (!productId) return
      const ok = window.confirm('确定要删除该商品吗？')
      if (!ok) return
      try {
        const res = await axios.get('/deleteCart', { params: { productId } })
        if (res.data?.success) {
          toast(res.data?.message || '商品已删除', 'success')
          await this.fetchCart()
          return true
        }
        toast(res.data?.message || '删除失败', 'error')
      } catch (e) {
        toast('删除失败', 'error')
      }
    },
    async fetchFavorites() {
      if (!this.token) return
      try {
        const res = await axios.get('/myFavorites')
        if (res.data?.success) {
          this.favorites = res.data.data || []
        } else {
          toast(res.data?.message || '获取收藏失败', 'error')
        }
      } catch (e) {
        toast('获取收藏失败', 'error')
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
          toast(res.data?.message || '操作成功', 'success')
          await this.fetchFavorites()
        } else {
          toast(res?.data?.message || '操作失败', 'error')
        }
      } catch (e) {
        toast('操作失败', 'error')
      }
    },
    async fetchOrders() {
      if (!this.token) return
      try {
        const res = await axios.get('/myOrders')
        if (res.data?.success) {
          this.orders = res.data.data || []
        } else {
          toast(res.data?.message || '获取订单失败', 'error')
        }
      } catch (e) {
        toast('获取订单失败', 'error')
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
          toast(res.data?.message || '下单成功', 'success')
          await this.fetchCart()
          await this.fetchOrders()
          return res.data.data
        }
        toast(res.data?.message || '下单失败', 'error')
      } catch (e) {
        toast('下单失败', 'error')
      }
      return null
    },
    async payOrder(orderNumber) {
      if (!orderNumber) return null
      try {
        const res = await axios.post('/payOrder', null, { params: { orderNumber } })
        if (res.data?.success) {
          toast(res.data?.message || '支付成功', 'success')
          await this.fetchOrders()
        } else {
          toast(res.data?.message || '支付失败', 'error')
        }
        return res.data
      } catch (e) {
        toast('支付失败', 'error')
        return null
      }
    }
  },
  persist: true
})
