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
          <el-form-item :label="tableTitle" prop="dictValue">
            <el-input
              type="textarea"
              placeholder=""
              v-model="form.dictValue"
              maxlength="100"
              show-word-limit
              @input="change($event)"
            >
            </el-input>
          </el-form-item>
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
      <el-table-column prop="dictValue" :label="tableTitlename">
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
        style="float: right"
        :page-size="pageSize"
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
      // 表单校验
      rules: {
        dictValue: [
          { required: true, message: "暴露风险名称不能为空", trigger: "blur" },
        ],
      },
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

      // 表格数据
      tableData: [],
      // 选中的项的数据
      multipleSelection: [],
      // 分页器多少条每页
      pageSizes: [20, 50, 100],
      // 共多少页
      total: 300,
      // 每页显示条数
      pageSize: 20,
      // 当前页面
      currentPage: 1,
      // 新增弹窗的高危因素
      dictValue: "",
      tableTitlename: "暴露风险名称",
      tableTitle: "暴露风险名称",
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
      this.form.dictValue = ""; //清空新增弹窗的值
      this.dictValue = "";
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      if (row != null && row.id) {
        this.form.parentId = row.id;
        this.form.dictValue = row.dictValue;
      } else {
        this.form.parentId = undefined;
        this.form.dictValue = "";
      }
      this.open = true;
      this.title = "新增/编辑";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.getTreeselect();
      getMenu(row.menuId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改菜单";
      });
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
            addData.dictType = "4";
            addData.id = this.form.parentId;
            editNewData(addData).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
              this.deleteData();
            });
          } else {
            let addData = {};
            addData.dictValue = this.form.dictValue;
            addData.dictType = "4";
            addNewData(addData).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.deleteData();
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
    },
    /** 查询菜单列表 */
    getList() {
      let queryParams =
        "?dictType=4&pageNum=" +
        this.currentPage +
        "&pageSize=" +
        this.pageSize;
      getNewData(queryParams).then((response) => {
        console.log(response, "123123123");
        this.total = response.total;
        let startNum = (this.currentPage - 1) * parseInt(this.pageSize) + 1;
        let rowsData = this.getIndex(response.rows, startNum);
        this.tableData = rowsData;
      });
    },
    // 分页器值改变事件
    sizeChange(val) {
      this.pageSize = val;
      this.getList();
    },
    // 排序函数
    getIndex(data, startEnd) {
      for (let i = 0; i < data.length; i++) {
        data[i].index = startEnd + i;
      }
      return data;
    },
    // 解决element dialog 偶尔不让编辑文件
    change(e) {
      this.$forceUpdate();
    },
  },
};
</script>
<style scoped>
</style>
