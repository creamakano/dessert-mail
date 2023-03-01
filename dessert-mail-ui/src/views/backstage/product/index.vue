<script setup>
import { ref, reactive } from "vue";
import { get, post, del, put } from "../../../tool/http";

const productList = ref()
//获取页面数据
const queryForm = reactive({
  pageNo: 0,
  pageSize: 10,
  total: 0

})
function getPage () {
  get('/product/product/page', queryForm).then(res => {
    if (res.code == 200) {
      var data = res.data
      productList.value = data.records
      queryForm.total = data.total
    }
  })
}
getPage()


function openUpdateModel (row) {

}
function deleteType (id) {

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
        <el-button size="small" plain>新增</el-button>
        <div class="query-array">
          <el-input v-model="queryForm.name" placeholder="类型名称" size="small" class="query-item" clearable
            @blur="getPage" />
        </div>
      </div>
      <el-table :data="productList" :cell-style="{ textAlign: 'center' }" :header-cell-style="{ 'text-align': 'center' }"
        :row-style="{ 'height': '42px' }" :header-row-style="{ 'height': '42px' }" height="465">
        <el-table-column label="商品图片" width="180">
          <template v-slot="scope">
            <el-image style="width: 100px; height:100px" :src="`/src/assets/images/${scope.row.name}.jpg`" :fit="fit"
              @click="goToProductDetail(val.id)" />
          </template>
        </el-table-column>
        <el-table-column prop="name" label="商品名称" />
        <el-table-column prop="type" label="商品类型" />
        <el-table-column prop="price" label="商品原价" />
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
  display: flex;
  justify-content: space-between;
}

.query-item {
  width: 100px;
}
</style>