package com.tinytree.entity;

import java.util.Date;

import javax.persistence.Entity;

import org.apache.ibatis.type.Alias;

/**
 * @Description:就诊记录实体类
 * @ClassName: VisitInfo
 * @Author：tangyang
 * @Date 2016-1-21
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Entity
@Alias(value="visit_info")
public class VisitInfo extends BaseEntity{
	
	private static final long serialVersionUID = -8216058698116945332L;
	
	private String userId;//用户ID
	
	private Date visitDate;//就诊日期
	
	private String doctor;//就诊医生
	
	private String hospital;//医院
	
	private String visitDesc;//就诊描述
	
	private String uri;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public String getVisitDesc() {
		return visitDesc;
	}

	public void setVisitDesc(String visitDesc) {
		this.visitDesc = visitDesc;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
}
