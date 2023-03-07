<script setup>
import { ref, reactive } from "vue";
import { get, post, del, put } from "../../../tool/http";
import ImageUploadComponent from "../../../components/backstage/ImageUploadComponent.vue";
import { ElMessage } from "element-plus";
import { Search, Refresh } from '@element-plus/icons-vue'
//字典
const typeDic = ref([])
get('/product/type/list').then(res => {
  typeDic.value = res.data
})




//获取页面数据
const productList = ref()
const queryForm = reactive({
  pageNo: 0,
  pageSize: 5,
  total: 0,
  name: '',
  typeId: ''

})
function getPage () {
  get('/product/product/allPage', queryForm).then(res => {
    if (res.code == 200) {
      var data = res.data
      productList.value = data.records
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

//删除商品
function deleteProduct (id) {
  del(`/product/product/delete/${id}`).then(res => {
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


//编辑商品
const updateDialog = ref(false)
const updateForm = reactive({
  id: '',
  name: '',
  price: null,
  picture: '',
  discount: '',
  typeId: '',
  storage: '',
  description: '',
  status: ''
})
function openUpdateDialog (row) {
  updateForm.id = row.id
  updateForm.name = row.name
  updateForm.price = row.price
  updateForm.picture = row.picture
  updateForm.discount = row.discount
  updateForm.typeId = row.typeId
  updateForm.storage = row.storage
  updateForm.description = row.description
  updateForm.status = row.status
  console.log(row);
  updateDialog.value = true
}
function updateType () {
  put('/product/product/update', updateForm).then(res => {
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
  updateForm.price = null
  updateForm.picture = null
  updateForm.discount = null
  updateForm.storage = null
  updateForm.description = null
  updateForm.status = null
  updateDialog.value = false

}
//获取图片地址
function getUrl (val) {
  updateForm.picture = val
}

//商品新增
const insertDialog = ref(false)
const insertForm = reactive({
  id: '',
  name: '',
  price: null,
  picture: '',
  discount: '',
  typeId: '',
  storage: '',
  description: '',
  status: ''
})

function insertProduct () {
  console.log(insertForm);
  post('/product/product/insert', insertForm).then(res => {
    if (res.code == 200) {
      getPage()
      insertDialog.value = false
    } else {
      ElMessage.error(res.msg)
    }
  })
}
function handleInsertClose () {
  insertForm.id = null
  insertForm.name = null
  insertForm.price = null
  insertForm.picture = null
  insertForm.discount = null
  insertForm.storage = null
  insertForm.description = null
  insertForm.status = null
  insertDialog.value = false
}



function getInsertUrl (val) {
  console.log(val);
  insertForm.picture = val
}




</script>

<template>
  <div class="all-c">
    <div class="content-c">

      <div class="title">商品类型列表 </div>
      <div class="content">
        <div class="query-line">
            <el-form :inline="true" :model="queryForm" class="demo-form-inline">
              <el-form-item label="商品名称">
                <el-input v-model="queryForm.name" placeholder="" />
              </el-form-item>

              <el-form-item label="商品类型">
                <el-select v-model="queryForm.typeId" placeholder=" " clearable size="">
                  <el-option v-for="item in typeDic" :key="item.id" :label="item.name" :value="item.id" />
                </el-select>
              </el-form-item>

              <el-form-item>
                <el-button :icon="Search" circle @click="getPage" /> <el-button :icon="Refresh" circle
                  @click="queryReset" />
              </el-form-item>

            </el-form>

          <el-form-item>
            <el-button type="warning" size="" plain @click="insertDialog = true">添加</el-button>
          </el-form-item>
        </div>
        <el-table :data="productList" :cell-style="{ textAlign: 'center' }"
          :header-cell-style="{ 'text-align': 'center' }" :row-style="{ 'height': '42px' }"
          :header-row-style="{ 'height': '42px' }" height="657">
          <el-table-column label="商品图片" width="180">
            <template v-slot="scope">
              <el-image style="width: 100px; height:100px" :src="`/src/assets/images/${scope.row.picture}`" :fit="fit"
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
              <el-button type="warning" size="small" @click="openUpdateDialog(scope.row)">编辑</el-button>
              <el-button type="danger" size="small" @click="deleteProduct(scope.row.id)">删除</el-button>
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




    <el-dialog v-model="updateDialog" title="编辑商品" width="30%" :before-close="handleClose">
      <el-form :label-position="right" label-width="100px" :model="updateForm" style="max-width: 460px">
        <el-form-item label="商品ID">
          <el-input v-model="updateForm.id" disabled />
        </el-form-item>
        <el-form-item label="商品名称">
          <el-input v-model="updateForm.name" />
        </el-form-item>

        <el-form-item label="商品价格">
          <el-input v-model="updateForm.price" />
        </el-form-item>
        <el-form-item label="商品折扣">
          <el-input v-model="updateForm.discount" />
        </el-form-item>
        <el-form-item label="商品类型">
          <el-select v-model="updateForm.typeId">
            <el-option v-for="item in typeDic" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="商品库存">
          <el-input v-model="updateForm.storage" />
        </el-form-item>
        <el-form-item label="商品描述">
          <el-input v-model="updateForm.description" />
        </el-form-item>
        <el-form-item label="上架状态">
          <el-select v-model="updateForm.status">
            <el-option label="上架" :value="1" />
            <el-option label="下架" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item label="商品图片">
          <ImageUploadComponent @sendUrl="getUrl" :picture="updateForm.picture" :src="'update'">
          </ImageUploadComponent>
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
      <el-form :label-position="right" label-width="100px" :model="insertForm" style="max-width: 460px">
        <el-form-item label="商品名称">
          <el-input v-model="insertForm.name" />
        </el-form-item>

        <el-form-item label="商品价格">
          <el-input v-model="insertForm.price" />
        </el-form-item>
        <el-form-item label="商品折扣">
          <el-input v-model="insertForm.discount" />
        </el-form-item>
        <el-form-item label="商品类型">
          <el-select v-model="insertForm.typeId">
            <el-option v-for="item in typeDic" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="商品库存">
          <el-input v-model="insertForm.storage" />
        </el-form-item>
        <el-form-item label="商品描述">
          <el-input v-model="insertForm.description" />
        </el-form-item>
        <el-form-item label="上架状态">
          <el-select v-model="insertForm.status">
            <el-option label="上架" :value="1" />
            <el-option label="下架" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item label="商品图片">
          <ImageUploadComponent @sendInsertUrl="getInsertUrl" :picture="insertForm.picture" :src="'insert'">
          </ImageUploadComponent>
        </el-form-item>


      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="handleInsertClose">取消</el-button>
          <el-button type="primary" @click="insertProduct()">
            确定
          </el-button>
        </span>
      </template>
    </el-dialog>

  </div>
</template>

<style scoped>
.all-c {
  padding: 40px 0;
}

.content-c {
  border: 1px solid rgb(221, 126, 107);
  width: 1100px;
  margin: 0 auto;
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
  padding: 0 60px;
  box-sizing: border-box;
  display: flex;
  justify-content: space-between;
}
</style>