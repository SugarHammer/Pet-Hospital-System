<template>
  <div>
    <el-dialog
      title="医生详情"
      :visible="true"
      width="60%"
      :close-on-click-modal="false"
      @close="$emit('cancel')"
    >
      <el-form :model="doctor" label-width="30%" class="demo-ruleForm">
        <el-row>
          <el-form-item label="id" prop="id" label-width="15%">
            <el-input v-model="doctor.id" disabled style="width: 100%;" />
          </el-form-item>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="doctor.name" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="sex">
              <el-select v-model="doctor.sex" disabled placeholder="请选择性别" style="width: 100%">
                <el-option label="男" value="男" />
                <el-option label="女" value="女" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="出生日期" prop="birth">
              <el-date-picker v-model="doctor.birth" disabled type="date" value-format="yyyy-MM-dd" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="doctor.phone" disabled />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="职称" prop="title">
              <el-input v-model="doctor.title" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="科室" prop="department">
              <el-input v-model="doctor.department" disabled />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="住址" prop="address">
              <el-input v-model="doctor.address" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="执业证书编号" prop="doctorPracticeCertificateNo">
              <el-input v-model="doctor.doctorPracticeCertificateNo" disabled />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="学历" prop="education">
              <el-input v-model="doctor.education" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="身份证号" prop="idCard">
              <el-input v-model="doctor.idCard" disabled style="width: 100%;" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="$emit('cancel')">取 消</el-button>
        <el-button type="primary" @click="$emit('cancel')">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

export default {
  props: {
    doctor: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {

    }
  },
  methods: {

  }
}
</script>

<style scoped>

</style>
