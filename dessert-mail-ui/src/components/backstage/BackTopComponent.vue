<template>
  <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" :ellipsis="false" @select="handleSelect"
    router>
    <!-- <el-menu-item v-if="$store.state.userInfo.auth>0" index="/home/link"> 后台 </el-menu-item> -->


    <el-menu-item index="/back/type">类型管理</el-menu-item>
    <el-menu-item index="/back/product">商品管理</el-menu-item>
    <el-menu-item index="/back/order">订单管理</el-menu-item>
    <el-menu-item index="/back/user">用户管理</el-menu-item>
    <el-menu-item index="/back/comment">评论管理</el-menu-item>
    <el-menu-item index="#" @click="logout">安全退出</el-menu-item>

  </el-menu>
</template>

<script setup>
import { ref } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { post } from '../../tool/http'
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