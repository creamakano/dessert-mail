<script setup>
import { ref, reactive } from "vue";
import { del, get } from "../../../tool/http";
import { ElMessage } from 'element-plus'

//获取页面数据
const queryForm = reactive({
  pageNo: 0,
  pageSize: 10,
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
  del(`/user/collection/delete/${id}`).then(res => {
    ElMessage.success('删除成功')
    getPage()
  })
}
</script>

<template>
  <div style="text-align: center;">
    <h1 style="color:rgb(221, 126, 107);  padding-top: 60px;padding-bottom: 20px;">我的收藏</h1>
  </div>
  <div class="collection-c">
    <div class="collection">
      <el-table :data="collectionList" style="width: 100%" :cell-style="{ textAlign: 'center' }"
        :header-cell-style="{ 'text-align': 'center' }">
        <el-table-column label="商品图片" width="180">
          <template v-slot="scope">
            <el-image style="width: 100px; height:100px" :src="`/src/assets/images/${scope.row.name}.jpg`" :fit="fit"
              @click="goToProductDetail(val.id)" />
          </template>
        </el-table-column>
        <el-table-column prop="name" label="商品名称" width="180" />
        <el-table-column prop="type" label="商品类型" width="180" />
        <el-table-column prop="price" label="商品原价" width="180" />
        <el-table-column label="折扣价" width="180">
          <template v-slot="scope">
            {{ scope.row.price * scope.row.discount }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180">
          <template v-slot="scope">
            <el-button type="danger" size="small" @click="deleteCollection(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="bottom-c">
        <el-pagination :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :page-sizes="[10, 20, 50]"
          :small="small" :disabled="disabled" :background="true" layout="total, sizes, prev, pager, next, jumper"
          :total="queryForm.total" @size-change="getPage()" @current-change="getPage()" class="bottom"></el-pagination>
      </div>
    </div>

  </div>
</template>

<style scoped>
.collection-c {
  width: 100%;
}

.collection {
  width: 1080px;
  margin: 0 auto;
}

.bottom-c {
  width: 100%;
  display: flex;
  justify-content: end;
  padding-top: 10px;
  box-sizing: border-box;
  padding-bottom: 20px;
}
</style>