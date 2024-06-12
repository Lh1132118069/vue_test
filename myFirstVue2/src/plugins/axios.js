import axios from 'axios'

const service= axios.create(
    {
        baseURL:"http://localhost:8080",
        timeout:5000
    }
)

//请求拦截
service.interceptors.request.use(
    config=>{},
    error=>{}
)
service.interceptors.response.use(
    response=>{},
    error=>{}
)

export default service
