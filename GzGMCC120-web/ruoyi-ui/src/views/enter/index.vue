<template>
  <div class="app-container">
    <el-row :gutter="20" v-loading="loading">
      <!--用户数据-->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
        <el-form-item label="选择文件">
          <el-button
            type="info"
            plain
            icon="el-icon-upload2"
            size="mini"
            @click="handleImport"
            v-hasPermi="['system:user:import']"
            >导入</el-button
          >
        </el-form-item>

        <el-form-item label="患者名称" prop="patientName">
          <el-input
            v-model="patientName"
            placeholder="请输入患者名称"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="呼车日期">
          <el-date-picker
            v-model="carCallDate"
            type="date"
            placeholder="选择呼车日期"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="区域" prop="jurisdictionArea">
          <el-select
            v-model="jurisdictionArea"
            placeholder="请选择"
            style="width: 100%"
          >
            <el-option
              v-for="item in options"
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
            >查询</el-button
          >
          <el-button type="primary" size="mini" @click="handleAdd"
            >新增调派</el-button
          >
        </el-form-item>
      </el-form>
      <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark">
        <el-table-column label="" width="55" prop="operation">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              @click="handleAdd(scope.row)"
              v-hasPermi="['system:menu:add']"
              >详情</el-button
            >
          </template>
        </el-table-column>
        <el-table-column
          label="受理编号"
          show-overflow-tooltip
          width="120"
          prop="acceptNo"
        >
        </el-table-column>
        <el-table-column prop="carCallUnit" label="呼车单位名称" width="120">
        </el-table-column>
        <el-table-column prop="carCallDate" label="呼车日期"> </el-table-column>
        <el-table-column prop="carCallTime" label="呼车时间"> </el-table-column>
        <el-table-column prop="carCallContractPerson" label="呼车联系人">
        </el-table-column>
        <el-table-column prop="carCallContractNumber" label="呼车联系电话">
        </el-table-column>
        <el-table-column prop="patientName" label="病人姓名"> </el-table-column>
        <el-table-column prop="patientIdNumber" label="病人证件号">
        </el-table-column>
        <el-table-column prop="patientContractNumber" label="病人联系电话">
        </el-table-column>
        <el-table-column prop="sex" label="性别"> </el-table-column>
        <el-table-column prop="age" label="年龄"> </el-table-column>
        <el-table-column prop="caseSource" label="病例来源"> </el-table-column>
        <el-table-column prop="personIdentity" label="人员身份">
        </el-table-column>
        <el-table-column prop="nucleateResult" label="核酸检测结果">
        </el-table-column>
        <el-table-column prop="nucleateIsPositive" label="核酸检测是否阳性">
        </el-table-column>
        <el-table-column prop="newHighRiskFactor" label="高危因素">
        </el-table-column>
        <el-table-column prop="specialCondition" label="特殊病情">
        </el-table-column>
        <el-table-column prop="sentHospital" label="派出医院">
        </el-table-column>
        <el-table-column prop="licensePlateNumber" label="车牌号">
        </el-table-column>
        <el-table-column prop="cityEightConfirmTime" label="市八确认时间">
        </el-table-column>
        <el-table-column prop="pickUpAddress" label="换车地址">
        </el-table-column>
        <el-table-column prop="carCallArea" label="区域"> </el-table-column>
        <el-table-column prop="receiveHospital" label="接收医院">
        </el-table-column>
        <!-- <el-table-column prop="receivingHospitalName" label="接收医院(标准名)">
        </el-table-column> -->
        <el-table-column prop="createTime" label="录入时间"> </el-table-column>
        <el-table-column prop="createBy" label="录入员姓名"> </el-table-column>
        <el-table-column prop="dataSource" label="数据来源"> </el-table-column>
      </el-table>
      <div class="block">
        <el-pagination
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="pageSizes"
          :page-size="pageSize"
          @size-change="sizeChange"
          style="float: right"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        >
        </el-pagination>
      </div>
    </el-row>

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
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <!-- <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" />
            是否更新已经存在的用户数据
          </div> -->
          <span>仅允许导入xls、xlsx格式文件。</span>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 新增调派对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form
        ref="form"
        :model="form"
        :inline="true"
        :rules="windowRule"
        label-width="150px"
      >
        <el-row>
          <el-col :span="24">
            <el-form-item label="受理编号" prop="acceptNo">
              <el-input
                v-model="form.acceptNo"
                placeholder="必填，数字，10字以内"
                maxlength="10"
                @input="change($event)"
                :disabled="disabled"
              />
            </el-form-item>
            <el-form-item label="呼车单位" prop="carCallUnit">
              <el-select
                v-model="form.carCallUnit"
                placeholder="请选择"
                style="width: 100%"
                :disabled="disabled"
              >
                <el-option
                  v-for="item in callCarOptions"
                  :key="item.value"
                  :label="item.dict_value"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="呼车日期" prop="carCallDate">
              <el-date-picker
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择日期时间"
                v-model="form.carCallDate"
                :disabled="disabled"
                v-if="!disabled"
                style="width: 183px"
              >
              </el-date-picker>
              <el-input
                v-model="form.carCallDateTime"
                maxlength="30"
                @input="change($event)"
                :disabled="disabled"
                v-if="disabled"
                style="min-width: 540px"
              />
              <!-- <el-input v-model="form.callCarDay" @input="change($event)" /> -->
            </el-form-item>
            <el-form-item label="呼车时间" prop="carCallTime" v-if="!disabled">
              <el-time-picker
                v-model="form.carCallTime"
                format="HH:mm"
                value-format="HH:mm"
                placeholder=""
                style="width: 200px"
              >
              </el-time-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="呼车联系人" prop="carCallContractPerson">
              <el-input
                v-model="form.carCallContractPerson"
                maxlength="30"
                @input="change($event)"
                :disabled="disabled"
              />
            </el-form-item>
            <el-form-item label="呼车联系电话" prop="carCallContractNumber">
              <el-input
                v-model="form.carCallContractNumber"
                maxlength="11"
                @input="change($event)"
                :disabled="disabled"
                style="width: 200px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="病人姓名" prop="patientName">
              <el-input
                v-model="form.patientName"
                maxlength="30"
                @input="change($event)"
                :disabled="disabled"
              />
            </el-form-item>
            <el-form-item label="病人证件号码" prop="patientIdNumber">
              <el-input
                v-model="form.patientIdNumber"
                maxlength="18"
                @input="change($event)"
                :disabled="disabled"
                style="width: 200px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="性别" prop="sex">
              <el-radio-group v-model="form.sex">
                <el-radio :disabled="disabled" label="0">男</el-radio>
                <el-radio :disabled="disabled" label="1">女</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="年龄" prop="age">
              <el-input
                v-model="form.age"
                :disabled="disabled"
                @input="change($event)"
              />
            </el-form-item>
            <el-form-item label="病人联系电话" prop="patientContractNumber">
              <el-input
                :disabled="disabled"
                v-model="form.patientContractNumber"
                maxlength="11"
                @input="change($event)"
                style="min-width: 540px"
              />
            </el-form-item>
          </el-col>

          <el-col :span="24">
            <el-form-item label="病人来源" prop="caseSource">
              <el-radio-group v-model="form.caseSource">
                <el-radio :disabled="disabled" label="0">本土</el-radio>
                <el-radio :disabled="disabled" label="1">入境</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="人员身份" prop="personIdentity">
              <el-radio-group v-model="form.personIdentity">
                <el-radio :disabled="disabled" label="0">本人</el-radio>
                <el-radio :disabled="disabled" label="1">陪同人员</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="核酸检测结果" prop="nucleateResult">
              <el-radio-group v-model="form.nucleateResult">
                <el-radio :disabled="disabled" label="0">阴性</el-radio>
                <el-radio :disabled="disabled" label="1">阳性</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="核酸检测是否阳性" prop="nucleateIsPositive">
              <el-radio-group v-model="form.nucleateIsPositive">
                <el-radio :disabled="disabled" label="1">是</el-radio>
                <el-radio :disabled="disabled" label="0">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="高危因素" prop="epgHighRiskFactor">
              <el-input
                type="textarea"
                placeholder=""
                v-if="form.epgHighRiskFactor"
                v-model="form.epgHighRiskFactor"
                @input="change($event)"
                style="min-width: 540px; min-height: 80px"
                disabled
              >
              </el-input>
            </el-form-item>
            <el-form-item prop="newHighRiskFactor">
              <el-checkbox-group
                v-model="form.newHighRiskFactor"
                style="margin-left: 80px"
              >
                <el-checkbox
                  :label="item.dict_value"
                  :value="item.id"
                  :key="index"
                  v-for="(item, index) in highRiskFactorCheckBox"
                ></el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
          <el-col :span="24" class="textAreaWidth100">
            <el-form-item label="特殊病情" prop="specialCondition">
              <el-input
                type="textarea"
                placeholder=""
                v-model="form.specialCondition"
                maxlength="300"
                show-word-limit
                style="min-width: 540px; min-height: 80px"
                @input="change($event)"
              >
              </el-input>
            </el-form-item>
          </el-col>

          <el-col :span="24">
            <el-form-item label="呼车区域" prop="carCallArea">
              <el-radio-group
                v-model="form.carCallArea"
                size="mini"
                style="margin-left: 60px"
              >
                <el-radio
                  v-for="(item, index) in callAreaRadio"
                  :key="index"
                  :label="item.label"
                ></el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  addEnterData,
  getEnterData,
  editEnterData,
  getArray,
} from "@/api/system/menu";

import { getToken } from "@/utils/auth";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "User",
  dicts: ["sys_normal_disable", "sys_user_sex"],
  components: { Treeselect },
  data() {
    return {
      newHighRiskFactor: [],
      carCallTime: "",
      // 呼车日期
      carCallDate: "",
      // 患者名称
      patientName: "",
      // 区域
      jurisdictionArea: "",
      // 高危因素文本
      epgHighRiskFactor: "",
      // 当前页面
      currentPage: 1,
      // 分页器多少条每页
      pageSizes: [5, 10, 20, 50],
      // 每页显示条数
      pageSize: 5,
      // 表单参数
      form: {
        acceptNo: "",
        carCallUnit: "",
        callCarDayWin: "",
        carCallDate: "",
        callCarTime: "",
        carCallContractPerson: "",
        carCallContractNumber: "",
        patientName: "",
        patientIdNumber: "",
        sex: "",
        age: "",
        patientContractNumber: "",
        caseSource: "",
        personIdentity: "",
        nucleateResult: "",
        nucleateIsPositive: "",
        newHighRiskFactor: [],
        epgHighRiskFactor: "",
        specialCondition: "",
        carCallArea: "",
        callArea: "",
      },
      // 新增或编辑弹窗规则
      windowRule: {
        acceptNo: [
          { required: true, message: "受理编号不能为空", trigger: "blur" },
        ],
        carCallUnit: [
          { required: true, message: "呼车单位不能为空", trigger: "change" },
        ],
        carCallDate: [
          {
            required: true,
            message: "呼车日期不能为空",
            trigger: "change",
          },
        ],
        carCallTime: [
          { required: true, message: "呼车时间不能为空", trigger: "change" },
        ],
        carCallContractPerson: [
          { required: true, message: "呼车联系人不能为空", trigger: "blur" },
        ],
        carCallContractNumber: [
          { required: true, message: "呼车联系电话不能为空", trigger: "blur" },
          {
            min: 11,
            max: 11,
            message: "呼车联系电话长度为11位",
            trigger: "blur",
          },
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的呼车联系电话",
            trigger: "blur",
          },
        ],

        patientName: [
          { required: true, message: "病人姓名不能为空", trigger: "blur" },
        ],
        patientIdNumber: [
          { required: true, message: "病人证件号码不能为空", trigger: "blur" },
          // {
          //   min: 18,
          //   max: 18,
          //   message: "病人证件号码长度为18位",
          //   trigger: "blur",
          // },
          // {
          //   pattern:
          //     /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/,
          //   message: "请输入正确的病人证件号码",
          //   trigger: "blur",
          // },
        ],
        sex: [{ required: true, message: "性别不能为空", trigger: "change" }],
        age: [{ required: true, message: "年龄不能为空", trigger: "blur" }],
        patientContractNumber: [
          { required: true, message: "病人联系电话不能为空", trigger: "blur" },
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的病人联系电话",
            trigger: "blur",
          },
        ],
        caseSource: [
          {
            required: true,
            message: "病例来源不能为空",
            trigger: "change",
          },
        ],
        personIdentity: [
          {
            required: true,
            message: "人员身份不能为空",
            trigger: "change",
          },
        ],
        nucleateResult: [
          {
            required: true,
            message: "核酸检测结果不能为空",
            trigger: "change",
          },
        ],
        nucleateIsPositive: [
          {
            required: true,
            message: "核酸检测是否阳性不能为空",
            trigger: "change",
          },
        ],
        newHighRiskFactor: [
          {
            type: "array",
            required: true,
            message: "高危因素不能为空",
            trigger: "change",
          },
        ],
        specialCondition: [
          { required: true, message: "特殊病情不能为空", trigger: "blur" },
        ],
        carCallArea: [
          { required: true, message: "呼车区域不能为空", trigger: "change" },
        ],
      },
      // 呼车日期 弹窗
      callCarDayWin: "",
      // 呼车区域
      callArea: "1",
      callAreaRadio: [
        {
          value: "海珠区",
          label: "海珠区",
        },
        {
          value: "天河区",
          label: "天河区",
        },
        {
          value: "从化区",
          label: "从化区",
        },
        {
          value: "增城区",
          label: "增城区",
        },
        {
          value: "南沙区",
          label: "南沙区",
        },
        {
          value: "花都区",
          label: "花都区",
        },
        {
          value: "番禺区",
          label: "番禺区",
        },
        {
          value: "黄埔区",
          label: "黄埔区",
        },
        {
          value: "白云区",
          label: "白云区",
        },
        {
          value: "荔湾区",
          label: "荔湾区",
        },
        {
          value: "越秀区",
          label: "越秀区",
        },
        {
          value: "入境",
          label: "入境",
        },
      ],
      // 高危因素  多选框 大于60岁
      highRiskFactorCheckBox: [
        {
          label: ">60岁",
          value: "1",
        },
        {
          label: "<14岁",
          value: "2",
        },
        {
          label: "心脑血管疾病",
          value: "3",
        },
        {
          label: "糖尿病",
          value: "4",
        },
      ],

      // 核酸检测是否阳性
      isPositive: "",
      // 核酸检测结果
      nucleicAcidTestingResult: "",
      // 人员身份
      personnelIdentity: "",
      // 性别
      sex: "",
      // 病例来源
      perpeocase: "",
      // 是否编辑
      disabled: false,
      // 受理编号
      acceptanceNum: "",
      // 呼车单位选中值
      callCarUnit: "",
      // 呼车单位下拉框  后台拿值
      callCarOptions: [
        {
          value: "1",
          label: "1",
        },
        {
          value: "2",
          label: "2",
        },
      ],
      // 列表数据
      tableData: [],
      // 当前下拉框的值
      value: "",
      // 下拉框数据
      options: [
        {
          value: "请选择",
          label: "",
        },
        {
          value: "海珠区",
          label: "海珠区",
        },
        {
          value: "天河区",
          label: "天河区",
        },
        {
          value: "从化区",
          label: "从化区",
        },
        {
          value: "增城区",
          label: "增城区",
        },
        {
          value: "南沙区",
          label: "南沙区",
        },
        {
          value: "花都区",
          label: "花都区",
        },
        {
          value: "番禺区",
          label: "番禺区",
        },
        {
          value: "黄埔区",
          label: "黄埔区",
        },
        {
          value: "白云区",
          label: "白云区",
        },
        {
          value: "荔湾区",
          label: "荔湾区",
        },
        {
          value: "越秀区",
          label: "越秀区",
        },
        {
          value: "入境",
          label: "入境",
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
      total: 10,
      // 用户表格数据
      userList: null,
      // 弹出层标题
      title: "",
      // 部门树选项
      deptOptions: undefined,
      // 是否显示弹出层
      open: false,
      // 部门名称
      deptName: undefined,
      // 默认密码
      initPassword: undefined,
      // 日期范围
      dateRange: "",

      // 用户导入参数
      upload: {
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
        url: process.env.VUE_APP_BASE_API + "/postran/entry/importData",
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        patientName: undefined, //患者名称
        carCallArea: "", //区域
      },
    };
  },
  watch: {},
  created() {
    this.getList();
    this.getCallCar();
    this.gethighRiskFactor();
  },
  methods: {
    // 获取呼车单位下拉框
    getCallCar() {
      let that = this;
      getArray("?dictType=1").then((response) => {
        that.callCarOptions = response.data;
        console.log(that.callCarOptions);
      });
    },
    // 获取高危因素多选框
    gethighRiskFactor() {
      let that = this;
      getArray("?dictType=2").then((response) => {
        that.highRiskFactorCheckBox = response.data;
        console.log(that.highRiskFactorCheckBox);
      });
    },
    /** 查询用户列表 */
    getList() {
      console.log(this.jurisdictionArea);
      let searchArea = "";
      if (this.jurisdictionArea == "越秀区") {
        searchArea = "10";
      } else if (this.jurisdictionArea == "海珠区") {
        searchArea = "20";
      } else if (this.jurisdictionArea == "荔湾区") {
        searchArea = "30";
      } else if (this.jurisdictionArea == "天河区") {
        searchArea = "40";
      } else if (this.jurisdictionArea == "白云区") {
        searchArea = "50";
      } else if (this.jurisdictionArea == "黄埔区") {
        searchArea = "60";
      } else if (this.jurisdictionArea == "南沙区") {
        searchArea = "70";
      } else if (this.jurisdictionArea == "番禺区") {
        searchArea = "80";
      } else if (this.jurisdictionArea == "花都区") {
        searchArea = "90";
      } else if (this.jurisdictionArea == "增城区") {
        searchArea = "100";
      } else if (this.jurisdictionArea == "从化区") {
        searchArea = "110";
      } else if (this.jurisdictionArea == "入境") {
        searchArea = "120";
      } else {
        searchArea = "";
      }
      this.loading = true;
      var requestData =
        "?patientName=" +
        this.patientName +
        "&carCallDate=" +
        this.carCallDate +
        "&carCallArea=" +
        searchArea +
        "&pageNum=" +
        this.currentPage +
        "&pageSize=" +
        this.pageSize;
      getEnterData(requestData).then((response) => {
        this.total = response.total; //总数据条数
        console.log(response, "response");
        let data = response.rows;
        data = this.dataToData(data);
        this.tableData = data;
        this.loading = false;
      });
    },
    // 转换数据函数
    dataToData(data) {
      let newData = data;
      newData = this.areaSetVal(newData);
      newData = this.sourceVal(newData);
      newData = this.isArriveHospitalVal(newData);
      newData = this.personIdentityVal(newData);
      newData = this.nucleateResultVal(newData);
      newData = this.nucleateIsPositiveVal(newData);
      newData = this.sexVal(newData);
      newData = this.caseSourceVal(newData);
      return newData;
    },
    // 病例来源替换
    caseSourceVal(data) {
      for (let i = 0; i < data.length; i++) {
        if (data[i].caseSource == "0") {
          data[i].caseSource = "本地";
        } else if (data[i].caseSource == "1") {
          data[i].caseSource = "入境";
        }
      }
      return data;
    },
    // 区域值替换
    areaSetVal(data) {
      for (let i = 0; i < data.length; i++) {
        if (data[i].carCallArea == "10") {
          data[i].carCallArea = "越秀区";
        } else if (data[i].carCallArea == "20") {
          data[i].carCallArea = "海珠区";
        } else if (data[i].carCallArea == "30") {
          data[i].carCallArea = "荔湾区";
        } else if (data[i].carCallArea == "40") {
          data[i].carCallArea = "天河区";
        } else if (data[i].carCallArea == "50") {
          data[i].carCallArea = "白云区";
        } else if (data[i].carCallArea == "60") {
          data[i].carCallArea = "黄埔区";
        } else if (data[i].carCallArea == "70") {
          data[i].carCallArea = "南沙区";
        } else if (data[i].carCallArea == "80") {
          data[i].carCallArea = "番禺区";
        } else if (data[i].carCallArea == "90") {
          data[i].carCallArea = "花都区";
        } else if (data[i].carCallArea == "100") {
          data[i].carCallArea = "增城区";
        } else if (data[i].carCallArea == "110") {
          data[i].carCallArea = "从化区";
        } else if (data[i].carCallArea == "120") {
          data[i].carCallArea = "入境";
        }
      }
      return data;
    },
    // 数据来源替换
    sourceVal(data) {
      for (let i = 0; i < data.length; i++) {
        if (data[i].dataSource == "0") {
          data[i].dataSource = "防疫通";
        } else if (data[i].dataSource == "1") {
          data[i].dataSource = "新增";
        }
      }
      return data;
    },
    // 是否已到院替换
    isArriveHospitalVal(data) {
      for (let i = 0; i < data.length; i++) {
        if (data[i].isArriveHospital == "0") {
          data[i].isArriveHospital = "否";
        } else if (data[i].isArriveHospital == "1") {
          data[i].isArriveHospital = "是";
        }
      }
      return data;
    },
    // 人员身份替换
    personIdentityVal(data) {
      for (let i = 0; i < data.length; i++) {
        if (data[i].personIdentity == "0") {
          data[i].personIdentity = "本人";
        } else if (data[i].personIdentity == "1") {
          data[i].personIdentity = "陪同人员";
        }
      }
      return data;
    },
    // 核酸检测结果替换
    nucleateResultVal(data) {
      for (let i = 0; i < data.length; i++) {
        if (data[i].nucleateResult == "0") {
          data[i].nucleateResult = "阴性";
        } else if (data[i].nucleateResult == "1") {
          data[i].nucleateResult = "阳性";
        }
      }
      return data;
    },
    // 核酸检测是否阳性替换
    nucleateIsPositiveVal(data) {
      for (let i = 0; i < data.length; i++) {
        if (data[i].nucleateIsPositive == "0") {
          data[i].nucleateIsPositive = "否";
        } else if (data[i].nucleateIsPositive == "1") {
          data[i].nucleateIsPositive = "是";
        }
      }
      return data;
    },
    // 性别替换
    sexVal(data) {
      for (let i = 0; i < data.length; i++) {
        if (data[i].sex == "0") {
          data[i].sex = "男";
        } else if (data[i].sex == "1") {
          data[i].sex = "女";
        }
      }
      return data;
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.clearWinData();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      if (this.$refs.form) {
        this.$refs.form.resetFields();
      }
      this.clearWinData();
      console.log(row, "row");
      if (row != null && row.fdId) {
        this.form.acceptNo = row.acceptNo;
        this.form.carCallUnit = row.carCallUnit;
        this.form.carCallDate = row.carCallDate;
        this.form.carCallDateTime = row.carCallDate + " " + row.carCallTime;
        this.form.carCallContractPerson = row.carCallContractPerson;
        this.form.carCallContractNumber = row.carCallContractNumber;
        this.form.patientName = row.patientName;
        this.form.patientIdNumber = row.patientIdNumber;
        this.form.sex = row.sex == "男" ? "0" : "1";
        this.form.age = row.age;
        this.form.patientContractNumber = row.patientContractNumber;
        this.form.caseSource = row.caseSource == "本土" ? "0" : "1";
        this.form.personIdentity = row.personIdentity == "本人" ? "0" : "1";
        this.form.nucleateResult = row.nucleateResult == "阴性" ? "0" : "1";
        this.form.nucleateIsPositive =
          row.nucleateIsPositive == "否" ? "0" : "1";

        if (row.newHighRiskFactor && row.newHighRiskFactor.slice(-1) == ";") {
          row.newHighRiskFactor = row.newHighRiskFactor.slice(
            0,
            row.newHighRiskFactor.length - 1
          );
        }
        console.log(row.newHighRiskFactor);
        let checkList = [];
        if (row.newHighRiskFactor) {
          checkList = row.newHighRiskFactor.split(";");
        }
        this.form.newHighRiskFactor = checkList;
        this.form.epgHighRiskFactor = row.epgHighRiskFactor;
        this.form.specialCondition = row.specialCondition;
        this.form.carCallArea = row.carCallArea;
        this.form.fdId = row.fdId;
        this.disabled = true;
        this.title = "详情进入";
      } else {
        let checkList = [];
        this.form.newHighRiskFactor = checkList;
        this.disabled = false;
        this.form.fdId = undefined;
        this.title = "新增调派进入";
      }
      this.open = true;
    },
    /** 提交按钮 */
    submitForm: function () {
      console.log(this.form, "asdfasdf");
      let that = this;
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.$modal.confirm("是否确认修改当前详情页？").then(function () {
            if (that.form.fdId != undefined) {
              let addData = {};
              addData.fdId = that.form.fdId;
              addData.specialCondition = that.form.specialCondition;
              addData.newHighRiskFactor = that.form.newHighRiskFactor.join(";");
              if (that.form.carCallArea == "越秀区") {
                addData.carCallArea = "10";
              } else if (that.form.carCallArea == "海珠区") {
                addData.carCallArea = "20";
              } else if (that.form.carCallArea == "荔湾区") {
                addData.carCallArea = "30";
              } else if (that.form.carCallArea == "天河区") {
                addData.carCallArea = "40";
              } else if (that.form.carCallArea == "白云区") {
                addData.carCallArea = "50";
              } else if (that.form.carCallArea == "黄埔区") {
                addData.carCallArea = "60";
              } else if (that.form.carCallArea == "南沙区") {
                addData.carCallArea = "70";
              } else if (that.form.carCallArea == "番禺区") {
                addData.carCallArea = "80";
              } else if (that.form.carCallArea == "花都区") {
                addData.carCallArea = "90";
              } else if (that.form.carCallArea == "增城区") {
                addData.carCallArea = "100";
              } else if (that.form.carCallArea == "从化区") {
                addData.carCallArea = "110";
              } else if (that.form.carCallArea == "入境") {
                addData.carCallArea = "120";
              } else {
                addData.carCallArea = "";
              }
              console.log(addData);
              editEnterData(addData).then((response) => {
                that.$modal.msgSuccess("修改成功");
                that.open = false;
                that.currentPage = 1;
                that.getList();
              });
            } else {
              let addData = {};
              addData.acceptNo = that.form.acceptNo;
              addData.carCallUnit = that.form.carCallUnit;
              addData.carCallDate = that.form.carCallDate;
              addData.carCallTime = that.form.carCallTime;
              addData.carCallTime = addData.carCallTime.replace("-", ":");
              addData.carCallUnit = that.form.carCallUnit;

              addData.carCallContractPerson = that.form.carCallContractPerson;
              addData.carCallContractNumber = that.form.carCallContractNumber;
              addData.patientName = that.form.patientName;
              addData.patientIdNumber = that.form.patientIdNumber;
              addData.sex = that.form.sex;
              addData.age = that.form.age;
              addData.patientContractNumber = that.form.patientContractNumber;

              addData.caseSource = that.form.caseSource;
              addData.personIdentity = that.form.personIdentity;
              addData.nucleateResult = that.form.nucleateResult;
              addData.nucleateIsPositive = that.form.nucleateIsPositive;
              addData.newHighRiskFactor = that.form.newHighRiskFactor.join(";");
              // 高危因素以英文分号分隔

              addData.specialCondition = that.form.specialCondition;

              if (that.form.carCallArea == "越秀区") {
                addData.carCallArea = "10";
              } else if (that.form.carCallArea == "海珠区") {
                addData.carCallArea = "20";
              } else if (that.form.carCallArea == "荔湾区") {
                addData.carCallArea = "30";
              } else if (that.form.carCallArea == "天河区") {
                addData.carCallArea = "40";
              } else if (that.form.carCallArea == "白云区") {
                addData.carCallArea = "50";
              } else if (that.form.carCallArea == "黄埔区") {
                addData.carCallArea = "60";
              } else if (that.form.carCallArea == "南沙区") {
                addData.carCallArea = "70";
              } else if (that.form.carCallArea == "番禺区") {
                addData.carCallArea = "80";
              } else if (that.form.carCallArea == "花都区") {
                addData.carCallArea = "90";
              } else if (that.form.carCallArea == "增城区") {
                addData.carCallArea = "100";
              } else if (that.form.carCallArea == "从化区") {
                addData.carCallArea = "110";
              } else if (that.form.carCallArea == "入境") {
                addData.carCallArea = "120";
              } else {
                addData.carCallArea = "";
              }
              console.log(addData);
              addEnterData(addData).then((response) => {
                that.$modal.msgSuccess("新增成功");
                that.open = false;
                that.currentPage = 1;
                that.getList();
              });
            }
          });
        }
      });
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "选择文件导入";
      this.upload.updateSupport = true;
      this.upload.open = true;
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      // this.loading = true;
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      // this.loading = false;
      this.upload.open = false;
      this.upload.isUploading = false;
      if (response.code == "500") {
        response.msg = "导入失败";
      }
      if (response.code == "200" && response.msg == "") {
        response.msg = "导入成功";
      }
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

    // element dialog 偶尔不让编辑解决
    change(e) {
      this.$forceUpdate();
    },
    // 清除表单数据
    clearWinData() {
      this.form.acceptNo = "";
      this.form.carCallUnit = "";
      this.form.carCallDate = "";
      this.form.callCarTime = "";
      this.form.carCallContractPerson = "";
      this.form.carCallContractNumber = "";
      this.form.patientName = "";
      this.form.patientIdNumber = "";
      this.form.sex = "";
      this.form.age = "";
      this.form.patientContractNumber = "";
      this.form.caseSource = "";
      this.form.personIdentity = "";
      this.form.nucleateResult = "";
      this.form.nucleateIsPositive = "";
      let checkList = "";
      this.form.newHighRiskFactor = "";
      this.form.epgHighRiskFactor = "";
      this.form.specialCondition = "";
      this.form.carCallArea = "";
      this.form.fdId = "";
    },
    // 排序函数
    getIndex(data, startEnd) {
      for (let i = 0; i < data.length; i++) {
        data[i].index = startEnd + i;
      }
      return data;
    },
    // 分页器值改变事件
    sizeChange(val) {
      this.pageSize = val;
      console.log(val, "123123");
      this.currentPage = 1;
      this.getList();
    },
    // 当前页面改变触发事件
    handleCurrentChange(val) {
      // 请求数据，渲染数据
      this.currentPage = val;
      console.log(`当前页: ${val}`);
      this.getList();
    },
  },
};
</script>
<style scoped>
</style>