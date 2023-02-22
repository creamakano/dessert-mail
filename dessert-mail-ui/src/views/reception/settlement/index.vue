<script setup>
import { reactive, ref } from "vue";
import { useRoute, useRouter } from 'vue-router';
import { get ,post} from "../../../tool/http";
import { ElMessage } from 'element-plus'

const { query } = useRoute()
const route = useRouter()
function back () {
  route.push('/home/cart')
}
const order = reactive({
  name: '', 
  phone: '',
  address: ''
})
const hasAddress = ref(false)
get('/user/address/getDefault').then(res => {
  if (res.code == 200) {
    var data = res.data
    if (data == null || data == '') {
      ElMessage.error('您还没设置收货地址')
    } else {
      order.name = data.name
      order.phone = data.phone
      order.address = data.address
      hasAddress.value = true
    }
  }
})


//维护收货信息
const receipt = ref()
const page = reactive({
  pageSize: 5,
  pageNo: 0,
  total: 0
})
function getReceipt () {
  get("/user/address/page", page).then(res => {
    if (res.code == 200) {
      var data = res.data
      page.total = data.total
      receipt.value = data.records
    }
  })
}
function handleCurrentChange (val) {
  page.pageNo = val
  getReceipt()
}
function handleSizeChange (val) {
  page.pageSize = val
  getReceipt()
}

//选择地址
const selectAddressModel = ref(false)
function openReceiptModel () {
  if (hasAddress.value) {
    getReceipt()
    selectAddressModel.value = true
  }

}
function selectAddress (row) {
  order.name = row.name
  order.phone = row.phone
  order.address = row.address
  selectAddressModel.value = false
}


function toAddressInfoPage () {
  route.push('/home/userInfo')
}


//支付
function pay () {
  order.orderNum = query.orderNum
  order.total = query.total
  order.description = query.description
  console.log(order);
  post('/order/pay/alipay', order).then(res => {
    document.querySelector('body').innerHTML = res.data
    document.forms[0].submit()
  })
}
</script>

<template>
  <div style="text-align: center;">
    <h1 style="color:rgb(221, 126, 107);  padding-top: 60px;padding-bottom: 20px;">订单结算</h1>
  </div>
  <el-form :label-position="labelPosition" label-width="100px" class="main" style="max-width: 460px">
    <el-form-item label="订单编号">
      <el-input readonly v-model='query.orderNum' />
    </el-form-item>
    <el-form-item label="支付金额">
      <el-input readonly v-model='query.total' />
    </el-form-item>
    <el-form-item label="收货人">
      <el-input readonly v-model='order.name' @click="openReceiptModel" />
    </el-form-item>
    <el-form-item label="联系方式">
      <el-input readonly v-model='order.phone' @click="openReceiptModel" />
    </el-form-item>
    <el-form-item label="收货地址">
      <el-input readonly v-model='order.address' @click="openReceiptModel" />
    </el-form-item>
    <el-form-item label="订单详情">
      <el-input readonly v-model='query.description' type="textarea" :rows="3" />
    </el-form-item>
    <div class="bottom-btn">
      <el-button type="primary" @click="back()">取消</el-button>
      <el-button v-if="hasAddress" type="warning" @click="pay">去付款</el-button>
      <el-button v-else type="danger" @click="toAddressInfoPage">添加收货地址</el-button>
    </div>
  </el-form>

  <el-dialog v-model="selectAddressModel" title="点击选择收货地址" width="40%" :before-close="handleClose">
    <div style="margin: 0 auto;">
      <el-table :data="receipt" style="width: 680px" class="receipt-table" :header-cell-style="{ 'text-align': 'center' }"
        height="240" :cell-style="{ 'text-align': 'center' }" @row-click="selectAddress">
        <el-table-column type="index" width="20" />
        <el-table-column prop="name" label="收货人" width="180" />
        <el-table-column prop="phone" label="手机号码" width="180" />
        <el-table-column prop="address" label="详细地址" width="300" show-overflow-tooltip />
      </el-table>
      <el-pagination :current-page="page.pageNo" :page-size="page.pageSize" :page-sizes="[5]" :small="small"
        :disabled="disabled" :background="true" layout="total, prev, pager, next, jumper" :total="page.total"
        @size-change="handleSizeChange" @current-change="handleCurrentChange" class="bottom"></el-pagination>
    </div>



    <template #footer>
      <span class="dialog-footer">
        <el-button @click="selectAddressModel = false">取消</el-button>

      </span>
    </template>
  </el-dialog>
</template>



<style scoped>
.main {
  margin: 0 auto;
}

.bottom {
  margin-top: 20px;
  display: flex;
  justify-content: end;
}

.bottom-btn {
  padding-left: 100px;
  display: flex;
  justify-content: space-between;
}
</style>