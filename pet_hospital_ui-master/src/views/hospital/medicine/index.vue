<template>
  <div class="app-container">
    <el-form ref="form"  :inline="true" :model="listQuery" size="small">
      <el-form-item label-width="80px" label="药品名称:">
        <el-input v-model="listQuery.name" label-width="80px" placeholder="请输入" clearable />
      </el-form-item>
      <el-form-item label-width="80px" label="药品类别:">
        <el-input v-model="listQuery.type" label-width="80px" placeholder="请输入" clearable />
      </el-form-item>
      <div style="text-align: right">
        <el-button type="primary" size="small" @click="searchQuery">查询</el-button>
        <el-button size="small" @click="resetListQuery">重置</el-button>
        <el-button class="el-icon-document-add" type="primary" size="small" @click="handleAdd">添加药品</el-button>

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
      <el-table-column align="center" label="药品名称">
        <template slot-scope="scope">
          {{ scope.row.name }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="药品数量">
        <template slot-scope="scope">
          {{ scope.row.number }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="功能主治">
        <template slot-scope="scope">
          {{ scope.row.treatment }}
        </template>
      </el-table-column>
      <el-table-column align="center"  label="生产公司">
        <template slot-scope="scope">
          {{ scope.row.company }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="价格">
        <template slot-scope="scope">
          {{ scope.row.price }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="类别">
        <template slot-scope="scope">
          {{ scope.row.type }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="备注" :show-overflow-tooltip='true'>
        <template slot-scope="scope">
          {{ scope.row.remark }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作">
        <template slot-scope="scope">
          <el-button-group>
            <el-button
              size="mini"
              type="success"
              @click="getMedicine(scope.row)"
            >
              取药
            </el-button>
            <el-button
              size="mini"
              type="error"
              @click="enterMedicine(scope.row)"
            >
              入库
            </el-button>
          </el-button-group>
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
    <UpdateMedicine ref="updateMedicine" @medicine="handleMedicine"></UpdateMedicine>
    <AddMedicine ref="addMedicine" @medicine="handleMedicine"></AddMedicine>
  </div>
</template>

<script>
import * as archivesApp from '@/api/archives'
import { mapGetters } from 'vuex'
import * as medicineApp from '@/api/medicine'
import UpdateMedicine from '@/components/pet/archives/UpdateMedicine'
import AddMedicine from '@/components/pet/archives/AddMedicine'
export default {
  name: 'Medicine',
  components: { AddMedicine, UpdateMedicine },
  state: undefined,
  computed: {
    ...mapGetters([
      'name',
      'user'
    ])
  },
  data() {
    return {
      role: 0,
      total: 100,
      listQuery: {
        page: 1,
        size: 10,
        type: undefined,
        name: undefined
      },
      list: []
    }
  },
  methods: {
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    handleAdd() {
      this.$refs.addMedicine.title = "添加药品"
      this.$refs.addMedicine.add()
    },
    getMedicine(row) {
      this.$refs.updateMedicine.title = '取药'
      this.$refs.updateMedicine.add(row)
    },
    enterMedicine(row) {
      this.$refs.updateMedicine.title = '入库'
      this.$refs.updateMedicine.edit(row)
    },
    resetListQuery() {
      this.listQuery = {
        page: 1,
        size: 10,
        name: undefined
      }
    },
    searchQuery() {
      this.getTableDatas()
    },
    getTableDatas() {
      return medicineApp.getAll(this.listQuery).then(res => {
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
    },
    handleMedicine() {
      // 新增/修改 成功时，重载列表
      this.getTableDatas()
    }
  },
  created() {
    this.getTableDatas()
  }
}
</script>

<style scoped>
/* 日期选择框的宽度 */
.el-date-editor.el-input, .el-date-editor.el-input__inner {
  width: 130px;
}

</style>
