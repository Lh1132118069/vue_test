import request from "/plugins/axios"
export default{
    login(userInfo){
        return request({
          url:'/',
          method:'get',
          params:userInfo 
        })
    }
}