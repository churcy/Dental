package com.tinytree.dao;

import com.tinytree.entity.User;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface FriendManageDao extends BaseDao<User>{
	
	public List<Map<String,Object>> getFriendList(Map<String, String> map);

}
