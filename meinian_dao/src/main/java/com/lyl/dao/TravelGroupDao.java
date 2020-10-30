package com.lyl.dao;

import com.github.pagehelper.Page;
import com.lyl.pojo.TravelGroup;

import java.util.List;
import java.util.Map;

public interface TravelGroupDao {
    /**
     * 报团增加
     * @param travelGroup
     */
    void add(TravelGroup travelGroup);

    /**
     * 根据报团的id 和旅游项目的id 完成中间表的添加
     * @param map
     */
    void setCheckGroupAndCheckItem(Map<String, Integer> map);

    Page<TravelGroup> findPage(String queryString);

    TravelGroup findById(Integer id);

    List<Integer> findTravelItemIdByTravelgroupId(Integer id);

    void edit(TravelGroup travelGroup);

    void deleteTravelGroupAndTravelItemByTravelGroupId(Integer id);

    List<TravelGroup> findAll();

//    通过SetmealDao查询出中间表，再根据id查询出TravelGroup 的数据
    List<TravelGroup> findTravelGroupListById(Integer id);
}
