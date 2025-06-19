<template>
  <div>
    <el-dialog
      title="宠物详情"
      :visible="true"
      width="30%"
      :close-on-click-modal="false"
      @close="$emit('cancel')"
    >
      <el-form :model="pet" label-width="100px">
        <el-form-item label="名字" prop="name">
          <el-input v-model="pet.name" disabled />
        </el-form-item>
        <el-form-item label="物种" prop="species">
          <el-input v-model="pet.species" disabled />
        </el-form-item>
        <el-form-item label="品种" prop="varieties">
          <el-input v-model="pet.varieties" disabled />
        </el-form-item>
        <el-form-item label="出生日期" prop="birth">
          <el-input v-model="pet.birth" disabled />
        </el-form-item>
        <el-form-item label="身高(cm)" prop="height">
          <el-input :value="pet.height.toFixed(2)" disabled />
        </el-form-item>
        <el-form-item label="体重(kg)" prop="weight">
          <el-input :value="pet.weight.toFixed(2)" disabled />
        </el-form-item>
        <el-form-item label="是否住院" prop="inHospital">
          <el-switch :value="pet.inHospital === 1" disabled />
        </el-form-item>
      </el-form>
      <el-image :src="pet.pictureUrl" />
      <span slot="footer" class="dialog-footer">
        <el-button @click="$emit('cancel')">取 消</el-button>
        <el-button type="primary" @click="$emit('cancel')">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

export default {
  props: {
    pet: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {}
  }
}
</script>

<style scoped>

</style>
