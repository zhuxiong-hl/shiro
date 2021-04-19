package com.hl.springboot.mapper;

import com.hl.springboot.entity.Perm;
import com.hl.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    void saveUser(User user);
    User findByUsername(String username);
    //  根据用户查询所有角色
    User findRoleByUserName(String username);
    //  根据角色id查询权限集合
    List<Perm> findPermByRoleId(Integer id);

}
