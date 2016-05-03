package com.tinytree.entity;

import java.util.Date;

import javax.persistence.Entity;

import org.apache.ibatis.type.Alias;

/**
 * @Description:日程安排
 * @ClassName: Stroke
 * @Author：tangyang
 * @Date 2016-1-25
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Entity
@Alias(value="stroke")
public class Stroke extends BaseEntity{

	private static final long serialVersionUID = -6926843767040367058L;
	
	private String accountId;//医生账户ID
	
	private String subject;//行程主题
	
	private String place;//行程地点
	
	private Date time;//行程时间
	
	private Integer hour;//行程时间段
	
	private Date timeByday;//每日时间
	
	private Integer timeByweek;//每周时间，二进制表示，多个时间做与运算。1000000表示周一，0000001表示周日
	
	private Integer timeBymonth;//每月时间
	
	private Integer timeByyear;//每年时间
	
	private Integer repeatType;//重复方式，0不重复1每天2每周3每月4每年
	
	private String remark;//行程备注

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public Date getTimeByday() {
		return timeByday;
	}

	public void setTimeByday(Date timeByday) {
		this.timeByday = timeByday;
	}

	public Integer getTimeByweek() {
		return timeByweek;
	}

	public void setTimeByweek(int timeByweek) {
		this.timeByweek = timeByweek;
	}

	public Integer getTimeBymonth() {
		return timeBymonth;
	}

	public void setTimeBymonth(int timeBymonth) {
		this.timeBymonth = timeBymonth;
	}

	public Integer getTimeByyear() {
		return timeByyear;
	}

	public void setTimeByyear(int timeByyear) {
		this.timeByyear = timeByyear;
	}

	public Integer getRepeat() {
		return repeatType;
	}

	public void setRepeat(int repeat) {
		this.repeatType = repeat;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
