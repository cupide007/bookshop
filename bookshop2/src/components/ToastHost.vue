<template>
  <transition name="fade">
    <div v-if="visible" class="toast" :class="type">
      {{ text }}
    </div>
  </transition>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'

const visible = ref(false)
const text = ref('')
const type = ref('info')
let timer = null

const show = (message, level) => {
  text.value = message
  type.value = level || 'info'
  visible.value = true
  clearTimeout(timer)
  timer = setTimeout(() => {
    visible.value = false
  }, 2000)
}

const handler = (e) => {
  const { message, type: level } = e.detail || {}
  show(message, level)
}

onMounted(() => {
  window.addEventListener('app-toast', handler)
})

onBeforeUnmount(() => {
  window.removeEventListener('app-toast', handler)
  clearTimeout(timer)
})
</script>

<style scoped>
.toast {
  position: fixed;
  top: 20px;
  right: 20px;
  background: rgba(0, 0, 0, 0.8);
  color: #fff;
  padding: 10px 14px;
  border-radius: 8px;
  z-index: 9999;
  min-width: 200px;
  text-align: center;
}

.toast.success {
  background: #16a34a;
}

.toast.error {
  background: #dc2626;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
