<template>
  <div>
    <el-dialog
      title="添加医生"
      :visible="true"
      width="60%"
      :close-on-click-modal="false"
      @close="$emit('cancel')"
    >
      <el-form ref="doctorAddForm" :model="doctor" :rules="rules" label-width="30%" class="demo-ruleForm">
        <el-row>
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="doctor.name" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="sex">
              <el-select v-model="doctor.sex" placeholder="请选择性别" style="width: 100%">
                <el-option label="男" value="男" />
                <el-option label="女" value="女" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="出生日期" prop="birth">
              <el-date-picker v-model="doctor.birth" type="date" value-format="yyyy-MM-dd" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="doctor.phone" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="职称" prop="title">
              <el-input v-model="doctor.title" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="科室" prop="department">
              <el-input v-model="doctor.department" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="住址" prop="address">
              <el-input v-model="doctor.address" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="执业证书编号" prop="doctorPracticeCertificateNo">
              <el-input v-model="doctor.doctorPracticeCertificateNo" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="学历" prop="education">
              <el-input v-model="doctor.education" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="身份证号" prop="idCard">
              <el-input v-model="doctor.idCard" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="$emit('cancel')">取 消</el-button>
        <el-button type="primary" @click="handleAddDoctor">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { queryAllRole } from '@/api/role'
import { addDoctor } from '@/api/doctor'

export default {
  props: {
    doctor: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    const checkPhone = (rule, value, callback) => {
      if (!/^(?:(?:\+|00)86)?1(?:(?:3[\d])|(?:4[5-7|9])|(?:5[0-3|5-9])|(?:6[5-7])|(?:7[0-8])|(?:8[\d])|(?:9[1|8|9]))\d{8}$/.test(value)) {
        callback(new Error('请输入正确的手机号'))
      } else {
        callback()
      }
    }
    const checkName = (rule, value, callback) => {
      if (!/^(?:[\u4e00-\u9fa5·]{2,16})$/.test(value)) {
        callback(new Error('请输入正确的姓名'))
      } else {
        callback()
      }
    }
    const checkIdCard = (rule, value, callback) => {
      if (!/^[1-9]\d{5}(?:18|19|20)\d{2}(?:0\d|10|11|12)(?:0[1-9]|[1-2]\d|30|31)\d{3}[\dXx]$/.test(value)) {
        callback(new Error('请输入正确的身份证号'))
      } else {
        callback()
      }
    }
    return {
      roleList: [],
      rules: {
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
          { validator: checkName, trigger: 'blur' }
        ],
        sex: [
          { required: true, message: '请选择性别', trigger: 'change' }
        ],
        idCard: [
          { required: true, message: '请输入身份证号', trigger: 'blur' },
          { validator: checkIdCard, trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { validator: checkPhone, trigger: 'blur' }
        ],
        address: [
          { required: true, message: '请输入地址', trigger: 'blur' }
        ],
        birth: [
          { required: true, message: '请选择出生日期', trigger: 'blur' }
        ],
        title: [
          { required: true, message: '请输入职称', trigger: 'blur' }
        ],
        department: [
          { required: true, message: '请输入科室', trigger: 'blur' }
        ],
        doctorPracticeCertificateNo: [
          { required: true, message: '请输入执业证书编号', trigger: 'blur' }
        ],
        education: [
          { required: true, message: '请输入学历', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    queryAllRole().then(res => {
      this.roleList = res.body.data
    })
    console.log(this.doctor)
  },
  methods: {
    handleAddDoctor() {
      this.$refs['doctorAddForm'].validate((valid) => {
        if (valid) {
          addDoctor(this.doctor).then(res => {
            this.$message.success(res.body.data)
            this.$emit('addDoctorSuccess')
          })
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
