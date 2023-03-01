<script setup>
import { ElMessage } from "element-plus";
import { ref } from "vue";
import { useRoute } from "vue-router";
import { get } from "../../../tool/http";
const { query } = useRoute()

const detailList = ref()
get('/order/orderDetail/list', {
  orderId: query.orderId
}).then(res => {
  if (res.code == 200) {
    detailList.value = res.data
  } else {
    ElMessage.error(res.msg)
  }
})


//跳转到商品详情页
function goToProductDetail (id) {
  route.push({
    path: '/home/productDetail',
    query: { id: id }
  })
}
</script>

<template>
  <div style="text-align: center;">
    <h1 style="color:rgb(221, 126, 107);  padding-top: 10px;padding-bottom: 0px; ">订单详情</h1>
  </div>
  <el-table :data="detailList" style="width: 960px; margin: 0 auto;" :cell-style="{ textAlign: 'center' }"
    :header-cell-style="{ 'text-align': 'center' }">
    <el-table-column type="index" width="60" />
    <el-table-column label="商品图片" width="180">
      <template v-slot="scope" width="180">
        <el-image style="width: 60px; height:60px" :src="`/src/assets/images/${scope.row.productPicture}`" :fit="fit"
          @click="goToProductDetail(scope.row.productId)" />
      </template>
    </el-table-column>
    <el-table-column prop="productName" label="商品名称" width="180" />
    <el-table-column prop="productPrice" label="商品价格" width="180" />
    <el-table-column prop="num" label="购买数量" width="180" />
    <el-table-column label="操作" width="180">
      <template v-slot="scope" width="180">
        <el-button type="warning" @click="comment(scope.row.productId)">去评价</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>