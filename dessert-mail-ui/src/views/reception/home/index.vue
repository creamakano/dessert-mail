<script setup>
import TopComponent from '../../../components/reception/top/TopComponent.vue'
import LunboComponent from '../../../components/reception/lunbo/index.vue'


</script>
<template>
  <div class="home">
    <TopComponent />
    <LunboComponent />
    <router-view></router-view>
  </div>
</template>
<style>
.home {
  background-image: url('../../../assets/images/background.jpg');
  background-size: cover;
  min-height: 927px;
}


</style>