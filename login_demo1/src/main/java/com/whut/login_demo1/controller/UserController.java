package com.whut.login_demo1.controller;

import com.whut.login_demo1.domain.User;
import com.whut.login_demo1.service.UserService;
import com.whut.login_demo1.utils.Result;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result<User> loginController( @RequestParam String username, @RequestParam String password){
        User user = userService.loginService(username, password);
        System.out.println(user);
        if(user!=null){
            return Result.success(user,"登录成功！");
        }else{
            return Result.error("123","账号或密码错误！");
        }
    }

    @PostMapping("/regist")
    public Result<User> registController(@RequestBody @DateTimeFormat(pattern="yyyy-MM-dd") User newUser){
        User user = userService.registService(newUser);
        if(user!=null){
            return Result.success(user,"注册成功！");
        }else{
            return Result.error("456","用户名已存在！");
        }
    }


    @PostMapping("/getInfo")
    public User getUserInfo(@RequestParam String username){
        return userService.getInfoService(username);
    }

    @PostMapping("/changePassword")
    public Result<User> changePassword(@RequestParam String username, @RequestParam String old_password, @RequestParam String new_password){
        User user =  userService.changePasswordService(username,old_password,new_password);
        if(user!=null){
            return Result.success(user,"修改密码成功！");
        }else{
            return Result.error("789","修改密码失败！");
        }
    }
}

