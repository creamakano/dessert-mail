<script setup>
import { ElMessage } from "element-plus";
import { ref, reactive } from "vue";
import { get, post, del, put } from "../../../tool/http";
import { Search, Refresh } from '@element-plus/icons-vue'

const commentList = ref()
//获取页面数据
const queryForm = reactive({
  pageNo: 0,
  pageSize: 10,
  total: 0,
  userName: '',
  productName: ''
})
function getPage () {
  get('/product/comment/allPage', queryForm).then(res => {
    if (res.code == 200) {
      var data = res.data
      commentList.value = data.records
      queryForm.total = data.total
    }
  })
}
getPage()
//重置表单
function queryReset () {
  queryForm.userName = ''
  queryForm.productName = ''
  getPage()
}



//删除评论
function deleteComment (id) {
  del(`/product/comment/delete/${id}`).then(res => {
    if (res.code == 200) {
      ElMessage.success("删除成功")
      getPage()
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

    <div class="title">商品评价列表</div>

    <div class="content">
      <div class="query-line">
        <el-form :inline="true" :model="queryForm" class="demo-form-inline">
          <el-form-item label="商品名称">
            <el-input v-model="queryForm.productName" placeholder="" />
          </el-form-item>
          <el-form-item label="评价用户">
            <el-input v-model="queryForm.userName" placeholder="" />
          </el-form-item>


          <el-form-item>
            <el-button :icon="Search" circle @click="getPage" /> <el-button :icon="Refresh" circle @click="queryReset" />
          </el-form-item>

        </el-form>

      </div>
      <el-table :data="commentList" :cell-style="{ textAlign: 'center' }" :header-cell-style="{ 'text-align': 'center' }"
        :row-style="{ 'height': '42px' }" :header-row-style="{ 'height': '42px' }" height="465">
        <el-table-column type="index" />
        <el-table-column prop="productName" label="商品名称" />
        <el-table-column prop="userName" label="评价用户" />
        <el-table-column prop="date" label="评价时间" />
        <el-table-column prop="content" label="评价内容" show-overflow-tooltip />
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="danger" size="small" @click="deleteComment(scope.row.id)">删除</el-button>
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
  padding: 0 144px;
  box-sizing: border-box;
  display: flex;
  justify-content: space-between;
}
</style>