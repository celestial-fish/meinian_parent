package com.lyl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lyl.pojo.OrderSetting;
import com.lyl.constant.MessageConstant;
import com.lyl.entity.Result;
import com.lyl.service.OrdersettingService;
import com.lyl.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private OrdersettingService ordersettingService;

    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
         ordersettingService.editNumberByDate(orderSetting);
         return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
    }


//    此时传递来的data= 2020-10 是个字符串
    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){
        List<Map>list = ordersettingService.getOrderSettingByMonth(date);
        return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);

    }


    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile){
//    使用解析类解析要上传的内容 读取到一个集合，每一行是个string数组
        try {
            List<String[]> strings = POIUtils.readExcel(excelFile);
//            把string类型转换成实体类List<OrderSetting>
            List<OrderSetting>lists = new ArrayList<>();
            for (String[] string : strings) {
//                将所有的OrderSetting对象封装到新的list集合里面
                OrderSetting orderSetting = new OrderSetting(new Date(string[0]),Integer.parseInt(string[1]));
                lists.add(orderSetting);
            }
            ordersettingService.add(lists);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.ORDERSETTING_FAIL);
        }

    }
}
