package com.tinytree.entity;

import java.util.Date;

import javax.persistence.Entity;

import org.apache.ibatis.type.Alias;
/**
 * @Description:预约设置实体类
 * @ClassName: ReserveSetting
 * @Author：tangyang
 * @Date 2016-1-13
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Entity
@Alias(value="reserve_setting")
public class ReserveSetting extends BaseEntity{

	private static final long serialVersionUID = -523628605228832473L;
	
	private String doctorId;//医生用户ID
	
	private Integer weekDay;//预约的天数，二进制表示一周中的某一天
	
	private Date startTime;//预约开始时间
	
	private Date endTime;//预约截止时间
	
	private Integer count;//接受预约的个数
	
	private Integer repeatStatus;//重复状态，0，不重复，1：每周重复
	
	private Integer status;//预约状态，0，正常，1：过期

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}
	
	public Integer getWeekDay() {
		return weekDay;
	}

	/*public User getDoctor() {
		return doctorId;
	}

	public void setDoctor(User doctor) {
		this.doctorId = doctor;
	}*/

	public void setWeekDay(Integer weekDay) {
		this.weekDay = weekDay;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getRepeatStatus() {
		return repeatStatus;
	}

	public void setRepeatStatus(Integer repeatStatus) {
		this.repeatStatus = repeatStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ReserveSetting [doctorId=" + doctorId + ", weekDay=" + weekDay
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", count=" + count + ", repeatStatus=" + repeatStatus
				+ ", status=" + status + "]";
	}
}
