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
    <el-form  ref="updateMedicine" :rules="rules" :model="form" :inline="true" status-icon size="small" label-position="right">
      <el-form-item label="药品数量" prop="number" label-width="100px">
        <el-input v-model="form.number" placeholder="请输入" style="width: 230px" clearable></el-input>
      </el-form-item>
      <el-form-item label="宠物ID" prop="number" label-width="100px">
        <el-input v-model="form.petId" placeholder="请输入" style="width: 230px" clearable></el-input>
      </el-form-item>
      <div style="text-align: right">
        <el-button @click="handleCancel">取 消</el-button>
        <el-button type="primary"  @click="handleSubmit" :loading="confirmLoading">{{this.submit_message}}</el-button>
      </div>
    </el-form>
  </el-dialog>
</template>

<script>
import * as medicineApp from '@/api/medicine'
export default {
  name: 'UpdateMedicine',
  data() {
    return {
      title: '药品',
      visible: false,
      close_modal_destroy: true,
      close_modal: false,
      medicine_id : 0,
      submit_message: '确定',
      form: {
        number: undefined,
        petId: undefined
      },
      rules: {
        number: [
          { required: true, trigger: 'blur', message: '请输入药品数量' }
        ],
        petId: [
          { required: true, trigger: 'blur', message: '请输入宠物ID' }
        ]
      }
    }
  },
  methods: {
    add(row) {
      this.$nextTick(() => {
        this.$refs.updateMedicine.resetFields()
      })

      this.medicine_id = row.id
      this.updateDisabled = false
      this.visible = true
    },
    edit(row) {
      this.$nextTick(() => {
        this.$refs.updateMedicine.resetFields()
      })

      this.medicine_id = row.id
      this.updateDisabled = true
      this.visible = true
    },
    handleCancel() {
      this.close()
    },
    close() {
      this.$emit('close')
      this.visible = false
      this.form = {
        number: undefined
      }
    },
    handleSubmit() {
      this.form.id = this.medicine_id
      if (this.updateDisabled) {
        medicineApp.enterMedicine(this.form).then(res => {
          if (res.body.data == "成功") {
            this.$message.success("入库成功")
            this.$emit("medicine")
          } else
            this.$message.error("入库失败")
        })
      } else {
        medicineApp.getMedicine(this.form).then(res => {
          if (res.body.data == "成功") {
            this.$message.success("取药成功")
            this.$emit("medicine")
          } else
            this.$message.error("取药失败")
        })
      }
      this.close()
    }
  }
}
</script>

<style scoped>

</style>
