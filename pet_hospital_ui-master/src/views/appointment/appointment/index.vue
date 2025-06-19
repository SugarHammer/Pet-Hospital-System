<template>
  <div>
    <el-row style="width: 80%; margin: 100px auto 0">
      <el-col :span="8">
        <el-input v-model="doctorQuery.name" style="width: 80%" placeholder="输入医生姓名，手机号或身份证号" />
        <el-button
          icon="el-icon-search"
          type="primary"
          style="width: 20%"
          @click="doctorQuery.idCard = doctorQuery.name; doctorQuery.phone = doctorQuery.name; handleQueryDoctor(1)"
        />
      </el-col>
      <el-button style="margin-left: 50px" type="primary" @click="isViewMyAppointment = true">我的预约</el-button>
    </el-row>
    <el-table v-loading="loading" :data="doctorList" style="width: 80%; margin: 50px auto 0">
      <el-table-column prop="name" label="姓名" />
      <el-table-column prop="sex" label="性别" />
      <el-table-column prop="birth" label="出生日期" />
      <el-table-column prop="title" label="职称" />
      <el-table-column prop="department" label="科室" />
      <el-table-column prop="education" label="学历" />
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="success"
            @click="doctor = scope.row"
          >
            预约
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="width: 80%; margin: 0 auto 0">
      <el-pagination
        background
        layout="prev, pager, next"
        :total="total"
        :page-size="doctorQuery.size"
        :current-page="doctorQuery.page"
        @current-change="handleQueryDoctor"
      />
    </div>
    <ViewDoctorDialog v-if="doctor != null" :doctor="doctor" @appointDoctorSuccess="doctor = null" @cancel="doctor = null" />
    <ViewMyAppointmentDialog v-if="isViewMyAppointment" @cancel="isViewMyAppointment = false" />
  </div>
</template>

<script>
import { queryDoctor } from '@/api/doctor'
import ViewDoctorDialog from '@/views/appointment/appointment/ViewDoctorDialog'
import ViewMyAppointmentDialog from '@/views/appointment/appointment/ViewMyAppointmentDialog'

export default {
  components: { ViewDoctorDialog, ViewMyAppointmentDialog },
  data() {
    return {
      doctorList: [],
      doctorQuery: {
        page: 1,
        size: 5,
        name: '',
        phone: '',
        idCard: ''
      },
      total: 0,
      loading: false,
      doctor: null,
      isViewMyAppointment: false
    }
  },
  created() {
    this.handleQueryDoctor()
  },
  methods: {
    handleQueryDoctor(page) {
      this.loading = true
      if (!isNaN(page)) {
        this.doctorQuery.page = page
      }
      queryDoctor(this.doctorQuery).then(res => {
        console.log(res.body.data)
        this.doctorList = res.body.data
        this.total = res.body.total
        this.loading = false
      })
    }
  }
}
</script>

<style scoped>

</style>
