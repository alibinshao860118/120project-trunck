<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--用户数据-->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
        <el-form-item label="呼车日期">
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

        <el-form-item label="呼车联系人" prop="carCallContractPerson">
          <el-input
            v-model="queryParams.carCallContractPerson"
            placeholder="请输入呼车联系人"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="呼车联系电话" prop="carCallContractNumber">
          <el-input
            v-model="queryParams.carCallContractNumber"
            placeholder="请输入呼车联系电话"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>

        <el-form-item label="病人姓名" prop="patientName">
          <el-input
            v-model="queryParams.patientName"
            placeholder="请输入病人姓名"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>

        <el-form-item label="是否危重症" prop="isSerious">
          <el-select
            v-model="queryParams.isSerious"
            placeholder="请选择"
            clearable
            style="width: 100%"
          >
            <el-option
              v-for="item in isSeriousOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="是否已到院" prop="isArriveHospital">
          <el-select
            v-model="queryParams.isArriveHospital"
            placeholder="请选择"
            clearable
            style="width: 100%"
          >
            <el-option
              v-for="item in isArriveHospitaloptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否调派" prop="assignStatus">
          <el-select
            v-model="queryParams.assignStatus"
            clearable
            placeholder="请选择"
            style="width: 100%"
          >
            <el-option
              v-for="item in assignStatusOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="病例来源" prop="sourceCases">
          <el-select
            v-model="queryParams.sourceCases"
            placeholder="请选择"
            clearable
            style="width: 100%"
          >
            <el-option
              v-for="item in caseSourceOptions"
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

          <el-button type="primary" size="mini" @click="getWork"
            >认领任务</el-button
          >
          <el-button type="primary" size="mini" @click="getWorkStatus('2')"
            >任务终止</el-button
          >
          <el-button type="primary" size="mini" @click="getWorkStatus('1')"
            >任务延续</el-button
          >
          <!-- <div
            style="display: inline-block; margin-left: 30px; font-size: 15px"
          >
            未调派急危重症任务<span
              style="
                color: red;
                font-size: 20px;
                padding: 0 10px;
                font-weight: 800;
              "
              >{{ workNum }}</span
            >单
          </div> -->
        </el-form-item>
      </el-form>
      <el-table
        ref="multipleTable"
        :data="assignList"
        tooltip-effect="dark"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55"> </el-table-column>
        <el-table-column label="" width="55" prop="operation">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              @click="handleAdd(scope.row)"
              v-hasPermi="['system:assign:add']"
              >详情</el-button
            >
          </template>
        </el-table-column>
        <el-table-column
          label="是否已到院"
          show-overflow-tooltip
          prop="isArriveHospital"
          :formatter="isArriveHospitalFormat"
        >
        </el-table-column>
        <el-table-column
          prop="assignStatus"
          label="是否调派"
          :formatter="assignStatusFormat"
        >
        </el-table-column>
        <el-table-column prop="dispatchNo" label="派车单号"> </el-table-column>
        <el-table-column prop="acceptNo" label="受理编号"> </el-table-column>
        <el-table-column prop="carCallUnit" label="呼车单位"> </el-table-column>
        <el-table-column prop="carCallDate" label="呼车日期"> </el-table-column>
        <el-table-column prop="carCallTime" label="呼车时间"> </el-table-column>
        <el-table-column prop="carCallContractPerson" label="呼车联系人">
        </el-table-column>
        <el-table-column prop="carCallContractNumber" label="呼车联系电话">
        </el-table-column>
        <el-table-column prop="patientName" label="病人姓名"> </el-table-column>
        <el-table-column prop="patientIdNumber" label="病人证件号" width="100%">
        </el-table-column>
        <el-table-column prop="patientContractNumber" label="病人联系电话">
        </el-table-column>
        <el-table-column prop="sex" label="性别" :formatter="sexFormat">
        </el-table-column>

        <el-table-column prop="age" label="年龄"> </el-table-column>
        <el-table-column prop="caseSource" label="病例来源">
          <template slot-scope="scope">
            <span>{{ parseCaseSource(scope.row.caseSource) }}</span>
          </template>
        </el-table-column>
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
        <el-table-column prop="licensePlateNumber" label="录入车牌号">
        </el-table-column>

        <el-table-column prop="doctor1" label="医生1"> </el-table-column>
        <el-table-column prop="doctor2" label="医生2"> </el-table-column>
        <el-table-column prop="nurse1" label="护士1"> </el-table-column>
        <el-table-column prop="nurse2" label="护士2"> </el-table-column>
        <el-table-column prop="driver" label="司机"> </el-table-column>
        <el-table-column label="派车单号" align="center" prop="dispatchNo" />
        <el-table-column prop="drawOutHospital" label="出车医院">
        </el-table-column>
        <el-table-column prop="carNo" label="车号"> </el-table-column>
        <el-table-column prop="licensePlateNo" label="调派车牌号">
        </el-table-column>
        <el-table-column prop="cityEightConfirmTime" label="市八确认时间">
        </el-table-column>
        <el-table-column prop="pickUpAddress" label="接车地址">
        </el-table-column>
        <el-table-column prop="newPickUpAddress" label="新接车地址">
        </el-table-column>
        <el-table-column prop="receivingHospital" label="接收医院">
        </el-table-column>

        <el-table-column
          prop="newReceiveHospitalAddress"
          label="新接收医院地址"
        >
        </el-table-column>

        <el-table-column prop="remark" label="调派环节备注"> </el-table-column>
        <el-table-column prop="jobSuspendReason" label="任务中止原因">
        </el-table-column>
        <el-table-column prop="jobSuspendTime" label="任务中止时间">
        </el-table-column>
        <el-table-column prop="createBy" label="调派员姓名"> </el-table-column>
      </el-table>
      <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        @size-change="sizeChange"
        @pagination="getList"
      />
    </el-row>

    <!-- 新增调派对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="680px" append-to-body>
      <el-form
        ref="form"
        :rules="rules"
        :model="form"
        :inline="true"
        label-width="100px"
      >
        <el-row>
          <el-col :span="24">
            <el-form-item label="医生1" prop="doctor1">
              <el-select
                v-model="form.doctor1"
                placeholder="请选择"
                clearable
                style="width: 500px"
              >
                <el-option
                  v-for="item in doctor1options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="医生2" prop="doctor2">
              <el-select
                v-model="form.doctor2"
                placeholder="请选择"
                clearable
                style="width: 500px"
              >
                <el-option
                  v-for="item in doctor1options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="护士1" prop="nurse1">
              <el-select
                v-model="form.nurse1"
                placeholder="请选择"
                clearable
                style="width: 500px"
              >
                <el-option
                  v-for="item in nurse1options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="护士2" prop="nurse2">
              <el-select
                v-model="form.nurse2"
                placeholder="请选择"
                clearable
                style="width: 500px"
              >
                <el-option
                  v-for="item in nurse1options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="司机" prop="driver">
              <el-select
                v-model="form.driver"
                placeholder="请选择"
                style="width: 500px"
              >
                <el-option
                  v-for="item in driveroptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="出车医院下拉框" prop="drawOutHospital">
              <el-select
                v-model="form.drawOutHospital"
                placeholder="请选择"
                style="width: 500px"
              >
                <el-option
                  v-for="item in drawOutHospitaloptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="车号" prop="carNo">
              <el-input
                v-model="form.carNo"
                placeholder="必填，数字，2字以内"
                maxlength="2"
                @input="change($event)"
              />
            </el-form-item>
            <el-form-item label="车牌号" prop="licensePlateNo">
              <el-input
                v-model="form.licensePlateNo"
                placeholder="必填，文本，7字以内"
                maxlength="7"
                @input="change($event)"
              />
            </el-form-item>
            <el-form-item label="备注" prop="remark">
              <el-input
                type="textarea"
                placeholder=""
                v-model="form.remark"
                maxlength="300"
                show-word-limit
                style="min-width: 400px"
                @input="change($event)"
              >
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <template
          v-if="form.sickerList != undefined && form.sickerList.length > 0"
        >
          <el-row v-for="(item, index) in form.sickerList" :key="index">
            <el-col :span="24">
              <div style="display: block; font-size: 20px; font-weight: 800">
                患者{{ index + 1 }}
              </div>
              <el-form-item
                label="受理编号"
                :prop="'data.' + index + 'acceptNo'"
              >
                <el-input
                  v-model="item.acceptNo"
                  placeholder="必填，数字，10字以内"
                  maxlength="10"
                  @input="change($event)"
                  :disabled="disabled"
                />
              </el-form-item>
              <el-form-item
                label="呼车单位"
                :prop="'data.' + index + 'carCallUnit'"
              >
                <el-input
                  v-model="item.carCallUnit"
                  @input="change($event)"
                  :disabled="disabled"
                />
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item
                label="呼车时间"
                :prop="'data.' + index + 'carCallTime'"
                v-if="!disabled"
              >
                <el-input
                  v-model="item.carCallTime"
                  maxlength="30"
                  @input="change($event)"
                  :disabled="disabled"
                />
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item
                label="呼车联系人"
                :prop="'data.' + index + 'carCallContractPerson'"
              >
                <el-input
                  v-model="item.carCallContractPerson"
                  maxlength="30"
                  @input="change($event)"
                  :disabled="disabled"
                />
              </el-form-item>
              <el-form-item
                label="呼车联系电话"
                :prop="'data.' + index + 'carCallContractNumber'"
              >
                <el-input
                  v-model="item.carCallContractNumber"
                  maxlength="11"
                  @input="change($event)"
                  :disabled="disabled"
                />
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item
                label="病人姓名"
                :prop="'data.' + index + 'patientName'"
              >
                <el-input
                  v-model="item.patientName"
                  maxlength="30"
                  @input="change($event)"
                  :disabled="disabled"
                />
              </el-form-item>
              <el-form-item
                label="病人证件号码"
                :prop="'data.' + index + 'patientIdNumber'"
              >
                <el-input
                  v-model="item.patientIdNumber"
                  maxlength="18"
                  @input="change($event)"
                  :disabled="disabled"
                />
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item
                label="性别"
                :prop="'data.' + index + 'sex'"
                :rules="rules.sex"
              >
                <el-radio v-model="item.sex" label="0">男</el-radio>
                <el-radio v-model="item.sex" label="1">女</el-radio>
              </el-form-item>
              <el-form-item label="年龄" :prop="'data.' + index + 'age'">
                <el-input
                  v-model="item.age"
                  :disabled="disabled"
                  @input="change($event)"
                />
              </el-form-item>
              <el-form-item
                label="病人联系电话"
                :prop="'data.' + index + 'patientContractNumber'"
              >
                <el-input
                  :disabled="disabled"
                  v-model="item.patientContractNumber"
                  maxlength="11"
                  @input="change($event)"
                  style="min-width: 400px"
                />
              </el-form-item>
            </el-col>

            <el-col :span="24">
              <el-form-item
                label="病人来源"
                :prop="'data.' + index + 'caseSource'"
                :rules="rules.perpeocase"
              >
                <el-radio-group v-model="item.caseSource">
                  <el-radio-button :label="0">本土</el-radio-button>
                  <el-radio-button :label="1">入境</el-radio-button>
                </el-radio-group>
              </el-form-item>
              <el-form-item
                label="人员身份"
                :prop="'data.' + index + 'personIdentity'"
                :rules="rules.personIdentity"
              >
                <el-radio-group v-model="item.personIdentity">
                  <el-radio-button :label="0">本人</el-radio-button>
                  <el-radio-button :label="1">陪同人员</el-radio-button>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item
                label="核酸检测结果"
                :rules="rules.nucleateResult"
                :prop="'data.' + index + 'nucleateResult'"
              >
                <el-radio-group v-model="item.nucleateResult">
                  <el-radio-button label="0">阴性</el-radio-button>
                  <el-radio-button label="1">阳性</el-radio-button>
                </el-radio-group>
              </el-form-item>
              <el-form-item
                label="核酸检测是否阳性"
                :rules="rules.nucleateIsPositive"
                :prop="'data.' + index + 'nucleateIsPositive'"
              >
                <el-radio-group v-model="item.nucleateIsPositive">
                  <el-radio-button label="0">否</el-radio-button>
                  <el-radio-button label="1">是</el-radio-button>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item
                label="高危因素"
                :rules="rules.checkList"
                :prop="'data.' + index + 'checkList'"
              >
                <el-checkbox-group v-model="seriousList">
                  <el-checkbox
                    :label="item1.value+''"
                    :key="index1"
                    v-for="(item1, index1) in epgHighRiskFactorCheckBox"
                  ></el-checkbox>
                </el-checkbox-group>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item
                label="呼车区域"
                :prop="'data.' + index + 'callArea'"
              >
                <el-input
                  :disabled="disabled"
                  v-model="item.callArea"
                  @input="change($event)"
                  style="min-width: 400px"
                />
              </el-form-item>
              <el-form-item
                label="市八确认时间"
                :prop="'data.' + index + 'cityEightConfirmTime'"
              >
                <el-input
                  :disabled="disabled"
                  v-model="item.cityEightConfirmTime"
                  @input="change($event)"
                  style="min-width: 400px"
                />
              </el-form-item>
              <el-form-item
                label="接车地址"
                :prop="'data.' + index + 'pickUpAddress'"
              >
                <el-input
                  :disabled="disabled"
                  v-model="item.pickUpAddress"
                  @input="change($event)"
                  style="min-width: 400px"
                />
              </el-form-item>
              <el-form-item
                label="新接车地址"
                :prop="'data.' + index + 'newPickUpAddress'"
                :rules="rules.newPickUpAddress"
              >
                <el-input
                  v-model="item.newPickUpAddress"
                  @input="change($event)"
                  style="min-width: 400px"
                />
              </el-form-item>
              <el-form-item
                label="特殊病情"
                :prop="'data.' + index + 'specialCondition'"
                :rules="rules.specialCondition"
              >
                <el-input
                  v-model="item.specialCondition"
                  @input="change($event)"
                  style="min-width: 400px"
                />
              </el-form-item>
              <el-form-item
                label="接收医院地址"
                :prop="'data.' + index + 'receivingHospital'"
              >
                <el-input
                  :disabled="disabled"
                  v-model="item.receivingHospital"
                  @input="change($event)"
                  style="min-width: 400px"
                />
              </el-form-item>
              <el-form-item
                label="新接收医院地址"
                :prop="'data.' + index + 'newReceiveHospitalAddress'"
                :rules="rules.newReceiveHospitalAddress"
              >
                <el-input
                  v-model="item.newReceiveHospitalAddress"
                  @input="change($event)"
                  style="min-width: 400px"
                />
              </el-form-item>
              <el-form-item
                label="接收医院"
                :prop="'data.' + index + 'receivingHospital'"
              >
                <el-select
                  v-model="item.receivingHospitalOption"
                  placeholder="请选择"
                  style="width: 100%"
                >
                  <el-option
                    v-for="item in receivingHospitalOption"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item
                label="任务中止原因"
                :prop="'data.' + index + 'jobSuspendReason'"
              >
                <el-input
                  :disabled="disabled"
                  v-model="item.jobSuspendReason"
                  @input="change($event)"
                  style="min-width: 400px"
                />
              </el-form-item>
            </el-col>
          </el-row>
        </template>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 任务中止或任务延续界面 -->
    <!-- 添加或修改菜单对话框 -->
    <el-dialog
      :title="workTitle"
      :visible.sync="workopen"
      width="680px"
      append-to-body
    >
      <el-form
        ref="workform"
        :model="workform"
        :rules="workrules"
        label-width="100px"
      >
        <el-row>
          <el-form-item :label="workTitle" prop="reason">
            <el-input
              type="textarea"
              placeholder=""
              v-model="workform.reason"
              maxlength="50"
              show-word-limit
              @input="change($event)"
            >
            </el-input>
          </el-form-item>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="workSubmitForm">确 定</el-button>
        <el-button @click="workCancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listAssign,
  getAssign,
  delAssign,
  addAssign,
  updateAssign,
  getDictInfo,
  editStatus,
} from "@/api/system/assign";

import { getToken } from "@/utils/auth";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { MessageBox } from "element-ui";
export default {
  name: "User",
  dicts: ["sys_normal_disable", "sys_user_sex"],
  components: { Treeselect },
  data() {
    return {
      // 高危因素
      seriousList: [],

      // 任务中止或延续状态
      workStatus: "",
      // 任务中止或延续表单是否展示
      workopen: false,
      // 任务中止或延续表单标题
      workTitle: "",
      // 任务中止或延续表单
      workform: {
        reason: "",
      },
      // 任务中止或延续表单规则
      workrules: {
        reason: [{ required: true, message: "原因不能为空", trigger: "blur" }],
      },
      // 表单校验规则
      rules: {
        doctor1: [
          { required: true, message: "请选择医生1", trigger: "change" },
        ],
        nurse1: [{ required: true, message: "请选择医生1", trigger: "change" }],
        driver: [{ required: true, message: "请选择司机", trigger: "change" }],
        drawOutHospital: [
          { required: true, message: "请选择出车医院", trigger: "change" },
        ],
        carNo: [
          { required: true, message: "车号不能为空", trigger: "blur" },
          {
            max: 2,
            message: "车号最多为2位数",
            trigger: "blur",
          },
        ],
        licensePlateNo: [
          { required: true, message: "车牌号不能为空", trigger: "blur" },
          {
            max: 7,
            message: "车牌号最多为7位数",
            trigger: "blur",
          },
        ],
        sex: [{ required: true, message: "请选择性别", trigger: "change" }],
        caseSource: [
          {
            required: true,
            message: "请选择病例来源",
            trigger: "change",
          },
        ],
        personIdentity: [
          {
            required: true,
            message: "请选择人员身份",
            trigger: "change",
          },
        ],
        nucleateResult: [
          {
            required: true,
            message: "请选择核酸检测结果",
            trigger: "change",
          },
        ],
        nucleateIsPositive: [
          {
            required: true,
            message: "请选择核酸检测是否阳性",
            trigger: "change",
          },
        ],
        checkList: [
          {
            required: true,
            message: "请选择高危因素",
            trigger: "change",
          },
        ],
        newPickUpAddress: [
          { required: true, message: "新接车地址不能为空", trigger: "blur" },
        ],
        specialCondition: [
          { required: true, message: "特殊病情不能为空", trigger: "blur" },
        ],
        newReceiveHospitalAddress: [
          {
            required: true,
            message: "新接收医院地址不能为空",
            trigger: "blur",
          },
        ],
      },
      // 是否可编辑
      disabled: true,
      // 弹窗表单数据
      form: {
        doctor1: "",
        doctor2: "",
        nurse1: "",
        nurse2: "",
        driver: "",
        drawOutHospital: "",
        carNo: "",
        licensePlateNo: "",
        remark: "",
        sickerList: [],
      },
      // 医生下拉框
      doctor1options: [],
      // 护士下拉框
      nurse1options: [],
      // 接收医院下拉框
      receivingHospitalOption: [],
      // 出车医院下拉框
      drawOutHospitaloptions: [],
      // 司机下拉框
      driveroptions: [],
      // 高危因素  多选框 大于60岁
      epgHighRiskFactorCheckBox: [
        // {
        //   label: ">60岁",
        //   value: "1",
        // },
        // {
        //   label: "<14岁",
        //   value: "2",
        // },
        // {
        //   label: "心脑血管疾病",
        //   value: "3",
        // },
        // {
        //   label: "糖尿病",
        //   value: "4",
        // },
      ],
      // 多选框选中的值的数据
      multipleSelection: [],
      // 未调派急危重症任务数量
      workNum: "10",
      // 当前页面
      currentPage: 1,
      // 分页器多少条每页
      pageSizes: [10, 20, 50],
      // 每页显示条数
      pageSize: "",
      // 呼车时间
      carCallTime: "",
      // 呼车联系人
      callContact: "",
      // 患者姓名
      patientName: "",
      // 是否已到院
      isArriveHospital: "",
      // 是否高危
      isSeriousOptions: [
        {
          value: "0",
          label: "否",
        },
        {
          value: "1",
          label: "是",
        }
      ],

      // 是否已到院下拉框
      isArriveHospitaloptions: [
        {
          value: "",
          label: "请选择",
        },
        {
          value: "1",
          label: "已到院",
        },
        {
          value: "2",
          label: "未到院",
        },
      ],
      // 是否调派
      assignStatus: "",
      // 是否调派下拉框
      assignStatusOptions: [
        {
          value: "",
          label: "请选择",
        },
        {
          value: "1",
          label: "已调派",
        },
        {
          value: "2",
          label: "未调派",
        },
      ],
      // 病例来源
      sourceCases: "",
      // 病例来源下拉框
      caseSourceOptions: [
        {
          value: "0",
          label: "本土",
        },
        {
          value: "1",
          label: "入境",
        },
      ],

      // 列表数据
      // tableData: [
      //   {
      //     id: "1",
      //     isArriveHospital: "是否已到院",
      //     assignStatus: "是否调派",
      //     dispatchNo: "派车单号",
      //
      //     acceptNo: "受理编号",
      //     carCallUnit: "呼车单位",
      //     carCallDate: "呼车日期",
      //     carCallTime: "2022-0808",
      //     carCallContractPerson: "呼车联系人",
      //     carCallContractNumber: "呼车联系电话",
      //     patientName: "病人姓名",
      //     patientIdNumber: "病人证件号",
      //     patientContractNumber: "病人联系电话",
      //     sex: "",
      //     age: "年龄11",
      //     case: "病例来源",
      //     perpeocase: "本土",
      //     personIdentity: "本人",
      //     nucleateIsPositive: "阴性",
      //     isPositive: "是",
      //     checkList: [],
      //     callArea: "呼车区域1",
      //     specialCondition: "特殊病情",
      //     sentHospital: "派出医院",
      //     plateNum: "车牌号",
      //     doctor1: "1",
      //     doctor2: "2",
      //     nurse1: "1",
      //     nurse2: "2",
      //     driver: "1",
      //     drawOutHospital: "1",
      //     carNo: "车号",
      //     licensePlateNo: "车牌号",
      //     cityEightConfirmTime: "市八确认时间",
      //     pickUpAddress: "接车地址",
      //     newPickUpAddress: "新接车地址",
      //     receivingHospital: "接收医院 ",
      //     newReceiveHospitalAddress: "新接手医院地址",
      //     remark: "调派环节备注",
      //     jobSuspendReason: "任务中止原因",
      //     jobSuspendTime: "任务中止时间",
      //     dispatcherName: "调派员姓名",
      //     receivingHospitalOption: "1",
      //   },
      //   {
      //     id: "2",
      //     isArriveHospital: "是否已到院",
      //     assignStatus: "是否调派",
      //     dispatchNo: "派车单号",
      //
      //     acceptNo: "受理编号",
      //     carCallUnit: "呼车单位",
      //     carCallDate: "呼车日期",
      //     carCallTime: "2022-0808",
      //     carCallContractPerson: "呼车联系人",
      //     carCallContractNumber: "呼车联系电话",
      //     patientName: "病人姓名",
      //     patientIdNumber: "病人证件号",
      //     patientContractNumber: "病人联系电话",
      //     sex: "男",
      //     age: "年龄11",
      //     case: "病例来源",
      //     perpeocase: "本土",
      //     personIdentity: "本人",
      //     nucleateIsPositive: "阴性",
      //     isPositive: "是",
      //     checkList: [],
      //     callArea: "呼车区域1",
      //     specialCondition: "特殊病情",
      //     sentHospital: "派出医院",
      //     plateNum: "车牌号",
      //     doctor1: "1",
      //     doctor2: "2",
      //     nurse1: "1",
      //     nurse2: "2",
      //     driver: "1",
      //     drawOutHospital: "1",
      //     carNo: "车号",
      //     licensePlateNo: "车牌号",
      //     cityEightConfirmTime: "市八确认时间",
      //     pickUpAddress: "接车地址",
      //     newPickUpAddress: "新接车地址",
      //     receivingHospital: "接收医院 ",
      //     newReceiveHospitalAddress: "新接手医院地址",
      //     remark: "调派环节备注",
      //     jobSuspendReason: "任务中止原因",
      //     jobSuspendTime: "任务中止时间",
      //     dispatcherName: "调派员姓名",
      //     receivingHospitalOption: "1",
      //   },
      // ],

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

      // 120核阳转运调派表格数据
      assignList: [],

      // 日期范围
      dateRange: [],

      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        isSerious: null,
        pageNum: 1,
        pageSize: 10,
        doctor1: null,
        doctor2: null,
        nurse1: null,
        nurse2: null,
        driver: null,
        drawOutHospital: null,
        carNo: null,
        licensePlateNo: null,
        newPickUpAddress: null,
        newReceiveHospitalAddress: null,
        receiveHospital: null,
        dispatchNo: null,
        carCallUnit: null,
        carCallDate: null,
        carCallTime: null,
        carCallContractPerson: null,
        carCallContractNumber: null,
        patientName: null,
        patientIdNumber: null,
        patientContractNumber: null,
        sex: null,
        age: null,
        caseSource: null,
        personIdentity: null,
        nucleateIsPositive: null,
        epgepgHighRiskFactor: null,
        newepgHighRiskFactor: null,
        sentHospital: null,
        licensePlateNumber: null,
        cityEightConfirmTime: null,
        assignDate: null,
        assignTime: null,
        ambulanceType: null,
        noticeTime: null,
        drawOutTime: null,
        arriveAirportEcTime: null,
        pickUpAddress: null,
        ambulanceBackTime: null,
        completeJobTime: null,
        patientPersonTime: null,
        carCallRemark: null,
        assignRemark: null,
        finishRemark: null,
        dutyMan: null,
        specialCondition: null,
        carCallArea: null,
        dataSource: null,
        isArriveHospital: null,
        assignStatus: null,
      },
    };
  },
  watch: {},
  created() {
    this.getList();

    // 获取字典
    this.getDictInfo();
  },
  methods: {
    // 任务中止或延续取消按钮
    workCancel() {
      this.workopen = false;
      this.workform.reason = "";
    },
    // 任务中止或延续确认按钮
    workSubmitForm() {
      let that = this;
      this.$refs["workform"].validate((valid) => {
        if (valid) {
          console.log(that.workform, "this.workform");
          let obj = {
            fdIds: [],
            taskType: "",
            reason: "",
          };
          obj.fdIds = that.ids;
          obj.taskType = that.workStatus;
          obj.reason = that.workform.reason;
          editStatus(obj).then((response) => {
            if (response.code == 200) {
              if (that.workStatus == "1") {
                this.$modal.msgSuccess("任务延续成功");
              } else if (that.workStatus == "2") {
                this.$modal.msgSuccess("任务终止成功");
              }
            }
          });
        }
      });
    },

    // 任务终止 连续
    // 调派状态（0未调派 1已调派 2任务中止）
    getWorkStatus(status) {
      this.workStatus = status;
      console.log(this.multipleSelection);
      if (this.multipleSelection.length > 6) {
        MessageBox.alert("最多勾选六项", "系统提示", { type: "warning" });
        return;
      }
      if (this.multipleSelection.length == 0) {
        MessageBox.alert("请勾选要认领任务的数据", "系统提示", {
          type: "warning",
        });
        return;
      }
      this.disabled = true;
      this.workopen = true;
      if (status == "2") {
        //判断选中项是否已调派  调派状态（0未调派 1已调派 2任务中止）
        for (let i = 0; i < this.multipleSelection.length; i++) {
          if (this.multipleSelection[i].assignStatus != "1") {
            alert("选择项必须已调派才能终止");
            return;
          }
        }
        this.workopen = true;
        this.workTitle = "任务中止原因";
        this.workform.reason = "";
      }
      if (status == "1") {
        //判断选中项是否已终止,才能重新调派延续
        for (let i = 0; i < this.multipleSelection.length; i++) {
          if (this.multipleSelection[i].assignStatus != "1") {
            alert("选择项必须已终止才能延续");
            return;
          }
        }
        this.workopen = true;
        this.workTitle = "任务延续原因";
        this.workform.reason = "";
      }
    },

    getDictInfo() {
      // 清空
      this.epgHighRiskFactorCheckBox = [];
      this.doctor1options = [];
      this.nurse1options = [];
      this.driveroptions = [];
      this.receivingHospitalOption = [];
      this.drawOutHospitaloptions = [];

      // 字典类型，1 呼车单位，2 高危因素，3 派出医院，4 暴露风险，5 医院
      getDictInfo("2").then((response) => {
        let dataList = response.data;

        if (dataList.length == 0) {
          return;
        }
        for (let i in dataList) {
          let item = {
            label: "",
            value: "",
          };
          item.value = dataList[i].id+'';
          item.label = dataList[i].dict_value;
          this.epgHighRiskFactorCheckBox.push(item);
        }
      });
      getDictInfo("3").then((response) => {
        let dataList = response.data;
        if (dataList.length == 0) {
          return;
        }
        for (let i in dataList) {
          let item = {
            label: "",
            value: "",
          };
          item.value = dataList[i].id + '';
          item.label = dataList[i].dict_value;
          this.doctor1options.push(item);
          this.nurse1options.push(item);
          this.driveroptions.push(item);
        }
      });
      getDictInfo("5").then((response) => {
        let dataList = response.data;
        if (dataList.length == 0) {
          return;
        }
        for (let i in dataList) {
          let item = {
            label: "",
            value: "",
          };
          item.value = dataList[i].id+ '';
          item.label = dataList[i].dict_value;
          // 接收医院下拉框
          // 出车医院下拉框
          this.receivingHospitalOption.push(item);
          this.drawOutHospitaloptions.push(item);
        }
      });
    },

    parseCaseSource(status) {
      const finder = this.caseSourceOptions.find(
        (item) => item.value == status
      );
      return finder ? finder.label : "";
    },

    sexFormat(row, index) {
      if (row.sex == 0) {
        return "男";
      } else if (row.sex == 1) {
        return "女";
      } else {
        return "未知";
      }
    },

    isArriveHospitalFormat(row, index) {
      if (row.isArriveHospital == 1) {
        return "是";
      } else {
        return "否";
      }
    },

    // 调派状态（0未调派 1已调派 2任务中止）
    assignStatusFormat(row, index) {
      if (row.assignStatus == 0) {
        return "未调派";
      } else if (row.assignStatus == 1) {
        return "已调派";
      } else if (row.assignStatus == 2) {
        return "任务中止";
      }
    },

    /** 查询用户列表 */
    getList() {
      this.loading = true;
      listAssign(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.assignList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.clearWinData();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      console.log(this.carCallTime);
      console.log(this.callContact);
      console.log(this.patientName);
      console.log(this.isArriveHospital);
      console.log(this.assignStatus);
      console.log(this.sourceCases);

      this.queryParams.pageNum = 1;
      this.getList();
    },
    // 查询未调派按钮操作
    handleQueryDispatch() {},
    /** 新增按钮操作 */
    handleAdd(row) {
      this.clearWinData();
      console.log(row, "row");
      if (row != null) {
        this.disabled = true;
        this.title = "详情进入";
        this.form.doctor1 = row.doctor1;
        this.form.doctor2 = row.doctor2;
        this.form.nurse1 = row.nurse1;
        this.form.nurse2 = row.nurse2;
        this.form.driver = row.driver;
        this.form.drawOutHospital = row.drawOutHospital;
        this.form.carNo = row.carNo;
        this.form.licensePlateNo = row.licensePlateNo;
        this.form.remark = row.remark;
        console.log("性别：" + row.sex);
        let formData = [];
        formData.push(row);
        console.log(row.newHighRiskFactor);
        let checkList = [];
        if (row.newHighRiskFactor) {
          checkList = row.newHighRiskFactor.split(";");
        }
        // 高危因素得转为数组才能绑定数据

        this.seriousList = checkList;
        this.form.sickerList = formData;
      } else {
        this.disabled = false;
        this.form.id = undefined;
        this.title = "新增调派进入";
      }
      this.open = true;
    },
    /** 提交按钮 */
    submitForm: function () {
      console.log(this.form, "asdfasdf");
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.$modal.confirm("是否确认修改当前详情页？").then(function () {
            if (this.form.userId != undefined) {
              updateUser(this.form).then((response) => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addUser(this.form).then((response) => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          });
        }
      });
    },
    // element dialog 偶尔不让编辑解决
    change(e) {
      this.$forceUpdate();
    },
    // 清除表单数据
    clearWinData() {
      this.form.doctor1 = "";
      this.form.doctor2 = "";
      this.form.nurse1 = "";
      this.form.nurse2 = "";
      this.form.driver = "";
      this.form.drawOutHospital = "";
      this.form.carNo = "";
      this.form.licensePlateNo = "";
      this.form.remark = "";
      this.form.sickerList = [];
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
      console.log(val);
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
    // 呼车时间值改变事件
    getSTime(val) {
      console.log(val, "123123");
      this.carCallTime = val;
    },
    // 认领任务按钮触发事件
    getWork() {
      console.log(this.multipleSelection);
      if (this.multipleSelection.length > 6) {
        MessageBox.alert("最多勾选六项", "系统提示", { type: "warning" });
        return;
      }
      if (this.multipleSelection.length == 0) {
        MessageBox.alert("请勾选要认领任务的数据", "系统提示", {
          type: "warning",
        });
        return;
      }
      this.disabled = true;
      this.title = "认领任务进入";
      this.form.doctor1 = "";
      this.form.doctor2 = "";
      this.form.nurse1 = "";
      this.form.nurse2 = "";
      this.form.driver = "";
      this.form.drawOutHospital = "";
      this.form.carNo = "";
      this.form.licensePlateNo = "";
      this.form.remark = "";
      let formData = [];
      for (let i = 0; i < this.multipleSelection.length; i++) {
        formData.push(this.multipleSelection[i]);
      }
      console.log(formData, "formData");
      console.log(this.form);
      this.form.sickerList = formData;
      this.open = true;
    },
    // 表格的页面选择框的值改变事件
    handleSelectionChange(val) {
      console.log(val);
      this.multipleSelection = val;
      this.ids = val.map((item) => item.fdId);
    },
  },
};
</script>
<style scoped>
</style>
