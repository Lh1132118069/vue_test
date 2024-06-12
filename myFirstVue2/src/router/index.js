import Vue from 'vue'
import Vuex from 'vuex'
import Router from 'vue-router'
import Login from '@/components/Login'
import Enroll from '@/components/Enroll'
import Forgetpassword from '@/components/Forgetpassword'
import MainFrame from '@/components/MainFrame'
import user_manage from '@/components/aside/user_manage'
import home from '@/components/aside/home'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path:'/',
      redirect: '/Login'
    },
    {
      path: '/Login',
      name: 'Login',
      component: Login
    },
    {
      path:'/Enroll',
      name:'Enroll',
      component:Enroll
    },
    {
      path:'/Forgetpassword',
      name:'Forgetpassword',
      component:Forgetpassword
    },
    {
      path:'/MainFrame',
      name:'MainFrame',
      component:MainFrame,
      children:[
        {
          path:'/',
          name:'home',
          component:home
        },
        {
          path:'/user_manage',
          name:'user_manage',
          component:user_manage
        }
      ]
    },
    
  ]

})


