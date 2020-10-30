package com.lyl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lyl.dao.TravelItemDao;
import com.lyl.entity.PageResult;
import com.lyl.entity.QueryPageBean;
import com.lyl.pojo.TravelItem;
import com.lyl.service.TravelItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = TravelItemService.class)
@Transactional
public class TravelItemServiceImpl implements TravelItemService {
  @Autowired
  private TravelItemDao travelItemDao;


    @Override
    public void add(TravelItem travelItem) {
        travelItemDao.add(travelItem);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //如果实现分页，分页代码必须写在第一行
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
      Page<TravelItem>page = travelItemDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());

    }

    @Override
    public void deleteById(Integer id) {
        travelItemDao.deleteById(id);
    }

  @Override
  public TravelItem findById(Integer id) {
      return travelItemDao.findById(id);


  }

    @Override
    public void edit(TravelItem travelItem) {
        travelItemDao.edit(travelItem);
    }

    @Override
    public List<TravelItem> findAll() {
        return travelItemDao.findAll();

    }


}
