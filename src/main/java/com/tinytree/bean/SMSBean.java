package com.tinytree.bean;
/**
 * @Description：短信bean
 * @ClassName: SMSBean
 * @Author：zhengzhong
 * @Date：2016-03-07
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功能
 *
 */
public class SMSBean {
	private String appId;//开发者自己申请的应用ID
	private String templateSNId;//短信验证码模板ID
	private String templateMessageId;//短信通知模板ID
	private String templateDocResId;//医生预约
	private String templatePatResId;//医生预约
	private String templateDocStrokeId;//医生预约
	private String authToken;//开发者自己的token
	private String accountSid;//开发者自己的账号
	private String version;//目前就是这个值不用修改，也可以在接口文档中查到
	private String server;//目前就是这个值不用修改
	private String url;
	private String expTime;//验证码持续时间
	
	public String getTemplateDocResId() {
		return templateDocResId;
	}
	public void setTemplateDocResId(String templateDocResId) {
		this.templateDocResId = templateDocResId;
	}
	public String getTemplatePatResId() {
		return templatePatResId;
	}
	public void setTemplatePatResId(String templatePatResId) {
		this.templatePatResId = templatePatResId;
	}
	public String getTemplateDocStrokeId() {
		return templateDocStrokeId;
	}
	public void setTemplateDocStrokeId(String templateDocStrokeId) {
		this.templateDocStrokeId = templateDocStrokeId;
	}
	public String getTemplateSNId() {
		return templateSNId;
	}
	public void setTemplateSNId(String templateSNId) {
		this.templateSNId = templateSNId;
	}
	public String getTemplateMessageId() {
		return templateMessageId;
	}
	public void setTemplateMessageId(String templateMessageId) {
		this.templateMessageId = templateMessageId;
	}
	public String getExpTime() {
		return expTime;
	}
	public void setExpTime(String expTime) {
		this.expTime = expTime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	private SMSBean(){
		
	}
	private static class SmsBeanHodler{
		private static SMSBean instance = new SMSBean();
	}
	
	public static SMSBean getInstance(){
		return SmsBeanHodler.instance;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public String getAccountSid() {
		return accountSid;
	}
	public void setAccountSid(String accountSid) {
		this.accountSid = accountSid;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	
}
