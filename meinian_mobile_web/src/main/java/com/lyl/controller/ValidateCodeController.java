package com.lyl.controller;

import com.lyl.constant.MessageConstant;
import com.lyl.entity.Result;
import com.lyl.utils.SMSUtils;
import com.lyl.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){
//        生成验证码
        Integer code = ValidateCodeUtils.generateValidateCode(6);
//        发送短息
        try {
            SMSUtils.sendShortMessage(telephone,code.toString());
        } catch (Exception e) {

            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        System.out.println("code--------------->"+code);
//        将验证码暂时存到redis里面，并且设置过期时间
        jedisPool.getResource().setex(telephone,5*60,code.toString());
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

}
