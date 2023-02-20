<script setup>
import { ref } from 'vue'
const lunboList = ref([
  {
    id: 1, name: 'head1'
  },
  {
    id: 2, name: 'head2'
  },

])
</script>

<template>
  <div style="width: 100%; height: 180px;">
    <el-carousel height="180" style="overflow: hidden;">
      <el-carousel-item v-for="item in lunboList" :key="item" style="width: 100%; height: 180px;">
        <el-image :src="`/src/assets/images/${item.name}.jpg`" style="width: 100%; height: 180px;" fit="cover" />
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<style>
.el-carousel__container {
  height: 180px;
}
</style>