<template>
  <div class="login-container">
    <div class="login-card">
      <h2 class="login-title">系统登录</h2>
      
      <!-- 登录表单 -->
      <form class="login-form" @submit.prevent="handleLogin">
        <!-- 账号输入框 -->
        <div class="form-item">         
          <input
            type="text"
            id="account"
            class="form-input"
            v-model="form.account"
            placeholder="请输入账号"
            :class="{ 'input-error': validate.account }"
          />
          <span class="error-tip" v-if="validate.account">{{ validate.account }}</span>
        </div>

        <!-- 密码输入框 -->
        <div class="form-item">        
          <input
            type="password"
            id="password"
            class="form-input"
            v-model="form.password"
            placeholder="请输入密码"
            :class="{ 'input-error': validate.password }"
          />
          <span class="error-tip" v-if="validate.password">{{ validate.password }}</span>
        </div>

        <!-- 登录按钮 -->
        <button 
          type="submit" 
          class="login-btn"
          :disabled="isLoading"
        >
          <span v-if="isLoading">登录中...</span>
          <span v-else>登录</span>
        </button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

// 1. 初始化路由实例（修复未声明router的问题）
const router = useRouter()

// 表单数据
const form = reactive({
  account: '',
  password: ''
})

// 验证信息
const validate = reactive({
  account: '',
  password: ''
})

// 加载状态
const isLoading = ref(false)

// 表单验证
const validateForm = () => {
  let isValid = true
  
  // 账号验证
  if (!form.account.trim()) {
    validate.account = '请输入账号'
    isValid = false
  } else {
    validate.account = ''
  }

  // 密码验证
  if (!form.password.trim()) {
    validate.password = '请输入密码'
    isValid = false
  } else if (form.password.length < 6) {
    validate.password = '密码长度不能少于6位'
    isValid = false
  } else {
    validate.password = ''
  }

  return isValid
}

// 登录处理（完整闭合所有语法结构，修复所有错误）
const handleLogin = async () => {
  // 先验证表单
  if (!validateForm()) return

  // 开启加载状态
  isLoading.value = true

  try {
    // 修复：参数匹配（表单是account，请求时传username，统一为account或username）
    const response = await axios.post("/login", {
      username: form.account, // 表单绑定的是account，这里映射为接口需要的username
      password: form.password
    })

    console.log(response.data)
    alert(response.data.message || '登录成功')

    // 修复：nill → null（拼写错误）
    if (response.data.data !== null) {
      sessionStorage.setItem("Authorization", response.data.data)
    }

    // 登录成功跳转首页
    if (response.data.status === 200) {
      router.push('/')
    }
  } catch (error) {
    // 完善错误处理，避免error.response不存在时报错
    if (error.response) {
      if (error.response.status === 504) {
        alert("服务器没有启动")
      } else if (error.response.status === 500) {
        alert("服务器运行出错")
      }
    }
    }
}
</script>

<style scoped>
/* 整体容器样式 */
.login-container {
  /* min-height: 100vh; */
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f5f5f5;
}

/* 登录卡片 */
.login-card {
  width: 400px;
  padding: 30px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

/* 登录标题 */
.login-title {
  text-align: center;
  margin: 0 0 24px 0;
  color: #333;
  font-size: 20px;
  font-weight: 600;
}

/* 表单样式 */
.login-form {
  width: 100%;
}

/* 表单项 */
.form-item {
  margin-bottom: 20px;
  position: relative;
}

/* 标签样式 */
.form-label {
  display: block;
  margin-bottom: 8px;
  color: #666;
  font-size: 14px;
}

/* 输入框样式 */
.form-input {
  width: 100%;
  height: 40px;
  padding: 0 12px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  font-size: 14px;
  box-sizing: border-box;
  transition: border-color 0.2s;
}

.form-input:focus {
  outline: none;
  border-color: #409eff;
}

/* 错误状态 */
.input-error {
  border-color: #f56c6c;
}

/* 错误提示 */
.error-tip {
  position: absolute;
  bottom: -20px;
  left: 0;
  color: #f56c6c;
  font-size: 12px;
}

/* 登录按钮 */
.login-btn {
  width: 100%;
  height: 40px;
  background: #409eff;
  color: #fff;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.login-btn:hover {
  background: #66b1ff;
}

.login-btn:disabled {
  background: #a0cfff;
  cursor: not-allowed;
}
</style>