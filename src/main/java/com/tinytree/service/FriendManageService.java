package com.tinytree.service;

import java.util.List;
import java.util.Map;

import com.tinytree.entity.User;

public interface FriendManageService extends BaseService<User, String>{
	
	/**
	 * 通过userId 获取好友列表和好友内容
	 * @param userId systemType 0:工作站 1:app
	 * @return
	 */
	public List<Map<String,Object>> getFriendList(String userId,String systemType,String registerType);
	
	/**
	 * 添加好友分组
	 * @param groupName 分组姓名
	 * @param userId 用户id
	 * @return 0 分组存在或者重复 1 成功 2数据库操作失败
	 */
	public int addGroup(String groupName,String userId);
	
	/**
	 * 删除好友分组
	 * @param groupId 分组id
	 * @param userId 用户id
	 * @return 0 不存在或者重复 1成功 2数据库操作失败 3分组为默认分组
	 * @throws Exception 
	 */
	public int deleteGroup(String userId,String groupId) throws Exception;
	
	/**
	 * 修改好友分组
	 * @param groupName 新的分组名
	 * @param userId 用户id
	 * @return 0分组不存在或者重复 1 修改成功 2数据库操作失败
	 * @throws Exception 
	 */
	public int modifyGroup(String groupName,String newName,String userId) throws Exception;
	
	/**
	 * 添加好友
	 * @param groupId 分组id
	 * @param userId 好友id
	 * @return 0 好友已存在 1添加成功 2数据库操作出错 3好友未通过验证
	 */
	public int addFriend(String groupId,String userId);
	
	/**
	 * 删除好友
	 * @param groupId 分组id
	 * @param userId 好友id
	 * @return 0 好友不存在或者 不止一个 1删除成功 2数据库操作出错
	 */
	public int deleteFriend(String groupId, String userId, String groupMebId);
	
	/**
	 * 是否存在好友分组
	 * @param userId
	 * @param groupName
	 * @param groupId
	 * @return 0 不存在 1 存在 2数据库操作失败
	 */
	public int checkGroup(String userId,
			String groupName,String groupId );
	
	/**
	 * 检查是否已存在该好友
	 * @param userId 用户id
	 * @param friendUserId 好友id
	 * @return 0 不存在 1 存在 2数据库操作失败
	 */
	public int checkFriend(String userId,String friendUserId);
	
	/**
	 * 查找指定好友信息
	 * @param userId 用户id
	 * @param friendUserId 好友id
	 * @return 0 不存在 1 存在 2数据库操作失败
	 */
	public Map<String, Object> getFriend(String userId,String friendUserId);

	/**
	 * 根据用户id 获取分组列表
	 * @param userId 用户id
	 * @return
	 */
	public List<Map<String, Object>> getGroupList(String userId);

	/**
	 * 移动好友到其他分组
	 * @param groupId
	 * @param fromUserId 自己的userId
	 * @param toUserId 好友的userId
	 * @return 0 不成功 1成功 2数据库操作失败
	 */
	public int moveFriend(String fromUserId,String groupId,String toUserId);

}
