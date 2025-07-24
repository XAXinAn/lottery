<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="信用卡账号" prop="acctnbr">
        <el-input
          v-model="queryParams.acctnbr"
          placeholder="请输入信用卡账号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="交易日期" prop="inpDate">
        <el-input
          v-model="queryParams.inpDate"
          placeholder="请输入交易日期"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="交易时间" prop="inpTime">
        <el-input
          v-model="queryParams.inpTime"
          placeholder="请输入交易时间"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="交易序号" prop="xtranno">
        <el-input
          v-model="queryParams.xtranno"
          placeholder="请输入交易序号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="交易金额" prop="billAmt">
        <el-input
          v-model="queryParams.billAmt"
          placeholder="请输入交易金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="支行号" prop="org1Code">
        <el-input
          v-model="queryParams.org1Code"
          placeholder="请输入支行号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="网点号" prop="orgNo">
        <el-input
          v-model="queryParams.orgNo"
          placeholder="请输入网点号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户内码(加密)" prop="custIsn">
        <el-input
          v-model="queryParams.custIsn"
          placeholder="请输入客户内码(加密)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="卡片状态" prop="cardStatusTxt">
        <el-input
          v-model="queryParams.cardStatusTxt"
          placeholder="请输入卡片状态"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['lottery:transactions:add']"
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
          v-hasPermi="['lottery:transactions:edit']"
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
          v-hasPermi="['lottery:transactions:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['lottery:transactions:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-gift"
          size="mini"
          @click="handleLottery"
          v-hasPermi="['lottery:transactions:lottery']"
        >抽奖</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="transactionsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="信用卡账号" align="center" prop="acctnbr" />
      <el-table-column label="交易日期" align="center" prop="inpDate" />
      <el-table-column label="交易时间" align="center" prop="inpTime" />
      <el-table-column label="交易序号" align="center" prop="xtranno" />
      <el-table-column label="交易金额" align="center" prop="billAmt" />
      <el-table-column label="支行号" align="center" prop="org1Code" />
      <el-table-column label="网点号" align="center" prop="orgNo" />
      <el-table-column label="客户内码(加密)" align="center" prop="custIsn" />
      <el-table-column label="卡片状态" align="center" prop="cardStatusTxt" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['lottery:transactions:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['lottery:transactions:remove']"
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

    <!-- 添加或修改信用卡交易记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="信用卡账号" prop="acctnbr">
          <el-input v-model="form.acctnbr" placeholder="请输入信用卡账号" />
        </el-form-item>
        <el-form-item label="交易日期" prop="inpDate">
          <el-input v-model="form.inpDate" placeholder="请输入交易日期" />
        </el-form-item>
        <el-form-item label="交易时间" prop="inpTime">
          <el-input v-model="form.inpTime" placeholder="请输入交易时间" />
        </el-form-item>
        <el-form-item label="交易序号" prop="xtranno">
          <el-input v-model="form.xtranno" placeholder="请输入交易序号" />
        </el-form-item>
        <el-form-item label="交易金额" prop="billAmt">
          <el-input v-model="form.billAmt" placeholder="请输入交易金额" />
        </el-form-item>
        <el-form-item label="支行号" prop="org1Code">
          <el-input v-model="form.org1Code" placeholder="请输入支行号" />
        </el-form-item>
        <el-form-item label="网点号" prop="orgNo">
          <el-input v-model="form.orgNo" placeholder="请输入网点号" />
        </el-form-item>
        <el-form-item label="客户内码(加密)" prop="custIsn">
          <el-input v-model="form.custIsn" placeholder="请输入客户内码(加密)" />
        </el-form-item>
        <el-form-item label="卡片状态" prop="cardStatusTxt">
          <el-input v-model="form.cardStatusTxt" placeholder="请输入卡片状态" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 中奖信息列表对话框 -->
    <el-dialog :title="'中奖信息列表'" :visible.sync="lotteryDialogVisible" width="1500px">
      <el-table :data="lotteryList">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="信用卡账号" align="center" prop="acctnbr" />
        <el-table-column label="交易日期" align="center" prop="inpDate" />
        <el-table-column label="交易时间" align="center" prop="inpTime" />
        <el-table-column label="交易序号" align="center" prop="xtranno" />
        <el-table-column label="交易金额" align="center" prop="billAmt" />
        <el-table-column label="支行号" align="center" prop="org1Code" />
        <el-table-column label="网点号" align="center" prop="orgNo" />
        <el-table-column label="客户内码(加密)" align="center" prop="custIsn" />
        <el-table-column label="卡片状态" align="center" prop="cardStatusTxt" />
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="lotteryDialogVisible = false">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listTransactions, getTransactions, delTransactions, addTransactions, updateTransactions, lotteryTransactions } from "@/api/lottery/transactions"

export default {
  name: "Transactions",
  data() {
    return {
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
      // 信用卡交易记录表格数据
      transactionsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        acctnbr: null,
        inpDate: null,
        inpTime: null,
        xtranno: null,
        billAmt: null,
        org1Code: null,
        orgNo: null,
        custIsn: null,
        cardStatusTxt: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      // 中奖信息列表对话框是否显示
      lotteryDialogVisible: false,
      // 中奖信息列表
      lotteryList: []
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询信用卡交易记录列表 */
    getList() {
      this.loading = true
      listTransactions(this.queryParams).then(response => {
        this.transactionsList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        acctnbr: null,
        inpDate: null,
        inpTime: null,
        xtranno: null,
        billAmt: null,
        org1Code: null,
        orgNo: null,
        custIsn: null,
        cardStatusTxt: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.acctnbr)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加信用卡交易记录"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const acctnbr = row.acctnbr || this.ids
      getTransactions(acctnbr).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改信用卡交易记录"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.acctnbr != null) {
            updateTransactions(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addTransactions(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const acctnbrs = row.acctnbr || this.ids
      this.$modal.confirm('是否确认删除信用卡交易记录编号为"' + acctnbrs + '"的数据项？').then(function() {
        return delTransactions(acctnbrs)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('lottery/transactions/export', {
        ...this.queryParams
      }, `transactions_${new Date().getTime()}.xlsx`)
    },
    /** 抽奖按钮操作 */
    handleLottery() {
      this.loading = true;
      lotteryTransactions().then(response => {
        this.loading = false;
        this.lotteryList = response.data;
        this.lotteryDialogVisible = true;
      });
    }
  }
}
</script>
