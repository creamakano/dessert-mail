<script setup>
import { ElMessage } from 'element-plus';
import { reactive, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { get, post } from '../../../tool/http';
const { query } = useRoute()
const product = ref({
  name: ''
})
const queryForm = reactive({
  pageNo: 1,
  pageSize: 5,
  total: 0,
  productId: query.id
})

get(`/product/product/getById/${query.id}`).then(res => {
  console.log(res.data);
  product.value = res.data

})

function addCollection () {
  post('/user/collection/insert', {
    productId: product.value.id
  }).then(res => {
    if (res.code == 200) {
      ElMessage.success("收藏成功")
    } else {
      ElMessage.error(res.msg)
    }
  })
}

function addCart () {
  post('/product/cart/insert', {
    productId: product.value.id,
    num: 1,
    typeId: product.value.typeId
  }).then(res => {
    if (res.code == 200) {
      ElMessage.success("加入购物车成功")
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const commentList = ref()

function getPage () {
  get('/product/comment/page', queryForm).then(res => {
    if (res.code == 200) {
      var data = res.data
      commentList.value = data.records
      queryForm.total = data.total
    }
  })
}
getPage()

//分页信息改变
function handlePageChange (val) {
  queryForm.pageNo = val
  getPage()
}

function handleSizeChange (val) {
  queryForm.pageSize = val
  getPage()
}
</script>

<template>
  <div class="product-c">
    <el-image style="width: 260px; height: 260px" :src="`/src/assets/images/${product.picture}`" :fit="fit" />
    <div class="info">
      <div class="info-array">
        <div class="info-item">
          <div class="info-item-title">
            <el-icon size="20">
              <Goods />
            </el-icon>
            &nbsp;
            商品名称
          </div>
          <div class="info-item-content">
            {{ product.name }}
          </div>
        </div>
        <div class="info-item">
          <div class="info-item-title">
            <el-image src="/src/assets/icons/product-type.svg" style="width: 20px;height: 20px;" />

            &nbsp;
            商品类型
          </div>
          <div class="info-item-content">
            {{ product.type }}
          </div>
        </div>


      </div>

      <div class="info-array">
        <div class="info-item ">
          <div class="info-item-title discount">
            <el-image src="/src/assets/icons/discount.svg" style="width: 20px;height: 20px;" />

            &nbsp;
            商品折扣价
          </div>
          <div class="info-item-content discount">
            ￥{{ product.price * product.discount }}
          </div>
        </div>
        <div class="info-item">
          <div class="info-item-title">
            <el-image src="/src/assets/icons/price.svg" style="width: 20px;height: 20px;" />
            &nbsp;
            商品原价
          </div>
          <div class="info-item-content">
            ￥{{ product.price }}
          </div>
        </div>
      </div>
      <div class="info-array">
        <div class="info-item " v-if="product.quantity">
          <div class="info-item-title" style="width:140px;">
            <el-image src="/src/assets/icons/product-quantity.png" style="width: 20px;height: 20px;" />

            &nbsp;
            商品数量
          </div>
          <div class="info-item-content" style="width: 210px;height: 20px;">
            {{ product.quantity }}
          </div>
        </div>
        <div class="info-item" v-if="product.weight">
          <div class="info-item-title" style="width:140px;">
            <el-image src="/src/assets/icons/product-weight.png" style="width: 20px;height: 20px;" />
            &nbsp;
            商品重量
          </div>
          <div class="info-item-content" style="width: 210px;height: 20px;">
            {{ product.weight }}g
          </div>
        </div>
      </div>

      <div class="info-array" style=" height: 90px;">
        <div class="info-item">
          <div class="info-item-title" style="width: 20%;">
            <el-image src="/src/assets/icons/product-description.svg" style="width: 20px;height: 20px;" />

            &nbsp;
            商品描述
          </div>
          <div class="info-item-content" style="width: 80%;">
            <el-tooltip class="box-item" effect="dark" :content="product.description" placement="top">
              <div style="width: fit-content;max-width: 100%;overflow: hidden;  font-size: 15px; 
                    display: -webkit-box;-webkit-box-orient: vertical; -webkit-line-clamp: 2;">
                {{ product.description }}

              </div>

            </el-tooltip>
          </div>
        </div>
      </div>
      <div>
        <el-button type="warning" @click="addCollection">加入收藏</el-button>
        <el-button type="success" @click="addCart">加入购物车</el-button>
      </div>

    </div>
  </div>
  <div class="comment-c">
    <div class="content-c">

      <div class="title">商品评价</div>

      <div class="content">
        <div class="content-item" v-for="item in commentList" :key="item.id">
          <div class="content-item-title">
            <el-image src="/src/assets/icons/comment.svg" style="width: 40px;height: 40px;" />
            <div class="item-title-info">
              <div style="padding-bottom: 5px;">{{ item.userName }}</div>
              <div>{{ item.date }}</div>
            </div>
          </div>
          <div class="content-content">
            {{ item.content }}
          </div>

        </div>


      </div>
      <div class="bottom-c" v-if="queryForm.total != 0">
        <el-pagination :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :page-sizes="[5, 10, 20, 50]"
          :disabled="disabled" :background="true" layout="total, sizes, prev, pager, next, jumper"
          :total="queryForm.total" @size-change="handleSizeChange" @current-change="handlePageChange"
          class="bottom"></el-pagination>
      </div>
    </div>
  </div>
</template>
<style scoped>
.info {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}

.info-array {
  display: flex;
  width: 700px;
}

.discount {
  color: rgb(245, 112, 112);
  /* color:tomato; */
}

.info-item {
  width: 100%;
  height: 40px;
  line-height: 40px;
  color: rgb(105, 105, 105);
  display: flex;
}

.info-item-title {
  width: 40%;
  display: flex;
  align-items: center;
  font-size: 18px;
  font-weight: bolder;
}

.info-item-content {
  width: 60%;
  font-size: 18px;
}




.product-c {
  display: flex;
  margin: 0 auto;
  width: 60%;
  display: flex;
  justify-content: center;
  padding-top: 40px;
}

.info {
  padding: 10px;
  padding-left: 30px;
}

.comment-c {
  padding: 40px 0;
}

.content-c {
  border: 1px solid rgb(221, 126, 107);
  width: 1000px;
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
  padding: 0 15px;
}

.bottom-c {
  width: 100%;
  display: flex;
  justify-content: end;
  padding-top: 10px;
  box-sizing: border-box;
  padding-bottom: 20px;
}

.info-line {
  display: flex;
}

.content-item {
  padding: 20px 0;
  border-bottom: 2px dotted tomato;
}

.content-item-title {
  display: flex;
  align-items: center;
}

.item-title-info {
  padding-left: 10px;
}

.content-content {
  padding-top: 10px;
  padding-left: 50px;
}
</style>