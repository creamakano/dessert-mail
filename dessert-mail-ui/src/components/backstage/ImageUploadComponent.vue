<template>
  <el-upload class="avatar-uploader" action="http://localhost:7777/product/file/uploadImg" :show-file-list="false"
    :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
    <img v-if="imageUrl" :src="imageUrl" class="avatar" />
    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
  </el-upload>
</template>


<script setup>
import { ElMessage } from 'element-plus';
import { ref, toRef, watch } from 'vue';
import { defineEmits } from 'vue'

const props = defineProps({
  picture: String,
  src: String
})
console.log(props.src);
const emit = defineEmits(['sendUrl', 'sendInsertUrl'])
const imageUrl = ref('/src/assets/images/' + props.picture)
const src = ref(props.src)
watch(() => props.picture, (newVal, oldVal) => {
  if (newVal != null && newVal != '') {
    imageUrl.value = '/src/assets/images/' + newVal
  }
})
if(props.picture==null || props.picture == ''){
  imageUrl.value = ''
}


function handleAvatarSuccess (res, file) {
  imageUrl.value = "/src/assets/images/" + res
  if (src.value == 'insert') {
    emit('sendInsertUrl', res)
  } else {
    emit('sendUrl', res)
  }
}
function beforeAvatarUpload (file) {
  const isLt2M = file.size / 1024 / 1024 < 2;
  if (file.type !== 'image/jpeg' && file.type !== 'image/png') {
    this.$message.error("上传图片只能是 JPG 格式或PNG格式!");
    return false;
  }
  if (!isLt2M) {
    ElMessage.error("上传图片大小不能超过 2MB!");
    return false;
  }
  return true;
}

</script>


<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>

