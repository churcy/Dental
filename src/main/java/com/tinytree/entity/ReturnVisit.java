package com.tinytree.entity;

import java.util.Date;

import javax.persistence.Entity;

import org.apache.ibatis.type.Alias;

/**
 * @Description:回访记录表
 * @ClassName: ReturnVisit
 * @Author：tangyang
 * @Date 2016-1-23
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Entity
@Alias(value="return_visit")
public class ReturnVisit extends BaseEntity{

	private static final long serialVersionUID = -7168712774928057992L;
	
	private String userId;//用户ID
	
	private String medicalRecordId;//病历ID
	
	private String visitorId;//回访人accountId
	
	private Integer status;//回访状态，0，未回访，1.已回访
	
	private Date visitTime;//回访时间

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMedicalRecordId() {
		return medicalRecordId;
	}

	public void setMedicalRecordId(String medicalRecordId) {
		this.medicalRecordId = medicalRecordId;
	}

	public String getVisitorId() {
		return visitorId;
	}

	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

}
