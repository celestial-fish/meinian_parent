package com.lyl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lyl.dao.SetmealDao;
import com.lyl.pojo.Setmeal;
import com.lyl.utils.RedisConstant;
import com.lyl.entity.PageResult;
import com.lyl.entity.QueryPageBean;
import com.lyl.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;

    @Override
    public void add(Integer[] travelgroupIds, Setmeal setmeal) {
        setmealDao.add(setmeal);
        if(travelgroupIds != null && travelgroupIds.length > 0){
            setSetmealAndTravelGroup(setmeal.getId(),travelgroupIds);
        }
        savePic2Redis(setmeal.getImg());

    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //分页在第一行启动
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Setmeal> page = setmealDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public List<Setmeal> getSetmeal() {
        return setmealDao.getSetmeal();

    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    @Override
    public List<Map<String, Object>> findSetmealCount() {
        return setmealDao.findSetmealCount();
    }


    private void setSetmealAndTravelGroup(Integer id,Integer[] travelgroupIds){
        for (Integer travelgroupId : travelgroupIds) {
            Map<String,Integer>map = new HashMap<>();
            map.put("travelgroup_id",travelgroupId);
            map.put("setmeal_id",id);
            setmealDao.setSetmealAndTravelGroup(map);
        }
    }

    private void savePic2Redis(String pic){
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,pic);
    }
}
