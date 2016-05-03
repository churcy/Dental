package com.tinytree.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinytree.dao.GroupMembersDao;
import com.tinytree.entity.GroupMembers;
import com.kungfu.dental.service.GroupMembersService;

@Service
public class GroupMembersServiceImpl extends BaseServiceImpl<GroupMembers, String> implements GroupMembersService{
	
	@Autowired
	private GroupMembersDao groupMembersDao;
	
	@Autowired
	public void setBaseDao(GroupMembersDao groupMembersDao){
		super.setBaseDao(groupMembersDao);
	}
	
	@Override
	public List<Map<String, Object>> getByConditons(String groupId,
			String userId, String groupMebId,int status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("groupId", groupId);
		map.put("groupMebId", groupMebId);
		if(status == 0 | status == 1){
			map.put("status", status);
		}
		List<Map<String, Object>> result = groupMembersDao.getByConditions(map);
		return result;
	}

	@Override
	public List<Map<String, Object>> getFriend(String userId,
			String friendUserId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("friendUserId", friendUserId);
		List<Map<String, Object>> list = groupMembersDao.checkFriend(map);
		return list;
	}

}
