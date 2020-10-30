package com.lyl.dao;

import com.github.pagehelper.Page;
import com.lyl.pojo.Address;


import java.util.List;

public interface AddressDao {
    List<Address> findAll();

    Page<Address> selectByCondition(String queryString);

    void addAddress(Address address);

    void deleteById(Integer id);
}
