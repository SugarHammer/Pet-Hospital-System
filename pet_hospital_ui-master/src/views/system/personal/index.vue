<template>
  <div style="width: 60%; margin: 100px auto;">
    <div v-show="isUpdatePassword">
      <el-form ref="updatePasswordForm" :model="updatePasswordForm" :rules="updatePasswordRules" label-width="10%">
        <el-form-item label="原来的密码" prop="oldPassword">
          <el-input v-model="updatePasswordForm.oldPassword" type="password" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密码" prop="newPassword">
          <el-input v-model="updatePasswordForm.newPassword" type="password" autocomplete="off" />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="updatePasswordForm.confirmPassword" type="password" autocomplete="off" />
        </el-form-item>
        <el-form-item>
          <el-button type="danger" @click="handleUpdatePassword">确认修改</el-button>
          <el-button type="primary" @click="isUpdatePassword = false">个人信息</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div v-show="!isUpdatePassword">
      <el-form ref="updatePersonalInfoForm" :model="currentUser" :rules="rules" label-width="10%" class="demo-ruleForm">
        <el-form-item label="用户名">
          <el-input v-model="currentUser.username" disabled />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="currentUser.name" />
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="currentUser.sex" placeholder="请选择性别">
            <el-option label="男" value="男" />
            <el-option label="女" value="女" />
          </el-select>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="currentUser.phone" />
        </el-form-item>
        <el-form-item label="住址" prop="address">
          <el-input v-model="currentUser.address" />
        </el-form-item>
        <el-form-item label="身份证号" prop="idCard">
          <el-input v-model="currentUser.idCard" />
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="handleUpdatePersonalInfo">保存修改</el-button>
      <el-button type="danger" @click="isUpdatePassword = true">修改密码</el-button>
    </div>
  </div>
</template>

<script>

import { mapGetters } from 'vuex'
import { updatePassword, updatePersonalInfo } from '@/api/user'

export default {
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
    const validatePassword = (rule, value, callback) => {
      if (!/^[a-zA-Z0-9_-]{6,12}$/.test(value)) {
        callback(new Error('请输入6到12位的字符'))
      } else {
        callback()
      }
    }
    const validateConfirmPassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.updatePasswordForm.newPassword) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      currentUser: null,
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
        ]
      },
      isUpdatePassword: false,
      updatePasswordRules: {
        oldPassword: [
          { validator: validatePassword, trigger: 'blur' }
        ],
        newPassword: [
          { validator: validatePassword, trigger: 'blur' }
        ],
        confirmPassword: [
          { validator: validateConfirmPassword, trigger: 'blur' }
        ]
      },
      updatePasswordForm: {
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      }
    }
  },
  computed: {
    ...mapGetters([
      'name',
      'user'
    ])
  },
  created() {
    this.currentUser = {
      username: this.user.username,
      name: this.user.name,
      sex: this.user.sex,
      phone: this.user.phone,
      address: this.user.address,
      idCard: this.user.idCard
    }
  },
  methods: {
    handleUpdatePersonalInfo() {
      this.$refs['updatePersonalInfoForm'].validate((valid) => {
        if (valid) {
          updatePersonalInfo(this.currentUser).then(res => {
            this.$message.success(res.body.data)
          })
        }
      })
    },
    handleUpdatePassword() {
      this.$refs['updatePasswordForm'].validate((valid) => {
        if (valid) {
          this.$confirm('是否修改密码?', '修改密码', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            updatePassword(this.updatePasswordForm).then(res => {
              this.$message.success(res.body.data)
            })
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
