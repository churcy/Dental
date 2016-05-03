package com.tinytree.entity;

import java.util.Date;

import javax.persistence.Entity;

import org.apache.ibatis.type.Alias;

/**
 * @Description:回访记录详情表
 * @ClassName: ReturnVisitRecord
 * @Author：tangyang
 * @Date 2016-1-23
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Entity
@Alias(value="return_visit_record")
public class ReturnVisitRecord extends BaseEntity{

	private static final long serialVersionUID = 8353514022763687124L;
	
	private String returnVisitId;//回访表ID
	
	private String content;//回访内容
	
	private String result;//回访结果
	
	private Date visitTime;//回访时间

	public String getReturnVisitId() {
		return returnVisitId;
	}

	public void setReturnVisitId(String returnVisitId) {
		this.returnVisitId = returnVisitId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}
	
}
