package com.tinytree.dental.dao;

import com.tinytree.dental.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by 重 on 2016/3/29.
 */
@Repository
public interface UserDao {

   // @Select("SELECT * FROM user WHERE id = #{0}")
    public User get(String id);

    public List<Map<String,Object>> getByConditions(Map<String, Object> conditions);

    public List<Map<String,Object>> testMethod(Map<String, Object> conditions);

    /**
     * 登录
     * @param conditions:username password
     * @return
     */
    public List<Map<String,Object>> login(Map<String, Object> conditions);
}
