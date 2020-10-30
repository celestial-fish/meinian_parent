package com.lyl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lyl.constant.MessageConstant;
import com.lyl.entity.Result;
import com.lyl.pojo.Setmeal;
import com.lyl.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {
    @Reference
    private SetmealService setmealService;

    @RequestMapping("/findById")
    public Result findById(Integer id){
        Setmeal setmeal = setmealService.findById(id);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
    }


    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){
        List<Setmeal>list= setmealService.getSetmeal();
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,list);
    }
}
