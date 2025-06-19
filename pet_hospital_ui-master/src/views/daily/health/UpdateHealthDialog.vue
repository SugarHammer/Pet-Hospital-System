<template>
  <div>
    <el-dialog
      title="修改日常健康"
      :visible="true"
      width="60%"
      :close-on-click-modal="false"
      @close="$emit('cancel')"
    >
      <el-form ref="healthUpdateForm" :model="health" :rules="rules" label-width="30%" class="demo-ruleForm">
        <el-form-item label="选择宠物" prop="name">
          <el-input v-model="health.pet.name" style="width: 90%" disabled />
          <el-button icon="el-icon-plus" type="primary" disabled @click="isSelectPet = true" />
        </el-form-item>
        <el-form-item label="健康状态" prop="healthStatus">
          <el-select v-model="health.healthStatus" placeholder="请选择健康状态" style="width: 100%">
            <el-option label="健康" value="健康" />
            <el-option label="良好" value="良好" />
            <el-option label="不健康" value="不健康" />
          </el-select>
        </el-form-item>
        <el-form-item label="身高(cm)" prop="height">
          <el-input v-model="health.height" />
        </el-form-item>
        <el-form-item label="体重(kg)" prop="weight">
          <el-input v-model="health.weight" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="health.description" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="$emit('cancel')">取 消</el-button>
        <el-button type="primary" @click="handleUpdateHealth">确 定</el-button>
      </span>
    </el-dialog>
    <SelectPetDialog v-if="isSelectPet" @cancel="isSelectPet = false" @selectPet="(val) => {pet = val; health.petId = pet.id; isSelectPet = false}" />
  </div>
</template>

<script>
import { updateHealth } from '@/api/health'
import SelectPetDialog from '@/views/daily/health/SelectPetDialog'

export default {
  components: {
    SelectPetDialog
  },
  props: {
    health: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    const checkHeight = (rule, value, callback) => {
      if (isNaN(value)) {
        callback(new Error('请输入数字'))
      } else {
        callback()
      }
    }
    const checkWeight = (rule, value, callback) => {
      if (isNaN(value)) {
        callback(new Error('请输入数字'))
      } else {
        callback()
      }
    }
    return {
      rules: {
        height: [
          { required: true, message: '请输入身高', trigger: 'blur' },
          { validator: checkHeight, trigger: 'blur' }
        ],
        weight: [
          { required: true, message: '请输入体重', trigger: 'change' },
          { validator: checkWeight, trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请输入描述', trigger: 'blur' }
        ]
      },
      isSelectPet: false,
      pet: {}
    }
  },
  created() {
    console.log(this.health)
  },
  methods: {
    handleUpdateHealth() {
      this.$refs['healthUpdateForm'].validate((valid) => {
        if (valid) {
          updateHealth(this.health).then(res => {
            this.$message.success(res.body.data)
            this.$emit('updateHealthSuccess')
          })
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
