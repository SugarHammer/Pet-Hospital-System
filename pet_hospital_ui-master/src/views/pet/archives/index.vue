<template>
  <div class="app-container">
    <el-form ref="form"  :inline="true" :model="listQuery" size="small">
      <el-form-item v-if="this.role == 0" label-width="80px" label="宠物名称:">
        <el-input v-model="listQuery.name" label-width="80px" placeholder="请输入" clearable />
      </el-form-item>
      <el-form-item v-if="this.role != 0" label-width="80px" label="用户名称:">
        <el-input v-model="listQuery.userName" label-width="80px" placeholder="请输入" clearable />
      </el-form-item>
      <el-form-item label-width="80px" label="宠物品种:">
        <el-input v-model="listQuery.varieties" placeholder="请输入" clearable />
      </el-form-item>
<!--      <el-form-item v-if="this.role != 0" label="宠物种类:">-->
<!--        <el-input v-model="listQuery.species" placeholder="请输入" clearable />-->
<!--      </el-form-item>-->
      <el-form-item v-if="this.role != 0" label="是否住院" label-width="80px" prop="inHospital">
        <el-radio-group v-model="listQuery.inHospital" >
          <el-radio :label="1">是</el-radio>
          <el-radio :label="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item v-if="this.role != 0" label="注册日期" prop="addTime" label-width="100px">
        <el-date-picker
          v-model="listQuery.addTime"
          type="date"
          value-format="yyyy-MM-dd"
          size="mini"
          placeholder="选择日期">
        </el-date-picker>
      </el-form-item>
      <div style="text-align: right">
        <el-button class="el-icon-document-add" type="primary" size="small" @click="handleAdd">添加宠物</el-button>
        <el-button type="primary" size="small" @click="searchQuery">查询</el-button>
        <el-button size="small" @click="resetListQuery">重置</el-button>
        <el-button
          type="primary"
          size="small"
          @click="getAll"
        >
          <download-excel class="export-excel-wrapper" :data="this.allList" :fields="json_fields" name="宠物信息Excel">
          </download-excel>
        </el-button>

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
      <el-table-column align="center" v-if="this.role == 0" label="宠物物种">
        <template slot-scope="scope">
          {{ scope.row.species }}
        </template>
      </el-table-column>
      <el-table-column align="center" v-if="this.role != 0" label="客户名称">
        <template slot-scope="scope">
          {{ scope.row.userName }}
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
      <el-table-column align="center" label="操作">
        <template slot-scope="scope">
          <el-button-group>
            <i class="el-icon-delete" style="font-size: 20px" @click="handleDeleteTable(scope.row)" />
            &nbsp;&nbsp;&nbsp;&nbsp;
            <i class="el-icon-edit" style="font-size: 20px" @click="handleEditTable(scope.row)" />
            &nbsp;
            <i class="el-icon-tickets" style="font-size: 20px" @click="handleQueryCaseRecord(scope.row)" />
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
    <AddPetArchive ref="addPetArchive" @ok="handleOk"></AddPetArchive>
    <CaseRecord ref="caseRecord" @ok="handleOk"></CaseRecord>
  </div>
</template>

<script>
import AddPetArchive from '@/components/pet/archives/AddPetArchive'
import * as archivesApp from '@/api/archives'
import { mapGetters } from 'vuex'
import CaseRecord from '@/components/pet/archives/CaseRecord'
import moment from 'moment'
export default {
  name: 'Archives',
  components: { CaseRecord, AddPetArchive },
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
      json_fields: {
        "宠物编号": "id",
        "宠物名称": "name",
        "客户名称": "userName",
        "宠物品种": "species",
        "注册时间": "addTime"
      },
      listQuery: {
        page: 1,
        size: 10,
        name: undefined,
        varieties: undefined,
        species: undefined,
        addTime: undefined,
        inHospital: undefined,
        userName: undefined
      },
      list: [],
      allList: []
    }
  },
  methods: {
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    handleDeleteTable(row) {
      archivesApp.deletePet(row.id).then(res=> {
        if (res.body.data == "删除成功") {
          this.$message.success("删除成功")
          this.$emit("ok")
        } else {
          this.$message.error("删除失败")
        }
        this.getTableDatas()
      })
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
    handleEditTable(row) {
      this.$refs.addPetArchive.edit(row)
      this.$refs.addPetArchive.role = this.role
      this.$refs.addPetArchive.title = row.name+'的宠物档案'
    },
    searchQuery() {
      if (this.listQuery.addTime) {
        this.listQuery.addTime = moment(this.listQuery.addTime).format('YYYY-MM-DD')
      }
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
    getAll() {
      return archivesApp.queryPetQueryAll(this.listQuery).then(res => {
        this.allList = res.body.data
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
      this.$refs.addPetArchive.title = "添加宠物档案"
      this.$refs.addPetArchive.role = this.role
      this.$refs.addPetArchive.add()
      this.existCustomer()
    },
    handleOk () {
      // 新增/修改 成功时，重载列表
      this.getTableDatas()
    },
    handleQueryCaseRecord (row) {
      this.$refs.caseRecord.title = row.name+'的宠物病例'
      this.$refs.caseRecord.add(row,this.role)
    },
    existCustomer() { // 判断是否角色 1-客户 2-工作人员 3-管理人员
      for (var i=0;i<this.user.roleDTOList.length;i++) {
        if (this.user.roleDTOList[i].name == '工作人员')
          this.role = 1
        else if(this.user.roleDTOList[i].name == '管理员') {
          this.role = 2
          break
        }
      }
    }
  },
  created() {
    this.getTableDatas()
    this.existCustomer()
  }
}
</script>

<style scoped>
/* 日期选择框的宽度 */
.el-date-editor.el-input, .el-date-editor.el-input__inner {
  width: 130px;
}

</style>
