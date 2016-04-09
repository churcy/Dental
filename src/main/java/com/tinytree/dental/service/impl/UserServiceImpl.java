package com.tinytree.dental.service.impl;

import com.tinytree.dental.dao.UserDao;
import com.tinytree.dental.entity.User;
import com.tinytree.dental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by 重 on 2016/3/29.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User get(String id) {
        User user = userDao.get(id);
        return user;
    }

    @Override
    public List<Map<String, Object>> getByConditions(String id, String username, String password) {
        Map<String,Object> conditions = new HashMap<>();
        //conditions.put("id",id);
        conditions.put("username",username);
        conditions.put("password",password);
        List<Map<String, Object>> users = userDao.getByConditions(conditions);
        return users;
    }

    @Override
    public List<Map<String, Object>> login(String username, String password) {
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("username",username);
        conditions.put("password",password);
        List<Map<String, Object>> users = userDao.login(conditions);
        return users;
    }

}
