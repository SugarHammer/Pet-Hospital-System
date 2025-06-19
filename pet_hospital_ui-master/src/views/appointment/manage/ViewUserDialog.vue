<template>
  <div>
    <el-dialog
      title="用户详情"
      :visible="true"
      width="30%"
      :close-on-click-modal="false"
      @close="$emit('cancel')"
    >
      <el-form :model="user" label-width="100px" class="demo-ruleForm">
        <el-form-item label="用户名">
          <el-input v-model="user.username" disabled />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="user.name" disabled />
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="user.sex" placeholder="请选择性别" disabled>
            <el-option label="男" value="男" />
            <el-option label="女" value="女" />
          </el-select>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="user.phone" disabled />
        </el-form-item>
        <el-form-item label="住址" prop="address">
          <el-input v-model="user.address" disabled />
        </el-form-item>
        <el-form-item label="身份证号" prop="idCard">
          <el-input v-model="user.idCard" disabled />
        </el-form-item>
        <el-form-item label="是否有效">
          <el-switch v-model="user.status" disabled />
        </el-form-item>
      </el-form>
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
    user: {
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
