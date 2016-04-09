package com.tinytree.dental.service.impl;

import com.tinytree.dental.dao.GroupMemberDao;
import com.tinytree.dental.service.GroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by 重 on 2016/3/29.
 */
@Service
public class GroupMemberServiceImpl implements GroupMemberService{

    @Autowired
    private GroupMemberDao groupMemberDao;

    @Override
    public List<Map<String, Object>> getFreindByUserId(String userId) {
        List<Map<String, Object>> groupMembers = groupMemberDao.getFriendByUserId(userId);
        return groupMembers;
    }
}
