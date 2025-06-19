<template>
  <div>
    <el-dialog
      title="我的预约"
      :visible="true"
      width="80%"
      :close-on-click-modal="false"
      @close="$emit('cancel')"
    >
      <el-row style="width: 80%; margin: 100px auto 0">
        <el-col :span="8">
          <el-input v-model="appointmentQuery.userName" style="width: 80%" />
          <el-button
            icon="el-icon-search"
            type="primary"
            style="width: 20%"
            @click="appointmentQuery.doctorName = appointmentQuery.userName; appointmentQuery.petName = appointmentQuery.userName; handleQueryAppointment(1)"
          />
        </el-col>
      </el-row>
      <el-table v-loading="loading" :data="appointmentList" style="width: 80%; margin: 50px auto 0">
        <el-table-column label="宠物名字" prop="pet.name" />
        <el-table-column label="预约医生" prop="doctor.name" />
        <el-table-column prop="appointDate" label="预约日期" />
        <el-table-column label="状态">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status === '等待审核'">等待审核</el-tag>
            <el-tag v-if="scope.row.status === '拒绝'" type="danger">拒绝</el-tag>
            <el-tag v-if="scope.row.status === '同意'" type="success">同意</el-tag>
          </template>
        </el-table-column>
      </el-table>
      <div style="width: 80%; margin: 0 auto 0">
        <el-pagination
          background
          layout="prev, pager, next"
          :total="total"
          :page-size="appointmentQuery.size"
          :current-page="appointmentQuery.page"
          @current-change="handleQueryAppointment"
        />
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="$emit('cancel')">取 消</el-button>
        <el-button type="primary" @click="$emit('cancel')">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

import { queryAppointment, updateAppointment } from '@/api/appointment'

export default {
  data() {
    return {
      appointmentList: [],
      appointmentQuery: {
        userName: '',
        doctorName: '',
        petName: '',
        page: 1,
        size: 5
      },
      total: 0,
      loading: false,
      appointment: null,
      doctor: null,
      user: null,
      pet: null
    }
  },
  created() {
    this.handleQueryAppointment()
  },
  methods: {
    handleQueryAppointment(page) {
      this.loading = true
      if (!isNaN(page)) {
        this.appointmentQuery.page = page
      }
      queryAppointment(this.appointmentQuery).then(res => {
        console.log(res.body.data)
        this.appointmentList = res.body.data
        this.total = res.body.total
        this.loading = false
      })
    },
    handleUpdateAppointment(appointment, status) {
      appointment.status = status
      updateAppointment(appointment).then(res => {
        this.$message.success(res.body.data)
        this.handleQueryAppointment()
      })
    }
  }
}
</script>

<style scoped>

</style>
