package com.lyl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lyl.dao.OrderSettingDao;
import com.lyl.pojo.OrderSetting;
import com.lyl.service.OrdersettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrdersettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrdersettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void add(List<OrderSetting> lists) {
//        在这一层，将数据再次剥离出来，
        for (OrderSetting orderSetting  : lists) {
//            判断日期是否重复，是用当前的日期作为查询条件
            long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
//            这里通过数据库查询可以得出，大于零有数据，小于零后台没有数据
            if (count>0){
//                有数据执行编辑
                orderSettingDao.editNumberByOrderDate(orderSetting);
            }else {
//                没有数据直接添加操作
                orderSettingDao.add(orderSetting);
            }
        }


    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {
//       查询需要的数据是从1号到31，这里拼接一个开始和结束日期
        String dataBegin = date + "-1";
        String dataEnd = date + "-31";
        Map<String,Object>map = new HashMap<>();
        map.put("dataBegin",dataBegin);
        map.put("dataEnd",dataEnd);
        List<OrderSetting>lists = orderSettingDao.getOrderSettingByMonth(map);
        List<Map>data = new ArrayList<>();
        for (OrderSetting list : lists) {
            Map<Object,Object>orderSetting = new HashMap<>();
            orderSetting.put("date",list.getOrderDate().getDate());
            orderSetting.put("number",list.getNumber());
            orderSetting.put("reservations",list.getReservations());
            data.add(orderSetting);
        }
       return data;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
//            这里通过数据库查询可以得出，大于零有数据，小于零后台没有数据  没有数据直接添加
        if (count>0){
//            查到数据count大于零，直接编辑
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else {
//            没查到数据小于零。直接添加
            orderSettingDao.add(orderSetting);
        }

    }
}
