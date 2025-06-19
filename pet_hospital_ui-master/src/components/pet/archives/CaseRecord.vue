<template>
  <el-dialog
    :title="title"
    :visible.sync="visible"
    :destroy-on-close="close_modal_destroy"
    :close-on-click-modal="close_modal"
    @close="handleCancel"
    width="900px"
  >
    <div style="text-align: right">
      <el-button class="el-icon-document-add"  v-if="this.role != 0" type="primary" size="mini" @click="handleAdd">添加病例</el-button>
    </div>
    <br/>
    <el-table
      ref="caseRecord"
      size="mini"
      :data="form"
      element-loading-text="Loading"
      highlight-current-row
      @selection-change="handleSelectionChange"
    >
      <el-table-column align="center" label="病例编号">
        <template slot-scope="scope">
          {{ scope.row.id }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="医生名字">
        <template slot-scope="scope">
          {{ scope.row.doctorName }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="描述" :show-overflow-tooltip='true'>
        <template slot-scope="scope">
          {{ scope.row.description }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="添加时间">
        <template slot-scope="scope">
          {{ scope.row.addTime }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" v-if="this.role != 0">
        <template slot-scope="scope">
          <el-button-group>
            <i class="el-icon-delete" style="font-size: 20px" @click="handleDeleteTable(scope.row)" />
            &nbsp;&nbsp;&nbsp;&nbsp;
            <i class="el-icon-edit"  style="font-size: 20px" @click="handleEditTable(scope.row)" />
          </el-button-group>
        </template>
      </el-table-column>
    </el-table><AddCaseRecord ref="addCaseRecord" @case="handleCase"></AddCaseRecord>
  </el-dialog>

</template>

<script>
import * as archivesApp from '@/api/archives'
import AddCaseRecord from '@/components/pet/archives/AddCaseRecord'
export default {
  name: 'CaseRecord',
  components: { AddCaseRecord },
  data() {
    return {
      title: '宠物病例',
      visible: false,
      close_modal_destroy: true,
      close_modal: false,
      pet_id: 0,
      role: 0,
      form: {}
    }
  },
  methods: {
    handleCancel() {
      this.close()
    },
    close() {
      this.$emit('close')
      this.visible = false
      this.form = {}
    },
    getForm() {
      archivesApp.queryCase(this.pet_id).then(res => {
        this.form = res.body.data
      })
    },
    add(row,role) {
      this.$nextTick(() => {
        this.$refs.caseRecord.resetFields()
      })

      this.updateDisabled = true
      this.visible = true
      this.pet_id = row.id
      this.role = role

      this.getForm()
    },
    handleAdd() {
      this.$refs.addCaseRecord.title = "添加宠物病例"
      this.$refs.addCaseRecord.add(this.pet_id)
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    handleEditTable(row) {
      this.$refs.addCaseRecord.title = "病例详情"
      this.$refs.addCaseRecord.edit(row)
    },
    handleDeleteTable(row) {
      archivesApp.deleteCase(row.id).then(res => {
        if (res.body.data == "删除成功") {
          this.$message.success("删除成功")
        } else {
          this.$message.error("删除失败")
        }
        this.getForm()
      })
    },
    handleCase () {
      // 新增/修改 成功时，重载列表
      this.getForm()
    }
  }
}
</script>

<style scoped>
.el-tooltip__popper{font-size: 12px; max-width:10% } /*设置显示隐藏部分内容，按20%显示*/
</style>
