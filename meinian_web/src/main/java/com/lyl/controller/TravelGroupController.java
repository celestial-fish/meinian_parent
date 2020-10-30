package com.lyl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lyl.entity.PageResult;
import com.lyl.entity.QueryPageBean;
import com.lyl.entity.Result;
import com.lyl.pojo.TravelGroup;
import com.lyl.constant.MessageConstant;
import com.lyl.service.TravelGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/travelgroup")
public class TravelGroupController {
    @Reference
    private TravelGroupService travelGroupService;

    @RequestMapping("/add")
    //请求传来的参数 与之后端的类对应 可以直接接收数据  ，这里主要接收报团的信息 和要报旅游项目的id信息
    public Result add(@RequestBody TravelGroup travelGroup, Integer[] travelItemIds){
        travelGroupService.add(travelGroup,travelItemIds);
        return new Result(true, MessageConstant.ADD_MEMBER_SUCCESS);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult  = travelGroupService.findPage(queryPageBean);
        return pageResult;

    }


    @RequestMapping("/findById")
    public Result findById(Integer id){
        TravelGroup travelGroup = travelGroupService.findById(id);
        return new Result(true,MessageConstant.QUERY_TRAVELGROUP_SUCCESS,travelGroup);
    }

    @RequestMapping("/findTravelItemIdByTravelgroupId")
    public List<Integer> findTravelItemIdByTravelgroupId(Integer id){
        List<Integer>list = travelGroupService.findTravelItemIdByTravelgroupId(id);
        return list;
    }

    @RequestMapping("/edit")
    public Result edit(Integer[]travelItemIds,@RequestBody TravelGroup travelGroup){
        travelGroupService.edit(travelItemIds,travelGroup);
        return new Result(true,MessageConstant.EDIT_TRAVELGROUP_SUCCESS);
    }

    @RequestMapping("/findAll")
    public Result findAll(){
        List<TravelGroup>list = travelGroupService.findAll();
        if (list != null && list.size() > 0){
            return new Result(true,MessageConstant.QUERY_TRAVELGROUP_SUCCESS,list);
        }
        return new Result(false,MessageConstant.QUERY_TRAVELGROUP_SUCCESS);

    }

}
