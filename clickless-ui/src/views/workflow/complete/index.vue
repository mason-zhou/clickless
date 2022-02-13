<template>
  <div class="app-container">
    <!-- 条件查询 -->
    <el-form :inline="true" :model="query" size="mini">
      <el-form-item label="任务名称:">
        <el-input v-model.trim="query.taskName"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" type="primary" @click="queryData">查询</el-button>
        <el-button icon="el-icon-refresh" @click="reload">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- stripe 带斑马纹 -->
    <el-table :data="list" stripe border style="width: 100%">
      <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
      <el-table-column align="center" prop="taskName" label="任务名称" min-width="160"></el-table-column>
      <el-table-column align="center" label="所属流程" min-width="120">
        <template slot-scope="{row}">
          {{ `${row.processName} - v${row.version}` }}
        </template>
      </el-table-column>
      <el-table-column align="center" prop="proposer" label="流程发起人" width="180"></el-table-column>
      <el-table-column align="center" prop="taskStartTime" label="任务开始日期" width="180"></el-table-column>
      <el-table-column align="center" prop="taskEndTime" label="任务结束日期" width="180"></el-table-column>
      <el-table-column align="center" label="操作" fixed="right" width="260">
        <template slot-scope="{row}">
          <el-button type="text" @click="clickProcessHistory(row)">审批历史</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="page.current"
      :page-sizes="[10, 20, 50]"
      :page-size="page.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="page.total">
    </el-pagination>

    <!-- 审批历史 -->
    <history ref="historyRef" :businessKey="row.businessKey" :processInstanceId="row.processInstanceId"></history>
  </div>
</template>
<script>
import api from '@/api/workflow/task'
import History from '@/components/Process/History'

export default {
  name: 'Await', // 和对应路由表中配置的name值一致

  components: {History},

  data() {
    return {
      list: [
        {
          "taskId": "leave2:3:62509",
          "taskName": "总经理审批",
          "processName": "请假",
          "version": 3,
          "proposer": "小梦",
          "taskStartTime": "2020-01-24 17:07:16",
          "taskEndTime": "2021-04-15 15:37:36"
        },
        {
          "taskId": "test:1:60011",
          "taskName": "人事审批",
          "processName": "休假",
          "version": 3,
          "proposer": "小学",
          "taskStartTime": "2020-12-24 11:07:10",
          "taskEndTime": "2021-08-15 15:30:36"
        },
      ],
      page: {
        current: 1,
        size: 10,
        total: 0
      },
      query: {}, // 查询条件
      row: {}, // 点击的行数据
      processInstanceId: null,
    }
  },

  created() {
    this.fetchData()
  },

  methods: {
    // 分页条件查询
    async fetchData() {
      const {data} = await api.getCompleteTaskList(this.query, this.page.current, this.page.size)
      this.list = data.records
      this.page.total = data.total
    },

    // 当每页显示多少条改变后触发
    handleSizeChange(val) {
      this.page.size = val
      this.fetchData()
    },
    // 切换页码触发
    handleCurrentChange(val) {
      this.page.current = val
      this.fetchData()
    },

    // 条件查询方法
    queryData() {
      this.page.current = 1
      this.fetchData()
    },

    // 刷新重置
    reload() {
      this.query = {}
      this.fetchData()
    },

    // 点击审批历史
    clickProcessHistory(row) {
      this.row = row
      this.$refs.historyRef.visible = true
    }
  }
}
</script>
