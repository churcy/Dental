package com.tinytree.entity;

import javax.persistence.Entity;

@Entity
public class GroupInfo extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 498924233024579550L;

	private String userId;//用户的主键
	
	private String name;//分组的名字
	
	private int type ;//分组类型

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "GroupInfo [userId=" + userId + ", name=" + name + ", type="
				+ type + "]";
	}

}
