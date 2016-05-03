package com.tinytree.entity;

import javax.persistence.Entity;

import org.apache.ibatis.type.Alias;

@Entity
@Alias(value="short_message")
public class ShortMessage extends BaseEntity{

	private static final long serialVersionUID = -4721548225032138272L;

	private String userPhone;//用户手机号
	
	private String verificationCode;//短信验证码
	
	private String expirationTime;//短信过期时间
	
	private int status;//0:已发送 1:已使用 2已过期

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}

	@Override
	public String toString() {
		return "ShortMessage [userPhone=" + userPhone + ", verificationCode="
				+ verificationCode + ", expirationTime=" + expirationTime
				+ ", status=" + status + "]";
	}

}
