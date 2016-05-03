package com.tinytree.entity;

import java.util.Date;

import javax.persistence.Entity;

import org.apache.ibatis.type.Alias;
/**
 * @Description:图片版本基本信息
 * @ClassName: ImageVersion
 * @Author：zhengzhong
 * @Date 2016-01-26
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Entity
@Alias(value="image_version")
public class ImageVersion extends BaseEntity{

	private static final long serialVersionUID = 898371738827632215L;
	
	private int appType;//app类型 0:ios 1:android
	
	private int appName;//app名称 0:医生端 1:客户端
	
	private String version;//版本号
	
	private int usedBy;//使用者 0:开机画面 1:首页轮播
	
	private String description;//版本描述
	
	private Date publishTime;//发布时间
	
	private String publisher;//发布人
	
	private String modifyBy;//修改人
	
	private boolean status;//状态 0:未使用 1:已使用

	public int getAppType() {
		return appType;
	}

	public void setAppType(int appType) {
		this.appType = appType;
	}

	public int getAppName() {
		return appName;
	}

	public void setAppName(int appName) {
		this.appName = appName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getUsedBy() {
		return usedBy;
	}

	public void setUsedBy(int usedBy) {
		this.usedBy = usedBy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ImageVersion [appType=" + appType + ", appName=" + appName
				+ ", version=" + version + ", usedBy=" + usedBy
				+ ", description=" + description + ", publishTime="
				+ publishTime + ", publisher=" + publisher + ", modifyBy="
				+ modifyBy + ", status=" + status + "]";
	}
}
