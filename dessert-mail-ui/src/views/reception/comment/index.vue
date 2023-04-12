<script setup>
import { ElMessage } from "element-plus";
import { ref, reactive } from "vue";
import { useRoute, useRouter } from "vue-router";
import { get, post } from "../../../tool/http";
const { query } = useRoute()
const route = useRouter()

//页面列表
const details = ref()
function getPage () {
  get('/order/orderDetail/getDetailList', {
    idList: query.ids.toString()
  }).then(res => {
    details.value = res.data
    console.log(details.value);
  })
}
getPage()
function comment (id, productId, content) {
  post('/product/comment/insert', {
    orderDetailId: id,
    productId: productId,
    content: content
  }).then(res => {
    if (res.code == 401) {
      ElMessage.error(res.msg)
      route.push('/')
    } else if (res.code == 200) {
      ElMessage.success("评价成功")
      getPage()
    }
  })
}

const value = ref()
</script>


<template>
  <div class="all-c">
    <div class="content-c">

      <div class="title">商品评价</div>

      <div class="content">
        <div class="comment-item" v-for="item in details" :key="item.id">
          <div class="item-top">
            <el-image :src="`/src/assets/images/${item.productPicture}`" style="width: 250px;" />
            <div class="desc-c">
              <div class="info-array">
                <div class="info-item">
                  商品名称： &nbsp;
                </div>
                <div class="info-item">{{ item.productName }}</div>
              </div>
              <div class="info-array">
                <div class="info-item ">
                  购买金额： &nbsp;
                </div>
                <div class="info-item">￥{{ item.productPrice }}</div>
              </div>
              <div class="info-array">
                <div class="info-item">
                  购买数量： &nbsp;
                </div>
                <div class="info-item">{{ item.num }}</div>
              </div>
              <!-- <div class="info-array">
                  <el-rate v-model="value" allow-half size="large" void-color="#806a6a" />
                </div> -->

            </div>
          </div>
          <el-input v-model="item.comment" :rows="5" type="textarea" placeholder="请输入评价"
            :readonly="item.isComment == 1" />
          <div v-if="item.isComment == 0" class="bottom-c"><el-button type="primary"
              @click="comment(item.id, item.productId, item.comment)">评价 </el-button></div>

        </div>



      </div>

    </div>
  </div>
</template>

<style scoped>
.desc-c {
  padding: 20px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}

.all-c {
  padding: 40px 0;
}

.content-c {
  border: 1px solid rgb(221, 126, 107);
  width: 1000px;
  margin: 20px auto;
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

.comment-item {
  margin: 0 auto;
  width: 60%;
  padding: 20px;
  border: 1px solid white;
  margin-bottom: 20px;
  border-radius: 10px;
  background-color: #fdeadb;

}

.item-top {

  display: flex;
  padding-bottom: 20px;
}

.info-array {
  display: flex;

}

.info-item {
  height: 40px;
  line-height: 40px;
  color: gray;
}

.bottom-c {
  margin-top: 10px;
  width: 100%;
  display: flex;
  justify-content: end;
}
</style>