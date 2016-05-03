package com.tinytree.bean;
/**
 * @Description：rongcloud Api bean
 * @ClassName: RongCloudBean
 * @Author：zhengzhong
 * @Date：2016-03-07
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功能
 *
 */
public class RongCloudBean  {
	private String appKey;//唯一标示
	private String appSecret;//秘钥
	private String uri ;//请求uri
	private String tokenUri;//获取token的uri
	private String userRefreshUri;//刷新用户信息uri
	private String userCheckOnlineUri;//检查用户是否在线uri
	private String messageUri ;//发送消息
	private String systemMessageUri;//系统消息
	
	public String getMessageUri() {
		return messageUri;
	}
	public void setMessageUri(String messageUri) {
		this.messageUri = messageUri;
	}
	public String getSystemMessageUri() {
		return systemMessageUri;
	}
	public void setSystemMessageUri(String systemMessageUri) {
		this.systemMessageUri = systemMessageUri;
	}
	public String getUserCheckOnlineUri() {
		return userCheckOnlineUri;
	}
	public void setUserCheckOnlineUri(String userCheckOnlineUri) {
		this.userCheckOnlineUri = userCheckOnlineUri;
	}
	public String getUserRefreshUri() {
		return userRefreshUri;
	}
	public void setUserRefreshUri(String userRefreshUri) {
		this.userRefreshUri = userRefreshUri;
	}
	public String getTokenUri() {
		return tokenUri;
	}
	public void setTokenUri(String tokenUri) {
		this.tokenUri = tokenUri;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	@Override
	public String toString() {
		return "RongCloudBean [appKey=" + appKey + ", appSecret=" + appSecret
				+ ", uri=" + uri + ", tokenUri=" + tokenUri
				+ ", userRefreshUri=" + userRefreshUri
				+ ", userCheckOnlineUri=" + userCheckOnlineUri
				+ ", messageUri=" + messageUri + ", systemMessageUri="
				+ systemMessageUri + "]";
	}
	
}
