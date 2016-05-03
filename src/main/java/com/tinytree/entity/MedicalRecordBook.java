package com.tinytree.entity;

import java.util.Date;

import javax.persistence.Entity;

import org.apache.ibatis.type.Alias;

/**
 * @Description:注册用户病历本
 * @ClassName: MedicalRecordBook
 * @Author：tangyang
 * @Date 2016-1-20
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Entity
@Alias(value="medical_record_book")
public class MedicalRecordBook extends BaseEntity{

	private static final long serialVersionUID = 6866745686002976814L;
	
	private User user;//患者ID
	
	private String doctorId;//医生ID
	
	private Date lastVisitDate;//最近就诊时间
	
	private String allergy;//过敏史

	/*public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}*/
	
	public String getDoctorId() {
		return doctorId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public Date getLastVisitDate() {
		return lastVisitDate;
	}

	public void setLastVisitDate(Date lastVisitDate) {
		this.lastVisitDate = lastVisitDate;
	}

	public String getAllergy() {
		return allergy;
	}

	public void setAllergy(String allergy) {
		this.allergy = allergy;
	}

	@Override
	public String toString() {
		return "MedicalRecordBook [user=" + user + ", doctorId=" + doctorId
				+ ", lastVisitDate=" + lastVisitDate + ", allergy=" + allergy
				+ "]";
	}
	
}
