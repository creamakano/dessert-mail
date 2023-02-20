<script setup>
import { ref } from 'vue'
import { get, post } from '../../../tool/http.js'
import { ElMessage } from 'element-plus'
import { Star, StarFilled } from '@element-plus/icons-vue'
//商品分类
const productType = ref([])
get('/product/type/list').then((res) => {
  productType.value = res.data
}).catch(err => {
  ElMessage.error(err)
})
//获取商品数据函数
function getProductList () {
  const param = {}
  param.pageSize = pageSize.value,
    param.pageNo = currentPage.value,
    param.typeId = type.value,
    get('/product/product/page', param).then(res => [
      productList.value = res.data.records,
      currentPage.value = res.data.current,
      pageSize.value = res.data.size,
      total.value = res.data.total
    ])
}
// 分页信息

const currentPage = ref(1)
const total = ref(0)
const pageSize = ref(10)


//菜单默认值
const activeIndex = ref('0')
const type = ref(0)

//商品数据
const productList = ref([])
const param = {}
getProductList()
const selectType = (key) => {
  type.value = key;
  getProductList(param)
}



//分页信息改变
function handleCurrentChange (val) {
  currentPage.value = val
  getProductList()
}

function handleSizeChange (val) {
  pageSize.value = val
  getProductList()
}

//收藏
function starCancel (id) {
  console.log(id + "  cancel");
}
function star (id) {
  console.log(id + "  star");
}

//跳转到详情
function goToProductDetail (id) {
  console.log(id + "  goToProductDetail");
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

  // console.log("sessipn" + window.localStorage.getItem('LoginUser'));

}
</script>



<template>
  <div>
    <div class="product-type">
      <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="selectType">
        <el-menu-item index="0">所有商品</el-menu-item>
        <el-menu-item v-for="(val) in productType" :key='val.id' :index='val.id'>{{ val.name }}</el-menu-item>
      </el-menu>
    </div>
    <div class="content-c">
      <div class="content">
        <!-- <el-empty v-if="productList.values.size == 0" description="description" /> -->

        <div class="product-c" v-for="(val) in productList" :key="val.id">
          <el-image style="width: 160px; height: 160px" :src="`/src/assets/images/${val.name}.jpg`" :fit="fit"
            @click="goToProductDetail(val.id)" />
          <div>{{ val.name }}</div>
          <div class="price-c">
            <span v-if="val.discount == 1">￥{{ val.price }}</span>
            <span v-if="val.discount != 1" class="deleteLine">￥{{ val.price }}</span>
            <span v-if="val.discount != 1" class="discountPrice">&nbsp; ￥{{ val.price * val.discount }}</span>
          </div>
          <el-icon v-if="val.id % 2 == 1" class="star" size="25px" @click="star(val.id)" color="white">
            <Star />
          </el-icon>
          <el-icon v-else class="star" size="28px" @click="starCancel(val.id)" color="red">
            <StarFilled />
          </el-icon>
          <el-button type="primary" @click="addToCart(val.id)">加入购物车</el-button>
        </div>
        <div class="bottom-c">
          <el-pagination :current-page="currentPage" :page-size="pageSize" :page-sizes="[10, 20, 50]" :small="small"
            :disabled="disabled" :background="true" layout="total, sizes, prev, pager, next, jumper" :total="total"
            @size-change="handleSizeChange" @current-change="handleCurrentChange" class="bottom"></el-pagination>
        </div>
      </div>

    </div>



  </div>
</template>

<style scoped>
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