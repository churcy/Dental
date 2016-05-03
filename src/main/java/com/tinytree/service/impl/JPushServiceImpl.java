package com.tinytree.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;

import com.tinytree.JPush.JPushSupport;
import com.kungfu.dental.service.JPushService;
import com.kungfu.dental.util.GlobalUtil;
/**
 * @Description:极光推送服务实现
 * @ClassName: JPushServiceImpl
 * @Author：zhengzhong
 * @Date 2016-1-6
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Service("jPushService")
public class JPushServiceImpl implements JPushService{
	
	private static JPushSupport jPushSupport;//= JPushSupport.getInstanece();
	
	@Override
	public boolean push(String type, String message) throws APIConnectionException, APIRequestException  {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("message",message);
		return jPushSupport.pushMessage(map);
	}

	@Override
	public boolean push(String type, String message, String title) throws APIConnectionException, APIRequestException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("message",message);
		map.put("title",title);
		return jPushSupport.pushMessage(map);
	}

	@Override
	public boolean pushPresonal(String accoutId, String message) throws APIConnectionException, APIRequestException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("receiver", accoutId);
		map.put("message", message);
		return jPushSupport.pushPersonalMessage(map);
	}

	@Override
	public boolean tempPush(String accountId, int tempType, Object[] params)
			throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("receiver", accountId);
		String message;
		switch (tempType) {
		case 0:
			message = GlobalUtil.DOC_RES_MESSAGE;
			message = message.replaceFirst("param0",(String)params[0]);
			break;
		case 1:
			message = GlobalUtil.PAT_RES_MESSAGE;
			message = message.replaceFirst("param0",(String)params[0]);
			message = message.replaceFirst("param1",(String)params[1]);
			break;
		case 2:
			message = GlobalUtil.DOC_STR_MESSAGE;
			message = message.replaceFirst("param0",(String)params[0]);
			message = message.replaceFirst("param1",(String)params[1]);
			break;
		default:
			message = "";
			break;
		}
		map.put("message", message);
		return jPushSupport.pushPersonalMessage(map);
	}
	public static void main(String[] args) throws Exception {
		JPushServiceImpl impl = new JPushServiceImpl();
		impl.tempPush("1111", 2, new String[]{"xxx","xxxx"});
	}
}
