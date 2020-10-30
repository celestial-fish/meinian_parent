package com.lyl.dao;

import com.lyl.pojo.User;

public interface UserDao {
    User findUserByUsername(String username);
}
