package com.tinytree.entity;

public class UserSysSetting extends BaseEntity{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8453814697638240346L;
	
	private String userId;//用户id
	private int reserveRemind;//预约提醒 0关 1开
	private int pushRemind;//消息推送提醒 0关 1开
	private int smsRemind;//短信提醒 0关 1开
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getReserveRemind() {
		return reserveRemind;
	}
	public void setReserveRemind(int reserveRemind) {
		this.reserveRemind = reserveRemind;
	}
	public int getPushRemind() {
		return pushRemind;
	}
	public void setPushRemind(int pushRemind) {
		this.pushRemind = pushRemind;
	}
	public int getSmsRemind() {
		return smsRemind;
	}
	public void setSmsRemind(int smsRemind) {
		this.smsRemind = smsRemind;
	}
	@Override
	public String toString() {
		return "UserSysSetting [userId=" + userId + ", reserveRemind="
				+ reserveRemind + ", pushRemind=" + pushRemind + ", smsRemind="
				+ smsRemind + "]";
	}
	
}
