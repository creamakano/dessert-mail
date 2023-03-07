<template>
  <el-upload action="http://localhost:7777/product/file/uploadImg" :limit="1" :on-preview="handlePictureCardPreview" :on-change="handleChange"
    :on-progress="onProgress" :on-success="imgSuccess" :on-error="imgError" :http-request="uploadHttp"
    list-type="picture-card" :on-remove="handleRemove" :class="{ disabled: uploadDisabled }" :file-list="fileList">
  </el-upload>。。。。。。<el-dialog :modal-append-to-body="false" :visible="imgVisible" width="30%"> <img width="100%"
      :src="dialogImageUrl" alt="图片未找到" /></el-dialog>
</template>

<script>
import { ref } from "vue"

//上传身份证正面相关参数
const limitCount = ref(1)
const uploadDisabled = ref(false)
// 是否显示预览图片
const imgVisible = ref(false)
// 预览图片url
const dialogImageUrl = ref("")
const fileList = ref([])


function onProgress (event, file, fileList) {
  this.uploadDisabled = false;
}
// 图片上传成功
function imgSuccess (response, file, fileList) {
  this.uploadDisabled = true;
}
// 图片上传失败
function imgError (err, file, fileList) {
  this.uploadDisabled = true;
}
// 预览图片
function handlePictureCardPreview () {
  this.imgVisible = true;
  this.uploadDisabled = true;
}
// 删除图片
function handleRemove (file, fileList) {
  this.uploadDisabled = false;
  // this.hideUpload = fileList.length >= this.limitCount;
}
//图片更改
function handleChange (fileList) {
  this.uploadDisabled = fileList.length >= this.limitCount;
  this.uploadDisabled = true;
}

function uploadHttp (file) {  //定义上传方法
  let _this = this;
  //获取文件信息
  let fileLen = file.file;
  let name = fileLen.name
  let rand = this.calculate_object_name(name);  //把文件名变为随机数，也可以不更改，这里为了防止文件名重复
  client().multipartUpload(rand, fileLen).then(function (res) {  //调用阿里oss上传
    let str = res.res.requestUrls[0];
    _this.dialogImageUrl = 自有域名 + rand;
    console.log(_this.dialogImageUrl);
  }).catch((err) => {
    console.log(err)
  })
}



</script>


<style scoped>
.disabled .el-upload--picture-card {
  display: none;
}

.baseTheme .el-upload--picture-card {
  background-image: url("../../../../static/img/背景图片.png");
  background-position-x: center;  
  background-position-y: center;
  background-size: 100% 100%;    
  width: 190px;   
  height: 150px;
}

.baseTheme .el-upload-list--picture-card .el-upload-list__item {
  width: 190px;   
  height: 150px;
  position: inherit;

}

.baseTheme .el-upload-list--picture-card .el-upload-list__item-actions {
  width: 190px;  
  height: 150px;
}
</style>