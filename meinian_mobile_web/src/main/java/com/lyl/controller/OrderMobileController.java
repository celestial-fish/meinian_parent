package com.lyl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lyl.pojo.Order;
import com.lyl.constant.MessageConstant;
import com.lyl.entity.Result;
import com.lyl.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderMobileController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private OrderService orderService;

    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map){
//校验验证码  获取手机号和验证码
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
//        获取redis存储的验证码  根据手机号来取
        String redisInCode = jedisPool.getResource().get(telephone);
        if (redisInCode == null || !validateCode.equals(redisInCode)){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
//        设置预约类型
        map.put("orderType", Order.ORDERTYPE_WEIXIN);
//        下单
         return orderService.submit(map);

    }
}
