package com.hl.springbootshirothymeleaf.dao;

import com.hl.springbootshirothymeleaf.entity.Perm;
import com.hl.springbootshirothymeleaf.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {
    void saveUser(User user);
    User findByUserName(String username);
    User findRolesByUserName(String username);
    List<Perm> findPermByRoleId(Integer id);

}
