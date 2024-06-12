<template>
  <div class="container">
    <div class="background">
      <h1 class="title">待办事项管理</h1>
      <el-button type="primary" class="addnew" @click="handleAdd">新增</el-button>

      <el-dialog :title="title" :visible.sync="open">
        <el-form ref="form" class="form" :model="form" :rules="rules" label-width="180px">
          <el-form-item label="日期" prop="date" >
            <el-date-picker class="input" v-model="form.date" type="datetime" value-format="yyyy-MM-dd" placeholder="日期"></el-date-picker>
          </el-form-item>
          <el-form-item label="主题" prop="subject">
            <el-input class="input" v-model="form.subject"></el-input>
          </el-form-item>
          <el-form-item label="内容" prop="content">
            <el-input
              class="input"
              type="textarea"
              autosize
              placeholder="请输入内容"
              v-model="content">
            </el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button class="sure_bt" type="primary" @click="submitForm">确定</el-button>
          <el-button class="cancel_bt" @click="emptyform">重置</el-button>
        </div>
      </el-dialog>

    <el-input v-model="search" class="search" size="mini" @input="handleQuery" placeholder="输入关键字搜索"/>
    <el-table ref="fliterTable" :data="tableData.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
      style="width: 100%" border default-width="150px">
    <el-table-column label="id" prop="id" align="center"></el-table-column>
    <el-table-column label="日期" prop="date" align="center"></el-table-column>
    <el-table-column label="主题" prop="subject" width="180" align="center"></el-table-column>
    <el-table-column label="内容" prop="content" align="center" width="500"></el-table-column>
      <el-table-column>
        <template slot-scope="scope">
          <div class="button-container">
            <el-button class="row_button" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>
            <el-button class="row_button" size="mini" type="danger" @click="handleDelete(scope.$index,scope.row)">删除</el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <div class="block">
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[8, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="this.tableData.length">
      </el-pagination>
    </div>
  </div>
</div>
</template>

<script>
import axios from 'axios';
  export default {
    data() {
      return {
        tableData: [],
        originalData:[],
        form:{
          id:'',
          date:'',
          subject:'',
          content:'',
          isDisabled:true,
        },
        title:"",     //标题
        type:0,       //0表示新增，1表示修改
        search: '',
        currentPage:1,
        pageSize:8,
        open:false,   //是否弹出表单
        rules:{
          date:[
            {required:true,message:"日期不能为空",trigger:"blur"}
          ],
          subject:[
            {required:true,message:"主题不能为空",trigger:"blur"}
          ],
          content:[
            {required:true,message:"内容不能为空",trigger:"blur"}
          ]
        }
      }
    },
    created(){
        // 在组件创建时初始化 Axios 并绑定到 Vue 实例上
      this.axios = axios.create({
        baseURL: 'http://localhost:8081', // 后端 API 的地址
        timeout: 5000 // 请求超时时间
      });
      this.fetchData();
    },
    watch: {
      search(newValue) {
        if (!newValue) {
          this.tableData = JSON.parse(sessionStorage.getItem('usInfo'));
        }
      }
    },
    methods: {
      fetchData(){
        this.axios({
        method:"post",
        url:"http://localhost:8081/us/getInfo",
        params:{}
      }).then((res) => {
        if (res.data) {
          this.tableData=res.data
          this.originalData = this.tableData
          sessionStorage.setItem("usInfo", JSON.stringify(res.data));
        } else {
          // 登录失败处理
          this.$message({
            message: "获取数据失败！",
            type: "warning",
          });
        }
      }).catch((error) => {
        // 请求失败处理
        console.error('获取数据失败:', error);
        this.$message.error('获取数据失败，请检查网络');
      });
    },

    submitForm(){
      if(this.type === 0){
      const us = {
        date:this.form.date,
        subject:this.form.subject,
        content:this.form.content,
      };
      this.axios({
        method:"post",
        url:"http://localhost:8081/us/addUs",
        data:JSON.stringify(us),
        headers: {
          'Content-Type': 'application/json'
        }
      }).then((res) => {
        console.log(us);
        if (res.data.code === "0") {
          // 添加成功处理
          this.$message({
            message: res.data.msg,
            type: "success",
          });
          this.fetchData();
        } else {
          //添加失败处理
          this.$message({
            message: res.data.msg,
            type: "warning",
          });
        }
      }).catch((error) => {
        // 请求失败处理
        console.log(us);
        console.error('添加用户请求失败:', error);
        this.$message.error('添加用户失败，请稍后重试');
      });
    }else{
      const us = {
        id:this.form.id,
        date:this.form.date,
        subject:this.form.subject,
        content:this.form.content,
      };
      this.axios({
        method:"post",
        url:"http://localhost:8081/us/updateUs",
        data:JSON.stringify(us),
        headers: {
          'Content-Type': 'application/json'
        }
      }).then((res) => {
        console.log(us);
        if (res.data.code === "0") {
          // 修改成功处理
          this.$message({
            message: "修改成功",
            type: "success",
          });
          this.fetchData();
        } else {
          //修改失败处理
          this.$message({
            message: "修改失败",
            type: "warning",
          });
        }
      }).catch((error) => {
        // 请求失败处理
        console.log(us);
        console.error('修改用户请求失败:', error);
        this.$message.error('修改用户失败，请稍后重试');
      });
    }
  },

    handleAdd(){
      this.emptyform();
      this.type=0;
      this.title="新增待办事项";
      this.open=true;
    },

    emptyform(){
      this.form={
        id:'',
        date:'',
        subject:'',
        content:'',
      }
    },
    handleUpdate(data) {
      this.type = 1
      this.title = "修改代办事项"
      this.open = true
      this.form.id = data.id
      this.form.subject = data.subject
      this.form.content = data.content
    },
    handleDelete(index,data) {
      this.$confirm('此操作将永久删除该用户,是否继续？',
        {
          confirmButtonText:'确定',
          cancelButtonText:'取消',
          type:'warning'
        }).then(()=>{
          this.axios({
            method:"post",
            url:"http://localhost:8081/us/deleteUs",
            params:{id:data.id}
        }).then((res) => {
          if (res.data.code === "0") {
            // 删除成功处理
            this.$message({
              message: '删除成功',
              type: "success",
            });

            this.tableData.splice(index, 1);
            if (this.tableData.length % this.pageSize === 0) {
                this.currentPage--; // 切换到上一页
            }
          } else {
            //删除失败处理
            this.$message({
              message: '删除失败',
              type: "warning",
            });
          }
        }).catch((error) => {
          // 请求失败处理
          console.log(us);
          console.error('删除用户请求失败:', error);
          this.$message.error('删除用户失败，请稍后重试');
        });
          }
      )
    }, 

    handleQuery(){
        const filteredData = this.originalData.filter(
        data => {
          for (const key in data) {
            if (data.hasOwnProperty(key) && String(data[key]).toLowerCase().includes(this.search.toLowerCase())) {
              return true; // 如果任意一个属性包含搜索关键字，则返回 true
            }
          }
          return false; // 如果没有任何属性包含搜索关键字，则返回 false
        }
      );
      this.tableData = filteredData;
    },

    handleCurrentChange(cpage) {
      this.currentPage = cpage;
    },

    handleSizeChange(psize) {
      this.pageSize = psize;
    },

    provinceChange(){
      this.form.city='';
      this.cList = []; // 先清空城市列表
      this.pList.forEach(item => {
          if (item.pro === this.form.province) {
              this.cList = item.cities;
          }
      });
    }
  }
}
</script>

<style scoped>
.search{
  position: absolute;
  width:20%;
  left:78%;
}
.input{
  position:relative;
  width:70%;
  left:-15%;
}
.button-container {
  display: flex;
  align-items: center; /* 垂直居中 */
  margin-left: -10px;
}

.button-container .row_button {
  margin-right: 10px; /* 按钮之间的间距 */
}
.table{
  position:relative;
  top:10%;
  border-radius: 5px;
}
.title{
  position:relative;
  top:20px
}
.addnew{
  position:relative;
  left:-45%;
  height: 35px;
  text-align: center;
}

.background{
  background-color: white;
  position: relative;
  height: 80%;
}

.sure_bt{
  position: relative;
  left:-45%;
}

.cancel_bt{
  position: relative;
  left:-35%
}
</style>