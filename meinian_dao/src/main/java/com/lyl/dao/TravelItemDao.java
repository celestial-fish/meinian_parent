package com.lyl.dao;

import com.github.pagehelper.Page;
import com.lyl.pojo.TravelItem;

import java.util.List;

public interface TravelItemDao {
    void add(TravelItem travelItem);

    Page<TravelItem> findPage(String queryString);

    void deleteById(Integer id);

    TravelItem findById(Integer id);

    void edit(TravelItem travelItem);

    List<TravelItem> findAll();

//    通过TravelGroup的数据查询出中间表  ，再根据中间表查询出TravelItem的数据
    List<TravelItem> findTravelItemListById(Integer id);
}
