package com.lyl.service;

import com.lyl.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrdersettingService {
    void add(List<OrderSetting> lists);

    List<Map> getOrderSettingByMonth(String date);

    void editNumberByDate(OrderSetting orderSetting);
}
