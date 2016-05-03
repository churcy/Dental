package com.tinytree.entity;

import javax.persistence.Entity;
/**
 * @Description:部门信息
 * @ClassName: Department
 * @Author：zhengzhong
 * @Date 2016-01-26
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Entity 
public class Department extends BaseEntity{

	private static final long serialVersionUID = -6068902418036281808L;

	private int departmentNo;//科室编号
	
	private String descript;//科室描述
	
	private String name;//科室名称
	
	private int orderId;//排序值
	
	private String creator;//创建人
	
	private String modifier;//修改人

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDepartmentNo() {
		return departmentNo;
	}

	public void setDepartmentNo(int departmentNo) {
		this.departmentNo = departmentNo;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
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

	@Override
	public String toString() {
		return "Department [departmentNo=" + departmentNo + ", descript="
				+ descript + ", name=" + name + ", orderId=" + orderId
				+ ", creator=" + creator + ", modifier=" + modifier + "]";
	}
}
