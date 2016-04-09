package com.tinytree.dental.service;

import java.util.List;
import java.util.Map;

/**
 * Created by 重 on 2016/3/29.
 */
public interface GroupMemberService {

    /**
     * 根据userId 查询该用户的好友列表
     * @param userId 用户的id
     * @return 用户好友分组列表
     */
    List<Map<String,Object>> getFreindByUserId(String userId);
}
