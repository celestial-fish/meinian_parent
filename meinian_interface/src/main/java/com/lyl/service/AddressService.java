package com.lyl.service;

import com.lyl.entity.PageResult;
import com.lyl.entity.QueryPageBean;
import com.lyl.pojo.Address;

import java.util.List;

public interface AddressService {
    List<Address> findAll();

    PageResult findPage(QueryPageBean queryPageBean);

    void addAddress(Address address);

    void deleteById(Integer id);
}
