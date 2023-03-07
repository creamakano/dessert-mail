<template>
  <div class="all-c">
    <div class="content-c">

      <div class="title">我的收藏</div>

      <div class="content">

        <el-table :data="collectionList" style="width: 100%" :cell-sty-le="{ textAlign: 'center' }" max-height="655"
          :header-cell-style="{ 'text-align': 'center' }">
          <el-table-column label="商品图片" width="180">
            <template v-slot="scope">
              <el-image style="width: 100px; height:100px" :src="`/src/assets/images/${scope.row.name}.jpg`" :fit="fit"
                @click="goToProductDetail(val.id)" />
            </template>
          </el-table-column>
          <el-table-column prop="name" label="商品名称" />
          <el-table-column prop="type" label="商品类型" />
          <el-table-column prop="price" label="商品原价" />
          <el-table-column label="折扣价">
            <template v-slot="scope">
              {{ scope.row.price * scope.row.discount }}
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template v-slot="scope">
              <el-button type="danger" size="small" @click="deleteCollection(scope.row.id)">删除</el-button>
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