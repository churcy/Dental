package com.tinytree.dao;

import java.util.List;
import java.util.Map;

import com.kungfu.dental.entity.User;
/**
 * @Description:用户信息相关数据库操作
 * @ClassName: UserDao
 * @Author：zhengzhong
 * @Date 2016-1-13
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface UserDao extends BaseDao<User>{

	/**
	 * 根据userId 查询user信息
	 * @param userId
	 * @return User
	 */
	//@Select("SELECT * FROM user WHERE account_id = #{userId}")
	public Map<String, Object> getByUserId(String userId);
	
	/**
	 * 根据accountId 查询user信息
	 * @param accountId
	 * @return User
	 */
	public User getByAccountId(String accountId);
	/**
	 * 根据accountId 查询user信息
	 * @param accountId
	 * @return User
	 */
	public List<Map<String,String>> getByConditions(Map<String, String> map);

}
