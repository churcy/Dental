package com.tinytree.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinytree.RongCloud.RongCloudSupport;
import com.kungfu.dental.service.RongCloudService;
import com.kungfu.dental.service.UserService;
/**
 * @Description:融云信息服务实现类
 * @ClassName: RongCloudServiceImpl
 * @Author：zhengzhong
 * @Date 2016-03-16
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Service
public class RongCloudServiceImpl implements RongCloudService {

	@Autowired
	private UserService userService;
	@Override
	public String getToken(String userId,String userName,String portraitUri) throws Exception {
		String response = RongCloudSupport.getToken(userId, userName, null);
		return response;
	}
	@Override
	public String sendSystemMessage(String fromUserId, String toUserId,
			String objectName, String content) throws Exception {
		String response = RongCloudSupport.sendSystemMessage(fromUserId, toUserId, objectName, content);
		return response;
	}

}
