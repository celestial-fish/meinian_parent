package com.lyl.service;

import com.lyl.pojo.Setmeal;
import com.lyl.entity.PageResult;
import com.lyl.entity.QueryPageBean;

import java.util.List;
import java.util.Map;

public interface SetmealService {
    void add(Integer[] travelgroupIds, Setmeal setmeal);

    PageResult findPage(QueryPageBean queryPageBean);

    List<Setmeal> getSetmeal();

    Setmeal findById(Integer id);

    List<Map<String, Object>> findSetmealCount();

}
