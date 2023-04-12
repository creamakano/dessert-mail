<template>
  <div style="padding-right: 20px; background-color: #fff;">
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" :ellipsis="false" @select="handleSelect"
      router>
      <el-menu-item index="/home">&nbsp; 欢迎光临 甜品商城</el-menu-item>
      <el-menu-item v-if="$store.state.userInfo.name" index="/home/userInfo"> 欢迎 {{ $store.state.userInfo.name }}</el-menu-item>
      <el-menu-item v-else index="/"> 登录</el-menu-item>
      <!-- <el-menu-item v-if="$store.state.userInfo.auth > 0"> <a @click="goback">后台</a> </el-menu-item> -->
      <el-menu-item v-if="$store.state.userInfo.auth > 0" index="/back"> 后台 </el-menu-item>
      <el-menu-item v-if="$store.state.userInfo.name" index="#" @click="logout">安全退出</el-menu-item>

      <div class="flex-grow"></div>
      <el-menu-item index="/home/">商城首页</el-menu-item>
      <el-menu-item index="/home/userInfo">个人信息</el-menu-item>
      <el-menu-item index="/home/cart">购物车</el-menu-item>
      <el-menu-item index="/home/collection">我的收藏</el-menu-item>
      <el-menu-item index="/home/order">我的订单</el-menu-item>
      <!-- <el-menu-item index="/back">联系我们</el-menu-item> -->

    </el-menu>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useStore } from 'vuex'
import { get, post } from "../../../tool/http.js"
import { useRouter } from 'vue-router'
const route = useRouter()
const store = useStore()
const activeIndex = ref('/home')
const handleSelect = (key, keyPath) => {
  console.log(key, keyPath)
}


function logout () {
  post('/user/login/logout').then(res => {
    if (res.code == 200) {
      route.push("/")
    }
  })
}
</script>

<style>
.flex-grow {
  flex-grow: 1;
}
</style>