package com.tinytree.entity;

import java.util.Date;
/**
 * @Description:用户意见反馈实体
 * @ClassName: FeedBack
 * @Author：zhengzhong
 * @Date 2016-1-31
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public class FeedBack extends BaseEntity{

	private static final long serialVersionUID = 7582622955925954994L;
	
	private String fbContent;//反馈内容
	
	private Date fbTime;//反馈时间
	
	private String userId;//用户id

	public String getFbContent() {
		return fbContent;
	}

	public void setFbContent(String fbContent) {
		this.fbContent = fbContent;
	}

	public Date getFbTime() {
		return fbTime;
	}

	public void setFbTime(Date fbTime) {
		this.fbTime = fbTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "FeedBack [fbContent=" + fbContent + ", fbTime=" + fbTime
				+ ", userId=" + userId + "]";
	}
	
}
