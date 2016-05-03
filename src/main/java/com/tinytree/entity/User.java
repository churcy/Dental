package com.tinytree.entity;

import java.util.Date;

import javax.persistence.Entity;
/**
 * @Description:用户个人信息实体类
 * @ClassName: User
 * @Author：zhengzhong
 * @Date 2016-01-26
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Entity
public class User extends BaseEntity{

	private static final long serialVersionUID = -5699659386769695139L;
	
	private Account account;//账户id
	
	private String realName;//真实姓名
	
	private int gender;//性别 0:男1:女
	
	private int marital;//婚姻 0:未婚 1:已婚
	
	private String avatar;//用户头像
	
	private Date birthday;//生日 
	
	private int qq;//qq号码
	
	private String address;//地址
	
	private String wechat;//微信
	
	private String mobile;//手机号
	
	private String email;//邮箱

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getMarital() {
		return marital;
	}

	public void setMarital(int marital) {
		this.marital = marital;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getQq() {
		return qq;
	}

	public void setQq(int qq) {
		this.qq = qq;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Override
	public String toString() {
		return "User [account=" + account + ", realName=" + realName
				+ ", gender=" + gender + ", marital=" + marital + ", avatar="
				+ avatar + ", birthday=" + birthday + ", qq=" + qq
				+ ", address=" + address + ", wechat=" + wechat + ", mobile="
				+ mobile + ", email=" + email + "]";
	}
}
