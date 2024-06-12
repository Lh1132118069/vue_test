<template>
  <div>
    <div class="imageContainer">
          <img v-if="imageUrl" size="small" :src="imageUrl" alt="avatar" class="image">
    </div>
    <h2 class="welcome">欢迎{{ this.username }}</h2>
  <div class="show_table">
    <el-descriptions class="margin-top" :column="1" :items="userDescriptions"  border>
      <template slot="extra">
        <el-button type="primary" size="small">操作</el-button>
      </template>
        <el-descriptions-item class="username" v-model="username">
          <template slot="label"><i class="el-icon-user" >
          </i>用户名</template>
          {{this.userDescriptions.username}}
        </el-descriptions-item>
        <el-descriptions-item class="password"><template slot="label">
          <i class="el-icon-key"></i>密码</template>
          {{this.userDescriptions.password}}
        </el-descriptions-item>
     
        <el-descriptions-item><template slot="label"><i class="el-icon-date">
        </i>生日</template>
        {{this.userDescriptions.birthday}}
      </el-descriptions-item>
      <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-message"></i>
              邮箱
          </template>
            {{this.userDescriptions.email}}
      </el-descriptions-item>
    
    </el-descriptions> 
  </div>
</div>
</template>


<script>
import axios from 'axios';
import { mapState } from 'vuex';

  export default {
    data() {
      return {
        userDescriptions:{},
        avatarFile:null,
        username: '',
        uid:'',
        imageUrl:'',
      }
    },
    created() {
    // 在组件创建时初始化 Axios 并绑定到 Vue 实例上
    this.axios = axios.create({
      baseURL: 'http://localhost:8081', // 后端 API 的地址
      timeout: 5000 // 请求超时时间
    });
    const storedUser = sessionStorage.getItem('userInfo');
    const userObj = JSON.parse(storedUser);
    this.username = userObj.username;
    this.uid = userObj.uid
    this.getUserInfo();
  },
  methods:{
    getUserInfo(){
    // 发送请求到后端获取用户信息
     this.axios({
        method:"post",
        url:"http://localhost:8081/user/getInfo",
        params:{ username: this.username},
      }).then(response => {
        // 将后端返回的数据存储在userDescriptions中
        this.userDescriptions = response.data;
      })
      .catch(error => {
        console.error('Error fetching user info:', error);
      });

      console.log(this.userDescriptions)

      this.axios({
        method:"post",
        url:"http://localhost:8081/picture/getAvatar",
        params:{ uid: this.uid},
        responseType: 'arraybuffer'
      }).then(response => {
        this.avatarFile = new Uint8Array(response.data);
        console.log(this.avatarFile)
        this.imageUrl = window.URL.createObjectURL(new Blob([this.avatarFile],{type: 'image/jpeg'}));
      })
      .catch(error => {
        console.error('Error fetching user info:', error);
      });
    },
    },
  }
</script>

<style scoped>
.show_table{
  position: absolute;
  left:60%;
  top:20%;
  height:40%;
  width:39%;
  background-color: aliceblue;
  border-radius: 5px;
}
.imageContainer{
  position: absolute;
  left:30%;
  top:20%;
  width: 300px; /* 设置容器宽度 */
  height: 300px; /* 设置容器高度 */
  overflow: hidden; /* 隐藏溢出的部分 */
  border: 1px solid #ccc; /* 添加边框 */
}
.image{
  width: 100%; 
  height: 100%; 
  object-fit: cover; 
}
.welcome{
  position: absolute;
  left:36%;
  top:60%;
}
</style>