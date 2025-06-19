<template>
  <div>
    <el-dialog
      title="医生详情"
      :visible="true"
      width="60%"
      :close-on-click-modal="false"
      @close="$emit('cancel')"
    >
      <el-form ref="doctorUpdateForm" :model="doctor" label-width="30%" class="demo-ruleForm">
        <el-row>
          <el-form-item label="id" prop="id" label-width="15%">
            <el-input v-model="doctor.id" disabled style="width: 100%;"/>
          </el-form-item>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="doctor.name" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="sex">
              <el-select v-model="doctor.sex" disabled placeholder="请选择性别" style="width: 100%">
                <el-option label="男" value="男"/>
                <el-option label="女" value="女"/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="出生日期" prop="birth">
              <el-date-picker v-model="doctor.birth" disabled type="date" value-format="yyyy-MM-dd"
                              style="width: 100%"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="doctor.phone" disabled/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="职称" prop="title">
              <el-input v-model="doctor.title" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="科室" prop="department">
              <el-input v-model="doctor.department" disabled/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="住址" prop="address">
              <el-input v-model="doctor.address" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="执业证书编号" prop="doctorPracticeCertificateNo">
              <el-input v-model="doctor.doctorPracticeCertificateNo" disabled/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="学历" prop="education">
              <el-input v-model="doctor.education" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="身份证号" prop="idCard">
              <el-input v-model="doctor.idCard" disabled style="width: 100%;"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="选择宠物" prop="idCard">
              <el-select v-model="appointment.petId" placeholder="请选择宠物">
                <el-option v-for="pet in petList" :key="pet.id" :label="pet.name" :value="pet.id"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="选择日期" prop="idCard">
              <el-date-picker
                v-model="appointment.appointDate"
                type="date"
                placeholder="请选择日期"
                :picker-options="{
                  disabledDate: (date) => {return date < new Date()}
                }"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="$emit('cancel')">取 消</el-button>
        <el-button type="primary" @click="handleAppointDoctor">预 约</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

import { queryAllPet } from '@/api/pet'
import { addAppointment } from '@/api/appointment'
import moment from 'moment'

export default {
  props: {
    doctor: {
      type: Object,
      default: () => {
      }
    }
  },
  data() {
    return {
      petList: [],
      appointment: {
        petId: null,
        doctorId: this.doctor.id,
        appointDate: null
      }
    }
  },
  created() {
    queryAllPet().then(res => {
      this.petList = res.body.data
    })
  },
  methods: {
    handleAppointDoctor() {
      if (this.appointment.petId == null) {
        this.$message.error('请选择宠物')
        return
      }
      if (this.appointment.appointDate == null) {
        this.$message.error('请选择预约日期')
        return
      }
      this.$confirm('是否预约' + this.doctor.name + '医生?', '预约医生', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.appointment.appointDate = moment(this.appointment.appointDate).format('YYYY-MM-DD')
        addAppointment(this.appointment).then(res => {
          this.$message.success('预约成功')
          this.$emit('appointDoctorSuccess')
        })
      }).catch(() => {
        this.$message.info('已取消预约')
      })
    }
  }
}
</script>

<style scoped>

</style>
