package com.hl.springboot.service;

import com.hl.springboot.entity.Perm;
import com.hl.springboot.entity.User;

import java.util.List;

public interface UserService {
    void register(User user);
    User findByUserName(String username);
    //  根据用户查询所有角色
    User findRoleByUserName(String username);
    //  根据角色id查询权限集合
    List<Perm> findPermByRoleId(Integer id);
}
