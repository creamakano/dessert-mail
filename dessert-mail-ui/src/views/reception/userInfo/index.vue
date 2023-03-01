<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useStore } from 'vuex';
import { get, put, post, del } from '../../../tool/http.js';
import { ElMessage } from 'element-plus'

import {
  Check,
  Delete,
  Edit,
  Message,
  Search,
  Star,
} from '@element-plus/icons-vue'

const store = useStore()

//展示信息
var showInfo = ref('userInfo')
function showPasswordForm () {
  console.log('pass');
  showInfo.value = 'password'
}
function showReceipt () {
  showInfo.value = 'receipt'
}
function showUserInfoForm () {
  showInfo.value = 'userInfo'
}


//维护用户信息
// const useInfoForm = store.state.useInfo
const userInfoForm = store.state.userInfo
onMounted(() => {
  if (JSON.stringify(userInfoForm)) {
    userInfoForm.name = store.state.userInfo.name
  }
}),
  function submitUserInfoForm () {
    put("/user/user/update", userInfoForm).then(res => {
      if (res.code == 200) {
        ElMessage.success(res.msg)
      } else {
        ElMessage.error(res.msg)
      }
    })

    console.log(userInfoForm);
  }

//维护密码
const passwordForm = reactive({
  password: '',
  newPassword: '',
  rePassword: ''
})
function submitPasswordForm () {
  put("/user/user/updatePassword", passwordForm).then(res => {
    if (res.code == 200) {
      ElMessage.success(res.msg)
    } else {
      ElMessage.error(res.msg)
    }
  })
}

//维护收货信息

const receipt = ref()


const page = reactive({
  pageSize: 10,
  pageNo: 0,
  total: 0
})
function getReceipt () {
  get("/user/address/page", page).then(res => {
    if (res.code == 200) {
      var data = res.data
      page.pageSize = data.size
      page.pageNo = data.current
      page.total = data.total
      receipt.value = data.records
    }
  })
}
getReceipt()
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
      getReceipt()
      insertAddressDialog.value = false;
    } else {
      ElMessage.error(res.msg)

    }
  })
}

// 编辑收货地址
const updateAddressDialog = ref(false)
const updateAddressForm = reactive({
  id: '',
  name: '',
  address: '',
  phone: '',
  isDefault: 0,
})
function openUpdateAddressDialog (row) {
  updateAddressForm.id = row.id
  updateAddressForm.name = row.name
  updateAddressForm.address = row.address
  updateAddressForm.phone = row.phone
  updateAddressDialog.value = true
}

const handleUpdateClose = (done) => {

  updateAddressForm.name = ''
  updateAddressForm.address = ''
  updateAddressForm.phone = ''
  updateAddressForm.isDefault = 0
  done()
}

function updateAddress () {
  put("/user/address/update", updateAddressForm).then(res => {
    if (res.code == 200) {
      ElMessage.success("修改成功")
      getReceipt()
      updateAddressDialog.value = false
    }
  })
}
//收货地址分页信息改变
function handleSizeChange (val) {
  page.pageSize = val
  getReceipt()
}
function handleCurrentChange (val) {
  page.pageNo = val
  getReceipt()
}

//设置默认地址
function setDefault (id) {
  put('/user/address/setDefault', {
    id: id
  }).then(res => {
    ElMessage.success('设置成功')
    getReceipt()

  })
}
function deleteAddress (id) {
  del(`/user/address/delete/${id}`).then(res => {
    ElMessage.success('删除成功')
    getReceipt()

  })
}
</script>


<template>
  <div class="content-c">
    <el-form :model="userInfoForm" v-if="showInfo == 'userInfo'" label-width="60px" label-position="right">
      <div style="text-align: center;">
        <h1 style="color:rgb(221, 126, 107) ">个人信息</h1>
      </div>
      <el-form-item label="名称" class="user-info-item-label">
        <el-input v-model="userInfoForm.name" />
      </el-form-item>
      <el-input v-model="userInfoForm.id" v-show="false" />
      <el-form-item label="邮箱">
        <el-input v-model="userInfoForm.mail" />
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="userInfoForm.phone" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitUserInfoForm()">
          确认修改
        </el-button>
        <el-button @click="showPasswordForm()">修改密码</el-button>
        <el-button @click="showReceipt()">收货地址</el-button>
      </el-form-item>
    </el-form>
    <el-form :model="passwordForm" v-if="showInfo == 'password'" label-width="80px" label-position="right">
      <div style="text-align: center;">
        <h1 style="color:rgb(221, 126, 107) ">密码修改</h1>
      </div>
      <el-form-item label="原密码">
        <el-input v-model="passwordForm.password" type="password" placeholder="请输入原密码" />
      </el-form-item>
      <el-form-item label="新密码">
        <el-input v-model="passwordForm.newPassword" type="password" placeholder="请输入新密码" />
      </el-form-item>
      <el-form-item label="重复密码">
        <el-input v-model="passwordForm.rePassword" type="password" placeholder="请再次输入新密码" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitPasswordForm()">
          确认修改
        </el-button>
        <el-button @click="showUserInfoForm()">个人信息</el-button>
        <el-button @click="showReceipt()">收货地址</el-button>
      </el-form-item>
    </el-form>

    <el-dialog v-model="insertAddressDialog" title="新增收获信息" width="30%" :before-close="handleClose">
      <el-form :label-position="right" label-width="100px" :model="addressForm" style="max-width: 460px">
        <el-form-item label="收获人">
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



    <el-dialog v-model="updateAddressDialog" title="编辑收获信息" width="30%" :before-close="handleUpdateClose">
      <el-input v-model="updateAddressForm.id" style="display: none;" />
      <el-form :label-position="right" label-width="100px" :model="updateAddressForm" style="max-width: 460px">
        <el-form-item label="收获人">
          <el-input v-model="updateAddressForm.name" />
        </el-form-item>
        <el-form-item label="手机号码">
          <el-input v-model="updateAddressForm.phone" />
        </el-form-item>
        <el-form-item label="详细地址">
          <el-input v-model="updateAddressForm.address" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="updateAddressDialog = false">取消</el-button>
          <el-button type="primary" @click="updateAddress()">
            确定
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
  <div v-if="showInfo == 'receipt'" style="width: 890px;margin: 0 auto;">
    <div style="text-align: center;">
      <h1 style="color:rgb(221, 126, 107) ">收货地址</h1>
    </div>
    <el-table :data="receipt" style="width: 890px" class="receipt-table" :header-cell-style="{ 'text-align': 'center' }"
      :cell-style="{ 'text-align': 'center' }" height="530">
      <el-table-column type="index" width="50" />
      <el-table-column prop="name" label="收获人" width="180" />
      <el-table-column prop="phone" label="手机号码" width="180" />
      <el-table-column prop="address" label="详细地址" width="300" show-overflow-tooltip />

      <el-table-column label="操作" width="180">
        <template v-slot="scope">
          <el-button type="primary" :icon="Edit" circle plain title="编辑" @click="openUpdateAddressDialog(scope.row)" />
          <el-button v-if="scope.row.isDefault == 0" type="warning" :icon="Star" circle plain title="设为默认"
            @click="setDefault(scope.row.id)" />
          <el-button v-else type="warning" :icon="Star" circle />
          <el-button type="danger" :icon="Delete" circle plain title="删除" @click="deleteAddress(scope.row.id)" />
        </template>
      </el-table-column>
    </el-table>
    <el-pagination :current-page="page.pageNo" :page-size="page.pageSize" :page-sizes="[10, 20, 50]" :small="small"
      :disabled="disabled" :background="true" layout="total, sizes, prev, pager, next, jumper" :total="page.total"
      @size-change="handleSizeChange" @current-change="handleCurrentChange" class="bottom"></el-pagination>
    <el-button @click="insertAddressDialog = true">新增收获信息</el-button>
    <el-button @click="showUserInfoForm">个人信息</el-button>
    <el-button @click="showPasswordForm">修改密码</el-button>




  </div>
</template>

<style scoped>
.content-c {
  width: 30%;
  margin: 0 auto;
  padding-top: 100px;
}

.user-info-item-label label {
  color: rgb(221, 126, 107);
}

.bottom {
  display: flex;
  justify-content: end;
  padding-top: 20px;
}

.popclass .el-popper {
  width: 20px;
}
</style>