package com.tinytree.entity;

import javax.persistence.Entity;

import org.apache.ibatis.type.Alias;

/**
 * @Description:病历
 * @ClassName: MedicalRecord
 * @Author：tangyang
 * @Date 2016-1-20
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Entity
@Alias(value="medical_record")
public class MedicalRecord extends BaseEntity{

	private static final long serialVersionUID = 495712148259563247L;

	private String bookId;//病历本ID
	
	private String complaint;//主诉
	
	private String historyPresentIllness;//现病史
	
	private String historyPastIllness;//既往病史
	
	private String inspect;//检查
	
	private String inspectNo;//检查牙齿编号
	
	private String assistantInspect;//辅助检查
	
	private String assistantInspectNo;//辅助检查牙齿编号
	
	private String diagnosis;//诊断
	
	private String diagnosisNo;//诊断牙齿编号
	
	private String treatmentPlan;//治疗计划
	
	private String treatmentPlanNo;//治疗计划牙齿编号
	
	private String treatment;//治疗
	
	private String treatmentNo;//治疗牙齿编号
	
	private String orders;//医嘱
	
	private String uri;//病历照片URI

	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
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

	public String getInspectNo() {
		return inspectNo;
	}

	public void setInspectNo(String inspectNo) {
		this.inspectNo = inspectNo;
	}

	public String getAssistantInspect() {
		return assistantInspect;
	}

	public void setAssistantInspect(String assistantInspect) {
		this.assistantInspect = assistantInspect;
	}

	public String getAssistantInspectNo() {
		return assistantInspectNo;
	}

	public void setAssistantInspectNo(String assistantInspectNo) {
		this.assistantInspectNo = assistantInspectNo;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getDiagnosisNo() {
		return diagnosisNo;
	}

	public void setDiagnosisNo(String diagnosisNo) {
		this.diagnosisNo = diagnosisNo;
	}

	public String getTreatmentPlan() {
		return treatmentPlan;
	}

	public void setTreatmentPlan(String treatmentPlan) {
		this.treatmentPlan = treatmentPlan;
	}

	public String getTreatmentPlanNo() {
		return treatmentPlanNo;
	}

	public void setTreatmentPlanNo(String treatmentPlanNo) {
		this.treatmentPlanNo = treatmentPlanNo;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	public String getTreatmentNo() {
		return treatmentNo;
	}

	public void setTreatmentNo(String treatmentNo) {
		this.treatmentNo = treatmentNo;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
}
