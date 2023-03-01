<template>
  <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" :ellipsis="false" @select="handleSelect"
    router>
    <!-- <el-menu-item v-if="$store.state.userInfo.auth>0" index="/home/link"> 后台 </el-menu-item> -->


    <el-menu-item index="/back/type">类型管理</el-menu-item>
    <el-menu-item index="/back/product">商品管理</el-menu-item>
    <el-menu-item index="/back/order">查询订单</el-menu-item>
    <el-menu-item index="/back/user">用户管理</el-menu-item>
    <el-menu-item index="/">安全退出</el-menu-item>

  </el-menu>
</template>

<script setup>
import { ref } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
const route = useRouter()
const store = useStore()
const activeIndex = ref('/home')
const handleSelect = (key, keyPath) => {
  console.log(key, keyPath)
}
</script>

<style>
.flex-grow {
  flex-grow: 1;
}
</style>