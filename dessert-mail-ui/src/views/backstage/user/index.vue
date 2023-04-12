<script setup>
import { ElMessage } from "element-plus";
import { ref, reactive } from "vue";
import { get, post, del, put } from "../../../tool/http";
import { Search, Refresh } from '@element-plus/icons-vue'


//字典
const userAuth = ref()
get('user/sys/dict', {
  name: "userAuth"
}).then(res => {
  if (res.code == 200) {
    userAuth.value = res.data
  }
})

const userList = ref()
//获取页面数据
const queryForm = reactive({
  pageNo: 0,
  pageSize: 10,
  total: 0,
  name: null

})
function getPage () {
  get('/user/user/page', queryForm).then(res => {
    if (res.code == 200) {
      var data = res.data
      userList.value = data.records
      queryForm.total = data.total
    }
  })
}
getPage()
//重置表单
function queryReset () {
  queryForm.name = null
  queryForm.phone = null
  queryForm.mail = null
  queryForm.auth = null
  getPage()
}
//用户编辑
const updateDialog = ref(false)
const updateForm = reactive({
  id: '',
  name: '',
  mail: '',
  phone: '',
  auth: ''
})
function openUpdateDialog (row) {
  updateForm.id = row.id
  updateForm.name = row.name
  updateForm.mail = row.mail
  updateForm.phone = row.phone
  updateForm.auth = row.auth
  updateDialog.value = true
}
function updateType () {
  put('/user/user/update', updateForm).then(res => {
    if (res.code == 200) {
      getPage()
      updateDialog.value = false
    } else {
      ElMessage.error(res.msg)
    }
  })
}
function handleClose () {
  updateForm.id = null
  updateForm.name = null
  updateDialog.value = false

}


//删除用户
function deleteUser (id) {
  del(`/user/user/delete/${id}`).then(res => {
    if (res.code == 200) {
      getPage()
      ElMessage.success("删除成功")
    } else {
      ElMessage.error(res.msg)
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

    <div class="title">商品类型列表</div>

    <div class="content">
      <div class="query-line">
        <el-form :inline="true" :model="queryForm" class="demo-form-inline">
          <el-form-item label="用户名称">
            <el-input v-model="queryForm.name" placeholder="" style="width: 160px;" clearable />
          </el-form-item>
          <el-form-item label="用户邮箱">
            <el-input v-model="queryForm.mail" placeholder="" style="width: 160px;" clearable />
          </el-form-item>
          <el-form-item label="手机号码">
            <el-input v-model="queryForm.phone" placeholder="" style="width: 160px;" clearable />
          </el-form-item>
          <el-form-item label="用户权限">
            <el-select v-model="queryForm.auth" placeholder=" " clearable size="" style="width: 160px;">
              <el-option v-for="(label, value) in userAuth" :key="value" :label="label" :value="Number(value)"
                clearable />
            </el-select>
          </el-form-item>


          <el-form-item>
            <el-button :icon="Search" circle @click="getPage" /> <el-button :icon="Refresh" circle @click="queryReset" />
          </el-form-item>

        </el-form>
      </div>
      <el-table :data="userList" :cell-style="{ textAlign: 'center' }" :header-cell-style="{ 'text-align': 'center' }"
        :row-style="{ 'height': '42px' }" :header-row-style="{ 'height': '42px' }" height="465">
        <el-table-column prop="id" label="用户ID" />
        <el-table-column prop="name" label="用户名称" />
        <el-table-column prop="mail" label="用户邮箱" />
        <el-table-column prop="phone" label="手机号码" />
        <el-table-column prop="auth" label="用户权限">
          <template v-slot="scope">
            {{ userAuth[scope.row.auth] }}
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="warning" size="small" @click="openUpdateDialog(scope.row)">修改</el-button>
            <el-button type="danger" size="small" @click="deleteUser(scope.row.id)">删除</el-button>
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



  <el-dialog v-model="updateDialog" title="用户信息修改" width="30%" :before-close="handleClose">
    <el-form :label-position="right" label-width="100px" :model="updateTypeForm" style="max-width: 460px">
      <el-form-item label="用户ID">
        <el-input v-model="updateForm.id" disabled />
      </el-form-item>
      <el-form-item label="用户名称">
        <el-input v-model="updateForm.name" />
      </el-form-item>
      <el-form-item label="用户邮箱">
        <el-input v-model="updateForm.mail" />
      </el-form-item>
      <el-form-item label="手机号码">
        <el-input v-model="updateForm.phone" />
      </el-form-item>
      <el-form-item label="用户权限">
        <el-select v-model="updateForm.auth" placeholder="" size="" style="width: 160px;">
          <el-option v-for="(label, value) in userAuth" :key="value" :label="label" :value="Number(value)" clearable />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="updateDialog = false">取消</el-button>
        <el-button type="primary" @click="updateType()">
          确定
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>
.content-c {
  border: 1px solid rgb(221, 126, 107);
  width: 1300px;
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
  padding-left: 85px;
  box-sizing: border-box;
  display: flex;
  justify-content: space-between;
}
</style>