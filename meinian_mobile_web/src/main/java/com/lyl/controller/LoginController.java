package com.lyl.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.lyl.pojo.Member;
import com.lyl.constant.MessageConstant;
import com.lyl.entity.Result;
import com.lyl.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
@Autowired
private JedisPool jedisPool;
    @Reference
private MemberService memberService;

    @RequestMapping("/check")
    public Result check(@RequestBody Map map, HttpServletResponse response){
//    根据手机号 去redis查询验证码是否正确， 再去会员表去查看看有没数据 两个同时成立就返回成功
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        String codeInRedis = jedisPool.getResource().get(telephone);
        if (codeInRedis == null || !validateCode.equals(codeInRedis)){
            //验证码输入错误
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }else {
            Member member = memberService.findByTelephone(telephone);
            if (member==null){
//            如果查不到会员信息，就直接跳注册
                member = new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                memberService.add(member);
            }
//        两个都符合，就直接登录成功
            //写入Cookie，跟踪用户
            Cookie cookie = new Cookie("login_member_telephone",telephone);
            cookie.setPath("/");//路径
            cookie.setMaxAge(60*60*24*30);//有效期30天（单位秒）
            response.addCookie(cookie);
            return new Result(true,MessageConstant.LOGIN_SUCCESS);
        }


    }


}
