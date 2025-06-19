<template>
  <el-dialog
    :title="title"
    :visible.sync="visible"
    :destroy-on-close="close_modal_destroy"
    :close-on-click-modal="close_modal"
    @close="handleCancel"
  >
    <el-form  ref="addPetArchive" :rules="rules" :model="form" :inline="true" status-icon size="small" label-position="right">
        <el-form-item label="宠物名称" prop="name" label-width="120px">
          <el-input v-model="form.name" placeholder="请输入" clearable></el-input>
        </el-form-item>
      <el-form-item label="宠物种类" prop="species" label-width="120px">
        <el-input v-model="form.species" placeholder="请输入" clearable></el-input>
      </el-form-item>
      <el-form-item label="宠物品种" prop="varieties" label-width="120px">
        <el-input v-model="form.varieties" placeholder="请输入" clearable></el-input>
      </el-form-item>
      <el-form-item label="宠物体长(cm)" prop="height" label-width="120px">
        <el-input v-model="form.height" placeholder="请输入" clearable></el-input>
      </el-form-item>
      <el-form-item label="宠物体重(kg)" prop="weight" label-width="120px">
        <el-input v-model="form.weight" placeholder="请输入" clearable></el-input>
      </el-form-item>
      <el-form-item label="是否住院" label-width="120px" prop="inHospital">
        <el-radio-group v-model="form.inHospital" >
          <el-radio :label="1">是</el-radio>
          <el-radio :label="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="出生日期" prop="birth" label-width="120px">
        <el-date-picker
          v-model="form.birth"
          type="date"
          size="mini"
          placeholder="选择日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item v-if="this.role != 0" label="客户编号" prop="userId" label-width="120px">
        <el-input v-model="form.userId" placeholder="请输入" clearable></el-input>
      </el-form-item>
      <el-row>
        <el-form-item label="备注" label-width="120px" prop="remark">
          <el-input v-model="form.remark" type="textarea" :rows="4" placeholder="请输入" style="width: 480px" clearable ></el-input>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="宠物图片" label-width="120px" prop="pictureUrl">
        <el-upload
          class="upload-demo"
          action="http://localhost:8080/upload"
          :on-remove="handleRemoveFile"
          :file-list="fileList"
          :multiple="false"
          :limit="1"
          :on-success="onFileUploadSuccess"
          list-type="picture">
          <el-button size="small" type="primary">点击上传</el-button>
          <div slot="tip" class="el-upload__tip">不超过2M</div>
        </el-upload>
        </el-form-item>
      </el-row>
      <div style="text-align: right">
        <el-button @click="handleCancel">取 消</el-button>
        <el-button type="primary"  @click="handleSubmit" :loading="confirmLoading">{{this.submit_message}}</el-button>
      </div>
    </el-form>
  </el-dialog>
</template>

<script>
import * as archivesApp from '@/api/archives'
export default {
  name: 'AddPetArchive',
  data() {
    const validateUserId = (rule, value, callback) => {
      if (this.role != 0)
        if (value.length.trim()<1)
          callback(new Error('请输入用户ID'))
    }
    return {
      title: '新增宠物',
      visible: false,
      close_modal_destroy: true,
      close_modal: false,
      updateDisabled: true,//添加修改标记，true--修改 false--增加
      role: 0,
      uploadpicture: true,
      form: {
        id: undefined,
        name: '',
        species: '',
        varieties: '',
        height: '',
        weight: '',
        pictureUrl: '',
        inHospital: 0,
        birth: '',
        remark: '',
        userId: ''
      },
      rules: {
        name: [
          {required: true, trigger: 'blur', message: '请输入宠物名称'}
        ],
        species: [
          {required: true, trigger: 'blur', message: '请输入宠物种类'}
        ],
        height: [
          {required: true, trigger: 'blur', message: '请输入宠物体长'}
        ],
        weight: [
          {required: true, trigger: 'blur', message: '请输入宠物体重'}
        ],
        varieties: [
          {required: true, trigger: 'blur', message: '请输入宠物品种'}
        ],
        birth: [
          {required: true, trigger: 'blur', message: '请输入出生日期'}
        ],
        userId: [
          {required: true, trigger: 'blur', message: '请输入用户编号', validator: validateUserId}
        ]
      },
      submit_message: undefined,
      fileList: []
    }
  },
  methods: {
    add() {
      this.$nextTick(() => {
        this.$refs.addPetArchive.resetFields()
      })
      this.uploadpicture = false
      this.submit_message = '添 加'
      this.updateDisabled = false
      this.visible = true
    },
    edit(row) {
      this.$nextTick(() => {
        this.$refs.addPetArchive.resetFields()
      })

      this.updateDisabled = true
      this.submit_message = '修 改'
      this.visible = true

      archivesApp.queryPetById(row.id).then(res => {
        this.form = res.body.data
        this.form.id = row.id
        if (this.form.pictureUrl) {
          this.fileList.push({ name: 'picture', url: this.form.pictureUrl })
        }
      })
    },
    handleCancel() {
      this.close()
    },
    close() {
      this.$emit('close')
      this.visible = false
      this.uploadpicture = true
      this.form = {
        id: undefined,
        name: '',
        species: '',
        varieties: '',
        height: '',
        weight: '',
        pictureUrl: '',
        inHospital: 0,
        birth: '',
        remark: '',
        userId: ''
      }
      this.fileList = []
    },
    handleSelectionChange() {
      this.multipleSelection = val
    },
    handleSubmit() {
      if (this.updateDisabled) {
        archivesApp.updatePet(this.form).then(res => {
          if (res.body.data == "更新成功") {
            this.$message.success("更新成功")
            this.$emit("ok")
          } else {
            this.$message.error("更新失败")
          }
        })
      } else {
        archivesApp.addPet(this.form).then(res => {
          if (res.body.data == "添加成功") {
            this.$message.success("添加成功")
            this.$emit("ok")
          } else {
            this.$message.error("添加失败")
          }
        })
      }
      this.close()
    },
    onFileUploadSuccess(response, file, fileList)	{
      this.uploadpicture = false
      this.form.pictureUrl = response.body.data
    },
    handleRemoveFile(file, fileList) {
      this.form.pictureUrl = null
    }
  }
}
</script>

<style scoped>
/* 日期选择框的宽度 */
.el-date-editor.el-input, .el-date-editor.el-input__inner {
  width: 185px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
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
