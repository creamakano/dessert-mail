<script setup>
import { Money } from "@element-plus/icons-vue";
import { ElMessage } from "element-plus";
import { ref, reactive } from "vue";
import { useRoute, useRouter } from "vue-router";
import { get } from "../../../tool/http";
const { query } = useRoute()
const route = useRouter()
const orderStatus = ref({})
get('/user/sys/dict', {
  name: 'orderStatus'
}).then(res => {
  orderStatus.value = res.data
})
//获取页面数据
const queryForm = reactive({
  pageNo: 0,
  pageSize: 5,
  total: 0,
  orderId: query.orderId
})
//获取订单信息
const orderInfo = ref({})
get(`/order/order/${query.orderId}`).then(res => {
  orderInfo.value = res.data
})

const detailList = ref()
function getPage () {
  get('/order/orderDetail/page', queryForm).then(res => {
    if (res.code == 200) {
      detailList.value = res.data.records
      queryForm.total = res.data.total
    } else {
      ElMessage.error(res.msg)
    }
  })
}
getPage()

//跳转到商品详情页
function goToProductDetail (id) {
  route.push({
    path: '/home/productDetail',
    query: { id: id }
  })
}



//分页信息改变
function handleSizeChange (val) {
  queryForm.pageSize = val
  getPage()
}
function handlePageChange (val) {
  queryForm.pageNo = val
  getPage()
}

//评价
function comment (id) {
  route.push({
    path: '/home/comment',
    query: { ids: [id] }
  })
}
</script>

<template>
  <div class="all-c">
    <div class="content-c">
      <div class="title">订单详情</div>
      <div class="content">
        <div class="order-info-c">
          <div class="info-line">
            <div class="info-item info-item-title">
              <el-icon size="25">
                <Calendar />&nbsp;
              </el-icon>
              下单时间
            </div>
            <div class="info-item">{{ orderInfo.date }}</div>
            <div class="info-item info-item-title">
              <el-icon size="25">
                <List />&nbsp;
              </el-icon>
              订单编号
            </div>
            <div class="info-item">{{ orderInfo.orderNum }}</div>
          </div>
          <div class="info-line">
            <div class="info-item info-item-title">
              <el-icon size="25">
                <Money /> &nbsp;
              </el-icon>
              订单金额
            </div>
            <div class="info-item">￥{{ orderInfo.total }}</div>
            <div class="info-item info-item-title">
              <el-icon size="25">
                <InfoFilled />&nbsp;
              </el-icon>
              订单状态
            </div>
            <div class="info-item">{{ orderStatus[orderInfo.status] }}</div>
          </div>
        </div>
        <el-table :data="detailList" :cell-style="{ textAlign: 'center' }"
          :header-cell-style="{ 'text-align': 'center', 'background-color': 'rgb(238, 171, 157) !important', 'color': '#fff' }"
          style="margin-top: 20px;" height="460">
          <el-table-column type="index" />
          <el-table-column label="商品图片">
            <template v-slot="scope">
              <el-image style="width: 60px; height:60px" :src="`/src/assets/images/${scope.row.productPicture}`"
                :fit="fit" @click="goToProductDetail(scope.row.productId)" />
            </template>
          </el-table-column>
          <el-table-column prop="productName" label="商品名称" />
          <el-table-column prop="productPrice" label="商品价格" />
          <el-table-column prop="num" label="购买数量" />
          <el-table-column label="操作" v-if="orderInfo.status >= 4 ">
            <template v-slot="scope">
              <el-button v-if="scope.row.isComment == 1" type="warning" @click="comment(scope.row.id)">查看评价</el-button>
              <el-button v-else type="warning" @click="comment(scope.row.id)">评价</el-button>
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
  padding: 20px 0;
}

.content-c {
  border: 1px solid rgb(221, 126, 107);
  width: 1200px;
  margin: 20px auto;
}

.info-c {
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

.order-info-c {
  width: 100%;
  box-sizing: border-box;
}

.info-line {
  width: 100%;
  display: flex;
}

.info-item {
  width: 100%;
  height: 40px;
  line-height: 40px;
  padding-left: 10px;
  box-sizing: border-box;
  /* border: 1px solid rgb(238, 171, 157); */
  color: grey;
  display: flex;
  align-items: center;
  /* border-bottom: 2px solid white; */
}

.info-item-title {
  /* background-color: rgb(238, 171, 157); */
  /* color: white; */
  font-weight: bolder;
}
</style>