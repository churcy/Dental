package com.tinytree.dental.service;

import java.util.List;
import java.util.Map;

/**
 * Created by 重 on 2016/3/29.
 */
public interface GroupService {
    /**
     * 根据条件查询分组
     * @param id 分组id
     * @param userId 分组所属人用户id
     * @param groupName 分组姓名
     * @return groups 分组列表
     */
    public List<Map<String, Object>> getByConditions(String id, String userId, String groupName);
}
