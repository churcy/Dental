package com.tinytree.dao;

import java.util.List;
import java.util.Map;

import com.kungfu.dental.entity.User;

public interface FriendManageDao extends BaseDao<User>{
	
	public List<Map<String,Object>> getFriendList(Map<String, String> map);

}
