package com.tinytree.entity;

import javax.persistence.Entity;

import org.apache.ibatis.type.Alias;


/**
 * @Author zhengzhong
 * @Description 病历节点
 * @ClassName MedicalNode
 * @Date 2016-03-21
 *
 */
@Entity
@Alias(value="medical_node")
public class MedicalNode extends BaseEntity{

	private static final long serialVersionUID = 3368732137275085117L;
	private String parentNodeId;//父节点Id
	private String nodeName;//节点名称
	private int nodeType;//节点类型
	private String templateId;//模板id
	private int nodeLevel;//节点级别	
	
	public String getParentNodeId() {
		return parentNodeId;
	}

	public void setParentNodeId(String parentNodeId) {
		this.parentNodeId = parentNodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public int getNodeType() {
		return nodeType;
	}

	public void setNodeType(int nodeType) {
		this.nodeType = nodeType;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public int getNodeLevel() {
		return nodeLevel;
	}

	public void setNodeLevle(int nodeLevel) {
		this.nodeLevel = nodeLevel;
	}

	@Override
	public String toString() {
		return "MedicalNode [ parentNodeId="
				+ parentNodeId + ", nodeName=" + nodeName + ", nodeType="
				+ nodeType + ", templateId=" + templateId + ", nodeLevel="
				+ nodeLevel + "]";
	}
	
	
	

}
