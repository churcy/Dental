package com.tinytree.dental.service;

import com.tinytree.dental.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by 重 on 2016/3/29.
 */
public interface UserService {

    /**
     * @param id
     * @return
     */
    public User get(String id);

    /**
     * @param id
     * @param username
     * @param password
     * @return
     */
    public List<Map<String, Object>> getByConditions(String id, String username, String password);

}
