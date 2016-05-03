package com.tinytree.entity;

import javax.persistence.Entity;

import org.apache.ibatis.type.Alias;

/**
 * @Author zhengzhong
 * @Description 病历模板实体类
 * @Date 2016-03-22
 * @ClassName MedicalTemplate
 *
 */
@Entity
@Alias(value="medical_template")
public class MedicalTemplate extends BaseEntity {

	private static final long serialVersionUID = 7325315372236433687L;
	private String doctorId;//医师Id
	
	private String nodeId;//节点id
	
	private String complaint;//主诉
	
	private String historyPresentIllness;//现病史
	
	private String historyPastIllness;//既往病史
	
	private String inspect;//检查
	
	private String assistantInspect;//辅助检查
	
	private String diagnosis;//诊断
	
	private String treatmentPlan;//治疗计划
	
	private String treatment;//治疗

	private String orders;//医嘱

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getComplaint() {
		return complaint;
	}

	public void setComplaint(String complaint) {
		this.complaint = complaint;
	}

	public String getHistoryPresentIllness() {
		return historyPresentIllness;
	}

	public void setHistoryPresentIllness(String historyPresentIllness) {
		this.historyPresentIllness = historyPresentIllness;
	}

	public String getHistoryPastIllness() {
		return historyPastIllness;
	}

	public void setHistoryPastIllness(String historyPastIllness) {
		this.historyPastIllness = historyPastIllness;
	}

	public String getInspect() {
		return inspect;
	}

	public void setInspect(String inspect) {
		this.inspect = inspect;
	}

	public String getAssistantInspect() {
		return assistantInspect;
	}

	public void setAssistantInspect(String assistantInspect) {
		this.assistantInspect = assistantInspect;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getTreatmentPlan() {
		return treatmentPlan;
	}

	public void setTreatmentPlan(String treatmentPlan) {
		this.treatmentPlan = treatmentPlan;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "MedicalTemplate [doctorId=" + doctorId + ", nodeId=" + nodeId
				+ ", complaint=" + complaint + ", historyPresentIllness="
				+ historyPresentIllness + ", historyPastIllness="
				+ historyPastIllness + ", inspect=" + inspect
				+ ", assistantInspect=" + assistantInspect + ", diagnosis="
				+ diagnosis + ", treatmentPlan=" + treatmentPlan
				+ ", treatment=" + treatment + ", orders=" + orders + "]";
	}
}
