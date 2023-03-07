<script setup>
import { reactive, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { get } from '../../../tool/http';
const { query } = useRoute()
const route = useRouter()
const orderList = ref()
//字典
const orderStatus = ref()
get('/user/sys/dict', {
  name: 'orderStatus'
}).then(res => {
  orderStatus.value = res.data
})
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
function goToOrderDetail (id) {
  // get('/order/orderDetail/list',{
  //   orderId:id
  // }).then(res=>{

  // })

  route.push({
    path: '/home/orderDetail',
    query: { orderId: id }
  })
}
</script>

<template>
  <div class="all-c">
    <div class="content-c">
      <div class="title">我的订单</div>

      <div class="content"> 
        <div class="query-line">
          <el-tabs :tab-position="left" style="height: 200px" class="demo-tabs">
            <el-tab-pane label="User">User</el-tab-pane>
            <el-tab-pane label="Config">Config</el-tab-pane>
            <el-tab-pane label="Role">Role</el-tab-pane>
            <el-tab-pane label="Task">Task</el-tab-pane>
          </el-tabs>
        </div>
        <el-table :data="orderList" style="width: 100%" :cell-style="{ textAlign: 'center' }"
          :header-cell-style="{ 'text-align': 'center' }" height="530">

          <el-table-column type="index" />
          <el-table-column prop="orderNum" label="订单编号" width="200" />
          <el-table-column prop="total" label="订单金额" />
          <el-table-column label="下单时间" width="180">
            <template v-slot="scope">
              {{ orderStatus[scope.row.status] }}
            </template>
          </el-table-column>
          <el-table-column prop="date" label="下单时间" />
          <el-table-column label="收货信息" width="360" show-overflow-tooltip>
            <template v-slot="scope">
              {{ scope.row.name }}&nbsp; , &nbsp; {{ scope.row.phone }} &nbsp; , &nbsp; {{ scope.row.address }}

            </template>
          </el-table-column>


          <el-table-column label="操作">
            <template v-slot="scope">
              <el-button v-if="scope.row.status == 0" type="danger" size="small" @click="pay(scope.row.id)">支付</el-button>
              <el-button type="warning" size="small" @click="toOrderDetail(scope.row.id)">详情</el-button>


              <template v-if="scope.row.status == 3">
                <el-button v-if="scope.row.isComment == 0" type="primary" size="small"
                  @click="pay(scope.row.id)">评价</el-button>

                <el-button v-if="scope.row.isComment == 1" type="primary" size="small"
                  @click="pay(scope.row.id)">查看评价</el-button>

              </template>

            </template>
          </el-table-column>

        </el-table>
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
  width: 1500px;
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
</style>
