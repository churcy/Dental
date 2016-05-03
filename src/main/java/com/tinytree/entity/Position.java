package com.tinytree.entity;

import javax.persistence.Entity;

/**
 * @Description:医生的职位信息 (数据字典)
 * @ClassName: Position
 * @Author：zhengzhong
 * @Date 2016-1-22
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Entity
public class Position extends BaseEntity{
	
	private static final long serialVersionUID = -7015182873787877502L;

	private int positionNo;//职位编号
	
	private String name;//职位名称
	
	private String descript;//职位描述
	
	private int orderId;//排序

	private String creator;//创建人
	
	private String modifier;//修改人

	public int getPositionNo() {
		return positionNo;
	}

	public void setPositionNo(int positionNo) {
		this.positionNo = positionNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		return "Position [positionNo=" + positionNo + ", name=" + name
				+ ", descript=" + descript + ", orderId=" + orderId
				+ ", creator=" + creator + ", modifier=" + modifier + "]";
	}
}
