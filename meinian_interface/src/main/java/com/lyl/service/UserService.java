package com.lyl.service;

import com.lyl.pojo.User;

public interface UserService {
    User findUserByUserName(String username);
}
