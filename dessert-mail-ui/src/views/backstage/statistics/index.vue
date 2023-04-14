<template>
  <div class="all-c">
    <el-row :gutter="12">
      <el-col :span="6">
        <el-card shadow="always" :body-style="cardStyle"><el-image src="/src/assets/icons/order-num.svg"
            style="width: 50px;height: 50px;" />
          <div style="padding-left: 10px;box-sizing: border-box;">
            <div style="color: grey;padding-bottom: 5px; box-sizing: border-box;">今日订单总数</div>
            <div>{{todayOrderNums}}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="always" :body-style="cardStyle"><el-image src="/src/assets/icons/statistics-money.svg"
            style="width: 50px;height: 50px;" />
          <div style="padding-left: 10px;box-sizing: border-box;">
            <div style="color: grey;padding-bottom: 5px; box-sizing: border-box;">今日销售总额</div>
            <div>{{todayTotalMoney}}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="always" :body-style="cardStyle"><el-image src="/src/assets/icons/statistics-money-yesterday.svg"
            style="width: 50px;height: 50px;" />
          <div style="padding-left: 10px;box-sizing: border-box;">
            <div style="color: grey;padding-bottom: 5px; box-sizing: border-box;">昨日销售总额</div>
            <div>{{yesterdayOrderNums}}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="always" :body-style="cardStyle"><el-image src="/src/assets/icons/statistics-seven-days.svg"
            style="width: 50px;height: 50px;" />
          <div style="padding-left: 10px;box-sizing: border-box;">
            <div style="color: grey;padding-bottom: 5px; box-sizing: border-box;">近7天销售总额</div>
            <div>{{weekOrderNums}}</div>
          </div>
        </el-card>
      </el-col>



    </el-row>
    <div style="display: flex;margin: 40px auto;">
      <div ref="orderStatusCharts" style="width: 100%; height: 400px"></div>
      <div ref="typeCharts" style="width: 100%; color: black;"></div>
    </div>
    <div ref="orderCharts" style="width: 100%; height: 400px"></div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { get } from "../../../tool/http";
import * as echarts from "echarts";
import macaronsThem from './macarons.json'
echarts.registerTheme('macarons', macaronsThem)

const cardStyle = ref({
  backgroundColor: 'rgb(255 206 163)',
  display: 'flex'
})
// 类型统计
const typeCharts = ref() // 使用ref创建虚拟DOM引用，使用时用main.value
const datas = ref([])
var typeChart
// 基于准备好的dom，初始化echarts实例
const TypeChartOption = (param) => {
  typeChart.setOption({
    title: {
      text: '各类型销量统计',
      textStyle: {
        color: 'black'
      }
      ,
      x: 'center'
    },
    legend: {
      orient: 'vertical',
      x: 'left',
      y: 'top',
      textStyle: {
        color: 'grey'
      }
    },
    series: [
      {
        name: '访问来源',
        type: 'pie',    // 设置图表类型为饼图
        radius: '55%',  // 饼图的半径，外半径为可视区尺寸（容器高宽中较小一项）的 55% 长度。 // 数据数组，name 为数据项名称，value 为数据项值
        data: datas.value,

      }

    ]
  })
}


// 订单类型统计
const orderStatusCharts = ref()
const statusDatas = ref([])
var orderStatusChart
// 基于准备好的dom，初始化echarts实例
const orderStatusChartOption = (param) => {
  orderStatusChart.setOption({
    title: {
      text: '订单概况统计',
      textStyle: {
        color: 'black'
      }
      ,
      x: 'center'
    },
    legend: {
      orient: 'vertical',
      x: 'left',
      y: 'top',
      textStyle: {
        color: 'grey'
      }
    },
    series: [
      {
        name: '访问来源',
        type: 'pie',    // 设置图表类型为饼图
        radius: '55%',  // 饼图的半径，外半径为可视区尺寸（容器高宽中较小一项）的 55% 长度。 // 数据数组，name 为数据项名称，value 为数据项值
        data: statusDatas.value,
      }
    ]
  })
}

// 订单统计
const orderCharts = ref()
const orderDataX = ref([])
const orderDataY = ref([])
var orderChart
// 基于准备好的dom，初始化echarts实例
const orderChartOption = () => {
  orderChart.setOption({
    title: {
      text: '近30天订单数量统计',
      textStyle: {
        color: 'black'
      }
      ,
      x: 'center'
    },
    // legend: {
    //   orient: 'vertical',
    //   x: 'left',
    //   y: 'top',
    //   textStyle: {
    //     color: 'grey'
    //   }
    // },
    xAxis: {
      data: orderDataX.value
    },
    yAxis: [{
      type: 'value'
    }],
    series: [
      {
        data: orderDataY.value,
        type: 'bar',
        itemStyle: {
          normal: {
            label: {
              show: true, //开启显示
              position: 'top', //在上方显示
              formatter: function (params) {
                if (params.value == 0) {  //为0时不显示
                  return ''
                } else {
                  return params.value
                }
              },
              textStyle: { //数值样式
                color: 'black',
                fontSize: 16
              }
            },
            color: "#ffb980"
          }
        },
      }

    ]
  })
}

const todayOrderNums = ref()
const todayTotalMoney = ref()
const yesterdayOrderNums = ref()
const weekOrderNums = ref()

function initTypeChart () {
  orderStatusChart = echarts.init(orderStatusCharts.value, 'macarons')
  orderChart = echarts.init(orderCharts.value)
  typeChart = echarts.init(typeCharts.value, 'macarons')
  get('/order/statistics').then(res => {
    todayOrderNums.value = res.data.todayOrderNums
    todayTotalMoney.value =res.data.todayTotalMoney
    yesterdayOrderNums.value =res.data.yesterdayOrderNums
    weekOrderNums.value = res.data.weekOrderNums

    datas.value = res.data.type
    TypeChartOption(datas)
    statusDatas.value = res.data.orderStatus
    orderStatusChartOption(statusDatas)
    orderDataX.value = res.data.orderX
    orderDataY.value = res.data.orderY
    orderChartOption()

    
  }).catch(err => {
    console.log(err);
  })
}


onMounted(
  () => {
    initTypeChart()
  }
)

</script>
<style scoped>
.a {
  background-color: rgb(229, 161, 101, 0.8);

}

.all-c {
  width: 50%;
  margin: 0 auto;
  padding-top: 30px;
  box-sizing: border-box;
}
</style>