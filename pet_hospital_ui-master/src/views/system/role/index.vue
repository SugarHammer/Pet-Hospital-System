<template>
  <div>
    <el-row style="width: 80%; margin: 100px auto 0">
      <el-button type="primary" @click="handleAddRole">添加</el-button>
    </el-row>
    <el-table v-loading="loading" :data="roleList" style="width: 80%; margin: 50px auto 0">
      <el-table-column prop="id" label="id" />
      <el-table-column prop="name" label="名称" />
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="success"
            @click="handleUpdateRole(scope.row)"
          >
            编辑
          </el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDeleteRole(scope.row)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <UpdateRoleDialog v-if="updateRole != null" :role="updateRole" @cancel="updateRole = null" @updateRoleSuccess="updateRole = null; handleQueryRole()" />
  </div>
</template>

<script>
import { addRole, deleteRole, queryAllRole } from '@/api/role'
import UpdateRoleDialog from '@/views/system/role/UpdateRoleDialog'

export default {
  components: { UpdateRoleDialog },
  data() {
    return {
      roleList: [],
      loading: false,
      updateRole: null
    }
  },
  created() {
    this.handleQueryRole()
  },
  methods: {
    handleQueryRole() {
      this.loading = true
      queryAllRole().then(res => {
        this.roleList = res.body.data
        this.loading = false
      })
    },
    handleUpdateRole(role) {
      this.updateRole = {
        id: role.id,
        name: role.name,
        resourceDTOList: role.resourceDTOList
      }
    },
    handleAddRole() {
      this.$prompt('请输入角色名称', '添加角色', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /.+/,
        inputErrorMessage: '角色名称不能为空'
      }).then(({ value }) => {
        addRole({name: value}).then(res => {
          this.$message.success(res.body.data)
          this.handleQueryRole()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消添加'
        })
      })
    },
    handleDeleteRole(role) {
      this.$confirm('是否删除此角色?', '删除角色', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteRole(role).then(res => {
          this.$message({
            type: 'success',
            message: res.body.data
          })
          this.handleQueryRole()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    }
  }
}
</script>

<style scoped>

</style>
