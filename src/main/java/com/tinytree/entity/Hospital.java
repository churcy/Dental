package com.tinytree.entity;

import javax.persistence.Entity;
/**
 * @Description:医院信息
 * @ClassName: Hospital
 * @Author：zhengzhong
 * @Date 2016-01-26
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Entity
public class Hospital extends BaseEntity{

	private static final long serialVersionUID = -4717075769970625192L;
	
	private String name;//医院名称
	
	private String address;//医院地址
	
	private String mobile;//手机号码
	
	private String telephone;//联系电话
	
	private String businessLicenseSrc;//营业执照认证文件地址
	
	private String healthPermitsSrc;//卫生许可证认证文件地址

	private int status;//医院状态
	
	private String descript;//医院简介
	
	private String grade;//医院等级或者资质
	
	private String photos;//医院图片
	
	private int orderId;//排序值
	
	private int verifiedStatus;//认证状态；默认0；0：未认证；1：已认证;2:认证未通过

	private String creator;//创建人
	
	private String modifier;//修改人
	
	private String auditor;//审核人
	
	private String remark;//审核说明

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getBusinessLicenseSrc() {
		return businessLicenseSrc;
	}

	public void setBusinessLicenseSrc(String businessLicenseSrc) {
		this.businessLicenseSrc = businessLicenseSrc;
	}

	public String getHealthPermitsSrc() {
		return healthPermitsSrc;
	}

	public void setHealthPermitsSrc(String healthPermitsSrc) {
		this.healthPermitsSrc = healthPermitsSrc;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getVerifiedStatus() {
		return verifiedStatus;
	}

	public void setVerifiedStatus(int verifiedStatus) {
		this.verifiedStatus = verifiedStatus;
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
		return "Hospital [name=" + name + ", address=" + address + ", mobile="
				+ mobile + ", telephone=" + telephone + ", businessLicenseSrc="
				+ businessLicenseSrc + ", healthPermitsSrc=" + healthPermitsSrc
				+ ", status=" + status + ", descript=" + descript + ", grade="
				+ grade + ", photos=" + photos + ", orderId=" + orderId
				+ ", verifiedStatus=" + verifiedStatus + ", creator=" + creator
				+ ", modifier=" + modifier + ", auditor=" + auditor
				+ ", remark=" + remark + "]";
	}
}
