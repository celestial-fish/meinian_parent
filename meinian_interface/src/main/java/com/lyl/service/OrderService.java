package com.lyl.service;


import com.lyl.entity.Result;

import java.util.Map;

public interface OrderService {
    Result submit(Map map);

    Map findById4Detail(Integer id) throws Exception;
}
