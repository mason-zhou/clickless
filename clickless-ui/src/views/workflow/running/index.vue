<template>
  <div class="app-container">
    <!-- 条件查询 -->
    <el-form :inline="true" :model="query" size="small">
      <el-form-item label="流程名称">
        <el-input v-model.trim="query.processName" placeholder="请输入流程名称"></el-input>
      </el-form-item>
      <el-form-item label="流程发起人">
        <el-input v-model.trim="query.proposer" placeholder="请输入流程发起人"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" type="primary" @click="queryData">查询</el-button>
        <el-button icon="el-icon-refresh" @click="reload">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- stripe 带斑马纹 -->
    <el-table :data="list" stripe border style="width: 100%">
      <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
      <el-table-column align="center" prop="proposer" label="流程发起人" width="180"></el-table-column>
      <el-table-column align="center" prop="processInstanceName" label="流程名称" min-width="160"></el-table-column>
      <el-table-column align="center" label="当前环节" min-width="350">
        <template slot-scope="{row}">
          <span v-html="row.currTaskInfo"></span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="startTime" label="开始时间" min-width="160"></el-table-column>
      <el-table-column align="center" prop="processStatus" label="流程状态" width="150"></el-table-column>
      <el-table-column align="center" prop="processInstanceId" label="流程实例ID" min-width="180"></el-table-column>
      <el-table-column align="center" prop="processKey" label="流程标识Key" width="150"></el-table-column>
      <el-table-column align="center" label="版本号" width="90">
        <template slot-scope="{row}"> v{{ row.version }}.0</template>
      </el-table-column>
      <el-table-column align="center" label="操作" fixed="right" width="230">
        <template slot-scope="{row}">
          <el-popconfirm :title="row.processStatus == '已启动' ?
                        `暂停后，此流程实例的任务不允许审批，您确定暂停【${row.processInstanceName|| row.processKey}】吗？`:
                        `启动后，此流程实例的任务允许审批，您确定启动【${row.processInstanceName|| row.processKey}】吗？`"
                         @confirm="clickUpdateProcInstState(row.processInstanceId)">
            <el-button v-if="row.processStatus == '已启动'" slot="reference" type="text">暂停</el-button>
            <el-button v-else slot="reference" type="text">启动</el-button>
          </el-popconfirm>
          <el-popconfirm :title="`您确定作废【${row.processInstanceName || row.processKey}】吗？`"
                         @confirm="clickDelete(row.processInstanceId)">
            <el-button slot="reference" type="text">作废</el-button>
          </el-popconfirm>
          <el-button @click="clickProcessHistory(row)" type="text">审批历史</el-button>
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
import api from '@/api/workflow/instance'

import History from '@/components/Process/History'

export default {
  name: 'Running', // 和对应路由表中配置的name值一致

  components: {History},

  data() {
    return {
      list: [
        {
          "processInstanceId": "7d0d444e",
          "processStatus": "已启动",
          "proposer": "张三",
          "businessKey": "1414149",
          "processKey": "leaveProcess",
          "startTime": "2021-07-11 17:22:18",
          "currTaskInfo": "任务名【直接领导审批】，办理人【张三】<br>",
          "version": 2,
          "processInstanceName": "请假流程"
        },
        {
          "processInstanceId": "50a0eb91",
          "processStatus": "已启动",
          "proposer": "李四",
          "businessKey": "1414147",
          "processKey": "leaveProcess",
          "startTime": "2021-07-11 16:59:35",
          "currTaskInfo": "任务名【部门经理审批】，办理人【李四】<br>任务名【直接领导审批】，办理人【张三】<br>",
          "version": 2,
          "processInstanceName": "请假流程"
        },
      ],
      page: {
        current: 1,
        size: 10,
        total: 0
      },
      query: {}, // 查询条件
      row: {}, //点击的行数据
    }
  },

  created() {
    this.fetchData()
  },

  methods: {
    // 分页条件查询列表
    async fetchData() {
      const {data} = await api.getProcInstListRunning(this.query, this.page.current, this.page.size)
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
    },

    // 挂起或激活单个流程实例
    async clickUpdateProcInstState(procInstId) {
      // 发送更新请求
      const {code} = await api.updateProcInstState(procInstId)
      if (code === 200) {
        this.$message.success('操作成功')
        // 刷新列表数据
        this.fetchData()
      }
    },

    // 作废流程实例
    async clickDelete(procInstId) {
      // 发送作废请求
      const {code} = await api.deleteProcInst(procInstId)
      if (code === 200) {
        this.$message.success('作废成功')
        // 刷新列表数据
        this.fetchData()
      }
    },

  }
}
</script>
