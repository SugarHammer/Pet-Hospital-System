<template>
  <div>
    <el-row>
      <el-col :span="12">
        <Chart :options="petOptions" />
      </el-col>
      <el-col :span="12">
        <Chart :options="healthOptions" />
      </el-col>
    </el-row>
    <Chart :options="appointOptions" />
  </div>
</template>

<script>
import { Chart } from 'highcharts-vue'
import { appointmentCount, healthCount, petPercentage } from '@/api/form'

export default {
  components: { Chart },
  data() {
    return {
      petOptions: {
        title: {
          text: '各宠物种类占比'
        },
        credits: {
          enabled: false
        },
        exporting: { enabled: true },
        series: [{
          type: 'pie',
          name: '宠物数量',
          data: [],
          dataLabels: {
            enabled: true,
            formatter: function() {
              return '<span style="color: ' + this.point.color + '">' +
                this.point.name + '，占比' + this.percentage.toFixed(2) + '%</span>'
            }
          }
        }]
      },
      appointOptions: {
        title: {
          text: '近七天的预约数量'
        },
        credits: {
          enabled: false
        },
        exporting: { enabled: false },
        yAxis: {
          title: {
            text: '预约数量'
          },
          labels: {
            step: 2
          }
        },
        xAxis: {
          title: {
            text: '预约日期'
          },
          categories: []
        },
        legend: {
          layout: 'vertical',
          align: 'right',
          verticalAlign: 'middle'
        },
        series: [{
          type: 'line',
          name: '预约数量',
          data: [],
          dataLabels: {
            enabled: true,
            formatter: function() {
              return '<span style="color: ' + this.point.color + '">' +
                this.point.name + '</span>'
            }
          }
        }]
      },
      healthOptions: {
        title: {
          text: '近七天的健康状态统计'
        },
        credits: {
          enabled: false
        },
        exporting: { enabled: false },
        yAxis: {
          title: {
            text: '数量'
          },
          labels: {
            step: 2
          }
        },
        xAxis: {
          title: {
            text: '添加日期'
          },
          categories: []
        },
        legend: {
          layout: 'vertical',
          align: 'right',
          verticalAlign: 'middle'
        },
        series: [{
          type: 'column',
          name: '健康',
          data: [],
          dataLabels: {
            enabled: true
          }
        }, {
          type: 'column',
          name: '良好',
          data: [],
          dataLabels: {
            enabled: true
          }
        }, {
          type: 'column',
          name: '不健康',
          data: [],
          dataLabels: {
            enabled: true
          }
        }]
      }
    }
  },
  created() {
    petPercentage().then(res => {
      this.petOptions.series[0].data = res.body.data
    })
    appointmentCount().then(res => {
      const list = res.body.data
      this.appointOptions.series[0].data = list
      this.appointOptions.xAxis.categories = []
      for (let i = 0; i < list.length; i++) {
        console.log(list[i])
        this.appointOptions.xAxis.categories.push(list[i].name)
      }
    })
    healthCount().then(res => {
      const list = res.body.data
      this.healthOptions.xAxis.categories = []
      for (let i = 0; i < list.length; i++) {
        this.healthOptions.series[0].data.push(list[i].healthy)
        this.healthOptions.series[1].data.push(list[i].good)
        this.healthOptions.series[2].data.push(list[i].unhealthy)
        this.healthOptions.xAxis.categories.push(list[i].name)
      }
      console.log(this.healthOptions.series[0].data)
      console.log(this.healthOptions.xAxis.categories)
    })
  }
}
</script>

<style scoped>

</style>
