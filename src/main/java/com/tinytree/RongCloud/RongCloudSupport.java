package com.tinytree.RongCloud;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tinytree.bean.RongCloudBean;
import com.tinytree.encry.EncryptMD5Impl;
import com.kungfu.dental.util.GlobalUtil;
import com.kungfu.dental.util.HttpUtil;
import com.kungfu.dental.util.LoadProperty2Bean;

public class RongCloudSupport {
	private static final Logger logger = LoggerFactory.getLogger(RongCloudSupport.class);
	private static final RongCloudBean RONGCLOUDBEAN = (RongCloudBean) LoadProperty2Bean.getInstance().getBean(LoadProperty2Bean.RONGCLOUD);; 
	
	private RongCloudSupport(){
		
	}
	private static class RongCloudSupportHodler{
		private static final RongCloudSupport insatace = new RongCloudSupport();
	}
	public static RongCloudSupport getInstanece(){
		return RongCloudSupportHodler.insatace;
	}
	
	/**
	 * 获取token
	 * @param userId
	 * @param userName
	 * @param portraitUri
	 * @param format
	 * @throws Exception
	 */
	public static String getToken(String userId, String userName, String portraitUri) throws Exception{
		String resonse = baseMethod(RONGCLOUDBEAN.getTokenUri(), userId, userName, portraitUri, "json");
		return resonse;
	}
	/**
	 * 用户信息刷新
	 * @param userId
	 * @param userName
	 * @param portraitUri
	 * @return
	 * @throws Exception
	 */
	public static String userRefresh(String userId, String userName, String portraitUri) throws Exception{
		String resonse = baseMethod(RONGCLOUDBEAN.getUserRefreshUri(), userId, userName, portraitUri, "json");
		return resonse;
	}
	
	/**
	 * 检查好友在线
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public static String checkOnline(String userId) throws Exception{
		String resonse = baseMethod(RONGCLOUDBEAN.getUserCheckOnlineUri(), userId, null, null, "json");
		return resonse;
	}
	public static String sendSystemMessage(String fromUserId,String toUserId,String objectName,String content) throws Exception{
		String resonse = baseMessageMethod(RONGCLOUDBEAN.getSystemMessageUri(), fromUserId, toUserId, objectName, content, "json");
		return resonse;
	}
	
	
	private static String baseMethod(String methodUri,String userId, String userName,
			String portraitUri,String format) throws Exception{
		Map<String, String> postHeaders = createHeader(RONGCLOUDBEAN.getAppKey(),RONGCLOUDBEAN.getAppSecret());
		String postEntity = createEntity(userId, userName, portraitUri);
		String postUrl = RONGCLOUDBEAN.getUri()+methodUri+format;
		String response = HttpUtil.httpPost(postUrl, postHeaders, postEntity);
		return response.toString();
		
	}
	private static String baseMessageMethod(String methodUri,String fromUserId,String toUserId, String objectName,
			String content,String format) throws Exception{
		Map<String, String> postHeaders = createHeader(RONGCLOUDBEAN.getAppKey(),RONGCLOUDBEAN.getAppSecret());
		String postEntity = createMessageEntity(fromUserId, toUserId, objectName, content);
		String postUrl = RONGCLOUDBEAN.getUri()+methodUri+format;
		String response = HttpUtil.httpPost(postUrl, postHeaders, postEntity);
		return response.toString();
		
	}
	private static Map<String, String> createHeader(String appKey,
			String appSecret){
		String nonce = String.valueOf(Math.random() * 1000000);
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		StringBuilder toSign = new StringBuilder(appSecret).append(nonce)
				.append(timestamp);
		String sign = EncryptMD5Impl.hexSHA1(toSign.toString());
		Map<String, String> map = new HashMap<String, String>();
		map.put(GlobalUtil.APPKEY, appKey);
		map.put(GlobalUtil.NONCE, nonce);
		map.put(GlobalUtil.TIMESTAMP, timestamp);
		map.put(GlobalUtil.SIGNATURE, sign);
		return map;
	}
	private static String createMessageEntity(String fromUserId,String toUserId,String objectName,String content) throws Exception{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("fromUserId=").append(URLEncoder.encode(fromUserId, GlobalUtil.UTF8));
		stringBuilder.append("&toUserId=").append(URLEncoder.encode(toUserId==null?"":toUserId, GlobalUtil.UTF8));
		stringBuilder.append("&objectName=").append(URLEncoder.encode(objectName==null?"":objectName,GlobalUtil.UTF8));
		stringBuilder.append("&content=").append(URLEncoder.encode(content==null?"":content,GlobalUtil.UTF8));
		return stringBuilder.toString();
	}
	private static String createEntity(String userId,String name,String portraitUri) throws Exception{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("userId=").append(URLEncoder.encode(userId, GlobalUtil.UTF8));
		stringBuilder.append("&name=").append(URLEncoder.encode(name==null?"":name, GlobalUtil.UTF8));
		stringBuilder.append("&portraitUri=").append(URLEncoder.encode(portraitUri==null?"":portraitUri,GlobalUtil.UTF8));
		return stringBuilder.toString();
	}
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		RongCloudSupport cloudSupport = RongCloudSupport.getInstanece();
		
		String string = cloudSupport.getToken("123456", "zz", "xxx");
		System.out.println(string);
	}
}
