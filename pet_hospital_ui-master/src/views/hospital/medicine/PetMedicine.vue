<template>
  <div class="app-container">
    <el-form ref="form"  :inline="true" :model="listQuery" size="small">
      <el-form-item label-width="80px" label="药品ID:">
        <el-input v-model="listQuery.medicineId" label-width="80px" placeholder="请输入" clearable />
      </el-form-item>
      <el-form-item label-width="80px" label="宠物ID:">
        <el-input v-model="listQuery.petId" label-width="80px" placeholder="请输入" clearable />
      </el-form-item>
      <div style="text-align: right">
        <el-button type="primary" size="small" @click="searchQuery">查询</el-button>
        <el-button size="small" @click="resetListQuery">重置</el-button>

      </div>
    </el-form>

    <el-table
      ref="table"
      size="small"
      :data="list"
      element-loading-text="Loading"
      highlight-current-row
      @selection-change="handleSelectionChange"
    >
      <el-table-column align="center" label="药品ID">
        <template slot-scope="scope">
          {{ scope.row.medicineId }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="宠物ID">
        <template slot-scope="scope">
          {{ scope.row.petId }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="药品数量">
        <template slot-scope="scope">
          {{ scope.row.number }}
        </template>
      </el-table-column>
    </el-table>
    <div class="block" style="text-align: right">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="listQuery.page"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="listQuery.size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import * as medicineApp from '@/api/medicine'

export default {
  name: 'PetMedicine',
  data() {
    return {
      total: 100,
      listQuery: {
        page: 1,
        size: 10,
        petId: undefined,
        medicineId: undefined,
        number: undefined
      },
      list: []
    }
  },
  methods: {
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    resetListQuery() {
      this.listQuery = {
        page: 1,
        size: 10,
        petId: undefined,
        medicineId: undefined,
        number: undefined
      }
    },
    searchQuery() {
      this.getTableDatas()
    },
    getTableDatas() {
      return medicineApp.getAllPetMedicine(this.listQuery).then(res => {
        this.total = res.body.total
        this.page = res.body.page
        this.size = res.body.size
        this.list = res.body.data
      })
    },
    handleSizeChange(val) {
      this.listQuery.size = val
      this.getTableDatas()
    },
    handleCurrentChange(val) {
      this.listQuery.page = val
      this.getTableDatas()
    }
  },
  created() {
    this.getTableDatas()
  }
}
</script>

<style scoped>

</style>
