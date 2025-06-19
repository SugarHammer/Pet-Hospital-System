<template>
  <div>
    <el-dialog
      title="提示"
      :visible="true"
      width="30%"
      :close-on-click-modal="false"
      @close="$emit('cancel')"
    >
      <el-form ref="roleUpdateForm" :model="role" :rules="rules" label-width="100px" class="demo-ruleForm">
        <el-form-item label="名称" prop="name">
          <el-input v-model="role.name" />
        </el-form-item>
        <el-tree
          ref="tree"
          :data="resourceList"
          show-checkbox
          default-expand-all
          node-key="id"
          highlight-current
          :props="{
            children: 'children',
            label: 'name'
          }"
        />
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="$emit('cancel')">取 消</el-button>
        <el-button type="primary" @click="handleUpdateRole">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { queryAllRole, updateRole } from '@/api/role'
import { queryResource } from '@/api/resource'

export default {
  props: {
    role: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      roleList: [],
      rules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ]
      },
      resourceList: [],
      selectResourceIds: []
    }
  },
  created() {
    queryAllRole().then(res => {
      this.roleList = res.body.data
    })
    queryResource({}).then(res => {
      this.resourceList = res.body.data
      for (let i = 0; i < this.role.resourceDTOList.length; i++) {
        this.getResourceIds(this.role.resourceDTOList[i])
      }
      this.$refs.tree.setCheckedKeys(this.selectResourceIds);
    })
  },
  methods: {
    handleUpdateRole() {
      this.$refs['roleUpdateForm'].validate((valid) => {
        if (valid) {
          this.role.resourceIds = this.$refs.tree.getCheckedKeys()
          const halfCheckedKeys = this.$refs.tree.getHalfCheckedKeys()
          for (let i = 0; i < halfCheckedKeys.length; i++) {
            this.role.resourceIds.push(halfCheckedKeys[i])
          }
          updateRole(this.role).then(res => {
            this.$message.success(res.body.data)
            this.$emit('updateRoleSuccess')
          })
        } else {
          return false
        }
      })
    },
    getResourceIds(resource) {
      if (!resource.children) {
        this.selectResourceIds.push(resource.id)
        return
      }
      for (let i = 0; i < resource.children.length; i++) {
        this.getResourceIds(resource.children[i])
      }
    }
  }
}
</script>

<style scoped>

</style>
