package com.tinytree.service;

import java.util.List;
import java.util.Map;

import com.tinytree.entity.MedicalTemplate;

/**
 * @Author zhengzhong
 * @Description 病历模板服务类
 * @Date 2016-03-22
 * @ClassName MedicalTemplateService
 */
public interface MedicalTemplateService extends BaseService<MedicalTemplate, String> {
	
	/**
	 * 创建病历模板
	 * @param medicalTemplate
	 * @return
	 */
	public int createMedicalTemplate(MedicalTemplate medicalTemplate);
	
	/**
	 * 查询模板
	 * @param medicalTemplateId
	 * @param nodeId
	 * @return
	 */
	public List<Map<String, Object>> getByConditions(String templateId, String nodeId);

	/**
	 * 查询模板通过id
	 * @param templateId
	 * @return
	 */
	public MedicalTemplate getById(String templateId);
}
