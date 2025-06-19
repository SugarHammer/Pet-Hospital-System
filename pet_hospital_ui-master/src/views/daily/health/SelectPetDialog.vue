<template>
  <div>
    <el-dialog
      title="选择宠物"
      :visible="true"
      :close-on-click-modal="false"
      width="80%"
      @close="$emit('cancel')"
    >
      <el-form ref="form" :inline="true" :model="listQuery" size="small">
        <el-form-item label-width="80px" label="宠物名称:">
          <el-input v-model="listQuery.name" label-width="80px" placeholder="请输入" clearable />
        </el-form-item>
        <el-form-item v-if="role === 0" label-width="80px" label="宠物品种:">
          <el-input v-model="listQuery.varieties" placeholder="请输入" clearable />
        </el-form-item>
        <el-form-item v-if="role !== 0" label="宠物种类:">
          <el-input v-model="listQuery.species" placeholder="请输入" clearable />
        </el-form-item>
        <el-form-item v-if="role !== 0" label="是否住院" label-width="80px" prop="inHospital">
          <el-radio-group v-model="listQuery.inHospital">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-if="role !== 0" label="注册日期" prop="addTime" label-width="100px">
          <el-date-picker
            v-model="listQuery.addTime"
            type="date"
            value-format="yyyy-MM-dd"
            size="mini"
            placeholder="选择日期"
          />
        </el-form-item>
        <div style="text-align: right">
          <el-button class="el-icon-document-add" type="primary" size="small" @click="handleAdd">添加宠物</el-button>
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
        @current-change="(val) => selectPet = val"
      >
        <el-table-column align="center" label="宠物编号">
          <template slot-scope="scope">
            {{ scope.row.id }}
          </template>
        </el-table-column>
        <el-table-column align="center" label="宠物名称">
          <template slot-scope="scope">
            {{ scope.row.name }}
          </template>
        </el-table-column>
        <el-table-column align="center" label="宠物物种">
          <template slot-scope="scope">
            {{ scope.row.species }}
          </template>
        </el-table-column>
        <el-table-column align="center" label="宠物品种">
          <template slot-scope="scope">
            {{ scope.row.varieties }}
          </template>
        </el-table-column>
        <el-table-column align="center" label="注册时间">
          <template slot-scope="scope">
            {{ scope.row.addTime }}
          </template>
        </el-table-column>
      </el-table>
      <div class="block" style="text-align: right">
        <el-pagination
          :current-page="listQuery.page"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="listQuery.size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="$emit('cancel')">取 消</el-button>
        <el-button type="primary" @click="handleSelectPet">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import * as archivesApp from '@/api/archives'
import { mapGetters } from 'vuex'

export default {
  name: 'Archives',
  state: undefined,
  data() {
    return {
      role: 0,
      total: 100,
      listQuery: {
        page: 1,
        size: 10,
        name: undefined,
        varieties: undefined,
        species: undefined,
        addTime: undefined,
        inHospital: undefined
      },
      list: [],
      selectPet: null
    }
  },
  computed: {
    ...mapGetters([
      'name',
      'user'
    ])
  },
  created() {
    this.getTableDatas()
    this.existCustomer()
  },
  methods: {
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    resetListQuery() {
      this.listQuery = {
        page: 1,
        size: 10,
        name: undefined,
        varieties: undefined,
        species: undefined,
        addTime: undefined,
        inHospital: undefined
      }
    },
    searchQuery() {
      this.getTableDatas()
    },
    getTableDatas() {
      return archivesApp.queryPetQuery(this.listQuery).then(res => {
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
    handleAdd() {
      this.$refs.addPetArchive.title = '添加宠物档案'
      this.$refs.addPetArchive.role = this.role
      this.$refs.addPetArchive.add()
      this.existCustomer()
    },
    handleOk() {
      // 新增/修改 成功时，重载列表
      this.getTableDatas()
    },
    existCustomer() { // 判断是否角色 1-客户 2-工作人员 3-管理人员
      for (let i = 0; i < this.user.roleDTOList.length; i++) {
        if (this.user.roleDTOList[i].name === '工作人员') {
          this.role = 1
        } else if (this.user.roleDTOList[i].name === '管理员') {
          this.role = 2
          break
        }
      }
    },
    handleSelectPet() {
      if (this.selectPet == null) {
        this.$message.error('请选择宠物')
        return
      }
      this.$emit('selectPet', this.selectPet)
    }
  }
}
</script>

<style scoped>
/* 日期选择框的宽度 */
.el-date-editor.el-input, .el-date-editor.el-input__inner {
  width: 130px;
}

</style>
