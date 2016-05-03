package com.tinytree.entity;

import javax.persistence.Entity;
/**
 * @Description:部门详细信息
 * @ClassName: DepartmentInfo
 * @Author：zhengzhong
 * @Date 2016-01-26
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Entity
public class DepartmentInfo extends BaseEntity{

	private static final long serialVersionUID = -6039094873078929229L;
	//1-1
	//private Department department;//部门
	private String departmentId;//部门
	
	private String address;//地址
	
	private String telephone;//联系电话
	
	private String moblie;//手机号
	
	private String businessLicenseSrc;//营业执照认证文件地址
	
	private String healthPermitsSrc;//卫生许可证认证文件地址
	
	private int status;//科室状态；0：可用；1：禁用；2：删除
	
	private String desc;//科室描述
	
	private String grade;//科室等级和资质
	
	private String photos;//科室照片
	
	private int orderId;//排序值
	
	private int verifiedStatus;//认证状态；默认0；0：未认证；1：已认证;2:认证未通过

	private String creator;//创建人
	
	private String modifier;//修改人
	
	private String auditor;//审核人
	
	private String remark;//审核说明

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMoblie() {
		return moblie;
	}

	public void setMoblie(String moblie) {
		this.moblie = moblie;
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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
		return "DepartmentInfo [departmentId=" + departmentId + ", address="
				+ address + ", telephone=" + telephone + ", moblie=" + moblie
				+ ", businessLicenseSrc=" + businessLicenseSrc
				+ ", healthPermitsSrc=" + healthPermitsSrc + ", status="
				+ status + ", desc=" + desc + ", grade=" + grade + ", photos="
				+ photos + ", orderId=" + orderId + ", verifiedStatus="
				+ verifiedStatus + ", creator=" + creator + ", modifier="
				+ modifier + ", auditor=" + auditor + ", remark=" + remark
				+ "]";
	}
	
}
