package com.tinytree.JPush;

import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import com.tinytree.service.JPushService;
import com.tinytree.service.impl.JPushServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class TestJPush {
	@Autowired
	JPushService jPushService;
	
	public static void main(String[] args) throws APIConnectionException, APIRequestException {
		JPushServiceImpl jPushService = new JPushServiceImpl();
		boolean flag = jPushService.push("2", "你无不无聊");
		System.out.println("This is push return : "+flag);
	}
}
