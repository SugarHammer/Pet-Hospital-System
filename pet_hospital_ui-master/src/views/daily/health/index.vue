<template>
  <div>
    <el-row style="width: 80%; margin: 100px auto 0">
      <el-col :span="8">
        <el-input v-model="healthQuery.userName" style="width: 80%" placeholder="输入添加人姓名或宠物名称" />
        <el-button
          icon="el-icon-search"
          type="primary"
          style="width: 20%"
          @click="healthQuery.petName = healthQuery.userName; handleQueryHealth(1)"
        />
      </el-col>
      <el-col :span="2" :offset="14" style="text-align: right">
        <el-button icon="el-icon-plus" type="primary" @click="handleAddHealth" />
      </el-col>
    </el-row>
    <el-table v-loading="loading" :data="healthList" style="width: 80%; margin: 50px auto 0">
      <el-table-column prop="addUser.name" label="添加人" />
      <el-table-column prop="addTime" label="添加时间" />
      <el-table-column label="主人">
        <template slot-scope="scope">
          <el-button
            size="text"
            type="success"
            @click="viewUser = scope.row.owner"
          >
            {{ scope.row.owner.name }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="宠物">
        <template slot-scope="scope">
          <el-button
            size="text"
            type="success"
            @click="viewPet = scope.row.pet"
          >
            {{ scope.row.pet.name }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column prop="healthStatus" label="健康状态" />
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="success"
            @click="updateHealth = scope.row"
          >
            编辑
          </el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDeleteHealth(scope.row)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="width: 80%; margin: 0 auto 0">
      <el-pagination
        background
        layout="prev, pager, next"
        :total="total"
        :page-size="healthQuery.size"
        :current-page="healthQuery.page"
        @current-change="handleQueryHealth"
      />
    </div>
    <AddHealthDialog v-if="addHealth != null" :health="addHealth" @cancel="addHealth = null" @addHealthSuccess="addHealth = null; handleQueryHealth(1)" />
    <UpdateHealthDialog v-if="updateHealth != null" :health="updateHealth" @cancel="updateHealth = null" @updateHealthSuccess="updateHealth = null; handleQueryHealth(1)" />
    <ViewUserDialog v-if="viewUser != null" :user="viewUser" @cancel="viewUser = null" />
    <ViewPetDialog v-if="viewPet != null" :pet="viewPet" @cancel="viewPet = null" />
  </div>
</template>

<script>
import { deleteHealth, queryHealth } from '@/api/health'
import AddHealthDialog from '@/views/daily/health/AddHealthDialog'
import ViewUserDialog from '@/views/appointment/manage/ViewUserDialog'
import ViewPetDialog from '@/views/appointment/manage/ViewPetDialog'
import UpdateHealthDialog from '@/views/daily/health/UpdateHealthDialog'

export default {
  components: { UpdateHealthDialog, ViewPetDialog, ViewUserDialog, AddHealthDialog },
  data() {
    return {
      healthList: [],
      healthQuery: {
        page: 1,
        size: 5,
        userName: '',
        petName: ''
      },
      total: 0,
      loading: false,
      updateHealth: null,
      addHealth: null,
      viewPet: null,
      viewUser: null
    }
  },
  created() {
    this.handleQueryHealth()
  },
  methods: {
    handleQueryHealth(page) {
      this.loading = true
      if (!isNaN(page)) {
        this.healthQuery.page = page
      }
      queryHealth(this.healthQuery).then(res => {
        console.log(res.body.data)
        this.healthList = res.body.data
        this.total = res.body.total
        this.loading = false
      })
    },
    handleAddHealth() {
      this.addHealth = {
        petId: '',
        description: '',
        height: '',
        weight: '',
        healthStatus: ''
      }
    },
    handleDeleteHealth(health) {
      this.$confirm('是否删除此日常健康记录?', '删除日常健康记录', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteHealth(health).then(res => {
          this.$message.success(res.body.data)
          this.handleQueryHealth(1)
        })
      })
    }
  }
}
</script>

<style scoped>

</style>
