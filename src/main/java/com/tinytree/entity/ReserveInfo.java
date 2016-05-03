package com.tinytree.entity;

import java.util.Date;

import javax.persistence.Entity;

import org.apache.ibatis.type.Alias;

/**
 * @Description:预约信息实体类
 * @ClassName: ReserveInfo
 * @Author：tangyang
 * @Date 2016-1-13
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Entity
@Alias(value="reserve_info")
public class ReserveInfo extends BaseEntity{
	private static final long serialVersionUID = -8263785508912525161L;

	private String userId;//患者用户ID
	
	private String doctorId;//医生用户ID
	
	private Date startTime;//预约开始时间
	
	private Date endTime;//预约截止时间
	
	private String shortDescribe;//病症描述

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getDesc() {
		return shortDescribe;
	}

	public void setDesc(String desc) {
		this.shortDescribe = desc;
	}
	
}
