<template>
  <div class="app-container">
    <div>
      <div>
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
            label="发布人"
            prop="username"
          />
          <el-table-column
            align="right"
          >
            <template slot="header">
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
                @click="handleView(scope.row)"
              >查看</el-button>
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
    <div>
      <el-dialog title="通知详情" :visible.sync="isShow">
        <el-form>
          <h2 style="text-align: center">{{ viewGuide.title }}</h2>
          <p>{{ viewGuide.description }}</p>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="Visible">取 消</el-button>
          <el-button type="primary" @click="Visible">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { selectByGuideQuery } from '@/api/guide'

export default {
  data() {
    return {
      name: '发布',
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
      loading: false,
      isShow: false,
      viewGuide: {}
    }
  },
  created() {
    this.handleQueryguide()
  },
  methods: {
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

    handleView(notice) {
      this.isShow = true
      this.viewGuide = notice
      console.log(this.viewGuide)
    },

    Visible() {
      this.isShow = false
    }
  }
}

</script>

<style scoped>
.app-container{
  height: 100%;
}

</style>

