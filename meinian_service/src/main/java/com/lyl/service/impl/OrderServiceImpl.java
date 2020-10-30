package com.lyl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lyl.dao.MemberDao;
import com.lyl.dao.OrderDao;
import com.lyl.dao.OrderSettingDao;
import com.lyl.pojo.Member;
import com.lyl.pojo.Order;
import com.lyl.pojo.OrderSetting;
import com.lyl.constant.MessageConstant;
import com.lyl.entity.Result;
import com.lyl.service.OrderService;
import com.lyl.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class )
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
     private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;


    @Override
    public Result submit(Map map) {
/**
 * 要实现一下需求就能实现功能：
 * 1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
 * 2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
 * 3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
 * 4、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注册并进行预约
 * 5、预约成功，更新当日的已预约人数
  */
//   1、 获取旅游日期
        String orderDate = (String) map.get("orderDate");
        Date date = null;
        try {
             date = DateUtils.parseString2Date(orderDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        根据日期查询预约设置
       OrderSetting orderSetting = orderSettingDao.findByOrderDate(date);
        if (orderSetting==null){
//            没有旅游团  不能预约
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }else {
//       2、     不为空 说明有团，判断一下可预约人数预约满了没有，满了就返回人数满，没有就预约
//          number 可预约人数  ，reservations 已预约人数
            int number = orderSetting.getNumber();
            int reservations = orderSetting.getReservations();
            if (reservations>= number){
//                满了 返回人数预约满
                return new Result(false, MessageConstant.ORDER_FULL);
            }

        }
//        3 、 4 、 5 需要一起实现
//            根据手机号查询会员表 判断是否为会员，不是就直接注册  是会员就进行下一步
        String telephone = (String) map.get("telephone");
        Member member = memberDao.findByTelephone(telephone);
        if (member!=null){
//                是会员 进行下单
            Integer memberId = member.getId();
            Integer setmealId = Integer.parseInt((String) map.get("setmealId"));
//                避免重复预约（会员Id ，套餐id 旅游日期）
            Order order = new Order(memberId,date,null,null,setmealId);
//             通过订单的三个值，分别从三个表查询订单
            List<Order> list = orderDao.findByCondition(order);
//                如果有值则重复,没有值则可以添加
            if (list!=null&&list.size()>0){
                //已经完成了预约，不能重复预约
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        }else{
            //            不是会员 ,根据前端数据完成插入表
            member = new Member();
            member.setName((String)map.get("name"));
            member.setSex((String)map.get("sex"));
            member.setPhoneNumber((String)map.get("telephone"));
            member.setIdCard((String)map.get("idCard"));
            member.setRegTime(new Date()); // 会员注册时间，当前时间
            memberDao.add(member);
        }
//        5  预约成功  更新预约人数
        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);
        Order order = new Order();
        order.setMemberId(member.getId()); //会员id
        order.setOrderDate(date); // 预约时间
        order.setOrderStatus(Order.ORDERSTATUS_NO); // 预约状态（已到诊/未到诊）
        order.setOrderType((String)map.get("orderType"));
        order.setSetmealId(Integer.parseInt((String)map.get("setmealId")));
        orderDao.add(order);

        return new Result(true, MessageConstant.ORDER_SUCCESS, order);
    }

    @Override
    public Map findById4Detail(Integer id)throws Exception {
//        根据id查询旅游信息，包括旅游人和套餐
        Map map= orderDao.findById4Detail(id);
        if (map!=null){
            Date date= (Date)map.get("orderDate");
            map.put("orderDate",DateUtils.parseDate2String(date));
            return map;
        }
        return map;
    }
}
