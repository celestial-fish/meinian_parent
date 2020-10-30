package com.lyl.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lyl.pojo.Permission;
import com.lyl.pojo.Role;
import com.lyl.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class SpringSecurityUserService implements UserDetailsService  {

@Reference
private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        根据用户名在数据库查询
        com.lyl.pojo.User user= userService.findUserByUserName(username);
        if (user==null){
//            用户查询不到，直接return
            return null;
        }
//创建一个装权限集合
       List<GrantedAuthority> list = new ArrayList<>();
//       通过用户拿到获得角色，通过角色获得权限集合
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
//            每个角色的权限
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                //授权
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }
        UserDetails userDetails = new User(username,user.getPassword(),list);

        return userDetails;
    }
}
