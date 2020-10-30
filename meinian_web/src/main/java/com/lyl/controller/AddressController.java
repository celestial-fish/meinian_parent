package com.lyl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lyl.entity.PageResult;
import com.lyl.entity.QueryPageBean;
import com.lyl.entity.Result;
import com.lyl.pojo.Address;
import com.lyl.service.AddressService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Reference
    private AddressService addressService;

    @RequestMapping("/findAllMaps")
    public Map findAllMaps(){
        Map map1=new HashMap();
//        从数据库拿到公司地址
      List<Address>lists =  addressService.findAll();
//      创建一个对接前端数据的集合,存放经纬度 lng lat
      List<Map>gridnMaps = new ArrayList<>();
//       再创建一个存放分公司名字的集合 addressName
      List<Map> nameMaps=new ArrayList();
//遍历数据得到的所有哦数据 分别添加进入两个集合
        for (Address list : lists) {
            Map map = new HashMap<>();
            map.put("lng",list.getLng());
            map.put("lat",list.getLat());
            gridnMaps.add(map);

            Map nameMap=new HashMap();
            nameMap.put("addressName",list.getAddressName());
            nameMaps.add(nameMap);
        }
        map1.put("gridnMaps",gridnMaps);
        map1.put("nameMaps",nameMaps);
        return map1;
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult  = addressService.findPage(queryPageBean);
        return pageResult;
    }
    @RequestMapping("/addAddress")
    public Result addAddress(@RequestBody Address address){
        addressService.addAddress(address);
        return new Result(true,"地址保存成功");
    }

    @RequestMapping("/deleteById")
    public Result deleteById(Integer id){
        addressService.deleteById(id);
        return new Result(true,"删除成功");
    }
}
