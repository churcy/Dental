package com.tinytree.JPush;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tinytree.bean.JPushBean;
import com.kungfu.dental.util.LoadProperty2Bean;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.PushPayload.Builder;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
/**
 * @Description:极光推送通用方法
 * @ClassName: JPushSupport
 * @Author：zhengzhong
 * @Date 2016-1-6
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public class JPushSupport {
	private static final Logger logger = LoggerFactory.getLogger(JPushSupport.class);
	private static final JPushBean JPUSHBEAN = (JPushBean) LoadProperty2Bean.getInstance().getBean("JPushBean");; 
	private static final JPushClient jpushClient = new JPushClient(JPUSHBEAN.getMasterSecret(),
			JPUSHBEAN.getAppKey());
	
	private JPushSupport(){
		
	}
	private static class JPushSupportHodler{
		private static final JPushSupport insatace = new JPushSupport();
	}
	public static JPushSupport getInstanece(){
		return JPushSupportHodler.insatace;
	}
	//所有用户推送
	public boolean pushMessage(Map<String, Object> map)
			throws APIConnectionException, APIRequestException {
		Builder build = new Builder();
		int type = (int) map.get("type");
		String message = (String) map.get("message");
		String title = (String) map.get("title");
		if (type==0) {
			build = createBaseBuild(Platform.android(), Audience.all(),
					Notification.android(message, title, null));
		} else if (type==1) {
			build = createBaseBuild(Platform.ios(), Audience.all(),
					Notification.android(message, title, null));
		} else if (type==2) {
			build = createBaseBuild(Platform.all(), Audience.all(),
					Notification.alert(message));
		}
		PushResult pushResult =  jpushClient.sendPush(build.build());
		int code = pushResult.getResponseCode();
		if(code==200){
			return true;
		}else{
			logger.error(code+"");
			return false;
		}
		
	}
	//发送个人推送
	public boolean pushPersonalMessage(Map<String, String> map)
			throws APIConnectionException, APIRequestException {
		String receiver=  (String) map.get("receiver");
		String message = (String) map.get("message");
		String title = (String) map.get("title");
		Builder build = createBaseBuild(Platform.all(), Audience.alias(receiver),
					Notification.android(message, title, null));

		PushResult pushResult = jpushClient.sendPush(build.build());
		int code = pushResult.getResponseCode();
		if(code==200){
			return true;
		}else{
			logger.error(code+"");
			return false;
		}
	}
	//创建build
	private Builder createBaseBuild(Platform platform,Audience audience,Notification notification){
		Builder pushBuild =  PushPayload.newBuilder();
		pushBuild.setPlatform(platform);
		pushBuild.setAudience(audience);//Audience.tag("tag1") 一个
		pushBuild.setNotification(notification);
		return pushBuild;
	}
}
