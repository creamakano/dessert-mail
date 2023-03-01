<script setup>
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { get } from '../../../tool/http';
const { query } = useRoute()
const product = ref({
  name: ''
})
console.log(query);
get(`/product/product/getById/${query.id}`).then(res => {
  product.value = res.data

})

</script>

<template>
  <div class="content-c">
    <div class="product-c">
      <el-image style="width: 260px; height: 260px" :src="`/src/assets/images/${product.name}.jpg`" :fit="fit" />
      <div class="info">
        <el-form :label-position="left" label-width="100px" style="max-width: 460px">
          <el-form-item label="商品名称" class="fz">
            {{ product.name }}
          </el-form-item>
          <el-form-item label="商品折扣价" style="color: red;">
            ￥{{ product.price * product.discount }}
          </el-form-item>
          <el-form-item label="商品原价">
            ￥{{ product.price }}
          </el-form-item>
          <el-form-item label="商品类型">
            {{ product.price }}
          </el-form-item>
          <div>
            <el-button type="warning">加入收藏</el-button>
            <el-button type="success">加入购物车</el-button>
          </div>


        </el-form>
      </div>
    </div>
    <div class="comment-c">
      aa
    </div>
  </div>
</template>
<style scoped>
.content-c {
  padding-top: 60px;
  margin: 0 auto;
}

.product-c {
  display: flex;
  margin: 0 auto;
  width: 50%;
}

.info {
  padding: 10px;
  padding-left: 30px;
}

.comment-c {
  margin: 0 auto;
  width: 50%;
  margin-top: 40px;
}
</style>