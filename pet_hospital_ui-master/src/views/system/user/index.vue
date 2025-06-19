<template>
  <div>
    <el-row style="width: 80%; margin: 100px auto 0">
      <el-col :span="8">
        <el-input v-model="userQuery.name" style="width: 80%" placeholder="输入用户名，姓名或身份证号" />
        <el-button
          icon="el-icon-search"
          type="primary"
          style="width: 20%"
          @click="userQuery.username = userQuery.name; userQuery.idCard = userQuery.name; userQuery.phone = userQuery.name; handleQueryUser(1)"
        />
      </el-col>
    </el-row>
    <el-table v-loading="loading" :data="userList" style="width: 80%; margin: 50px auto 0">
      <el-table-column prop="id" label="id" />
      <el-table-column prop="name" label="姓名" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="updateTime" label="修改时间" />
      <el-table-column label="状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status" type="primary">正常</el-tag>
          <el-tag v-else type="warning">注销</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="success"
            @click="handleUpdateUser(scope.row)"
          >
            编辑
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="width: 80%; margin: 0 auto 0">
      <el-pagination
        background
        layout="prev, pager, next"
        :total="total"
        :page-size="userQuery.size"
        :current-page="userQuery.page"
        @current-change="handleQueryUser"
      />
    </div>
    <UpdateUserDialog v-if="updateUser != null" :user="updateUser" @updateUserSuccess="updateUser = null; handleQueryUser()" @cancel="updateUser = null" />
  </div>
</template>

<script>
import { queryUser } from '@/api/user'
import UpdateUserDialog from '@/views/system/user/UpdateUserDialog'

export default {
  components: {
    UpdateUserDialog
  },
  data() {
    return {
      userList: [],
      userQuery: {
        page: 1,
        size: 5,
        name: '',
        username: '',
        idCard: '',
        phone: ''
      },
      updateUser: null,
      total: 0,
      loading: false
    }
  },
  created() {
    this.loading = true
    queryUser(this.userQuery).then(res => {
      this.userList = res.body.data
      this.total = res.body.total
      this.loading = false
    })
  },
  methods: {
    handleQueryUser(page) {
      this.loading = true
      if (!isNaN(page)) {
        this.userQuery.page = page
      }
      queryUser(this.userQuery).then(res => {
        console.log(res.body.data)
        this.userList = res.body.data
        this.total = res.body.total
        this.loading = false
      })
    },
    handleUpdateUser(user) {
      const roleList = []
      for (let i = 0; i < user.roleDTOList.length; i++) {
        roleList.push(user.roleDTOList[i].name)
      }
      this.updateUser = {
        id: user.id,
        username: user.username,
        name: user.name,
        sex: user.sex,
        idCard: user.idCard,
        status: user.status,
        phone: user.phone,
        address: user.address,
        roleList
      }
    }
  }
}
</script>

<style scoped>

</style>
