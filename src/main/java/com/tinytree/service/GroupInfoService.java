package com.tinytree.service;

import java.util.List;
import java.util.Map;

import com.tinytree.entity.GroupInfo;

public interface GroupInfoService extends BaseService<GroupInfo, String>{
	

	/**
	 * 获取分组内好友
	 * @param userId 用户id
	 * @param groupId 分组id
	 * @param groupName 分组名
	 * @return  groupId groupName
	 */
	public List<Map<String, Object>> getByConditons(String userId,
			String groupId, String groupName);

	/**
	 * 检查该用户是否存在该分组
	 * @param userId
	 * @param groupName
	 * @return groupInfo 对象
	 * @throws Exception 
	 */
	public List<GroupInfo> getGroupByConditions(String userId,
			String groupId, String groupName) throws Exception;
}
