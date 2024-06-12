package com.whut.login_demo1.controller;

import com.whut.login_demo1.domain.Picture;
import com.whut.login_demo1.domain.User;
import com.whut.login_demo1.service.PicService;
import com.whut.login_demo1.utils.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Optional;

@RestController
@RequestMapping("/picture")
public class PicController {
    @Resource
    private PicService picService;

    @PostMapping("/uploadAvatar")
    public Result<Picture> registPicController(@RequestParam MultipartFile file){
        Picture user = picService.registPicService(file);
        if(user!=null){
            return Result.success(user,"注册成功！");
        }else{
            return Result.error("456","用户名已存在！");
        }
    }

    @PostMapping("/getAvatar")
    public byte[] getAvaInfo(@RequestParam Long uid){
        return (picService.getAvatarService(uid)).getAvatar();
    }
}
