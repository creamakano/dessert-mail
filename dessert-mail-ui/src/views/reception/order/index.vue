<script setup>
import { reactive, ref } from 'vue';
import { get } from '../../../tool/http';
const orderList = ref([
  {
    id: 1,
    code: '20222222',
    price: 222.15,
    status: 0,
    date: '2023-02-08 13:55',
    receiptInfo: 'xx 123333 广州xxxxxxxxxxxxxxxxxxxxxxxxsssssssssssssssssssssssssssssssssssssssssssssssss',
    isComment: 1,
  },
  {
    id: 1,
    code: '20222222',
    price: 222.15,
    status: 1,
    date: '2023-02-08 13:55',
    receiptInfo: 'xx 123333 广州',
    isComment: 2,

  },
  {
    id: 1,
    code: '20222222',
    price: 222.15,
    status: 0,
    date: '2023-02-08 13:55',
    receiptInfo: 'xx 123333 广州',
    isComment: 0,
  },
])
//获取页面数据
const queryForm = reactive({
  currentPage:0,
  pageSize:10,
  total:0

})
function getPage () {
  // get('/order/order/',)
}
</script>

<template>
  <div class="main">
    <div style="text-align: center;">
      <h1 style="color:rgb(221, 126, 107);  padding-top: 40px;padding-bottom: 20px;
                   ">订单详情</h1>
    </div>
    <div class="content-c">
      <div class="content">
        <el-table :data="orderList" style="width: 100%" :cell-style="{ textAlign: 'center' }"
          :header-cell-style="{ 'text-align': 'center' }" height="400">

          <el-table-column type="index" />
          <el-table-column prop="code" label="订单编号" width="180" />
          <el-table-column prop="price" label="订单金额" width="180" />
          <el-table-column label="下单时间" width="180">
            <template v-slot="scope">
              <template v-if="scope.row.status == 1">已支付</template>
              <template v-else>未支付</template>
            </template>
          </el-table-column>
          <el-table-column prop="date" label="下单时间" width="180" />
          <el-table-column prop="receiptInfo" label="收货信息" width="180" show-overflow-tooltip />
          <el-table-column label="查看详情" width="180">
            <template v-slot="scope">
              <el-button @click="goToOrderDetail(scope.row.id)">查看详情</el-button>
            </template>
          </el-table-column>
          <el-table-column label="评价" width="180">
            <template v-slot="scope">
              <template v-if="scope.row.isComment == 1">查看评价</template>
              <template v-else>
                <el-button @click="goToComment(scope.row.id)">去评价</el-button>
              </template>
            </template>
          </el-table-column>

        </el-table>
        <div>
        <el-pagination :current-page="queryForm.currentPage" :page-size="queryForm.pageSize" :page-sizes="[10, 20, 50]" :small="small"
          :disabled="disabled" :background="true" layout="total, sizes, prev, pager, next, jumper" :total="queryForm.total"
          @size-change="handleSizeChange" @current-change="handleCurrentChange" class="bottom"></el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.main {
  width: 70%;
  height: 600px;
  margin: 0 auto;
  margin-top: 30px;
}

.title {
  width: 100%;
  height: 50px;
  line-height: 50px;
  padding-left: 20px;
  font-size: 20px;
  box-sizing: border-box;
}

.content-c {
  padding: 10px;
  box-sizing: border-box;
  height: 550px;
}
</style>