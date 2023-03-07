<script setup>
import { ElMessage } from 'element-plus';
import { reactive, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { get, put } from '../../../tool/http';
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
const statusType = reactive({
  1: 'danger',
  2: 'info',
  3: '',
  4: 'warning',
  5: 'success'
})
//获取页面数据
const queryForm = reactive({
  pageNo: 0,
  pageSize: 10,
  total: 0,
  status: 0,

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
function goToOrderDetail (row) {
  route.push({
    path: '/home/orderDetail',
    query: { orderId: row.id }
  })
}
function changeStatus (val) {
  queryForm.status = val
  getPage()
}


//确认收货
function confirmReceipt (id) {
  put('/order/order/confirmReceipt', {
    id: id
  }).then(res => {
    if (res.code == 200) {
      ElMessage.success("确认收货成功")
      getPage()
    }
  })
}
//评价
function comment (id) {
  get('/order/orderDetail/idList', {
    orderId: id
  }).then(res => {
    if (res.code == 200) {
      route.push({
        path: '/home/comment',
        query: { ids: res.data }
      })
    }
  })
}
</script>

<template>
  <div class="all-c">
    <div class="content-c">
      <div class="title">我的订单</div>

      <div class="content">
        <div class="query-line">
          <div class="query-item" :class="queryForm.status == 0 ? 'active' : ''" @click="changeStatus(0)">全部</div>
          <div class="query-item" :class="queryForm.status == 1 ? 'active' : ''" @click="changeStatus(1)">待付款</div>
          <div class="query-item" :class="queryForm.status == 2 ? 'active' : ''" @click="changeStatus(2)">待发货</div>
          <div class="query-item" :class="queryForm.status == 3 ? 'active' : ''" @click="changeStatus(3)">待收货</div>
          <div class="query-item" :class="queryForm.status == 4 ? 'active' : ''" @click="changeStatus(4)">待评价</div>
          <div class="query-item" :class="queryForm.status == 5 ? 'active' : ''" @click="changeStatus(5)">已完成</div>
        </div>
        <el-table :data="orderList" style="width: 100%" :cell-style="{ textAlign: 'center' }"
          :header-cell-style="{ 'text-align': 'center' }" height="530" @row-click="goToOrderDetail">
          <el-table-column type="index" />
          <el-table-column prop="orderNum" label="订单编号" width="200" />
          <el-table-column prop="total" label="订单金额" />
          <el-table-column label="订单状态" width="180">
            <template v-slot="scope">
              <el-tag effect="light" :type="statusType[scope.row.status]">{{ orderStatus[scope.row.status] }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="date" label="下单时间" />
          <el-table-column label="收货信息" width="360" show-overflow-tooltip>
            <template v-slot="scope">
              {{ scope.row.name }}&nbsp; &nbsp; {{ scope.row.phone }} &nbsp; &nbsp; {{ scope.row.address }}

            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template v-slot="scope">
              <el-button v-if="scope.row.status == 1" type="danger" size="small" @click="pay(scope.row.id)">支付</el-button>
              <!-- <el-button type="warning" size="small" @click="toOrderDetail(scope.row.id)">详情</el-button> -->
              <el-button v-else-if="scope.row.status == 3" type="primary" size="small"
                @click.native.stop="confirmReceipt(scope.row.id)">确认收货</el-button>
              <el-button v-else-if="scope.row.status == 4" type="warning" size="small"
                @click.native.stop="comment(scope.row.id)">评价</el-button>
              <el-button v-else-if="scope.row.status == 5" type="warning" size="small"
                @click.native.stop="comment(scope.row.id)">查看评价</el-button>


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

  box-sizing: border-box;
  display: flex;
  justify-content: space-between;
  width: 100%;
}

.query-item {
  width: 100%;
  text-align: center;
  color: rgb(142, 144, 151);
}

.query-item:hover {
  color: rgb(221, 126, 107);
}

.active {
  color: rgb(221, 126, 107);
  border-bottom: 2px solid rgb(221, 126, 107);
  padding-bottom: 10px;
}
</style>
