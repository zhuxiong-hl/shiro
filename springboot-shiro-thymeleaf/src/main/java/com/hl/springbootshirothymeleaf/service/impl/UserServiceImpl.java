package com.hl.springbootshirothymeleaf.service.impl;

import com.hl.springbootshirothymeleaf.dao.UserDao;
import com.hl.springbootshirothymeleaf.entity.Perm;
import com.hl.springbootshirothymeleaf.entity.User;
import com.hl.springbootshirothymeleaf.service.UserService;
import com.hl.springbootshirothymeleaf.utils.SaltUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void register(User user) {
        String salt = SaltUtils.getSalt(8);
        user.setSalt(salt);
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 1024);
        user.setPassword(md5Hash.toHex());
        userDao.saveUser(user);
    }

    @Override
    public User findByUserName(String username) {
        return userDao.findByUserName(username);
    }

    @Override
    public User findRolesByUserName(String username) {
        return userDao.findRolesByUserName(username);
    }

    @Override
    public List<Perm> findPermByRoleId(Integer id) {
        return userDao.findPermByRoleId(id);
    }
}
