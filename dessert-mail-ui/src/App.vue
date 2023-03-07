<script setup>
</script>

<template>
  <RouterView />
</template>

<style>
body {
  width: 100%;
  margin: 0;
  padding: 0;
  border: 0;

}

.el-table,
.el-table__expanded-cell {
  background-color: transparent !important;
}

.el-table th,
.el-table tr {
  border: 0 !important;
  background-color: transparent !important;
}

.el-table--enable-row-hover .el-table__body tr:hover>td {
  background-color: rgb(245, 222, 207) !important;
}

.el-textarea__inner {
  background-color: #fff7f1 !important;
}

</style>
