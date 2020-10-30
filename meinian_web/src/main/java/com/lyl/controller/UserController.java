package com.lyl.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.lyl.constant.MessageConstant;
import com.lyl.entity.Result;
import com.lyl.pojo.User;
import com.lyl.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    private UserService userService;

    @RequestMapping(value = "/getUsername")
    public Result getUsername(){
        try {
            // 从SpringSecurity中获取认证用户的信息
            User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }


}
