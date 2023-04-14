<template>
  <div ref="main" style="width: 100%; height: 400px"></div>
</template>

<script setup>
import * as echarts from 'echarts/core';
import {
  CanvasRenderer
} from 'echarts/renderers';

import { ref, onMounted, onUnmounted, reactive, watch } from "vue";
//  按需引入 echarts
const dataMap = reactive({
  chartName: 'main',
  chartData: [],
  option: {},
})

// 基础数据
const chartPrepare = () => {
  dataMap.myChart = echarts.init(document.getElementById(dataMap.chartName));

  // 绘制图表需要的数据
  dataMap.option = {
    xAxis: {
      type: 'category',
      boundaryGap: false,
    },
    yAxis: {
      type: 'value'
    },
    series: [{
      data: dataMap.chartData,
      type: 'line'
    }]
  }
}
// 绘制图表
const chartOpen = () => {
  dataMap.myChart.setOption(dataMap.option, true);
}

onMounted(() => {
  // 基础数据
  chartPrepare()
  // 绘制图表
  chartOpen()
});

watch(() => dataMap.chartData,
  (count, prevCount) => {
    // 监听数据变了  就重新绘制一遍  也就是大佬们说的  "切记，数据变化后需要再次调init方法刷线图表"
    // 绘制图表
    chartOpen()
    console.log(count, prevCount);
  }, {
  deep: true
}
)
dataMap.chartData = [
  { value: 235, name: '视频广告' },
  { value: 274, name: '联盟广告' },
  { value: 310, name: '邮件营销' },
  { value: 335, name: '直接访问' },
  { value: 400, name: '搜索引擎' }
]


onUnmounted(() => {
  //销毁
  echarts.dispose(dataMap.myChart);
  dataMap.myChart = null;
})


</script>