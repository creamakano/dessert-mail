<script setup>
// import { msg, msg1 } from './api'
// import { ref, reactive, onMounted } from 'vue'
// import get from '../../tool/http.js'

// console.log("sss")
// let message = ref()

// get('/product/test').then((data) => {

//   message.value = data
//   console.log(data);
// }).catch(err => {
//   message.value = 'nothing'
// })


// console.log(message.value);


// const input = ref('')
// watch(input, (newValue) => {

// })
</script>

<template>
  <div class="root">
    this is login view
    <el-input v-model="input" placeholder="Please input" />

  </div>
</template>

<style scoped>
.root {
  width: 100%;
  height: 100vh;
  background-image: url('../../assets/images/login_bg.jpg');
  background-size: 100% 100%;
  background-attachment: fixed;
  display: flex;
  justify-content: center;
}
</style>
