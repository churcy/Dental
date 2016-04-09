package com.tinytree.dental.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by 重 on 2016/3/29.
 */
@Repository
public interface GroupMemberDao {
    /**
     * 根据条件查询好友
     * @return
     */
    public List<Map<String,Object>> getByConditions();

    /**
     * 根据用户userId查询该用户的好友列表
     * @param userId
     * @return 用户的好友列表
     */
    List<Map<String,Object>> getFriendByUserId(String userId);
}
