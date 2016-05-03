package com.tinytree.entity;

import javax.persistence.Entity;

@Entity
public class SysUser extends BaseEntity{

	private static final long serialVersionUID = -1929828071726542972L;
	
	private String username;
	
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "SysUser [username=" + username + ", password=" + password + "]";
	}
	
}
