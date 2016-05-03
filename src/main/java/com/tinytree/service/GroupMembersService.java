package com.tinytree.service;

import java.util.List;
import java.util.Map;

import com.tinytree.entity.GroupMembers;

public interface GroupMembersService extends BaseService<GroupMembers, String>{

	/**
	 * 条件查询好友列表
	 * @param userId
	 * @param groupId
	 * @param groupMebId
	 * @return  好友的相关信息   groupMebId 好友id userId 自己id requestorUserId
	 */
	List<Map<String, Object>> getByConditons(String groupId , String userId,
			String groupMebId,int status);

	/**
	 * 精确查询好友
	 * @param userId
	 * @param friendUserId
	 * @return
	 */
	List<Map<String, Object>> getFriend(String userId, String friendUserId);
	
}
