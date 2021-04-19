package com.hl.springbootshirothymeleaf.service;

import com.hl.springbootshirothymeleaf.entity.Perm;
import com.hl.springbootshirothymeleaf.entity.User;

import java.util.List;

public interface UserService {
    void register(User user);
    User findByUserName(String username);
    User findRolesByUserName(String username);
    List<Perm> findPermByRoleId(Integer id);
}
