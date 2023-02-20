<script  setup>
import { ref, reactive } from 'vue'
import { get, post } from '../../tool/http.js'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
const store = useStore()
const loginForm = reactive({
  mail: '',
  password: '',
})
const code = ''
const route = useRouter()
//登录事件
function login () {

  post('/user/login', loginForm).then((res) => {
    if (res.code != 200) {
      ElMessage.error(res.msg)
    } else {
      ElMessage.success(res.msg)

      // get('/user/session/getSession').then(res => {
      //   console.log(res.data);
      //   

      //   console.log(store.state.userInfo);
      // })
      store.commit("setUserInfo", res.data)
      window.sessionStorage.setItem("user", res.data)
      route.push({
        path: 'home'
      })

    }
  }).catch(err => {
    ElMessage.error(err)
  })
}
</script>

<template>
  <el-row :gutter="0" class="root">
    <el-col :span="9">
    </el-col>
    <el-col :span="6">
      <div class="login-title">用户登录</div>
      <el-form :model="loginForm" label-width="120px">
        <el-form-item label=" 邮箱" class="login-item">
          <el-input v-model="loginForm.mail" type="text" placeholder="请输入邮箱" clearable />
        </el-form-item>
        <el-form-item label="密码" class="login-item">
          <el-input v-model="loginForm.password" type="password" show-password placeholder="请输入密码" />
        </el-form-item>
        <el-form-item>
          <div class="login-btn-c">
            <el-button @click="login" type="success" size="large">登录</el-button>
            <el-button @click="registry" type="warning" size="large">注册</el-button>
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
  background-size: 100% 100%;
  background-attachment: fixed;
  align-items: center;
}

.login-item .el-form-item__label {
  color: white;
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
