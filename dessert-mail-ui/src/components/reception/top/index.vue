<script setup>
import { useStore } from 'vuex'
import { get } from "../../../tool/http.js"
import { useRouter } from 'vue-router'

const store = useStore()
// todo
// if (!store.state.useInfo) {
//   get('/user/session/getSession').then(res => {
//     console.log(res.data);
//     store.commit("setUserInfo", res.data)
//   })
// }
console.log(store.state);

//页面跳转
const route = useRouter()
function goToHome () {
  route.push({
    path: '/home'
  })
}
function goToUserInfo () {
  route.push({
    path: '/home/userInfo'
  })
}
function goToCart () {
  route.push({
    path: '/home/cart'
  })
}
</script>

<template>
  <el-row>
    <el-col :span="3" @click="goToHome()">
      <h3> &nbsp; 欢迎光临 老友记甜品屋🏠</h3>
    </el-col>
    <!-- {{ $store.useInfo.userName }} -->
    <el-col :span="3" class="top">欢迎 {{ $store.state.userInfo.name }}</el-col>
    <el-col :span="12" class="top"></el-col>
    <el-col :span="1" class="top" @click="goToUserInfo()">个人信息</el-col>
    <el-col :span="1" class="top" @click="goToCart()">购物车</el-col>
    <el-col :span="1" class="top">我的收藏</el-col>
    <el-col :span="1" class="top">我的订单</el-col>
    <el-col :span="1" class="top">联系我们</el-col>

  </el-row>
</template>
<style scoped>
.top {
  vertical-align: bottom;
  line-height: 70px;
}
</style>