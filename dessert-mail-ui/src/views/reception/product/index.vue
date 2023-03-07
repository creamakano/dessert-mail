<script setup>
import { reactive, ref } from 'vue'
import { get, post, del } from '../../../tool/http.js'
import { ElMessage } from 'element-plus'
import { Star, StarFilled, Search, Refresh } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'

const route = useRouter()
//商品分类
const productType = ref([])
get('/product/type/list').then((res) => {
  productType.value = res.data
}).catch(err => {
  ElMessage.error(err)
})
const queryForm = reactive({
  name: null,
  pageSize: 10,
  pageNo: 1,
  typeId: 0,
  total: 0
})
//获取商品数据函数
function getPage () {
  get('/product/product/page', queryForm).then(res => [
    productList.value = res.data.records,
    queryForm.pageNo = res.data.current,
    queryForm.pageSize = res.data.size,
    queryForm.total = res.data.total
  ])
}
//重置
function queryReset(){
  queryForm.name = null
  getPage()
}

//菜单默认值
const activeIndex = ref('0')

//商品数据
const productList = ref([])
const param = {}
getPage()
const selectType = (key) => {
  queryForm.typeId = key;
  getPage(param)
}



//分页信息改变
function handleCurrentChange (val) {
  queryForm.pageNo = val
  getPage()
}

function handleSizeChange (val) {
  queryForm.pageSize = val
  getPage()
}

//收藏
function starCancel (id, val) {
  del(`/user/collection/deleteByProductId/${id}`).then(res => {
    if (res.code == 200) {
      ElMessage.success("取消收藏成功");
      val.isCollection = 0
    } else {
      ElMessage.error(res.msg)
    }
  })
}
function star (id, val) {
  post('/user/collection/insert', {
    productId: id
  }).then(res => {
    if (res.code == 200) {
      ElMessage.success("收藏成功");
      val.isCollection = 1
    } else {
      ElMessage.error(res.msg)
    }
  })
}


//添加到购物车
function addToCart (id) {
  post('/product/cart/insert', {
    productId: id,
    num: 1
  }).then(res => {
    if (res.code == 200) {
      ElMessage.success("添加成功")
    } else {
      ElMessage.error(res.msg)
    }
  })
}

//跳转到商品详情页
function goToProductDetail (id) {
  route.push({
    path: '/home/productDetail',
    query: { id: id }
  })
}
</script>



<template>
  <div>
    <div class="menu-c">
      <div class="menu">
        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="selectType">
          <el-menu-item index="0">所有商品</el-menu-item>
          <el-menu-item v-for="(val) in productType" :key='val.id' :index='val.id'>{{ val.name }}</el-menu-item>
        </el-menu>
      </div>
      <div class="query-line">
        <el-form :inline="true" :model="queryForm" class="demo-form-inline form">
          <el-form-item label="商品名称">
            <el-input v-model="queryForm.name" placeholder="" />
          </el-form-item>
          <el-form-item>
            <el-button :icon="Search" circle @click="getPage" /> <el-button :icon="Refresh" circle @click="queryReset" />
          </el-form-item>

        </el-form>


      </div>
    </div>
    <div class="content-c">
      <div class="content">
        <!-- <el-empty v-if="productList.values.size == 0" description="description" /> -->

        <div class="product-c" v-for="(val) in productList" :key="val.id">
          <el-image style="width: 160px; height: 160px" :src="`/src/assets/images/${val.picture}`" :fit="fit"
            @click="goToProductDetail(val.id)" />
          <div>{{ val.name }}</div>
          <div class="price-c">
            <span v-if="val.discount == 1">￥{{ val.price }}</span>
            <span v-if="val.discount != 1" class="deleteLine">￥{{ val.price }}</span>
            <span v-if="val.discount != 1" class="discountPrice">&nbsp; ￥{{ val.price * val.discount }}</span>
          </div>
          <el-icon v-if="val.isCollection == 0" class="star" size="25px" @click="star(val.id, val)" color="white">
            <Star />
          </el-icon>
          <el-icon v-if="val.isCollection == 1" class="star" size="28px" @click="starCancel(val.id, val)" color="red">
            <StarFilled />
          </el-icon>
          <el-button type="primary" @click="addToCart(val.id)">加入购物车</el-button>
        </div>
        <div class="bottom-c">
          <el-pagination :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :page-sizes="[10, 20, 50]"
            :small="small" :disabled="disabled" :background="true" layout="total, sizes, prev, pager, next, jumper"
            :total="queryForm.total" @size-change="handleSizeChange" @current-change="handleCurrentChange"
            class="bottom"></el-pagination>
        </div>
      </div>

    </div>



  </div>
</template>

<style scoped>
.flex-grow {
  flex-grow: 1;
}

.menu-c {
  width: 100%;
  background-color: #fff;
  display: flex;
  justify-content: space-between;
}

.menu {
  width: 1800px;
}

.query-line {
  height: 58px;
  line-height: 58px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 500px;
}

.form {
  height: 40px;
}

.content-c {
  width: 100%;

  min-height: 629px;
}

.content {
  width: 90%;
  margin: 0 auto;
  text-align: center;
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start
}


.product-c {
  width: 20%;
  height: 293px;
  position: relative;
}

.product-c div {
  padding-top: 5px;
}

.deleteLine {
  text-decoration: line-through
}

.discountPrice {
  color: red;
}

.price-c {
  margin-bottom: 5px;
}

.bottom-c {
  width: 100%;
}

.bottom {
  text-align: right;
  float: right;
  padding-bottom: 10px;
}

.star {
  width: 40px;
  position: absolute;
  top: 10px;
  z-index: 1;
  right: 90px;
}
</style>