package com.tinytree.dao;

import java.util.List;
import java.util.Map;

import com.kungfu.dental.entity.GroupInfo;

public interface GroupInfoDao extends BaseDao<GroupInfo> {
	
	/**
	 * 根据 userid 和groupname 查询分组
	 * @param userId
	 * @param groupName
	 * @return
	 */
	List<Map<String, Object>> getByConditions(Map<String, Object> map);
	/**
	 * 根据 userid 和groupname 查询分组
	 * @param userId
	 * @param groupName
	 * @return
	 */
	GroupInfo getGroupByConditions(Map<String, Object> map);
	
	/**
	 * 添加好友分组
	 * @param groupName 分组姓名
	 * @param userId 用户id
	 * @return
	 */
	public boolean addGroup(String groupName,String userId);
	
	/**
	 * 删除好友分组
	 * @param groupName 分组名
	 * @param userId 用户id
	 * @return
	 */
	public boolean deleteGroup(String groupName,String userId);
	
	/**
	 * 修改好友分组
	 * @param groupName 新的分组名
	 * @param userId 用户id
	 * @return
	 */
	public boolean modifyGroup(String groupName,String userId);

}
