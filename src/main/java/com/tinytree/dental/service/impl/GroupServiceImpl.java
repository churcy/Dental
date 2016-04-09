package com.tinytree.dental.service.impl;

import com.tinytree.dental.dao.GroupDao;
import com.tinytree.dental.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 重 on 2016/3/29.
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDao groupDao;

    @Override
    public List<Map<String, Object>> getByConditions(String id, String userId, String groupName) {
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("id",id);
        conditions.put("userId",userId);
        conditions.put("groupName", groupName);
        List<Map<String, Object>> groups = groupDao.getByConditions(conditions);
        return groups;
    }
}
