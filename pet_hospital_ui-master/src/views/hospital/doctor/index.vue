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
      <el-col :span="1" :offset="15">
        <el-button
          icon="el-icon-plus"
          type="primary"
          @click="handleAddDoctor"
        />
      </el-col>
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
            @click="handleUpdateDoctor(scope.row)"
          >
            编辑
          </el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDeleteDoctor(scope.row)"
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
        :page-size="doctorQuery.size"
        :current-page="doctorQuery.page"
        @current-change="handleQueryDoctor"
      />
    </div>
    <AddDoctorDialog v-if="addDoctor != null" :doctor="addDoctor" @addDoctorSuccess="addDoctor = null; handleQueryDoctor()" @cancel="addDoctor = null" />
    <UpdateDoctorDialog v-if="updateDoctor != null" :doctor="updateDoctor" @updateDoctorSuccess="updateDoctor = null; handleQueryDoctor()" @cancel="updateDoctor = null" />
  </div>
</template>

<script>
import UpdateDoctorDialog from '@/views/hospital/doctor/UpdateDoctorDialog'
import AddDoctorDialog from '@/views/hospital/doctor/AddDoctorDialog'
import { deleteDoctor, queryDoctor } from '@/api/doctor'

export default {
  components: {
    AddDoctorDialog,
    UpdateDoctorDialog
  },
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
      updateDoctor: null,
      addDoctor: null,
      total: 0,
      loading: false
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
    },
    handleUpdateDoctor(doctor) {
      this.updateDoctor = {
        id: doctor.id,
        name: doctor.name,
        sex: doctor.sex,
        birth: doctor.birth,
        title: doctor.title,
        department: doctor.department,
        idCard: doctor.idCard,
        phone: doctor.phone,
        doctorPracticeCertificateNo: doctor.doctorPracticeCertificateNo,
        education: doctor.education,
        address: doctor.address
      }
    },
    handleAddDoctor() {
      this.addDoctor = {
        id: '',
        name: '',
        sex: '',
        birth: '',
        title: '',
        department: '',
        idCard: '',
        phone: '',
        doctorPracticeCertificateNo: '',
        education: '',
        address: ''
      }
    },
    handleDeleteDoctor(doctor) {
      this.$confirm('是否删除此医生?', '删除医生', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteDoctor(doctor).then(res => {
          this.$message({
            type: 'success',
            message: res.body.data
          })
          this.handleQueryDoctor(1)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    }
  }
}
</script>

<style scoped>

</style>
