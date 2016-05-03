package com.tinytree.entity;

import javax.persistence.Entity;
/**
 * @Description:医生职业信息
 * @ClassName: Doctor
 * @Author：zhengzhong
 * @Date 2016-1-21
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Entity
public class Doctor extends BaseEntity{

	private static final long serialVersionUID = 4634906545682222600L;
	//1-1
	private Account account;//账户 信息
	//n-1
	private Hospital hospital;//医院信息
	//n-1
	private Department department;//部门信息
	
	private int type;//医生类别 0:牙医等 
	
	private Position position;//职位 0：主任医师；1：副主任医师；2：主治医师；3：助理医师
	
	private String brief;//医生简介
	
	private int verifiedType;//验证类别 
	
	private int verifiedStatus;//认证状态默认0；0：未认证；1：已认证;2:认证未通过
	
	private String duties;//职务信息
	
	private String docCertificateSrc;//认证文件地址verified_src.
	
	private String workPermitSrc;//工作证地址
	
	private String assistVerifiedAccountId;//协助认证人
	
	private String specialty;//擅长领域
	
	private int orderId;//排序值
	
	private String auditor;//审核人
	
	private String remark;//审核说明

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public int getVerifiedType() {
		return verifiedType;
	}

	public void setVerifiedType(int verifiedType) {
		this.verifiedType = verifiedType;
	}

	public int getVerifiedStatus() {
		return verifiedStatus;
	}

	public void setVerifiedStatus(int verifiedStatus) {
		this.verifiedStatus = verifiedStatus;
	}

	public String getDuties() {
		return duties;
	}

	public void setDuties(String duties) {
		this.duties = duties;
	}

	public String getDocCertificateSrc() {
		return docCertificateSrc;
	}

	public void setDocCertificateSrc(String docCertificateSrc) {
		this.docCertificateSrc = docCertificateSrc;
	}

	public String getWorkPermitSrc() {
		return workPermitSrc;
	}

	public void setWorkPermitSrc(String workPermitSrc) {
		this.workPermitSrc = workPermitSrc;
	}

	public String getAssistVerifiedAccountId() {
		return assistVerifiedAccountId;
	}

	public void setAssistVerifiedAccountId(String assistVerifiedAccountId) {
		this.assistVerifiedAccountId = assistVerifiedAccountId;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "Doctor [account=" + account + ", hospital="
				+ hospital + ", department=" + department + ", type=" + type
				+ ", position=" + position + ", brief=" + brief
				+ ", verifiedType=" + verifiedType + ", verifiedStatus="
				+ verifiedStatus + ", duties=" + duties
				+ ", docCertificateSrc=" + docCertificateSrc
				+ ", workPermitSrc=" + workPermitSrc
				+ ", assistVerifiedAccountId=" + assistVerifiedAccountId
				+ ", specialty=" + specialty + ", orderId=" + orderId
				+ ", auditor=" + auditor + ", remark=" + remark + "]";
	}
}
