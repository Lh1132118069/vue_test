package com.whut.login_demo1.service.serviceImpl;

import com.whut.login_demo1.domain.User;
import com.whut.login_demo1.repository.UserDao;
import com.whut.login_demo1.service.UserService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;


@Service
public class UserServiceImpl implements UserService {
        @Resource
        private UserDao userDao;

        @Override
        public User loginService(String username, String password) {
            // 如果账号密码都对则返回登录的用户对象，若有一个错误则返回null
            User user = userDao.findByUsernameAndPassword(username, password);
            // 重要信息置空
            if(user != null){
                user.setPassword("");
            }
            return user;
        }

        @Override
        public User registService(User user) {
            //当新用户的用户名已存在时
            if(userDao.findByUsername(user.getUsername())!=null){
                // 无法注册
                return null;
            }else{
                if (user.getPassword() == null || user.getPassword().isEmpty()) {
                    // 密码为空，无法注册
                    System.out.println(user.getUsername());
                    System.out.println("fuck u");
                    return null;
                }
                //返回创建好的用户对象(带uid)
                System.out.println(user.getBirthday());
                System.out.println(user.getPassword());
                User newUser = userDao.save(user);
                if(newUser != null){
                    newUser.setPassword("");
                }
                return newUser;
            }
        }


        @Override
        public User getInfoService(String username){
            User user = userDao.findByUsername(username);
            return user;
        }

        @Override
        public User changePasswordService(String username,String old_password,String new_password){
            User user = userDao.findByUsernameAndPassword(username,old_password);
            System.out.println("修改密码！！！！！");
            if(user != null){
                user.setPassword(new_password);
                userDao.save(user);
            }
            return user;
        }
    }
