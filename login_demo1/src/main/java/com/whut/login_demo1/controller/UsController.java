package com.whut.login_demo1.controller;
import com.whut.login_demo1.domain.Us;
import com.whut.login_demo1.service.UsService;
import com.whut.login_demo1.utils.Result;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/us")
public class UsController {
    @Resource
    private UsService usService;

    @PostMapping("/getInfo")
    public List<Us> getUsInfo(){
        return usService.getInfoService();
    }

    @PostMapping("/addUs")
    public Result<Us> addUs(@RequestBody @DateTimeFormat(pattern="yyyy-MM-dd")Us user){
        Us us = usService.addUsService(user);
        if(us != null){
            return Result.success(user,"添加成功！");
        }else{
            return Result.error("246","添加失败！");
        }
    }

    @PostMapping("/updateUs")
    public Result<Us> updateUs(@RequestBody @DateTimeFormat(pattern="yyyy-MM-dd") Us us){
        Us user = usService.updateUsService(us);
        if(user != null){
            return Result.success(user,"修改成功！");
        }else{
            return Result.error("246","修改失败！");
        }
    }

    @PostMapping("/deleteUs")
    public Result<Us> deleteUs(@RequestParam Long id){
        Us user = usService.deleteUsService(id);
        if(user != null){
            return Result.success(user,"修改成功！");
        }else{
            return Result.error("246","修改失败！");
        }
    }

}