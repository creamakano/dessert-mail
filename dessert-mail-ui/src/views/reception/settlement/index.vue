<script setup>
import { reactive, ref } from "vue";
import { useRoute, useRouter } from 'vue-router';
import { get, post } from "../../../tool/http";
import { ElMessage, ElMessageBox } from 'element-plus'

const { query } = useRoute()
const route = useRouter()
function back () {
  route.push('/home/cart')
}
const data = ref()
console.log(query);
//获取页面数据
var cartIds = []
const order = reactive({
  name: '',
  phone: '',
  address: '',
  cartIds: '',
})
for (var i in query) {
  cartIds.push(query[i])
}

var ids = cartIds.join(',')
const total = ref(0)
get('/product/cart/settlement', {
  ids: ids
}).then(res => {
  if (res.code == 200) {
    data.value = res.data.cartList
    total.value = res.data.totalPrice
  }
})


const hasAddress = ref(false)
function getAddress () {
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
}
getAddress()
const isDefaultAddress = ref(true)
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
  isDefaultAddress.value = (row.isDefault == 1)
  order.name = row.name
  order.phone = row.phone
  order.address = row.address
  selectAddressModel.value = false
}


function submitOrder () {
  order.cartIds = cartIds
  get('/product/cart/checkStorage', {
    ids: ids
  }).then(res => {
    if (res.code != 200) {
      ElMessageBox.alert(res.msg, '商品库存不足', {
        confirmButtonText: '确定',
        dangerouslyUseHTMLString: true,
        type: 'warning',
        center: true,
      })
    } else {
      if (!hasAddress.value) {
        ElMessage.error("请添加收货信息")
        return
      }

      post('/order/order/submitOrder', order).then(res => {
        if (res.code == 200) {
          route.push({
            path: '/home/submitOrder',
            query: res.data
          })
        }
      })
    }
  })

}

//新增收货信息

const insertAddressDialog = ref(false)
const addressForm = reactive({
  name: '',
  address: '',
  phone: '',
  isDefault: 0,
})
const handleClose = (done) => {

  addressForm.name = ''
  addressForm.address = ''
  addressForm.phone = ''
  addressForm.isDefault = 0
  done()
}

function insertAddress () {
  post("/user/address/insert", addressForm).then(res => {
    if (res.code == 200) {
      ElMessage.success("添加成功")
      getAddress()
      insertAddressDialog.value = false;
    } else {
      ElMessage.error(res.msg)

    }
  })
}

function backToCart () {
  route.push('/home/cart')
}

</script>

<template>
  <div class="all-c">
    <div style="text-align: center;">
      <h1 style="color:rgb(221, 126, 107);  padding-bottom: 20px;">订单结算</h1>
    </div>
    <div class="content-c">
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span>收货信息&nbsp; <el-tag class="ml-2" type="warning" size="small"
                v-if="isDefaultAddress && hasAddress">默认</el-tag></span>
            <el-button class="button" v-if="hasAddress" text @click="openReceiptModel">选择其他地址</el-button>
            <el-button class="button" v-else type="primary" @click="insertAddressDialog = true">新增收货信息</el-button>
          </div>
        </template>
        <div>
          <el-row :gutter="20" v-if="hasAddress">
            <el-col :span="8">
              <div class="address-item">{{ order.name }}</div>
            </el-col>
            <el-col :span="8">
              <div style="text-align: center;">{{ order.phone }}</div>
            </el-col>
            <el-col :span="8" style="text-align: end;white-space: nowrap;text-overflow: ellipsis;overflow: hidden ;">
              <el-tooltip class="box-item" effect="dark" :content="order.address" placement="top">
                {{ order.address }}</el-tooltip>
            </el-col>
          </el-row>
          <el-tag type="danger" size="large" v-else>您还没有设置收货地址</el-tag>
        </div>
      </el-card>

      <template v-for="item in data" :key="item.id">
        <el-card class="box-card">
          <el-row :gutter="20">
            <el-col :span="4">
              <div class="address-item"><img :src="`/src/assets/images/${item.picture}`" class="image" width="130"
                  height="130" fix /></div>
            </el-col>
            <el-col :span="4" style="line-height: 130px; text-align: center; ">
              {{ item.productName }}
            </el-col>
            <el-col :span="4" style="line-height: 130px; text-align: center;text-decoration: line-through;">
              ￥{{ item.price }}
            </el-col>
            <el-col :span="4" style="line-height: 130px; text-align: center; color: red;">
              ￥{{ item.price * item.discount }}
            </el-col>
            <el-col :span="4" style="line-height: 130px; text-align: center;">
              x{{ item.num }}
            </el-col>
            <el-col :span="4" style="line-height: 130px; text-align: center;">
              小计：￥{{ item.price * item.discount * item.num }}
            </el-col>
          </el-row>

        </el-card></template>
    </div>
    <div class="total-c">
      <div class="total">总金额：<span style="font-size: 30px;color: red;">￥{{ total }}</span></div>

    </div>
    <div class="btn-c">
      <el-button type="primary" size="large" @click="backToCart">
        返回购物车
      </el-button>
      <el-button type="warning" size="large" @click="submitOrder">
        提交订单
      </el-button>
    </div>
  </div>
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

  <el-dialog v-model="insertAddressDialog" title="新增收货信息" width="30%" :before-close="handleClose">
    <el-form :label-position="right" label-width="100px" :model="addressForm" style="max-width: 460px">
      <el-form-item label="收货人">
        <el-input v-model="addressForm.name" />
      </el-form-item>
      <el-form-item label="手机号码">
        <el-input v-model="addressForm.phone" />
      </el-form-item>
      <el-form-item label="详细地址">
        <el-input v-model="addressForm.address" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="insertAddressDialog = false">取消</el-button>
        <el-button type="primary" @click="insertAddress()">
          确定
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style>
.all-c {
  width: 100%;
  padding: 40px;
  box-sizing: border-box;
}

.content-c {
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.total-c {
  width: 1080px;
  margin: 0 auto;
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  align-items: end;
}

.btn-c {
  width: 1080px;
  margin: 0 auto;
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
}

.total {
  height: 50px;
  line-height: 40px;
  padding-bottom: 10px;
  box-sizing: border-box;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.box-card {
  width: 1080px;
  margin-top: 10px;
}
</style>