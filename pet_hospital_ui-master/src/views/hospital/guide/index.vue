<template>
  <div class="app-container">
    <el-form ref="caseRecord" :rules="rules" :model="guide" :inline="true" status-icon size="small" label-position="right">
      <div class="left-content">
        <el-form-item label="主题：" prop="title" label-width="70px" style="width: 10px">
          <div class="left-content-title">
            <el-input v-model="guide.title" placeholder="请输入内容" />
          </div>
          <!--      <div class="left-content-title">-->
          <!--        <span>主题：</span>-->
          <!--        <el-input v-model="title" placeholder="请输入内容"></el-input>-->
          <!--      </div>-->
        </el-form-item>
        <div class="left-content-text">
          <el-form-item label="内容：" prop="description" label-width="70px" style="width: 50px">
            <!--        <span>内容：</span>-->
            <el-input
              v-model="guide.description"
              style="width: 550px"
              type="textarea"
              :rows="19"
              placeholder="请输入内容"
            />
          </el-form-item>
<!--          <leftfooter>-->
            <el-button class="button" type="primary" round @click="release">{{name}}</el-button>
<!--            <el-button v-show="IsShow" class="button" type="primary" round @click="release">修改</el-button>-->
            <el-button v-show="IsShow" class="x-button" type="primary" round @click="cancel" >取消</el-button>
<!--          </leftfooter>-->
          <!--        <el-button class="button" type="primary" round @click="release">发布</el-button>-->
        </div>
      </div>
    </el-form>
    <div class="right-content">
      <div class="right-content-text">
        <el-table
          v-loading="loading"
          :data="guidelist.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
          style="width: 100%"
        >
          <el-table-column
            label="日期"
            prop="addTime"
          />
          <el-table-column
            label="主题"
            prop="title"
          />
          <el-table-column
            align="right"
          >
            <template slot="header" slot-scope="scope">
              <el-input
                v-model="GuideQuery.title"
                size="mini"
                placeholder="输入关键字搜索"
                @change="handleQueryguide()"
              />
            </template>
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="handleEdit(scope.row)"
              >编辑</el-button>
              <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.row.id, scope.row)"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="block">
        <el-pagination
          :current-page="GuideQuery.page"
          :page-sizes="[10]"
          :page-size=" GuideQuery.size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @current-change="handleQueryguide"
        />
      </div>
    </div>
  </div>
</template>

<script>
import {addguide, deleteguide, selectByGuideQuery, selectByid, updateguide} from '@/api/guide'
import { title } from '@/settings'
// import leftfooter from '@/components/guide/leftfooter'

export default {
  // components: { leftfooter },
  data() {
    return {
      name:'发布',
      IsShow: false,
      guide: {
        title: undefined,
        description: undefined
      },
      rules: {
        title: [
          { required: true, trigger: 'blur', message: '请输入主题' }
        ],
        description: [
          { required: true, trigger: 'blur', message: '请输入描述' }
        ]
      },
      guidelist: [],
      title: '',
      description: '',
      GuideQuery: {
        page: 1,
        size: 10,
        title: '',
        description: '',
        total: 0
      },
      loading: false
    }
  },
  created() {
    this.handleQueryguide()
  },
  methods: {
    release() {
      if(this.name == '发布'){
        addguide(this.guide).then(res => {
          if (res.body.data == '发布成功') {
            this.$message.success(res.body.data)
            this.guide.title = undefined
            this.guide.description = undefined
          } else {
            this.$message.error(res.body.data)
          }

          //this.$emit('addDoctorSuccess')
          this.handleQueryguide()
        })
      }else{
         updateguide(this.guide).then(res => {
           // console.log(res.body.data)
           this.handleQueryguide()
         })
      }
      },
    //   addguide(this.guide).then(res => {
    //     if (res.body.data == '发布成功') {
    //       this.$message.success(res.body.data)
    //       this.guide.title = undefined
    //       this.guide.description = undefined
    //     } else {
    //       this.$message.error(res.body.data)
    //     }
    //
    //     //this.$emit('addDoctorSuccess')
    //   })
    //   this.handleQueryguide()
    // },

    handleQueryguide(page) {
      this.loading = true
      if (!isNaN(page)) {
        this.GuideQuery.page = page
      }
      selectByGuideQuery(this.GuideQuery).then(res => {
        // console.log(res.body.data)
        this.guidelist = res.body.data
        this.total = res.body.total
        this.loading = false
      })
    },

    handleDelete(id) {
      this.$confirm(`是否删除`).then(() => {
        deleteguide(id).then(res => {
          console.log(res.body.data)
          this.$message({
            type: 'success',
            message: res.body.data
          })
          this.handleQueryguide(1)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },

    handleEdit(row) {
      this.name = '修改'
      this.IsShow = true
      this.guide.id = row.id
      this.guide.title = row.title
      this.guide.description = row.description
    },

    cancel(){
      this.name = '发布'
      this.IsShow = false
      this.guide.title = undefined
      this.guide.description = undefined
      //this.handleQueryguide()
    }
  }
}

</script>

<style scoped>
.app-container{
  height: 100%;
}
/*left*/
.left-content{
  height: 100%;
  width: 600px;
  float: left;
}
.left-content .left-content-title{
  width: 550px;
  display: block;
}
.left-content .left-content-text{
  width: 550px;
  position: relative;
  padding-top: 20px;
}
.left-content .left-content-text .button{
  position: absolute;
  top: 470px;
  left: 470px;
}
.left-content .left-content-text .x-button{
  position: absolute;
  top: 470px;
  left: 370px;
}
/*right*/
.right-content{
  width: 600px;
  float: left;
  height: 100%;
  padding-left: 50px;
}
</style>
