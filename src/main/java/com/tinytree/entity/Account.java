package com.tinytree.entity;

import javax.persistence.Entity;
/**
 * @Description:账号信息
 * @ClassName: Account
 * @Author：zhengzhong
 * @Date 2016-01-26
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Entity
public class Account extends BaseEntity{

	private static final long serialVersionUID = -4631511217640934133L;

	private String loginName;//登录名
	
	private String password;//密码

	private int accountType;//用户类型
	
	private int accountFrom;//IOS ANDROID
	
	private int failureTimes;//登录失败次数
	
	private String referee;//推荐人账户id
	
	private String inviteCode;//个人邀请码
	
	private int status;//用户状态
	
	private String lastLogin;//最后登录时间
	
	private String creator;//创建人
	
	private String modifier;//修改人
	
	private int emailVerified;//邮箱
	
	public int getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(int emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getReferee() {
		return referee;
	}

	public void setReferee(String referee) {
		this.referee = referee;
	}

	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public int getAccountFrom() {
		return accountFrom;
	}

	public void setAccountFrom(int accountFrom) {
		this.accountFrom = accountFrom;
	}

	public int getFailureTimes() {
		return failureTimes;
	}

	public void setFailureTimes(int failureTimes) {
		this.failureTimes = failureTimes;
	}

	@Override
	public String toString() {
		return "Account [loginName=" + loginName + ", password=" + password
				+ ", accountType="
				+ accountType + ", accountFrom=" + accountFrom
				+ ", failureTimes=" + failureTimes + ", referee=" + referee
				+ ", inviteCode=" + inviteCode + ", status=" + status
				+ ", lastLogin=" + lastLogin + ", creator=" + creator
				+ ", modifier=" + modifier + "]";
	}

}
