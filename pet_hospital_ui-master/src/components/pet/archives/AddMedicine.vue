<template>
  <el-dialog
    :title="title"
    :visible.sync="visible"
    :destroy-on-close="close_modal_destroy"
    :close-on-click-modal="close_modal"
    @close="handleCancel"
  >
    <el-form ref="addMedicine" :rules="rules" :model="form" :inline="true" status-icon size="small" label-position="right">
      <el-form-item label="药品名称" prop="name" label-width="120px">
        <el-input v-model="form.name" placeholder="请输入" clearable />
      </el-form-item>
      <el-form-item label="药品数量" prop="number" label-width="120px">
        <el-input v-model="form.number" placeholder="请输入" clearable />
      </el-form-item>
      <el-form-item label="功能主治" prop="treatment" label-width="120px">
        <el-input v-model="form.treatment" placeholder="请输入" clearable />
      </el-form-item>
      <el-form-item label="生产公司" prop="company" label-width="120px">
        <el-input v-model="form.company" placeholder="请输入" clearable />
      </el-form-item>
      <el-form-item label="价格" prop="price" label-width="120px">
        <el-input v-model="form.price" placeholder="请输入" clearable />
      </el-form-item>
      <el-form-item label="类别" prop="type" label-width="120px">
        <el-input v-model="form.type" placeholder="请输入" clearable />
      </el-form-item>
      <el-row>
        <el-form-item label="备注" label-width="120px" prop="remark">
          <el-input v-model="form.remark" type="textarea" :rows="4" placeholder="请输入" style="width: 480px" clearable />
        </el-form-item>
      </el-row>
      <div style="text-align: right">
        <el-button @click="handleCancel">取 消</el-button>
        <el-button type="primary" :loading="confirmLoading" @click="handleSubmit">添 加</el-button>
      </div>
    </el-form>
  </el-dialog>
</template>

<script>
import * as medicineApp from '@/api/medicine'

export default {
  name: 'AddMedicine',
  data() {
    return {
      title: '新增宠物',
      visible: false,
      close_modal_destroy: true,
      close_modal: false,
      uploadpicture: true,
      form: {
        name: undefined,
        number: undefined,
        treatment: undefined,
        company: undefined,
        price: undefined,
        remark: undefined,
        type: undefined
      },
      rules: {
        name: [
          { required: true, trigger: 'blur', message: '请输入药品名称' }
        ],
        number: [
          { required: true, trigger: 'blur', message: '请输入药品数量' }
        ],
        treatment: [
          { required: true, trigger: 'blur', message: '请输入功能主治' }
        ],
        company: [
          { required: true, trigger: 'blur', message: '请输入生产公司' }
        ],
        price: [
          { required: true, trigger: 'blur', message: '请输入价格' }
        ],
        type: [
          { required: true, trigger: 'blur', message: '请输入类别' }
        ]
      }
    }
  },
  methods: {
    add() {
      this.$nextTick(() => {
        this.$refs.addMedicine.resetFields()
      })
      this.updateDisabled = false
      this.visible = true
    },
    handleCancel() {
      this.close()
    },
    close() {
      this.$emit('close')
      this.visible = false
      this.form = {
        name: undefined,
        number: undefined,
        treatment: undefined,
        company: undefined,
        price: undefined,
        remark: undefined,
        type: undefined
      }
      this.fileList = []
    },
    handleSelectionChange() {
      // eslint-disable-next-line no-undef
      this.multipleSelection = val
    },
    handleSubmit() {
      medicineApp.addMedicine(this.form).then(res => {
        if (res.body.data == '添加成功') {
          this.$message.success('添加成功')
          this.$emit('medicine')
        } else {
          this.$message.error('添加失败')
        }
      })
      this.close()
    }
  }
}
</script>

<style scoped>

</style>
