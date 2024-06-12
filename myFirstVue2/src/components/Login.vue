<template>
  <div class="background">
    <div class="login">
      <div id="title">{{ msg }}</div>
      <div id="input_login" class="input_login" >
        <el-form v-model="loginform" :rules="rules" ref="loginform">
          <el-input class="input_user" readonly onfocus="this.removeAttribute('readonly');" 
          v-model="loginform.username" prop="username" placeholder="请输入账号">
        </el-input>
          <el-input class="input_password" readonly onfocus="this.removeAttribute('readonly');" 
          placeholder="请输入密码" v-model="loginform.password" prop="password" show-password>
        </el-input>
          <el-button class="btn_login" type="primary"  @click="login">登录</el-button>
          
        </el-form>
      </div>
      <div class="centerdiv"></div>
      <div id="quick_login" class="quick_login">
        <el-button class="qq_login" @click="quickLogin('QQ登录成功')">
          <img id="qq_icon" class="qq_icon" src="@/assets/qq.png" alt="qq_login">
          <span id="description_qq_login">QQ登录</span>
        </el-button>

        <el-button class="wechat_login" @click="quickLogin('微信登录成功')">
          <img id="wechat_icon" class="vx_icon" src="@/assets/vx.png" alt="wechat_login">
          <span id="description_wechat_login">微信登录</span>
        </el-button>

        <el-button class="paypal_login"  @click="quickLogin('支付宝登录成功')">
          <img id="paypal_icon" class="paypal_icon" src="@/assets/paypal.png" alt="paypal_login">
          <span id="description_paypal_login">支付宝登录</span>
        </el-button>
      </div>
      <div class="footer">
          <el-button id="btn_enroll" class="btn_enroll" @click="goToEnroll">注册</el-button>
          <el-button id="btn_forgetpassword" class="btn_forgetpassword" @click="goToForgetpassword">忘记密码</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { mapState, mapMutations } from 'vuex';

export default {
  name: 'Login',
  data () {
    return {
      msg: '登陆界面',
      loginform:{
        username:"",
        password:""
      },
      rules: {
        username: [
          { required: true, message: "用户名不能为空！", trigger: "blur" },
        ],
        password: [
          { required: true, message: "密码不能为空！", trigger: "blur" },
        ],
      },
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
    login() {
      // 检查用户名和密码是否为空
      if (!this.loginform.username || !this.loginform.password) {
        this.$message({
          message: '用户名与密码不能为空',
          type: 'warning'
        });
        return;
      }
      const data = { username: this.loginform.username, password: this.loginform.password };
      // 发送登录请求
      this.axios({
        method:"post",
        url:"http://localhost:8081/user/login",
        params:{ username: this.loginform.username, password: this.loginform.password },
      }).then((res) => {
        if (res.data.code === "0") {
          // 登录成功处理
          //console.log("1111111111")
          sessionStorage.setItem("userInfo", JSON.stringify(res.data.data));
          this.$router.push('/MainFrame');
          this.$message({
            message: res.data.msg,
            type: "success",
          });
        } else {
          // 登录失败处理
          this.$message({
            message: res.data.msg,
            type: "warning",
          });
        }
      }).catch((error) => {
        // 请求失败处理
        console.error('登录请求失败:', error);
        this.$message.error('登录失败，请稍后重试');
      });
    },

    quickLogin(value){
      this.$message({
          message: value,
          type: 'success'
        });
      this.$router.push('/MainFrame');
    },
    goToEnroll(){
      this.$router.push('/Enroll');
    },
    goToForgetpassword(){
      this.$router.push('/Forgetpassword');
    }
  }
}
</script>

<style scoped>
#title{
  font-weight: normal;
  font-size: 30px;
  position: absolute;
  top:5%;
  left:43%
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
@media (min-width:650px) {
  .login{
    position:absolute;
    width: 60%;
    height:50%;
    left: 20%;
    top:15%;
    background-color:rgba(255, 255, 255, 0.5);
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    min-width:850px;
    min-height: 350px;
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

  .centerdiv{
	  position: absolute;
	  width:50px;
    left: 44%;
    top:20%;
    height:28%;
	  border-right: 3px solid rgb(42, 48, 106);
	  padding-bottom:50px;  
	  margin-bottom:-50px;  
  }

  .footer{
    position:absolute;
    width:100%;
    top:85%;
    height:15%;
    background-color:rgb(59, 59, 101);
  }

  .quick_login,.input_login{
    position:absolute;
    width:48%;
    top:12%;
    height:80%;
  }

  .input_login{
    left: 52%;
  }

  .quick_login{
    right: 52%;
  }

/*.input_option{
  position: absolute;
  width:60%;
  left:10%;
}
*/
  .input_user{
    position: absolute;
    width:60%;
    left:10%;
    top: 10%;
  }

  .input_password{
    position: absolute;
    width:60%;
    left:10%;
    top: 30%;
  }

  .btn_login{
    position: absolute;
    width:60%;
    left:10%;
    top: 50%;
    font-style: normal;
    font-family: sans-serif;
    font-weight: 700;
  }

  .qq_login,.wechat_login,.paypal_login{
    position: absolute;
    text-align: left;
    width: 60%;
    height: 13%;
    right:10%;
    /*border: solid 1px;
    background-color: white;
    border-radius: 10px;*/
    border: none;
    border-radius: 10px;
    overflow:hidden;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
  }

  .qq_login{
    top: 10%;
  }


  #description_qq_login{
    position: absolute;
    left: 40%;
    top: 30%;
    font-style: normal;
    font-family: sans-serif;
    font-weight: 700;
    }

  .wechat_login{
    top: 30%;
  }

  #description_wechat_login{
    position: absolute;
    left: 40%;
    top: 30%;
    font-style: normal;
    font-family: sans-serif;
    font-weight: 700;
  }

  .paypal_login{
    top: 50%;
  }

  #description_paypal_login{
    position: absolute;
    left: 38%;
    top: 30%;
    font-style: normal;
    font-family: sans-serif;
    font-weight: 700;
    
  }

  .qq_icon,.vx_icon,.paypal_icon{
    position:absolute;
    left:5px;
    top:1px;
    height: 32px;
    width:35px;
  }
  .qq_icon{
    height:30px;
  }


  .btn_enroll,.btn_forgetpassword{
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

  .btn_forgetpassword{
      left: 50%;
      margin-left:5px
  }
}

@media (max-width:650px) {
  #title{
    font-weight: normal;
    font-size: 25px;
    position: absolute;
    left:40%;
    top:3%;
    
  }

  .login{
    position:absolute;
    width: 60%;
    height:500px;
    left: 15%;
    top:15%;
    background-color:rgba(255, 255, 255, 0.5);
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    min-width:450px;
    min-height: 400px;
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
 

  .input_login,.quick_login{
    position:absolute;
    width:100%;
    height:35%;
  }

  .input_login{
    top:10%;
  }

  .quick_login{
    top:47%;
  }

  .input_user,.input_password,.btn_login{
    position: absolute;
    width:70%;
    left:15%;
  }

  .input_user{
    top:10%;
  }

  .input_password{
    top: 40%;
  }

  .btn_login{
    top: 70%;
    font-style: normal;
    font-family: sans-serif;
    font-weight: 700;
  }

  .qq_login,.wechat_login,.paypal_login{
    position: absolute;
    width: 70%;
    height: 25%;
    left:15%;
    border-radius: 10px;
    overflow:hidden;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
  }

  .qq_login{
    top: 0%;
  }

  #description_qq_login{
    position: absolute;
    left: 40%;
    top: 30%;
    font-style: normal;
    font-family: sans-serif;
    font-weight: 700;
    }

  .wechat_login{
    left: 13%;
    top: 30%;
  }

  #description_wechat_login{
    position: absolute;
    left: 40%;
    top: 30%;
    font-style: normal;
    font-family: sans-serif;
    font-weight: 700;
  }

  .paypal_login{
    top: 60%;
    left: 13%;
  }

  #description_paypal_login{
    position: absolute;
    left: 38%;
    top: 30%;
    font-style: normal;
    font-family: sans-serif;
    font-weight: 700;
  }

  .qq_icon,.vx_icon,.paypal_icon{
    position:absolute;
    left:5%;
    top:5px;
    height: 30px;
    width:30px;
  }

  .footer{
    position:absolute;
    width:100%;
    top:80%;
    height:20%;
    background-color:rgb(59, 59, 101);
    overflow: hidden;
  }

  #enroll,#forget_password{
    position: absolute;
    top: 32%;
  }
  #enroll{
    left:20%
  }

  #forget_password{
    left:65%
  }
  .btn_enroll,.btn_forgetpassword{
      position: absolute;
      width: 100%;
      height:50%;
      left:0;
      top:0;
      border: none;
      background-color: rgb(59, 59, 101);
      font-size: 20px;
      text-align: center;
      font-style: normal;
      font-family: sans-serif;
      font-weight: 700;
  }

  .btn_forgetpassword{
    top:50%;
    left:-2%;
  }
}
</style>
