package com.lyl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lyl.dao.UserDao;
import com.lyl.pojo.User;
import com.lyl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User findUserByUserName(String username) {
        return userDao.findUserByUsername(username);

    }
}
