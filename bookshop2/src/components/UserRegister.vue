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
import { useRouter } from 'vue-router'

const router = useRouter()

const form = reactive({
  account: '',
  password: '',
  confirmPwd: '',
  phone: ''
})

const validate = reactive({
  account: '',
  password: '',
  confirmPwd: '',
  phone: ''
})

const isLoading = ref(false)
const showPassword = ref(false)

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

const validateConfirmPwd = () => {
  if (!form.confirmPwd.trim()) {
    validate.confirmPwd = '请确认密码'
  } else if (form.confirmPwd !== form.password) {
    validate.confirmPwd = '两次输入的密码不一致'
  } else {
    validate.confirmPwd = ''
  }
}

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

const validateForm = () => {
  validateAccount()
  validatePassword()
  validateConfirmPwd()
  validatePhone()

  return !Object.values(validate).some(item => item !== '')
}

const handleRegister = async () => {
  if (!validateForm()) return

  try {
    isLoading.value = true
    
    console.log('注册成功', form)
    alert('注册成功！即将跳转到登录页')
    
    form.account = ''
    form.password = ''
    form.confirmPwd = ''
    form.phone = ''
    
    router.push('/login')

  } catch (error) {
    console.error('注册失败', error)
    alert('注册失败，请稍后重试')
  } finally {
    isLoading.value = false
  }
}
</script>

<style scoped>
.register-container {
  
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f7f8fa;
  padding: 20px;
}

.register-card {
  width: 100%;
  max-width: 450px;
  padding: 36px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  box-sizing: border-box;
}

.register-title {
  text-align: center;
  margin: 0 0 30px 0;
  color: #333;
  font-size: 22px;
  font-weight: 600;
}

.register-form {
  width: 100%;
}

.form-item {
  margin-bottom: 24px;
  position: relative;
}

.form-label {
  display: block;
  margin-bottom: 8px;
  color: #666;
  font-size: 14px;
  font-weight: 500;
}

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

.password-wrapper {
  position: relative;
}

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

.input-error {
  border-color: #ef4444;
}

.error-tip {
  position: absolute;
  bottom: -20px;
  left: 0;
  color: #ef4444;
  font-size: 12px;
  line-height: 1;
}

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

@media (max-width: 480px) {
  .register-card {
    padding: 24px;
  }
  
  .register-title {
    font-size: 20px;
  }
}
</style>
