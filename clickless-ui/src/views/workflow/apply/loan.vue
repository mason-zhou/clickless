<template>
  <div class="app-container">
    <!-- 条件查询 -->
    <el-form :inline="true" size="small">
      <el-form-item label="用途">
        <el-input v-model.trim="queryParams.purpose" placeholder="请输入用途"></el-input>
      </el-form-item>
      <el-form-item label="状态:">
        <el-select v-model="queryParams.status" clearable placeholder="请选择">
          <el-option v-for="item in processStatus" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" type="primary" @click="queryData">查询</el-button>
        <el-button icon="el-icon-refresh" @click="reload">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row style="margin-bottom:20px">
      <el-button icon="el-icon-plus" size="small" type="primary" @click="clickShowForm('新增')">新增申请</el-button>
    </el-row>

    <!-- stripe 带斑马纹 -->
    <el-table :data="list" stripe border style="width: 100%">
      <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
      <el-table-column align="center" prop="userId" label="借款人" min-width="90"></el-table-column>
      <el-table-column align="center" prop="money" label="借款金额" width="120"></el-table-column>
      <el-table-column align="center" prop="loanDate" label="借款日期" width="160"></el-table-column>
      <el-table-column align="center" prop="purpose" label="借款用途" min-width="160"></el-table-column>
      <el-table-column align="center" prop="remark" label="备注" min-width="160"></el-table-column>
      <el-table-column align="center" prop="statusStr" label="业务状态" width="90">
        <template slot-scope="{row}">
          <el-tag effect="plain">{{ row.statusStr }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="createTime" label="创建日期" width="160"></el-table-column>
      <el-table-column align="center" label="操作" fixed="right" width="220">
        <template slot-scope="{row}">
          <!-- 0已撤回，1未提交，2处理中，3已完成 -->
          <el-button v-if="row.status == 0 || row.status == 1" @click="clickShowForm('编辑', row)" type="text">编辑
          </el-button>
          <el-button v-if="row.status == 2 || row.status == 3 || row.status == 5" type="text"
                     @click="clickShowForm('详情', row)">详情
          </el-button>
          <el-button v-if="row.status == 2" @click="clickCancelProcess(row)" type="text">撤回</el-button>
          <el-button v-if="row.status == 0" @click="clickSubmitApply(row)" type="text">重新申请</el-button>
          <el-button v-if="row.status == 1" @click="clickSubmitApply(row)" type="text">提交申请</el-button>
          <el-button v-if="row.status == 2 || row.status == 3 || row.status == 4" @click="clickProcessHistory(row)"
                     type="text">审批历史
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="queryParams.pageNum"
      :page-sizes="[10, 20, 50]"
      :page-size="queryParams.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>

    <!-- 请假表单 -->
    <el-dialog :title="operate" :visible.sync="formVisible" @close="closeForm(false)" width="1000px" destroy-on-close>
      <loan-form :operate="operate" :businessKey="row.id" @close="closeForm"></loan-form>
    </el-dialog>

    <!-- 提交申请 -->
    <submit-apply ref="sumbitApplyRef" :row="row"></submit-apply>

    <!-- 撤回申请 -->
    <cancel-apply ref="cancelRef" :businessKey="row.id" :procInstId="row.processInstanceId"></cancel-apply>

    <!-- 审批历史 -->
    <history ref="historyRef" :businessKey="row.id" :processInstanceId="row.processInstanceId"></history>
  </div>
</template>
<script>
// 流程状态
const processStatus = [
  {value: 0, label: '已撤回'},
  {value: 1, label: '待提交'},
  {value: 2, label: '处理中'},
  {value: 3, label: '已完成'},
  {value: 4, label: '已作废'},
  {value: 5, label: '已删除'},
]

import LoanForm from '@/components/Process/Form/LoanForm'

import SubmitApply from './components/SubmitApply'
import CancelApply from './components/CancelApply'
import History from '@/components/Process/History'

import api from '@/api/workflow/loan'

export default {
  name: 'Leave', // 和对应路由表中配置的name值一致
  components: {LoanForm, SubmitApply, CancelApply, History},

  data() {
    return {
      processStatus, // 流程状态
      list: [
        {}
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        purpose: undefined,
        status: undefined
      },
      total: 0, //总条数
      operate: '新增', // 操作标识：新增，编辑，详情
      formVisible: false, // 显示隐藏业务表单
      row: {}, //点击的行数据

      definitionId: null, // 流程定义id
      processInstanceId: null, // 流程图
    }
  },

  created() {
    this.fetchData()
  },

  methods: {
    // 分页条件查询列表
    async fetchData() {
      const {rows, total} = await api.getList(this.queryParams)
      this.list = rows
      this.total = total
    },

    // 当每页显示多少条改变后触发
    handleSizeChange(val) {
      this.queryParams.pageSize = val
      this.fetchData()
    },

    // 切换页码触发
    handleCurrentChange(val) {
      this.queryParams.pageNum = val
      this.fetchData()
    },

    // 条件查询方法
    queryData() {
      this.queryParams.pageNum = 1
      this.fetchData()
    },

    // 刷新重置
    reload() {
      this.queryParams.purpose = null
      this.queryParams.status = null
      this.fetchData()
    },

    // 点击显示表单
    clickShowForm(operate, row = {}) {
      this.operate = operate
      this.row = row
      this.formVisible = true
    },

    // 关闭表单
    closeForm(refresh) {
      // 清空点击数据
      this.row = {}
      // 隐藏
      this.formVisible = false
      // 刷新列表
      if (refresh) {
        this.fetchData()
      }
    },

    // 提交申请
    clickSubmitApply(row) {
      this.row = row
      this.$refs.sumbitApplyRef.visible = true
    },

    // 点击审批进度
    clickProcessHistory(row) {
      this.row = row
      this.$refs.historyRef.visible = true
    },

    // 撤回申请
    clickCancelProcess(row) {
      this.row = row
      this.$refs.cancelRef.visible = true
    },

  }
}
</script>
