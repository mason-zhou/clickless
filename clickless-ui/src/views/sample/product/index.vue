<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="商品编号" prop="productNo">
        <el-input
          v-model="queryParams.productNo"
          placeholder="请输入商品编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品名称" prop="productName">
        <el-input
          v-model="queryParams.productName"
          placeholder="请输入商品名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品重量" prop="weight">
        <el-input
          v-model="queryParams.weight"
          placeholder="请输入商品重量"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上市日期">
        <el-date-picker
          v-model="daterangeLaunchDate"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="适用性别" prop="sex">
        <el-select v-model="queryParams.sex" placeholder="请选择适用性别" clearable size="small">
          <el-option
            v-for="dict in dict.type.sys_user_sex"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否禁用" prop="isDisabled">
        <el-select v-model="queryParams.isDisabled" placeholder="请选择是否禁用" clearable size="small">
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="逻辑删除" prop="isDeleted">
        <el-input
          v-model="queryParams.isDeleted"
          placeholder="请输入逻辑删除"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <click-less-table
          ref="tableLayout"
          :layoutStyle="{ height: 'calc( 100vh - 262px)'}"
          :autoReq="true"
          :pageKeyValue="pageKeyValue"
          :handleConfig="handleConfig"
          :getData="getData"
          :getPageDataBefor="getPageDataBefor"
          :showAllCheck="false"
          :showCheck="true"
          :showIndex="true"
          :getFilterTableSelect="getFilterTableSelect"
          :getFilterTableSelectBefor="getfilterTableSelectBefor"
          @checkChange="handleSelectionChange">
        <el-row :gutter="10" class="mb8" slot="buttons">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['sample:product:add']"
            >新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              plain
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['sample:product:edit']"
            >修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['sample:product:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['sample:product:export']"
            >导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="handleQuery(true)"></right-toolbar>
        </el-row>
    </click-less-table>
    <!--
    <el-table v-loading="loading" :data="productList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="代理主键" align="center" prop="id" />
      <el-table-column label="商品编号" align="center" prop="productNo" />
      <el-table-column label="商品名称" align="center" prop="productName" />
      <el-table-column label="商品重量" align="center" prop="weight" />
      <el-table-column label="上市日期" align="center" prop="launchDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.launchDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="适用性别" align="center" prop="sex">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.sex"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否禁用" align="center" prop="isDisabled">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.isDisabled"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['sample:product:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['sample:product:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    -->
    <!-- 添加或修改商品数据对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="商品编号" prop="productNo">
          <el-input v-model="form.productNo" placeholder="请输入商品编号" />
        </el-form-item>
        <el-form-item label="商品名称" prop="productName">
          <el-input v-model="form.productName" placeholder="请输入商品名称" />
        </el-form-item>
        <el-form-item label="商品重量" prop="weight">
          <el-input v-model="form.weight" placeholder="请输入商品重量" />
        </el-form-item>
        <el-form-item label="上市日期" prop="launchDate">
          <el-date-picker clearable size="small"
            v-model="form.launchDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择上市日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="适用性别">
          <el-radio-group v-model="form.sex">
            <el-radio
              v-for="dict in dict.type.sys_user_sex"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否禁用" prop="isDisabled">
          <el-select v-model="form.isDisabled" placeholder="请选择是否禁用">
            <el-option
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="逻辑删除" prop="isDeleted">
          <el-input v-model="form.isDeleted" placeholder="请输入逻辑删除" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listProduct, getProduct, delProduct, addProduct, updateProduct , getProductFieldEnums } from "@/api/sample/product";
import { getfilterTableSelectBefor,getPageDataBefor } from "@/clickLess-ui-config.js"
export default {
  name: "Product",
  dicts: ['sys_user_sex', 'sys_normal_disable'],
  data() {
    return {
      //表格数据接口
      getData:listProduct,
      //表头筛选项数据接口
      getFilterTableSelect:getProductFieldEnums,
      //页面标识
      pageKeyValue:{
        modelKey:'SampleProduct'
      },
      //尾部按钮配置
      handleConfig:{
        width:'200px',
        list:[
            {
                icon:null,
                label:'修改',
                method:this.handleUpdate,
                visable:this.$HasPermiFunc(['sample:product:edit']),
            },{
                icon:null,
                label:'删除',
                method:this.handleDelete,
                visable:this.$HasPermiFunc(['sample:product:remove']),
                style:(row)=>{ return {"color":"red"}},
            },
        ]
      },
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 商品数据表格数据
      productList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 逻辑删除时间范围
      daterangeLaunchDate: [],
      // 逻辑删除时间范围
      daterangeCreateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        productNo: null,
        productName: null,
        weight: null,
        launchDate: null,
        sex: null,
        isDisabled: null,
        isDeleted: null
      },
      //保留-接口请求参数
      reqQueryParams:{},
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    // this.getList(); 组件自动查询
  },
  methods: {
    /** 查询商品数据列表 -- 失效 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeLaunchDate && '' != this.daterangeLaunchDate) {
        this.queryParams.params["beginLaunchDate"] = this.daterangeLaunchDate[0];
        this.queryParams.params["endLaunchDate"] = this.daterangeLaunchDate[1];
      }
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params["beginCreateTime"] = this.daterangeCreateTime[0];
        this.queryParams.params["endCreateTime"] = this.daterangeCreateTime[1];
      }
      listProduct(this.queryParams).then(response => {
        this.productList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        productNo: null,
        productName: null,
        weight: null,
        launchDate: null,
        sex: 0,
        createTime: null,
        isDisabled: null,
        isDeleted: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery(remain) {
      // this.queryParams.pageNum = 1;
      // this.getList();
        if(remain === true){
            this.$refs.tableLayout.getListData()
        }else{
            //搜索按钮和重置按钮的格式化参数

                                                                                                                                                                this.queryParams.params = {};
                                                                                                                                                                                    if (null != this.daterangeLaunchDate && '' != this.daterangeLaunchDate) {
                        this.queryParams.params["beginLaunchDate"] = this.daterangeLaunchDate[0];
                        this.queryParams.params["endLaunchDate"] = this.daterangeLaunchDate[1];
                    }
                                                                                            if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
                        this.queryParams.params["beginCreateTime"] = this.daterangeCreateTime[0];
                        this.queryParams.params["endCreateTime"] = this.daterangeCreateTime[1];
                    }
                                                                                    
            this.$refs.tableLayout.screenData = this.queryParams
            this.$refs.tableLayout.sortData = []
            this.$refs.tableLayout.page.pageNum = 1//{pageNum: 1, pageSize: 20}
            this.$refs.tableLayout.clearHeaderFilterData()
        }
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeLaunchDate = [];
      this.daterangeCreateTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加商品数据";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getProduct(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改商品数据";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateProduct(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProduct(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除商品数据编号为"' + ids + '"的数据项？').then(function() {
        return delProduct(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('sample/product/export', {
        // ...this.queryParams
          ...this.reqQueryParams
      }, `product_${new Date().getTime()}.xlsx`)
    },
    //获取表格数据
    getPageDataBefor(query){
        return getPageDataBefor(this,query)
    },
    //表头筛选项获取
    getfilterTableSelectBefor(query,column){
        return getfilterTableSelectBefor(this,query,column)
    }
  }
};
</script>
