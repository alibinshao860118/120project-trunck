<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <!-- <el-form-item label="手机号" prop="sickerMobile">
        <el-input
          v-model="queryParams.sickerMobile"
          placeholder="请输入手机号"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->

      <el-form-item label="导入时间">
        <el-date-picker
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="导入时间" prop="dataRemark">
        <el-select
          v-model="dataRemark"
          placeholder="请选择"
          style="width: 100%"
        >
          <el-option
            v-for="item in dataRemarkOption"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport('1')"
          v-hasPermi="['system:import:import']"
          >智能外呼导入</el-button
        >
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport('2')"
          v-hasPermi="['system:import:import']"
          >入院信息导入</el-button
        >
      </el-col>

      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="primary"-->
      <!--          plain-->
      <!--          icon="el-icon-plus"-->
      <!--          size="mini"-->
      <!--          @click="handleAdd"-->
      <!--          v-hasPermi="['system:import:add']"-->
      <!--        >新增</el-button>-->
      <!--      </el-col>-->

      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="success"-->
      <!--          plain-->
      <!--          icon="el-icon-edit"-->
      <!--          size="mini"-->
      <!--          :disabled="single"-->
      <!--          @click="handleUpdate"-->
      <!--          v-hasPermi="['system:import:edit']"-->
      <!--        >修改</el-button>-->
      <!--      </el-col>-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="danger"-->
      <!--          plain-->
      <!--          icon="el-icon-delete"-->
      <!--          size="mini"-->
      <!--          :disabled="multiple"-->
      <!--          @click="handleDelete"-->
      <!--          v-hasPermi="['system:import:remove']"-->
      <!--        >删除</el-button>-->
      <!--      </el-col>-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="warning"-->
      <!--          plain-->
      <!--          icon="el-icon-download"-->
      <!--          size="mini"-->
      <!--          @click="handleExport"-->
      <!--          v-hasPermi="['system:import:export']"-->
      <!--        >导出</el-button>-->
      <!--      </el-col>-->
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="importList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        label="导入ID"
        align="center"
        prop="importId"
        v-if="false"
      />
      <el-table-column
        label="患者ID"
        align="center"
        prop="sickerId"
        v-if="false"
      />
      <el-table-column label="编号" align="center" prop="sickerNum" />
      <el-table-column label="患者手机号" align="center" prop="sickerMobile" />
      <el-table-column label="患者身份证" align="center" prop="identityCard" />
      <el-table-column label="呼车时间" align="center" prop="callTime" />
      <el-table-column label="导入时间" align="center" prop="createTime" />
      <el-table-column label="数据来源" align="center" prop="dataRemark" />
      <!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
      <!--        <template slot-scope="scope">-->
      <!--          <el-button-->
      <!--            size="mini"-->
      <!--            type="text"-->
      <!--            icon="el-icon-edit"-->
      <!--            @click="handleUpdate(scope.row)"-->
      <!--            v-hasPermi="['system:import:edit']"-->
      <!--          >修改</el-button>-->
      <!--          <el-button-->
      <!--            size="mini"-->
      <!--            type="text"-->
      <!--            icon="el-icon-delete"-->
      <!--            @click="handleDelete(scope.row)"-->
      <!--            v-hasPermi="['system:import:remove']"-->
      <!--          >删除</el-button>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改到达医院导入对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="患者ID" prop="sickerId">
          <el-input v-model="form.sickerId" placeholder="请输入患者ID" />
        </el-form-item>
        <el-form-item label="编号" prop="sickerNum">
          <el-input v-model="form.sickerNum" placeholder="请输入编号" />
        </el-form-item>
        <el-form-item label="患者手机号" prop="sickerMobile">
          <el-input
            v-model="form.sickerMobile"
            placeholder="请输入患者手机号"
          />
        </el-form-item>
        <el-form-item label="患者身份证" prop="identityCard">
          <el-input
            v-model="form.identityCard"
            placeholder="请输入患者身份证"
          />
        </el-form-item>
        <el-form-item label="呼车时间" prop="callTime">
          <el-input v-model="form.callTime" placeholder="请输入呼车时间" />
        </el-form-item>
        <el-form-item label="数据来源" prop="dataRemark">
          <el-input v-model="form.dataRemark" placeholder="请输入数据来源" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 用户导入对话框 -->
    <el-dialog
      :title="upload.title"
      :visible.sync="upload.open"
      width="400px"
      append-to-body
    >
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?type=' + upload.type"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <!--          <div class="el-upload__tip" slot="tip">-->
          <!--            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的用户数据-->
          <!--          </div>-->
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link
            type="primary"
            :underline="false"
            style="font-size: 12px; vertical-align: baseline"
            @click="importTemplate"
            >下载模板</el-link
          >
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listImport,
  getImport,
  delImport,
  addImport,
  updateImport,
} from "@/api/system/import";
import { getToken } from "@/utils/auth";

export default {
  name: "Import",
  data() {
    return {
      // 导入时间
      dataRemark: "",
      // 导入时间下拉框
      dataRemarkOption: [
        {
          value: "",
          label: "请选择",
        },
        {
          value: "智能外呼",
          label: "智能外呼",
        },
        {
          value: "入院",
          label: "入院",
        },
      ],
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
      // 到达医院导入表格数据
      importList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,

      // 用户导入参数
      upload: {
        type: "",
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/import/importData",
      },

      // 日期范围
      dateRange: [],

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sickerId: null,
        sickerNum: null,
        sickerMobile: null,
        identityCard: null,
        callTime: null,
        dataRemark: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询到达医院导入列表 */
    getList() {
      this.loading = true;
      //listImport(this.queryParams).then(response => {
      listImport(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.importList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        importId: null,
        sickerId: null,
        sickerNum: null,
        sickerMobile: null,
        identityCard: null,
        callTime: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        dataRemark: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.queryParams.dataRemark = this.dataRemark;
      console.log(this.queryParams);
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.importId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加到达医院导入";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const importId = row.importId || this.ids;
      getImport(importId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改到达医院导入";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.importId != null) {
            updateImport(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addImport(this.form).then((response) => {
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
      const importIds = row.importId || this.ids;
      this.$modal
        .confirm('是否确认删除到达医院导入编号为"' + importIds + '"的数据项？')
        .then(function () {
          return delImport(importIds);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "system/import/export",
        {
          ...this.queryParams,
        },
        `import_${new Date().getTime()}.xlsx`
      );
    },

    /** 导入按钮操作 */
    handleImport(type) {
      if (type == "1") {
        this.upload.title = "智能外呼导入";
        this.upload.type = "1";
      } else if (type == "2") {
        this.upload.title = "入院信息导入";
        this.upload.type = "2";
      }

      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      if (this.upload.type == "1") {
        this.download(
          "system/import/importTemplateOne",
          {},
          `import_template_${new Date().getTime()}.xlsx`
        );
      } else {
        this.download(
          "system/import/importTemplateTwo",
          {},
          `import_template_${new Date().getTime()}.xlsx`
        );
      }
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(
        "<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" +
          response.msg +
          "</div>",
        "导入结果",
        { dangerouslyUseHTMLString: true }
      );
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
  },
};
</script>
