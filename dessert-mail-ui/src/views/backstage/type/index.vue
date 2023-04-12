<script setup>
import { ElMessage } from "element-plus";
import { ref, reactive } from "vue";
import { get, post, del, put } from "../../../tool/http";
import { Search, Refresh } from '@element-plus/icons-vue'

const typeList = ref()
//获取页面数据
const queryForm = reactive({
  pageNo: 0,
  pageSize: 10,
  total: 0,
  name: null

})
function getPage () {
  get('/product/type/page', queryForm).then(res => {
    if (res.code == 200) {
      var data = res.data
      typeList.value = data.records
      queryForm.total = data.total
    }
  })
}
getPage()
//重置表单
function queryReset () {
  queryForm.name = ''
  queryForm.typeId = ''
  getPage()
}
//类型编辑
const updateDialog = ref(false)
const updateTypeForm = reactive({
  id: '',
  name: ''
})
function openUpdateDialog (row) {
  updateTypeForm.id = row.id
  updateTypeForm.name = row.name
  updateDialog.value = true
}
function updateType () {
  put('/product/type/update', updateTypeForm).then(res => {
    if (res.code == 200) {
      getPage()
      ElMessage.success("编辑成功")
      updateDialog.value = false
    } else {
      ElMessage.error(res.msg)
    }
  })
}
function handleClose () {
  updateTypeForm.id = null
  updateTypeForm.name = null
  updateDialog.value = false

}

//类型新增
const insertDialog = ref(false)
const insertTypeForm = reactive({
  name: ''
})
function openInsertDialog () {
  insertDialog.value = true
}
function insertType () {
  post('/product/type/insert', insertTypeForm).then(res => {
    if (res.code == 200) {
      getPage()
      ElMessage.success("添加成功")
      insertDialog.value = false
    } else {
      ElMessage.error(res.msg)
    }
  })
}
function handleInsertClose () {
  insertTypeForm.name = null
  insertDialog.value = false
}


//删除类型
function deleteType (id) {
  del(`/product/type/delete/${id}`).then(res => {
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
          <el-form-item label="类型名称">
            <el-input v-model="queryForm.name" placeholder="" />
          </el-form-item>


          <el-form-item>
            <el-button :icon="Search" circle @click="getPage" /> <el-button :icon="Refresh" circle @click="queryReset" />
          </el-form-item>

        </el-form>

        <el-form-item>
          <el-button type="warning" size="" plain @click="insertDialog = true">添加</el-button>
        </el-form-item>
      </div>
      <el-table :data="typeList" :cell-style="{ textAlign: 'center' }" :header-cell-style="{ 'text-align': 'center' }"
        :row-style="{ 'height': '42px' }" :header-row-style="{ 'height': '42px' }" height="465">
        <el-table-column prop="id" label="类型编号" />
        <el-table-column prop="name" label="类型名称" />
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="warning" size="small" @click="openUpdateDialog(scope.row)">修改</el-button>
            <el-button type="danger" size="small" @click="deleteType(scope.row.id)">删除</el-button>
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



  <el-dialog v-model="updateDialog" title="编辑类型" width="30%" :before-close="handleClose">
    <el-form :label-position="right" label-width="100px" :model="updateTypeForm" style="max-width: 460px">
      <el-form-item label="类型ID">
        <el-input v-model="updateTypeForm.id" disabled />
      </el-form-item>
      <el-form-item label="类型名称">
        <el-input v-model="updateTypeForm.name" />
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

  <el-dialog v-model="insertDialog" title="新增类型" width="30%" :before-close="handleInsertClose">
    <el-form :label-position="right" label-width="100px" :model="insertTypeForm" style="max-width: 460px">
      <el-form-item label="类型名称">
        <el-input v-model="insertTypeForm.name" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="insertDialog = false">取消</el-button>
        <el-button type="primary" @click="insertType()">
          确定
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>
.content-c {
  border: 1px solid rgb(221, 126, 107);
  width: 1100px;
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
  padding: 0 150px;
  box-sizing: border-box;
  display: flex;
  justify-content: space-between;
}
</style>