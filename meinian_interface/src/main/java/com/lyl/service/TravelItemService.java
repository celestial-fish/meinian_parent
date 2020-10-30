package com.lyl.service;

import com.lyl.pojo.TravelItem;
import com.lyl.entity.PageResult;
import com.lyl.entity.QueryPageBean;

import java.util.List;

public interface TravelItemService {
    void add(TravelItem travelItem);

    PageResult findPage(QueryPageBean queryPageBean);

    void deleteById(Integer id);

    TravelItem findById(Integer id);

    void edit(TravelItem travelItem);

    List<TravelItem> findAll();
}
