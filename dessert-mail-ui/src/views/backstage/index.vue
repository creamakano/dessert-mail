<script setup>
import BackTopComponent from '../../components/backstage/BackTopComponent.vue'


</script>
<template>
  <div class="home">
    <BackTopComponent />
    <router-view></router-view>
  </div>
</template>
<style>
.home {
  background-image: url('../../assets/images/background.jpg');
  background-size: cover;
  min-height: 927px;
}


</style>