package com.tinytree.entity;

import javax.persistence.Entity;

@Entity
public class GroupMembers extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6356858262055970051L;
	
	private String groupId;//分组id
	
	private String userId;//好友userId
	
	private String username;//好友姓名
	
	private String nickname;//好友昵称
	
	private int registerType;//注册类型
	
	private int type;//好友类型
	
	private int status;//验证状态
	
	public int getRegisterType() {
		return registerType;
	}

	public void setRegisterType(int registerType) {
		this.registerType = registerType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "GroupMembers [groupId=" + groupId + ", userId=" + userId
				+ ", username=" + username + ", nickname=" + nickname
				+ ", registerType=" + registerType + ", type=" + type
				+ ", status=" + status + "]";
	}

}
