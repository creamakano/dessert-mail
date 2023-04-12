<script  setup>
import { ref, reactive } from 'vue'
import { get, post } from '../../tool/http.js'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import linkView from '../reception/link/index.vue'
const store = useStore()
const registryForm = reactive({
  mail: '',
  password: '',
  code: '',
  confirmPassword: ''
})
const code = ''
const route = useRouter()
//获取验证码
function getCode () {
  if (!registryForm.mail) {
    ElMessage.error("请输入邮箱")
    return;
  }
  post('/user/login/getCode', {
    mail: registryForm.mail
  }).then(res => {
    if (res.code == 200) {
      ElMessage.success('发送验证码成功，请注意查收邮件')
    } else {
      ElMessage.error(res.msg)
    }
  })
}

function registry () {
  if (registryForm.password != registryForm.confirmPassword) {
    ElMessage.error('两次密码输入不相同')
    return;
  }
  post('/user/login/registry', registryForm).then(res => {
    if (res.code == 200) {
      ElMessage.success('注册成功')
      route.push('/')
    } else {
      ElMessage.error(res.msg)
    }
  })
}

function login () {
  route.push('/')
}


</script>

<template>
  <el-row :gutter="0" class="root">
    <el-col :span="9">
    </el-col>
    <el-col :span="6">
      <div class="login-title">用户注册</div>
      <el-form :model="registryForm" label-width="120px">
        <el-form-item label=" 邮箱" class="login-item">
          <el-input v-model="registryForm.mail" type="text" placeholder="请输入邮箱" clearable />
        </el-form-item>
        <el-form-item label="密码" class="login-item">
          <el-input v-model="registryForm.password" type="password" show-password placeholder="请输入密码" />
        </el-form-item>
        <el-form-item label="确认密码" class="login-item">
          <el-input v-model="registryForm.confirmPassword" type="password" show-password placeholder="请再次输入密码" />
        </el-form-item>
        <el-form-item label="验证码" class="login-item">
          <div style="display: flex;justify-content: space-around;">
            <el-input v-model="registryForm.code" type="password" show-password placeholder="请输入验证码" />
            <el-button type="primary" @click="getCode">获取验证码</el-button>
          </div>

        </el-form-item>
        <el-form-item>
          <div class="login-btn-c">
            <el-button @click="registry" type="warning" size="large">注册</el-button>
            <el-button @click="login" type="success" size="large">登录</el-button>
          </div>


        </el-form-item>
        <!-- <el-form-item label="验证码" class="login-item code">
                                            <el-input v-model="loginForm.code" type="text" placeholder="请输入验证码" />
                                          </el-form-item> -->

      </el-form>

    </el-col>
    <el-col :span="9">
    </el-col>
  </el-row>
</template>

<style>
.root {
  width: 100%;
  height: 100vh;
  background-image: url('../../assets/images/login_bg.jpg');
  opacity: 0.9;
  background-size: 100% 100%;
  background-attachment: fixed;
  align-items: center;
}

.login-item .el-form-item__label {
  color: rgb(10, 10, 10);
}

.code {
  width: 300px;
}

.login-title {
  position: relative;
  width: 100%;
  text-align: center;
  top: -30px;
  font-size: 30px;
  font-weight: 400;
  color: white;
}

.login-btn-c {
  width: 100%;
  display: flex;
  justify-content: space-between;
  padding: 0 10%;
}

/*  */
</style>
