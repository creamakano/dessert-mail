<script setup>
import { computed, reactive, ref } from 'vue'
import { get, put, del, post } from '../../../tool/http.js'
import { useStore } from 'vuex';
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router';
const route = useRouter()
const store = useStore()


const queryForm = reactive({
  pageNo: 1,
  pageSize: 10,
  total: 0
})

//购物车数据
const cartList = ref([])
function getPage () {
  get('/product/cart/page', queryForm).then((res) => {
    if (res.code == 200) {
      var data = res.data
      cartList.value = data.records
      queryForm.total = data.total
    } else {
      ElMessage.error(res.msg)
    }
  })
}
getPage()
//分页信息改变
function handleSizeChange (val) {
  queryForm.pageSize = val
  getPage()
}
function handlePageChange (val) {
  queryForm.pageNo = val
  getPage()
}


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
    getPage()
  })
}

const selectionList = ref([])
function selectionChange (v) {
  selectionList.value = v
}

//结算
function settlement () {
  if (selectionList.value.length == 0) {
    ElMessage.error("请选择要结算的商品")
    return;
  }
  var cartIds = selectionList.value.map(item => item.id);
  console.log(cartIds);
  route.push({
    path: '/home/settlement',
    query: cartIds
  })


}

//总数
const total = computed(() => {
  let count = 0
  if (selectionList.value.length != 0) {
    for (let i = 0; i < selectionList.value.length; i++) {
      count = count + selectionList.value[i].price * selectionList.value[i].discount * selectionList.value[i].num
    }
  }

  return count;
})


function checkOnSelf (row, index) {
  return row.status == 1
}
</script>

<template>
  <div class="all-c">
    <div class="content-c">

      <div class="title">我的购物车</div>

      <div class="content">
        <el-table :data="cartList" style="width: 100%" :cell-style="{ textAlign: 'center' }"
          :header-cell-style="{ 'text-align': 'center' }" @selection-change="selectionChange">
          <el-table-column type="selection" :selectable="checkOnSelf" />
          <el-table-column prop="productName" label="商品名称">
            <template v-slot="scope">
              {{ scope.row.productName }}
              <span v-if="scope.row.status == 0" style="color: red;">（已下架）</span>
            </template>
          </el-table-column>
          <el-table-column prop="price" label="商品价格" />
          <el-table-column label="优惠">
            <template v-slot="scope">
              {{ scope.row.discount * scope.row.price }}
            </template>
          </el-table-column>
          <el-table-column label="数量">
            <template v-slot="scope">
              <el-input-number v-model="scope.row.num" @change="handleChange(scope.row.id, scope.row.num)" :min="1"
                :max="scope.row.storage"></el-input-number>
            </template>
          </el-table-column>
          <el-table-column prop="storage" label="商品库存" />
          <el-table-column label="小计">
            <template v-slot="scope">
              {{ scope.row.discount * scope.row.price * scope.row.num }}
            </template>
          </el-table-column>

          <el-table-column label="操作">
            <template v-slot="scope">
              <el-button size="small" type="danger" @click="deleteOneCart(scope.row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="total">购物金额总计：￥{{ total }}</div>
        <div class="total"><el-button type="primary" @click="settlement()">去结算</el-button></div>
      </div>
      <div class="bottom-c">
        <el-pagination :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :page-sizes="[5, 10, 20, 50]"
          :disabled="disabled" :background="true" layout="total, sizes, prev, pager, next, jumper"
          :total="queryForm.total" @size-change="handleSizeChange" @current-change="handlePageChange"
          class="bottom"></el-pagination>
      </div>

    </div>
  </div>
</template>

<style scoped>
.all-c {
  padding: 40px 0;
}

.content-c {
  border: 1px solid rgb(221, 126, 107);
  width: 1400px;
  margin: 0 auto;
}

.title {
  height: 40px;
  line-height: 40px;
  padding-left: 20px;
  box-sizing: border-box;
  background-color: rgb(221, 126, 107);
  color: aliceblue;
}

.content {
  padding: 15px;
}

.bottom-c {
  width: 100%;
  display: flex;
  justify-content: end;
  padding-top: 10px;
  box-sizing: border-box;
  padding-bottom: 20px;
}


.query-line {
  border-bottom: 2px dotted rgb(221, 126, 107);
  padding: 0 60px;
  box-sizing: border-box;
  display: flex;
  justify-content: space-between;
}

.total {
  text-align: center;
  height: 49px;
  line-height: 49px;
  border-bottom: 1px solid rgb(182, 181, 181);
}
</style>