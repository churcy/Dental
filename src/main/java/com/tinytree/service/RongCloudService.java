package com.tinytree.service;


/**
 * @Description:融云信息服务
 * @ClassName: RongCloudService
 * @Author：zhengzhong
 * @Date 2016-1-13
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface RongCloudService{
	
	/**
	 * 获取token
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	public String getToken(String userId,String userName,String portraitUri) throws Exception;
	
	/**
	 * 发送系统消息
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	public String sendSystemMessage(String fromUserId,String toUserId,String objectName,String content) throws Exception;
	
}
