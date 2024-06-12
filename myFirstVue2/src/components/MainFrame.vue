<template>
    <el-container class="container1">
        <el-aside class="aside" v-if="isAsideVisible">
            <el-menu router:default-active="MainFrame" @select="handleMenuSelect">
                <el-menu-item index="home" class="submenu">
                    <!--<template slot="title"><i class="el-icon-s-home"></i>主页</template>-->
                    <router-link tag="div" to="/MainFrame"><i class="el-icon-s-home"></i>主页</router-link>
                </el-menu-item>
                <el-submenu index="1" class="submenu">
                    <template slot="title"><i class="el-icon-setting"></i>功能菜单</template>
                    <el-menu-item index="user_manage">
                        <router-link tag="div" to="/user_manage">用户管理</router-link>
                    </el-menu-item>
                    <el-menu-item index="passage_manage">文章管理</el-menu-item>
                </el-submenu>
                <el-submenu index="2" class="submenu">
                    <template slot="title"><i class="el-icon-picture-outline"></i>背景设置</template>
                    <el-menu-item index="blue" ref="defaultColor" @click="changeColor('#869eed','#305ae2','#3f38bb')">蓝色</el-menu-item>
                    <el-menu-item index="yellow" @click="changeColor('#cdc58e', '#e7d272','#e19333')">黄色</el-menu-item>
                </el-submenu>
            </el-menu>
        </el-aside>
        <el-container class="container2">
            <el-header class="header">
                <el-dropdown>
                    <i class="el-icon-user-solid"></i>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item>查看</el-dropdown-item>
                        <el-dropdown-item>新增</el-dropdown-item>
                        <el-dropdown-item @click.native="quit">退出</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </el-header>
            <el-main class="main">
                <router-view/>
            </el-main>
            
        </el-container>
    </el-container>
</template>

<script>
 export default {
    mounted() {
        this.$refs.defaultColor.$el.click();
        window.addEventListener('resize', this.handleResize);
        this.handleResize(); // 初始化时执行一次
    },
    data() {
        return {
            value: new Date(),
            isAsideVisible: true
        }
    },
    setup() {
        let changeColor = (bgcolor,item_color,header_color) => {
            document.documentElement.style.setProperty("--bgcolor", bgcolor);
            document.documentElement.style.setProperty("--item_color", item_color);
            document.documentElement.style.setProperty("--header_color", header_color);
        };
        return { changeColor };
    },
    methods:{
        quit(){
            this.$message({
                message: "退出登录",
                type: 'success'
        });
            this.$router.push('/');
        },
        handleResize() {
            // 获取窗口宽度
            const screenWidth = window.innerWidth;
            // 根据窗口宽度来决定是否显示 <el-aside>
            this.isAsideVisible = screenWidth >= 800;
        }
    }
}
</script>

<style scoped>
 :root{
    --bgcolor:'#7f79ef';
    --item_color: '#3f38bb';
    --header_color: '#3b67a0';
}

    .container1{
        position: absolute;
        width:100%;
        height: 100%;
        top: 0;
        left: 0;
    }
    .container2{
        background: var(--bgcolor)
    }
    .aside{
        width:100px;
        float:left;
    }

    .submenu{
        background:var(--item_color);
        /*color: #e7d272;*/
    }
    .header{
        text-align: right; 
        font-size: 12px;
        background-color: var(--header_color);
        color: #d0c0c0;
        line-height: 60px;
    }
    .el-icon-user-solid{
        font-size: 25px;
        margin-right: 15px;
        margin-top: 21px
    }

</style>