<script setup>
import { ref, reactive } from "vue";
import { get, post, del, put } from "../../../tool/http";
import { Search, Refresh } from '@element-plus/icons-vue'
import { ElMessage } from "element-plus";

//获取字典
//订单状态
const orderStatus = ref()
get('/user/sys/dict', {
  name: 'orderStatus'
}).then(res => {
  orderStatus.value = res.data
})


const productList = ref()
const times = ref()
//获取页面数据
const queryForm = reactive({
  pageNo: 0,
  pageSize: 10,
  total: 0,
  startTime: null,
  endTime: null,
  orderNum: null,
  status: null
})
function getPage () {
  if (times.value) {
    queryForm.startTime = times.value[0]
    queryForm.endTime = times.value[1]
  } else {
    queryForm.startTime = null
    queryForm.endTime = null
  }

  get('/order/order/allPage', queryForm).then(res => {
    if (res.code == 200) {
      var data = res.data
      productList.value = data.records
      queryForm.total = data.total
    }
  })
}
getPage()

//重置
function queryReset () {
  times.value = null
  queryForm.status = null
  queryForm.orderNum = null
  getPage()
}

//订单编辑
const updateDialog = ref(false)
const updateForm = reactive({
  id: '',
  orderNum: '',
  description: '',
  total: '',
  status: '',
  date: '',
  name: '',
  phone: '',
  address: '',
})
function openUpdateForm (row) {
  updateForm.id = row.id
  updateForm.orderNum = row.orderNum
  updateForm.description = row.description
  updateForm.total = row.total
  updateForm.status = row.status
  updateForm.date = row.date
  updateForm.name = row.name
  updateForm.phone = row.phone
  updateForm.address = row.address
  updateDialog.value = true
}
function updateOrder () {
  put('/order/order/', updateForm).then(res => {
    if (res.code == 200) {
      getPage()
      updateDialog.value = false
    }
  })
}

//发货
function send (id) {
  put('/order/order/send', {
    id: id
  }).then(res => {
    getPage()
    ElMessage.success('发货成功')
  })
}
//删除订单
function deleteOrder (id) {
  del(`/order/order/${id}`).then(res => {
    if (res.code == 200) {
      getPage()
      ElMessage.success("删除成功")
    }
  })
}

//分页信息修改
function handleSizeChange (val) {
  queryForm.pageSize = val;
  getPage()
}
function handlePageChange (val) {
  queryForm.pageNo = val;
  getPage()
}
</script>

<template>
  <div class="content-c">

    <div class="title">订单列表</div>

    <div class="content">
      <div class="query-line">
        <el-form :inline="true" :model="queryForm" class="demo-form-inline">
          <el-form-item label="订单编号">
            <el-input v-model="queryForm.orderNum" placeholder="" style="width: 180px;" />
          </el-form-item>

          <el-form-item label="订单状态">
            <el-select v-model="queryForm.status" placeholder=" " clearable size="" style="width: 180px;">
              <el-option v-for="(label, value) in orderStatus" :key="value" :label="label" :value="Number(value)" />
            </el-select>
          </el-form-item>
          <el-form-item label="下单时间">
            <el-date-picker v-model="times" type="daterange" range-separator="-" start-placeholder="开始时间"
              end-placeholder="结束时间" style="width: 290px;" size="" value-format="YYYY-MM-DD HH:mm:ss" />
          </el-form-item>

          <el-form-item>
            <el-button :icon="Search" circle @click="getPage" /> <el-button :icon="Refresh" circle @click="queryReset" />
          </el-form-item>

        </el-form>


      </div>
      <el-table :data="productList" :cell-style="{ textAlign: 'center' }" :header-cell-style="{ 'text-align': 'center' }"
        :row-style="{ 'height': '42px' }" :header-row-style="{ 'height': '42px' }" height="465">

        <el-table-column prop="id" label="订单ID" />
        <el-table-column prop="orderNum" label="订单编号" show-overflow-tooltip />
        <el-table-column prop="date" label="下单时间" show-overflow-tooltip />
        <el-table-column prop="total" label="订单金额" />
        <el-table-column prop="status" label="订单状态">
          <template v-slot="scope">
            {{ orderStatus[scope.row.status] }}
          </template>
        </el-table-column>
        <el-table-column prop="description" label="订单描述" show-overflow-tooltip />
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="warning" size="small" @click="openUpdateForm(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="deleteOrder(scope.row.id)">删除</el-button>
            <el-button v-if="scope.row.status == 1" type="success" size="small" @click="send(scope.row.id)">发货</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="bottom-c">
      <el-pagination :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :page-sizes="[10, 20, 50]"
        :disabled="disabled" :background="true" layout="total, sizes, prev, pager, next, jumper" :total="queryForm.total"
        @size-change="handleSizeChange" @current-change="handlePageChange" class="bottom"></el-pagination>
    </div>
  </div>

  <el-dialog v-model="updateDialog" title="编辑商品" width="30%" :before-close="handleClose">
    <el-form :label-position="right" label-width="100px" :model="updateForm" style="max-width: 460px">
      <el-form-item label="订单ID">
        <el-input v-model="updateForm.id" disabled />
      </el-form-item>
      <el-form-item label="订单编号">
        <el-input v-model="updateForm.orderNum" disabled />
      </el-form-item>

      <el-form-item label="下单时间">
        <el-input v-model="updateForm.date" disabled />
      </el-form-item>
      <el-form-item label="订单金额">
        <el-input v-model="updateForm.total" disabled />
      </el-form-item>
      <el-form-item label="订单状态">
        <el-select v-model="updateForm.status">
          <el-option v-for="(label, value) in orderStatus" :key="value" :label="label" :value="Number(value)" />
        </el-select>
      </el-form-item>
      <el-form-item label="收货人">
        <el-input v-model="updateForm.name" />
      </el-form-item>
      <el-form-item label="手机号码">
        <el-input v-model="updateForm.phone" />
      </el-form-item>
      <el-form-item label="收货地址">
        <el-input v-model="updateForm.address" />
      </el-form-item>
      <el-form-item label="订单描述">
        <el-input v-model="updateForm.description" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="updateDialog = false">取消</el-button>
        <el-button type="primary" @click="updateOrder">
          确定
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>
.content-c {
  border: 1px solid rgb(221, 126, 107);
  width: 1400px;
  margin: 40px auto;
}

.title {
  height: 40px;
  line-height: 20px;
  padding: 10px 20px;
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
  padding-left: 75px;
  padding-right: 61px;
  box-sizing: border-box;
  display: flex;
  justify-content: space-between;
}
</style>