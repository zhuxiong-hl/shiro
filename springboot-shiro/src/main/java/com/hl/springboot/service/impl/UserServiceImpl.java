package com.hl.springboot.service.impl;

import com.hl.springboot.entity.Perm;
import com.hl.springboot.mapper.UserMapper;
import com.hl.springboot.entity.User;
import com.hl.springboot.service.UserService;
import com.hl.springboot.utils.SaltUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(User user) {
        //  生成随机盐
        String salt = SaltUtils.getSalt(8);
        System.out.println("随机盐："+salt);
        //  将随机盐保存到数据
        user.setSalt(salt);
        //  明文密码进行MD5+salt+hash散列
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 1024);
        user.setPassword(md5Hash.toHex());
        userMapper.saveUser(user);
    }

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public User findRoleByUserName(String username) {
        return userMapper.findRoleByUserName(username);
    }

    @Override
    public List<Perm> findPermByRoleId(Integer id) {
        return userMapper.findPermByRoleId(id);
    }
}
