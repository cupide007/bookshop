<template>
  <div class="register-container">
    <div class="register-card">
      <h2 class="register-title">用户注册</h2>
      
      <!-- 注册表单 -->
      <form class="register-form" @submit.prevent="handleRegister">
        <!-- 账号输入框 -->
        <div class="form-item">
        
          <input
            type="text"
            id="account"
            class="form-input"
            v-model="form.account"
            placeholder="请输入6-20位字母/数字组合的账号"
            :class="{ 'input-error': validate.account }"
            @blur="validateAccount"
          />
          <span class="error-tip" v-if="validate.account">{{ validate.account }}</span>
        </div>

        <!-- 密码输入框 -->
        <div class="form-item">
         
          <div class="password-wrapper">
            <input
              :type="showPassword ? 'text' : 'password'"
              id="password"
              class="form-input"
              v-model="form.password"
              placeholder="请输入8-20位包含字母和数字的密码"
              :class="{ 'input-error': validate.password }"
              @blur="validatePassword"
            />
            <span class="password-toggle" @click="showPassword = !showPassword">
              {{ showPassword ? '隐藏' : '显示' }}
            </span>
          </div>
          <span class="error-tip" v-if="validate.password">{{ validate.password }}</span>
        </div>

        <!-- 确认密码输入框 -->
        <div class="form-item">
        
          <input
            type="password"
            id="confirmPwd"
            class="form-input"
            v-model="form.confirmPwd"
            placeholder="请再次输入密码"
            :class="{ 'input-error': validate.confirmPwd }"
            @blur="validateConfirmPwd"
          />
          <span class="error-tip" v-if="validate.confirmPwd">{{ validate.confirmPwd }}</span>
        </div>

        <!-- 手机号输入框 -->
        <div class="form-item">
         
          <input
            type="tel"
            id="phone"
            class="form-input"
            v-model="form.phone"
            placeholder="请输入11位手机号码"
            :class="{ 'input-error': validate.phone }"
            @blur="validatePhone"
          />
          <span class="error-tip" v-if="validate.phone">{{ validate.phone }}</span>
        </div>

        <!-- 注册按钮 -->
        <button 
          type="submit" 
          class="register-btn"
          :disabled="isLoading"
        >
          <span v-if="isLoading">注册中...</span>
          <span v-else>立即注册</span>
        </button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'

// 表单数据
const form = reactive({
  account: '',       // 账号
  password: '',      // 密码
  confirmPwd: '',    // 确认密码
  phone: ''          // 手机号
})

// 验证信息
const validate = reactive({
  account: '',
  password: '',
  confirmPwd: '',
  phone: ''
})

// 状态控制
const isLoading = ref(false)       // 注册加载状态
const showPassword = ref(false)    // 密码显示/隐藏

// 账号验证规则：6-20位字母/数字组合
const validateAccount = () => {
  const reg = /^[a-zA-Z0-9]{6,20}$/
  if (!form.account.trim()) {
    validate.account = '请输入账号'
  } else if (!reg.test(form.account)) {
    validate.account = '账号需为6-20位字母/数字组合'
  } else {
    validate.account = ''
  }
}

// 密码验证规则：8-20位包含字母和数字
const validatePassword = () => {
  const reg = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{8,20}$/
  if (!form.password.trim()) {
    validate.password = '请输入密码'
  } else if (!reg.test(form.password)) {
    validate.password = '密码需为8-20位包含字母和数字'
  } else {
    validate.password = ''
  }
}

// 确认密码验证
const validateConfirmPwd = () => {
  if (!form.confirmPwd.trim()) {
    validate.confirmPwd = '请确认密码'
  } else if (form.confirmPwd !== form.password) {
    validate.confirmPwd = '两次输入的密码不一致'
  } else {
    validate.confirmPwd = ''
  }
}

// 手机号验证规则：11位有效手机号
const validatePhone = () => {
  const reg = /^1[3-9]\d{9}$/
  if (!form.phone.trim()) {
    validate.phone = '请输入手机号'
  } else if (!reg.test(form.phone)) {
    validate.phone = '请输入有效的11位手机号'
  } else {
    validate.phone = ''
  }
}

// 整体表单验证
const validateForm = () => {
  // 触发所有字段验证
  validateAccount()
  validatePassword()
  validateConfirmPwd()
  validatePhone()

  // 检查是否有验证错误
  return !Object.values(validate).some(item => item !== '')
}

// 注册处理逻辑
const handleRegister = async () => {
  // 先验证表单
  if (!validateForm()) return

  try {
    isLoading.value = true
    
    // 模拟注册请求（实际项目替换为真实接口）
    // await new Promise(resolve => setTimeout(resolve, 1500))
    
    // 注册成功逻辑
    console.log('注册成功', form)
    alert('注册成功！即将跳转到登录页')
    
    // 清空表单
    form.account = ''
    form.password = ''
    form.confirmPwd = ''
    form.phone = ''
    
    // 可添加路由跳转逻辑
    // 例如：router.push('/login')

  } catch (error) {
    console.error('注册失败', error)
    alert('注册失败，请稍后重试')
  } finally {
    isLoading.value = false
  }
}
</script>

<style scoped>
/* 整体容器 */
.register-container {
  
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f7f8fa;
  padding: 20px;
}

/* 注册卡片 */
.register-card {
  width: 100%;
  max-width: 450px;
  padding: 36px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  box-sizing: border-box;
}

/* 注册标题 */
.register-title {
  text-align: center;
  margin: 0 0 30px 0;
  color: #333;
  font-size: 22px;
  font-weight: 600;
}

/* 表单样式 */
.register-form {
  width: 100%;
}

/* 表单项 */
.form-item {
  margin-bottom: 24px;
  position: relative;
}

/* 标签样式 */
.form-label {
  display: block;
  margin-bottom: 8px;
  color: #666;
  font-size: 14px;
  font-weight: 500;
}

/* 输入框基础样式 */
.form-input {
  width: 100%;
  height: 44px;
  padding: 0 15px;
  border: 1px solid #e5e7eb;
  border-radius: 6px;
  font-size: 14px;
  box-sizing: border-box;
  transition: all 0.2s ease;
}

.form-input:focus {
  outline: none;
  border-color: #2563eb;
  box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.1);
}

/* 密码输入框容器 */
.password-wrapper {
  position: relative;
}

/* 密码显示/隐藏切换 */
.password-toggle {
  position: absolute;
  right: 15px;
  top: 50%;
  transform: translateY(-50%);
  color: #9ca3af;
  font-size: 12px;
  cursor: pointer;
  user-select: none;
}

.password-toggle:hover {
  color: #2563eb;
}

/* 错误状态 */
.input-error {
  border-color: #ef4444;
}

/* 错误提示 */
.error-tip {
  position: absolute;
  bottom: -20px;
  left: 0;
  color: #ef4444;
  font-size: 12px;
  line-height: 1;
}

/* 注册按钮 */
.register-btn {
  width: 100%;
  height: 44px;
  background: #2563eb;
  color: #fff;
  border: none;
  border-radius: 6px;
  font-size: 16px;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.register-btn:hover {
  background: #1d4ed8;
}

.register-btn:disabled {
  background: #93c5fd;
  cursor: not-allowed;
}

/* 响应式适配 */
@media (max-width: 480px) {
  .register-card {
    padding: 24px;
  }
  
  .register-title {
    font-size: 20px;
  }
}
</style>