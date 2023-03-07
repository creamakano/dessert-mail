<template>
  
</template>

<script setup>
import { defineEmits, reactive } from 'vue'

const props = defineProps({
  row:Object
})


const form = reactive({
  id:'',
  orderNum:'',
  description:'',
  total:'',
  status:'',
  date:'',
  name:'',
  phone:'',
  address:'',
})
</script>