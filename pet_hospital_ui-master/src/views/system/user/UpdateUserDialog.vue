<template>
  <div>
    <el-dialog
      title="修改用户"
      :visible="true"
      width="30%"
      :close-on-click-modal="false"
      @close="$emit('cancel')"
    >
      <el-form ref="userUpdateForm" :model="user" :rules="rules" label-width="100px" class="demo-ruleForm">
        <el-form-item label="用户名">
          <el-input v-model="user.username" disabled />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="user.name" />
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="user.sex" placeholder="请选择性别">
            <el-option label="男" value="男" />
            <el-option label="女" value="女" />
          </el-select>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="user.phone" />
        </el-form-item>
        <el-form-item label="住址" prop="address">
          <el-input v-model="user.address" />
        </el-form-item>
        <el-form-item label="身份证号" prop="idCard">
          <el-input v-model="user.idCard" />
        </el-form-item>
        <el-form-item label="是否有效">
          <el-switch v-model="user.status" />
        </el-form-item>
        <el-form-item label="拥有角色">
          <el-checkbox-group
            v-model="user.roleList"
          >
            <el-checkbox v-for="role in roleList" :key="role.id" :label="role.name" />
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="$emit('cancel')">取 消</el-button>
        <el-button type="primary" @click="handleUpdateUser">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { queryAllRole } from '@/api/role'
import { updateUser } from '@/api/user'

export default {
  props: {
    user: {
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
        ]
      }
    }
  },
  created() {
    queryAllRole().then(res => {
      this.roleList = res.body.data
    })
    console.log(this.user)
  },
  methods: {
    handleUpdateUser() {
      this.$refs['userUpdateForm'].validate((valid) => {
        if (valid) {
          const roleDTOList = []
          for (let i = 0; i < this.roleList.length; i++) {
            for (let j = 0; j < this.user.roleList.length; j++) {
              if (this.roleList[i].name === this.user.roleList[j]) {
                roleDTOList.push(this.roleList[i])
                break
              }
            }
          }
          this.user.roleDTOList = roleDTOList
          updateUser(this.user).then(res => {
            this.$message.success(res.body.data)
            this.$emit('updateUserSuccess')
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
