package com.tinytree.dental.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by 重 on 2016/3/29.
 */
@Repository
public interface GroupDao {
    /**
     * 根据条件查询好友分组
     * @return
     */
    public List<Map<String,Object>> getByConditions(Map<String,Object> conditions);

}
