package com.lyl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lyl.pojo.TravelItem;
import com.lyl.constant.MessageConstant;
import com.lyl.entity.PageResult;
import com.lyl.entity.QueryPageBean;
import com.lyl.entity.Result;
import com.lyl.service.TravelItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/travelItem")
public class TravelItemController {
    @Reference
    private TravelItemService travelItemService;

    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('TRAVELITEM_ADD')")//权限校验
    public Result add(@RequestBody TravelItem travelItem){
        travelItemService.add(travelItem);

        return  new Result(true, MessageConstant.ADD_MEMBER_SUCCESS);
    }

    @RequestMapping("/findPage")
    @PreAuthorize("hasAuthority('TRAVELITEM_QUERY')")//权限校验
//  @RequestBody 是springMVC的注解，如果是post请求 而且传入来的请求体与 参数类的字段属性对应，可以映射直接赋值给实体bean
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = travelItemService.findPage(queryPageBean);
        return pageResult;
    }

    @RequestMapping("/deleteById")
    @PreAuthorize("hasAuthority('TRAVELITEM_DELETE111')")//权限校验，使用TRAVELITEM_DELETE123测试
    public Result deleteById(Integer id){
        travelItemService.deleteById(id);
        return  new Result(true, MessageConstant.DELETE_TRAVELITEM_SUCCESS);
    }
    @RequestMapping("/findById")
    public Result findById(Integer id){
        TravelItem travelItem  = travelItemService.findById(id);
        return new Result(true,MessageConstant.QUERY_TRAVELITEM_SUCCESS,travelItem);
    }

    @RequestMapping("/edit")
    @PreAuthorize("hasAuthority('TRAVELITEM_EDIT')")//权限校验
    public Result edit(@RequestBody TravelItem travelItem){
        travelItemService.edit(travelItem);
        return new Result(true,MessageConstant.EDIT_TRAVELITEM_SUCCESS);
    }

    @RequestMapping("/findAll")
    public Result findAll(){
         List<TravelItem>lists = travelItemService.findAll();
        return new Result(true,MessageConstant.QUERY_TRAVELGROUP_SUCCESS,lists);
    }

}
