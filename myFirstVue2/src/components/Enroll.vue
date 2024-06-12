<template>
    <div class="background">
        <div id="enroll-form">
            <div class="title">{{ msg }}</div>
            <div class="input_part">
                <el-form v-model="enrollform" ref="enrollform">
                  <el-input class="user" readonly onfocus="this.removeAttribute('readonly');" v-model="enrollform.enroll_username" prop="enroll_username" placeholder="请输入用户名" required></el-input>
                  <el-input class="password" readonly onfocus="this.removeAttribute('readonly');" v-model="enrollform.enroll_password" prop="enroll_password" placeholder="请输入密码" show-password required></el-input>
                  <el-input class="email" readonly onfocus="this.removeAttribute('readonly');" v-model="enrollform.enroll_email" prop="enroll_email" placeholder="请输入邮箱" required></el-input>
                  <el-alert class="alert" v-if="enroll_email && !validEmail" title="请输入正确的邮箱格式" type="warning" :closable='false' show-icon></el-alert>
                  <el-date-picker class="select_date" value-format="yyyy-MM-dd" v-model="enrollform.value1" type="date" placeholder="请选择生日"></el-date-picker>
                </el-form>
            </div>
            <div class="centerdiv"></div>
            <div class="description">请上传头像</div>
            <div class="upload_imag">
             <el-upload
                class="avatar-uploader"
                action="http://localhost:8081/picture/uploadAvatar"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
                <img v-if="imageUrl" :src="imageUrl" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </div>
            <div class="footer">
              <el-button class="btn_enroll"  @click="enroll">注册</el-button>
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
      msg: '注册界面',
      enrollform:{
        enroll_username:"",
        enroll_password:"",
        enroll_email:"",
        value1: "",
        validEmail:true
      },
      enroll_email:"",
      imageUrl:'',
      avatarFile: null,
    }
  },
  computed: {
    emailRegex() {
      return /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
    }
  },
  watch: {
    enroll_email(value) {
      if (!this.emailRegex.test(value)) {
        this.validEmail = false;
      } else{
        this.validEmail = true;
      }
    },
  },
  created() {
    // 在组件创建时初始化 Axios 并绑定到 Vue 实例上
    this.axios = axios.create({
      baseURL: '/api', // 后端 API 的地址
      timeout: 5000 // 请求超时时间
    });
  },
  methods:{
    return_login(){
      this.$router.push('/Login');
    },
    enroll(){
      if(this.validEmail){
        this.$message({
          message: '请输入正确的邮箱',
          type: 'warning'
        });
        return;
      }
      const user = {
          username: this.enrollform.enroll_username, 
          password: this.enrollform.enroll_password,
          email: this.enrollform.enroll_email,
          birthday: this.enrollform.value1
      };
      console.log(this.enrollform.value1)
      this.axios({
        method:"post",
        url:"http://localhost:8081/user/regist",
        data:JSON.stringify(user),
        headers: {
          'Content-Type': 'application/json'
        }
      }).then((res) => {
        if (res.data.code === "0") {
          // 注册成功处理
          sessionStorage.setItem("userInfo", JSON.stringify(res.data.data));
          this.$router.push('/Login');
          this.$message({
            message: res.data.msg,
            type: "success",
          });
        } else {
          //注册失败处理
          this.$message({
            message: res.data.msg,
            type: "warning",
          });
        }
      }).catch((error) => {
        // 请求失败处理
        console.error('注册请求失败:', error);
        this.$message.error('注册失败，请稍后重试');
      });
    },
    handleAvatarSuccess(response,file) {
      this.avatarFile = file.raw;
      this.imageUrl = window.URL.createObjectURL(new Blob([this.avatarFile],{type: 'application/zip'}));
    },
    beforeAvatarUpload(file) {
				const isJPG = file.type === 'image/jpeg';
				const isLt2M = file.size / 1024 / 1024 < 2;

				if (!isJPG) {
					this.$message.error('上传头像图片只能是 JPG 格式!');
				}
				if (!isLt2M) {
					this.$message.error('上传头像图片大小不能超过 2MB!');
				}
				return isJPG && isLt2M;

			},
    }
  }
</script>

<style scoped>

@media (min-width:800px) {
  .alert{
    position: absolute;
    width:50%;
    top:-13%;
  }

  
  .title{
    font-weight: normal;
    font-size: 30px;
    position: absolute;
    top:5%;
    left:43%
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
    height:60%;
    min-height: 400px;
    min-width: 600px;
    left: 15%;
    top:10%;
    background-color:rgba(255, 255, 255, 0.5);
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    overflow:hidden;
  }

.centerdiv{
	  position: absolute;
	  width:50px;
    left: 44%;
    top:20%;
    height:28%;
	  border-right: 3px solid rgb(137, 123, 123);
	  padding-bottom:50px;  
	  margin-bottom:-50px;  
  }

  .upload_imag{
    position:absolute;
    width: 40%;
    height:80%;
    top:20%;
    left:55%;
  }

  .avatar-uploader,.el-upload {
    border: dashed 5px #3d2424;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader,.el-upload:hover {
    border-color: #1c2e3e;
  }
  
  .avatar-uploader-icon {
    font-size: 28px;
    color: #0a0e13;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
      width: 178px;
      height: 178px;
      display: block;
    }

  .footer{
    position:absolute;
    top:80%;
    height:20%;
    width:100%;
    background-color: rgb(59, 59, 101);
  }


  .input_part{
    position:absolute;
    width: 50%;
    height:80%;
    top:10%
  }

  .user,.password,.email,.select_date{
      position: absolute;
      width: 70%;
      left: 15%;
  }

  .user{
    top:10%;
  }

  .password{
      top:25%;
  }

  .email{
      top:40%;
  }

  .select_date{
      top:55%;
  }

/*.alert{
  position: absolute;
  width: 70%;
  left: 15%;
  height:15px;
  top:42%;
}*/


  .btn_enroll,.btn_returnlogin{
      position: relative;
      width: 50%;
      height:100%;
      border: none;
      background-color: rgb(59, 59, 101);
      font-size: 20px;
      font-style: normal;
    font-family: sans-serif;
    font-weight: 700;
  }
  .btn_enroll{
    left:-25%;
  }
  .btn_returnlogin{
    left:25%;
    top:-100%;
    margin-left:5px
  }

  .description{
    font-weight: normal;
    font-size:20px;
    position:absolute;
    top:12%;
    left:70%;
  }
}

@media (max-width:800px) {
  .title{
    font-weight: normal;
    font-size: 30px;
    position: absolute;
    top:5%;
    left:38%
  }



  .alert{
    position: absolute;
    width:50%;
    top:-25%;
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
    height:60%;
    min-height: 600px;
    min-width: 400px;
    left: 15%;
    top:10%;
    background-color:rgba(255, 255, 255, 0.5);
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    overflow:hidden;
  }

  .input_part{
    position:absolute;
    width: 100%;
    height:40%;
    top:10%
  }

  .footer{
    position:absolute;
    top:80%;
    height:20%;
    width:100%;
    background-color: rgb(59, 59, 101);
  }

  .user,.password,.email,.select_date{
      position: absolute;
      width: 70%;
      left: 15%;
  }

  .user{
    top:10%;
  }
  .password{
      top:30%;
  }
  .email{
      top:50%;
  }
  .select_date{
      top:70%;
  }

  .upload_imag{
    position:absolute;
    width: 60%;
    height:20%;
    top:48%;
    left:20%;
  }

  .avatar-uploader,.el-upload {
    border: dashed 5px #3d2424;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader,.el-upload:hover {
    border-color: #1c2e3e;
  }
  
  .avatar-uploader-icon {
    font-size: 28px;
    color: #0a1c32;
    width: 178px;
    height: 140px;
    line-height: 135px;
    text-align: center;
  }
  .avatar {
      width: 178px;
      height: 178px;
      display: block;
  }

  .btn_enroll,.btn_returnlogin{
      position: relative;
      width: 100%;
      height:50%;
      border: none;
      background-color: rgb(59, 59, 101);
      font-size: 20px;
      text-align: center;
      vertical-align: middle;
      font-style: normal;
    font-family: sans-serif;
    font-weight: 700;
  }

  /*.btn_enroll{
    left:-25%;
  }

  .btn_returnlogin{
    left:25%;
    top:-100%;
    margin-left:5px
  }*/

  .description{
    font-weight: normal;
    font-size:20px;
    position:absolute;
    top:50%;
    left:40%;
  }
}
</style>