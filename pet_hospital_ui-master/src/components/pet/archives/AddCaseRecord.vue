<template>
  <el-dialog
    :title="title"
    :visible.sync="visible"
    :destroy-on-close="close_modal_destroy"
    :close-on-click-modal="close_modal"
    @close="handleCancel"
    width="500px"
    append-to-body
  >
      <el-form  ref="caseRecord" :rules="rules" :model="form" :inline="true" status-icon size="small" label-position="right">
        <el-form-item label="医生编号" prop="doctorId" label-width="100px">
          <el-input v-model="form.doctorId" placeholder="请输入" style="width: 230px" clearable></el-input>
        </el-form-item>
        <el-row>
          <el-form-item label="描述" label-width="100px" prop="description">
            <el-input v-model="form.description" type="textarea" :rows="6" placeholder="请输入" style="width: 350px" clearable ></el-input>
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
  name: 'AddCaseRecord',
  data() {
    return {
      title: '添加宠物病例',
      visible: false,
      close_modal_destroy: true,
      close_modal: false,
      pet_id : 0,
      submit_message: '保存',
      form: {},
      rules: {
        doctorId: [
          { required: true, trigger: 'blur', message: '请输入医生编号' }
        ],
        description: [
          { required: true, trigger: 'blur', message: '请输入描述' }
        ]
      }
    }
  },
  methods: {
    add(id) {
      this.$nextTick(() => {
        this.$refs.addCaseRecord.resetFields()
      })

      this.updateDisabled = false
      this.visible = true
      this.pet_id = id
    },
    edit(row) {
      this.$nextTick(() => {
        this.$refs.addCaseRecord.resetFields()
      })

      this.updateDisabled = true
      this.visible = true

      archivesApp.queryCaseById(row.id).then(res => {
        this.form = res.body.data
      })
    },
    handleCancel() {
      this.close()
    },
    close() {
      this.$emit('close')
      this.visible = false
      this.form = {
        doctorId: undefined,
        description: undefined
      }
    },
    handleSubmit() {
      this.form.petId = this.pet_id
      if (this.updateDisabled) {
        archivesApp.updateCase(this.form).then(res => {
          if (res.body.data == "更新成功") {
            this.$message.success("更新成功")
            this.$emit("case")
          } else
            this.$message.error("更新失败")
        })
      } else {
        archivesApp.addCaseRecord(this.form).then(res => {
          if (res.body.data == "添加成功") {
            this.$message.success("添加成功")
            this.$emit("case")
          } else
            this.$message.error("添加失败")
        })
      }
      this.close()
    }
  }
}
</script>

<style scoped>

</style>
