package com.tinytree.entity;

public class DocSysSetting extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3812966485224645152L;
	
	private String userId;//用户id
	private int reserveStatus;//预约状态 0关 1开
	private int reserveRemind;//预约提醒 0关 1开
	private int pushRemind;//消息推送提醒 0关 1开
	private int smsRemind;//短信提醒 0关 1开
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getReserveStatus() {
		return reserveStatus;
	}
	public void setReserveStatus(int reserveStatus) {
		this.reserveStatus = reserveStatus;
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
		return "DocSysSetting [userId=" + userId + ", reserveStatus="
				+ reserveStatus + ", reserveRemind=" + reserveRemind
				+ ", pushRemind=" + pushRemind + ", smsRemind=" + smsRemind
				+ "]";
	}

}
