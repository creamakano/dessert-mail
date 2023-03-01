<script setup>
import { reactive, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { get } from '../../../tool/http';
const {query} = useRoute()
const route = useRouter()
const orderList = ref()

//获取页面数据
const queryForm = reactive({
  pageNo: 0,
  pageSize: 10,
  total: 0

})
function getPage () {
  get('/order/order', queryForm).then(res => {
    if (res.code == 200) {
      var data = res.data
      orderList.value = data.records
      queryForm.total = data.total
    }
  })
}
getPage()

//分页信息改变
function handleSizeChange (val) {
  queryForm.pageSize = val
  getPage()
}
function handleCurrentChange (val) {
  queryForm.pageNo = val
  getPage()
}

//订单详情页
function goToOrderDetail(id){
  // get('/order/orderDetail/list',{
  //   orderId:id
  // }).then(res=>{
    
  // })

  route.push({
    path:'/home/orderDetail',
    query:{orderId:id}
  })
}
</script>

<template>
  <div class="main">
    <div style="text-align: center;">
      <h1 style="color:rgb(221, 126, 107);  padding-top: 10px;padding-bottom: 0px; ">订单详情</h1>
    </div>
    <div class="content-c">
      <div class="content">
        <el-table :data="orderList" style="width: 100%" :cell-style="{ textAlign: 'center' }"
          :header-cell-style="{ 'text-align': 'center' }" height="530">

          <el-table-column type="index" />
          <el-table-column prop="orderNum" label="订单编号" width="200" />
          <el-table-column prop="total" label="订单金额" width="180" />
          <el-table-column label="下单时间" width="180">
            <template v-slot="scope">
              <template v-if="scope.row.status == 1">已支付</template>
              <template v-else>未支付</template>
            </template>
          </el-table-column>
          <el-table-column prop="date" label="下单时间" width="180" />
          <el-table-column label="收货信息" width="360" show-overflow-tooltip>
            <template v-slot="scope">
              {{ scope.row.name }}&nbsp; , &nbsp; {{ scope.row.phone }} &nbsp; , &nbsp; {{ scope.row.address }}

            </template>
          </el-table-column>
          <el-table-column label="查看详情" width="180">
            <template v-slot="scope">
              <el-button @click="goToOrderDetail(scope.row.id)">查看详情</el-button>
            </template>
          </el-table-column>
          <el-table-column label="评价" width="180">
            <template v-slot="scope">
              <template v-if="scope.row.status == 0">去支付</template>
              <template v-else-if="scope.row.isComment == 1">查看评价</template>
              <template v-else>
                <el-button @click="goToComment(scope.row.id)">去评价</el-button>
              </template>
            </template>
          </el-table-column>

        </el-table>
        <div class="bottom-c">
          <el-pagination :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :page-sizes="[10, 20, 50]"
            :small="small" :disabled="disabled" :background="true" layout="total, sizes, prev, pager, next, jumper"
            :total="queryForm.total" @size-change="handleSizeChange" @current-change="handleCurrentChange"
            class="bottom"></el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.main {
  width: 80%;
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

.bottom-c {
  width: 100%;
  display: flex;
  justify-content: end;
  padding-right: 60px;
  padding-top: 10px;
  box-sizing: border-box;
}
</style>
