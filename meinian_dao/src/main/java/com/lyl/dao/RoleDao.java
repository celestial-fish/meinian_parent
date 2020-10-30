package com.lyl.dao;

import com.lyl.pojo.Role;

import java.util.Set;

public interface RoleDao {
    Set<Role> findRolesByUserId(Integer userId);
}
