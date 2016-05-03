package com.tinytree.entity;

import java.util.Date;

import javax.persistence.Entity;

import org.apache.ibatis.type.Alias;

/**
 * @Description:非注册用户病历本
 * @ClassName: MedicalRecordBookTmp
 * @Author：tangyang
 * @Date 2016-1-21
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Entity
@Alias(value="medical_record_book_tmp")
public class MedicalRecordBookTmp extends BaseEntity{

	private static final long serialVersionUID = -671791106347533987L;
	
	private String doctorId;//医生ID
	
	private Date lastVisitDate;//最近就诊时间
	
	private String allergy;//过敏史

	private String realName;//患者姓名
	
	private Integer gender;//性别，0：男；1：女
	
	private Integer marital;//婚否，0：未婚；1：已婚
	
	private Date birthday;//生日
	
	private String phone;//手机号

	public String getDoctorId() {
		return doctorId;
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getMarital() {
		return marital;
	}

	public void setMarital(Integer marital) {
		this.marital = marital;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
