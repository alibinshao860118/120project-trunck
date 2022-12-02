<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:menu:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-delete-solid"
          size="mini"
          @click="deleteAll"
          >删除</el-button
        >
      </el-col>
    </el-row>
    <!-- 添加或修改菜单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="680px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="呼车单位名称" prop="dictValue">
              <el-input
                v-model="form.dictValue"
                placeholder="文本，50字以内"
                @input="change($event)"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="所属单位" prop="subordinateUnit">
              <el-input
                v-model="form.subordinateUnit"
                @input="change($event)"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="所属辖区" prop="jurisdictionArea">
              <el-select
                v-model="value"
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
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-table
      ref="multipleTable"
      :data="tableData"
      tooltip-effect="dark"
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" class-name="selectCheckBox">
      </el-table-column>
      <el-table-column label="序号" width="120" prop="index"> </el-table-column>
      <el-table-column prop="dictValue" label="呼车单位名称"> </el-table-column>
      <el-table-column
        prop="subordinateUnit"
        label="所属单位"
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
        prop="jurisdictionArea"
        label="所属辖区"
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
        prop="updateTime"
        label="修改时间"
        width="180"
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        width="140"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['system:menu:add']"
            >编辑</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:menu:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column>
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
  </div>
</template>

<script>
import {
  listMenu,
  getMenu,
  delMenu,
  addMenu,
  updateMenu,
  getNewData,
  addNewData,
  editNewData,
  deleteNewData,
} from "@/api/system/menu";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import IconSelect from "@/components/IconSelect";
import { MessageBox } from "element-ui";

export default {
  name: "Menu",
  dicts: ["sys_show_hide", "sys_normal_disable"],
  components: { Treeselect, IconSelect },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 菜单表格树数据
      menuList: [],
      // 菜单树选项
      menuOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // // 是否展开，默认全部折叠
      // isExpandAll: false,
      // 重新渲染表格状态
      refreshTable: true,
      // 表单参数
      form: {
        dictValue: "",
      },
      // 表单校验
      rules: {
        dictValue: [
          { required: true, message: "呼车单位不能为空", trigger: "blur" },
        ],
      },
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
      value: "",

      // 表格数据
      tableData: [
        {
          id: "1", // 数据id
          companyName: "1", // 呼车单位名称
          company: "1", // 所属单位
          index: "1", // 序号
          updateTime: "2020-03-02", // 修改时间
          jurisdiction: "越秀区1", // 所属辖区
        },
        {
          id: "2",
          companyName: "2",
          company: "2",
          index: "2",
          updateTime: "2020-03-02", // 修改时间
          jurisdiction: "越秀区2", // 所属辖区
        },
        {
          id: "3",
          companyName: "3",
          company: "3",
          index: "3",
          updateTime: "2020-03-02", // 修改时间
          jurisdiction: "越秀区3", // 所属辖区
        },
        {
          id: "4",
          companyName: "4",
          company: "4",
          index: "4",
          modifupdateTimeicationTime: "2020-03-02", // 修改时间
          jurisdiction: "越秀区4", // 所属辖区
        },
      ],
      // 选中的项的数据
      multipleSelection: [],
      // 分页器多少条每页
      pageSizes: [10, 20, 50],
      // 共多少页
      total: 300,
      // 每页显示条数
      pageSize: 10,
      // 当前页面
      currentPage: 1,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 取消按钮
    cancel() {
      this.deleteData();
      this.open = false;
    },
    deleteData() {
      this.form.dictValue = ""; //清空呼车单位名称
      this.form.subordinateUnit = ""; // 清空所属单位
      this.form.jurisdictionArea = ""; // 清空所属辖区
      this.dictValue = ""; //清空呼车单位名称
      this.subordinateUnit = ""; // 清空所属单位
      this.value = ""; // 清空所属辖区
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      if (row != null && row.id) {
        this.form.parentId = row.id;
        this.form.subordinateUnit = row.subordinateUnit;
        this.form.dictValue = row.dictValue;
        this.value = row.jurisdictionArea;
      } else {
        this.form.parentId = undefined;
        this.value = "";
      }
      this.open = true;
      this.title = "新增/编辑";
    },
    /** 提交按钮 */
    submitForm: function () {
      // 清空新增弹窗页面数据
      this.$refs["form"].validate((valid) => {
        if (valid) {
          console.log(this.form, "this.form");
          if (this.form.parentId != undefined) {
            let addData = {};
            addData.dictValue = this.form.dictValue;
            addData.subordinateUnit = this.form.subordinateUnit;
            addData.jurisdictionArea = this.value;
            addData.dictType = "1";
            addData.id = this.form.parentId;
            editNewData(addData).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.currentPage = 1;
              this.getList();

              this.deleteData();
            });
          } else {
            let addData = {};
            addData.dictValue = this.form.dictValue;
            addData.subordinateUnit = this.form.subordinateUnit;
            addData.jurisdictionArea = this.value;
            addData.dictType = "1";
            addNewData(addData).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.deleteData();
              this.currentPage = 1;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      let that = this;
      this.$modal
        .confirm("是否确认删除该行数据？")
        .then(function () {
          console.log(row);
          let data = [];
          data.push(row.id);
          deleteNewData(data)
            .then((res) => {
              console.log(res);
              that.$modal.msgSuccess("删除成功");
            })
            .then((res) => {
              that.currentPage = 1;
              that.getList();
            });
        })
        .catch(() => {});
    },
    // 删除按钮
    deleteAll() {
      if (this.multipleSelection.length == 0) {
        MessageBox.alert("请选择删除项", "系统提示", { type: "warning" });
        return;
      }
      let selectA = [];
      for (let i = 0; i < this.multipleSelection.length; i++) {
        selectA.push(this.multipleSelection[i].id);
      }
      let that = this;
      deleteNewData(selectA).then((res) => {
        that.$modal.msgSuccess("删除成功");
        console.log(res);
        this.currentPage = 1;
        this.getList();
      });
    },
    // 表格的页面选择框的值改变事件
    handleSelectionChange(val) {
      console.log(val);
      this.multipleSelection = val;
    },
    // 当前页面改变触发事件
    handleCurrentChange(val) {
      // 请求数据，渲染数据
      this.currentPage = val;
      console.log(`当前页: ${val}`);
      this.getList();
    },
    /** 查询菜单列表 */
    getList() {
      let queryParams =
        "?dictType=1&pageNum=" +
        this.currentPage +
        "&pageSize=" +
        this.pageSize;
      getNewData(queryParams).then((response) => {
        this.total = response.total;
        let startNum = (this.currentPage - 1) * parseInt(this.pageSize) + 1;
        let rowsData = this.getIndex(response.rows, startNum);
        this.tableData = rowsData;
      });
    },
    // 分页器值改变事件
    sizeChange(val) {
      this.pageSize = val;
      console.log(val);
      this.currentPage = 1;
      this.getList();
    },
    // 排序函数
    getIndex(data, startEnd) {
      for (let i = 0; i < data.length; i++) {
        data[i].index = startEnd + i;
      }
      return data;
    },
    // element dialog 偶尔不让编辑解决
    change(e) {
      this.$forceUpdate();
    },
  },
};
</script>
<style scoped>
</style>
