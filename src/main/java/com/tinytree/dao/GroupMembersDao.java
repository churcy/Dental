package com.tinytree.dao;
import java.util.List;
import java.util.Map;

import com.kungfu.dental.entity.GroupMembers;

public interface GroupMembersDao extends BaseDao<GroupMembers> {

	/**
	 * 条件查询 好友列表
	 * @param map
	 * @param map 参数  userId 用户id 
	 * @param map 参数  groupId 分组id
	 * @param map 参数  groupMebId 好友表主键 
	 * @return
	 */
	List<Map<String, Object>> getByConditions(Map<String, Object> map);

	/**
	 * 检查好友是否存在
	 * map 参数如下
	 * @param userId 用户id
	 * @param friendUserId 好友用户id
	 * @return
	 */
	List<Map<String, Object>> checkFriend(Map<String, Object> map);

}
