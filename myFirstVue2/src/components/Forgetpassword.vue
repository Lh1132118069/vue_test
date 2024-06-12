<template>
    <div class="background">
        <div id="enroll-form">
            <div id="title">{{ msg }}</div>
            <el-form>
                <el-input class="user" readonly onfocus="this.removeAttribute('readonly');" v-model="forget_username" placeholder="请输入用户名" required></el-input>
                <el-input class="old_password" readonly onfocus="this.removeAttribute('readonly');" v-model="forget_password" placeholder="请输入密码" show-password required></el-input>
                <div class="centerdiv"></div>
                <el-input class="new_password" readonly onfocus="this.removeAttribute('readonly');" v-model="forget_new_password"  placeholder="请输入新密码" show-password required></el-input>
                <el-input class="confirm_password" readonly onfocus="this.removeAttribute('readonly');" v-model="forget_confirm_password"  placeholder="请确认新密码" show-password required></el-input>
            </el-form>
            <div class="footer">
              <el-button class="btn_change" @click="changePassword">修改密码</el-button>
              <el-button class="btn_returnlogin" @click="return_login">返回登陆界面</el-button>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'Enroll',
  data () {
    return {
      msg: '修改密码',
      forget_username:"",
      forget_password:"",
      forget_new_password:"",
      forget_confirm_password:"",
    }
  },
  created() {
    // 在组件创建时初始化 Axios 并绑定到 Vue 实例上
    this.axios = axios.create({
      baseURL: 'http://localhost:8081', // 后端 API 的地址
      timeout: 5000 // 请求超时时间
    });
  },
  methods:{
    return_login(){
      this.$router.push('/');
    },
    changePassword(){
      if(this.forget_new_password !== this.forget_confirm_password){
        this.$message({
            message: "密码不一致！",
            type: "warning",
          });
          return;
      }
      console.log(this.forget_username);
      console.log(this.forget_password);
      console.log(this.forget_new_password);

      this.axios({
        method:"post",
        url:"http://localhost:8081/user/changePassword",
        params:{ 
          username: this.forget_username, 
          old_password: this.forget_password,
          new_password:this.forget_new_password
        },
      }).then((res) => {
        if (res.data.code === "0") {
          // 修改密码成功处理
          console.log("1111111111")
          sessionStorage.setItem("userInfo", JSON.stringify(res.data.data));
          this.$router.push('/Login');
          this.$message({
            message: res.data.msg,
            type: "success",
          });
        } else {
          // 修改密码失败处理
          this.$message({
            message: "用户名或原始密码错误",
            type: "warning",
          });
        }
      }).catch((error) => {
        // 请求失败处理
        console.error('修改密码请求失败:', error);
        this.$message.error('修改密码失败，请稍后重试');
      });
    }
  }
}
</script>

<style scoped>

@media (min-width:800px) {
  #title{
    font-weight: normal;
    font-size: 30px;
    position: absolute;
    top:5%;
    left:43%
  }
  .alert{
    position: absolute;
    width:40%
  }

  .centerdiv{
	  position: absolute;
	  width:50px;
    left: 44%;
    top:22%;
    height:20%;
	  border-right: 3px solid rgb(137, 123, 123);
	  padding-bottom:50px;  
	  margin-bottom:-50px;  
  }


  .background{
      width: 100%;
      height:100%;
      background-size: 100% 100%;
      background-image: url("~@/assets/cool-background.svg");
      position: fixed;
      top:0;
      left:0
  }

  #enroll-form{
    position:absolute;
    width: 70%;
    height:50%;
    left: 15%;
    top:10%;
    background-color:rgba(255, 255, 255, 0.5);
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    overflow:hidden;
  }

  .user,.old_password{
      position: absolute;
      width: 35%;
      left: 10%;
      top:20%;
  }
  .old_password{
      top:45%;
  }

  .new_password,.confirm_password{
      position: absolute;
      width:35%;
      left:55%;
      top:20%;
  }
  .confirm_password{
      top:45%;
  }

  .footer{
    position: absolute;
    width: 100%;
    height:20%;
    top:80%;
    background-color: rgb(59, 59, 101);
    /*margin-top:2px;*/
  }

  .btn_change,.btn_returnlogin{
      position: absolute;
      width: 50%;
      height:100%;
      left: 0;
      top:0;
      background-color: rgb(59, 59, 101);
      border: none;
      font-size: 20px;
      font-style: normal;
      font-family: sans-serif;
      font-weight: 700;
  }

  .btn_returnlogin{
      left: 50%;
      margin-left:5px
  }
}

@media (max-width:800px){
  #title{
    font-weight: normal;
    font-size: 30px;
    position: absolute;
    top:5%;
    left:40%
  }
  .alert{
    position: absolute;
    width:40%
  }

  .background{
      width: 100%;
      height:100%;
      background-size: 100% 100%;
      background-image: url("~@/assets/cool-background.svg");
      position: fixed;
      top:0;
      left:0
  }

  #enroll-form{
    position:absolute;
    width: 60%;
    height:70%;
    min-width: 400px;
    min-height: 500px;
    left: 20%;
    top:10%;
    background-color:rgba(255, 255, 255, 0.5);
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    overflow:hidden;
  }

  .user,.old_password,.new_password,.confirm_password{
      position: absolute;
      width: 70%;
      left: 15%;
      top:20%;
  }
  .old_password{
      top:30%;
  }

  .new_password{
      top:40%;
  }
  .confirm_password{
      top:50%;
  }

  .footer{
    position: absolute;
    width: 100%;
    height:30%;
    top:70%;
    background-color: rgb(59, 59, 101);
    /*margin-top:2px;*/
  }

  .btn_change,.btn_returnlogin{
      position: absolute;
      width: 100%;
      height:50%;
      left: 0;
      top:0;
      background-color: rgb(59, 59, 101);
      border: none;
      font-size: 20px;
      text-align: center;
      vertical-align: auto;
      font-style: normal;
      font-family: sans-serif;
      font-weight: 700;
  }

  .btn_returnlogin{
      top:50%;
      
  }
}

</style>