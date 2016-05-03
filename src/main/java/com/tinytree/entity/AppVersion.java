package com.tinytree.entity;

import javax.persistence.Entity;

import org.apache.ibatis.type.Alias;
/**
 * @Description:app版本信息
 * @ClassName: AppVersion
 * @Author：zhengzhong
 * @Date 2016-01-26
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Entity
@Alias(value="app_version")
public class AppVersion extends BaseEntity{

	private static final long serialVersionUID = 5001044074069371218L;
	
	private int appType;//app类型 0 ios 1android
	
	private int appName;//app名称 0医生端 1患者端 等
	
	private String versionNo;//版本号
	
	private String description;//版本描述
	
	private String publishTime;//发布时间
	
	private String publisher;//发布人
	
	private String modifier;//修改人
	
	private String url;//跟新地址
	
	private String appstoreUrl;//第三方更新地址

	private int type;//是否强制新 0 否 1 是
	
	private int status;//是否正在使用0 否 1 是
	
	private String creator;//创建人
	
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

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

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAppstoreUrl() {
		return appstoreUrl;
	}

	public void setAppstoreUrl(String appstoreUrl) {
		this.appstoreUrl = appstoreUrl;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type; 	
	}

	@Override
	public String toString() {
		return "AppVersion [appType=" + appType + ", appName=" + appName
				+ ", versionNo=" + versionNo + ", description=" + description
				+ ", publishTime=" + publishTime + ", publisher=" + publisher
				+ ", modifier=" + modifier + ", url=" + url + ", appstoreUrl="
				+ appstoreUrl + ", type=" + type + "]";
	}
	
}
