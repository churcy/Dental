package com.tinytree.bean;

/**
 * @Description：消息提醒bean
 * @ClassName: RemindBean
 * @Author：tangyang
 * @Date：2015-02-01
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功能
 *
 */
public class RemindBean {
	
	public static final int DOCTOR_RESERVE_REMIND = 0;//医生预约提醒
	
	public static final int PATIENT_RESERVE_REMIND = 1;//患者预约提醒
	
	public static final int DOCTOR_STROKE_REMIND = 2;//医生行程提醒
	
	public static final int REMIND_TYPE_SMS = 0x1;
	
	public static final int REMIND_TYPE_APP = 0x2;
	
	public static final int REMIND_TYPE_ALL = 0xFF;
	
	private String accountId;
	
	private String mobile;
	
	private Object[] params;
	
	private Integer type;
	
	private Integer remindType;

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getRemindType() {
		return remindType;
	}

	public void setRemindType(Integer remindType) {
		this.remindType = remindType;
	}
	
}
