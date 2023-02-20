<template>

</template>
<script setup>
import { useStore } from 'vuex'
const store = useStore()
store.commit("setNum", 1)
console.log(store.state.num);
</script>
