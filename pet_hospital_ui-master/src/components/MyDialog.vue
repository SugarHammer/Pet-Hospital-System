<template>
  <div>
    <el-dialog
      v-loading="loading"
      :title="title"
      :visible.sync="showDialog"
      width="30%"
    >
      <slot />
      <span slot="footer" class="dialog-footer">
        <el-button @click="$emit('cancel')">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      default: () => { return '未命名' }
    }
  },
  data() {
    return {
      showDialog: true,
      loading: false
    }
  },
  methods: {
    submitForm() {
      this.loading = true
      this.$emit('submitForm')
    }
  }
}
</script>

<style scoped>

</style>
