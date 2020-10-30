package com.lyl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lyl.dao.TravelGroupDao;
import com.lyl.entity.PageResult;
import com.lyl.entity.QueryPageBean;
import com.lyl.pojo.TravelGroup;
import com.lyl.service.TravelGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = TravelGroupService.class)
@Transactional
public class TravelGroupServiceImpl implements TravelGroupService {
    @Autowired
    private TravelGroupDao travelGroupDao;

    @Override
    public void add(TravelGroup travelGroup, Integer[] travelItemIds) {
        //直接将添加信息传递到dao层去 然后返回报团的id回来
        travelGroupDao.add(travelGroup);//根基sql语句的查询可以返回报团的id
        //前端勾选要报团的id，和添加的id 存入中间表
        setTravelGroupAndTravelItem(travelGroup.getId(),travelItemIds);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<TravelGroup>page = travelGroupDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public TravelGroup findById(Integer id) {
        return travelGroupDao.findById(id);

    }

    @Override
    public List<Integer> findTravelItemIdByTravelgroupId(Integer id) {
        return travelGroupDao.findTravelItemIdByTravelgroupId(id);

    }

    @Override
    public void edit(Integer[] travelItemIds, TravelGroup travelGroup) {
        //这里分两步执行  1. 根据travelGroup数据先去修改跟团游标  2.中间表根据跟团travelGroup.getid 先删除  再添加
        travelGroupDao.edit(travelGroup);
        travelGroupDao.deleteTravelGroupAndTravelItemByTravelGroupId(travelGroup.getId());
        setTravelGroupAndTravelItem(travelGroup.getId(),travelItemIds);

    }

    @Override
    public List<TravelGroup> findAll() {
        return travelGroupDao.findAll();

    }


    public void  setTravelGroupAndTravelItem(Integer travelGroupId ,Integer[]travelItemIds){
        for (Integer travelItemId : travelItemIds) {
            Map<String,Integer>map = new HashMap<>();
            map.put("travelGroup",travelGroupId);
            map.put("travelItem",travelItemId);
            travelGroupDao.setCheckGroupAndCheckItem(map);
        }
    }
}
