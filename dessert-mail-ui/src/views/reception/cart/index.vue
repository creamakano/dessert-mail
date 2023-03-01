<script setup>
import { computed, ref } from 'vue'
import { get, put, del, post } from '../../../tool/http.js'
import { useStore } from 'vuex';
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router';
const route = useRouter()
const store = useStore()
//购物车数据
const cartList = ref([])
function loadCartList () {
  get('/product/cart/list', { userId: store.state.userInfo.id }).then((res) => {
    cartList.value = res.data
  })
}
loadCartList()


//购物车商品数量改变
function handleChange (id, num) {
  put("/product/cart/update", { id: id, num: num }).then(res => {
    // ElMessage.success('添加成功')
  })
}

//删除单个购物车商品
function deleteOneCart (id) {
  del(`/product/cart/delete/${id}`).then(res => {
    ElMessage.success('删除成功')
    loadCartList()
  })
}

const selectionList = ref([])
function selectionChange (v) {
  selectionList.value = v
}

//结算
function settlement () {
  if(selectionList.value.length==0){
    ElMessage.error("请选择要结算的商品")
    return;
  }
  var cartIds = selectionList.value.map(item => item.id);
  post('/order/pay/settlement', {
    cartIds: cartIds
  }).then(res => {
    if (res.code == 200) {
      route.push({
        path: '/home/settlement',
        query: res.data
      })
    }
  })
}

//总数
const total = computed(() => {
  let count = 0
  if(selectionList.value.length != 0){
    for (let i = 0; i < selectionList.value.length; i++) {
    count = count + selectionList.value[i].price * selectionList.value[i].discount * selectionList.value[i].num
  }
  }
  
  return count;
})

</script>

<template>
  <div style="text-align: center;">
    <h1 style="color:rgb(221, 126, 107);  padding-top: 60px;padding-bottom: 20px;">我的购物车</h1>
  </div>
  <div class="cart-c">

    <div class="cart">
      <el-table :data="cartList" style="width: 100%" :cell-style="{ textAlign: 'center' }"
        :header-cell-style="{ 'text-align': 'center' }" @selection-change="selectionChange">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="productName" label="商品名称" width="180" />
        <el-table-column prop="price" label="商品价格" width="180" />
        <el-table-column label="优惠" width="180">
          <template v-slot="scope">
            {{ scope.row.discount * scope.row.price }}
          </template>
        </el-table-column>
        <el-table-column label="数量" width="180">
          <template v-slot="scope">
            <el-input-number v-model="scope.row.num" @change="handleChange(scope.row.id, scope.row.num)" :min="1"
              :max="100"></el-input-number>
          </template>
        </el-table-column>

        <el-table-column label="小计" width="180">
          <template v-slot="scope">
            {{ scope.row.discount * scope.row.price * scope.row.num }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180">
          <template v-slot="scope">
            <el-button size="small" type="danger" @click="deleteOneCart(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="total">购物金额总计：￥{{ total }}</div>
      <div class="total"><el-button type="primary" @click="settlement()">去结算</el-button></div>
    </div>
  </div>
</template>

<style scoped>
.cart-c {
  width: 100%;
  display: flex;
  justify-content: center;
}

.total {
  text-align: center;
  height: 49px;
  line-height: 49px;
  border-bottom: 1px solid rgb(182, 181, 181);
}
</style>