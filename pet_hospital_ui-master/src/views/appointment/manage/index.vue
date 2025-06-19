<template>
  <div>
    <el-row style="width: 80%; margin: 100px auto 0">
      <el-col :span="8">
        <el-input v-model="appointmentQuery.userName" style="width: 80%" placeholder="输入用户姓名，宠物名称或医生姓名" />
        <el-button
          icon="el-icon-search"
          type="primary"
          style="width: 20%"
          @click="appointmentQuery.doctorName = appointmentQuery.userName; appointmentQuery.petName = appointmentQuery.userName; handleQueryAppointment(1)"
        />
      </el-col>
    </el-row>
    <el-table v-loading="loading" :data="appointmentList" style="width: 80%; margin: 50px auto 0">
      <el-table-column label="客户姓名">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="user = scope.row.user"
          >
            {{ scope.row.user.name }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="宠物名字">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="pet = scope.row.pet"
          >
            {{ scope.row.pet.name }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="预约医生">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="doctor = scope.row.doctor"
          >
            {{ scope.row.doctor.name }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column prop="appointDate" label="预约日期" />
      <el-table-column prop="status" label="状态" />
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.status === '等待审核'"
            size="mini"
            type="success"
            @click="handleUpdateAppointment(scope.row, '同意')"
          >
            同意
          </el-button>
          <el-button
            v-if="scope.row.status === '等待审核'"
            size="mini"
            type="danger"
            @click="handleUpdateAppointment(scope.row, '拒绝')"
          >
            拒绝
          </el-button>
          <el-button
            v-if="scope.row.status === '同意'"
            size="mini"
            type="success"
            disabled
          >
            已同意
          </el-button>
          <el-button
            v-if="scope.row.status === '拒绝'"
            size="mini"
            type="danger"
            disabled
          >
            已拒绝
          </el-button>
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
    <ViewDoctorDialog v-if="doctor != null" :doctor="doctor" @cancel="doctor = null" />
    <ViewUserDialog v-if="user != null" :user="user" @cancel="user = null" />
    <ViewPetDialog v-if="pet != null" :pet="pet" @cancel="pet = null" />
  </div>
</template>

<script>

import { queryAppointment, updateAppointment } from '@/api/appointment'
import ViewDoctorDialog from '@/views/appointment/manage/ViewDoctorDialog'
import ViewUserDialog from '@/views/appointment/manage/ViewUserDialog'
import ViewPetDialog from '@/views/appointment/manage/ViewPetDialog'

export default {
  components: { ViewPetDialog, ViewUserDialog, ViewDoctorDialog },
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
