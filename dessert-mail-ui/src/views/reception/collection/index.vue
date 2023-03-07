<script setup>
import { ref, reactive } from "vue";
import { del, get } from "../../../tool/http";
import { ElMessage } from 'element-plus'
import { Search, Refresh } from '@element-plus/icons-vue'

//获取页面数据
const queryForm = reactive({
  pageNo: 0,
  pageSize: 5,
  total: 0

})
const collectionList = ref()
function getPage () {
  get('/user/collection/page', queryForm).then(res => {
    if (res.code == 200) {
      var data = res.data
      collectionList.value = data.records
      queryForm.total = data.total
    }
  })
}
getPage()
function deleteCollection (id) {
  del(`/user/collection/deleteByProductId/${id}`).then(res => {
    ElMessage.success('删除成功')
    getPage()
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
  <div class="all-c">
    <div class="content-c">

      <div class="title">我的收藏</div>

      <div class="content">

        <el-table :data="collectionList" style="width: 100%" :cell-style="{ textAlign: 'center' }" max-height="655"
          :header-cell-style="{ 'text-align': 'center' }">
          <el-table-column label="商品图片" width="180">
            <template v-slot="scope">
              <el-image style="width: 100px; height:100px" :src="`/src/assets/images/${scope.row.name}.jpg`" :fit="fit"
                @click="goToProductDetail(val.id)" />
            </template>
          </el-table-column>
          <el-table-column prop="name" label="商品名称" />
          <el-table-column prop="type" label="商品类型" />
          <el-table-column prop="price" label="商品原价" />
          <el-table-column label="折扣价">
            <template v-slot="scope">
              {{ scope.row.price * scope.row.discount }}
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template v-slot="scope">
              <el-button type="danger" size="small" @click="deleteCollection(scope.row.id)">删除</el-button>
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
  width: 1200px;
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