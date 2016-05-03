package com.tinytree.service.impl;

import com.tinytree.dao.MedicalTemplateDao;
import com.tinytree.entity.MedicalTemplate;
import com.tinytree.service.MedicalTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Author zhengzhong
 * @Date 2016-03-22
 * @Description 病历模板实现类
 * @ClassName MedicalTemplateServiceImpl
 *
 */
@Service
public class MedicalTemplateServiceImpl extends BaseServiceImpl<MedicalTemplate, String> implements MedicalTemplateService {
	
	@Autowired
	private MedicalTemplateDao medicalTemplateDao;
	
	@Autowired
	public void setBaseDao(MedicalTemplateDao medicalTemplateDao){
		super.setBaseDao(medicalTemplateDao);
	}
	@Override
	public int createMedicalTemplate(MedicalTemplate medicalTemplate) {
		medicalTemplateDao.insert(medicalTemplate);
		return 0;
	}
	@Override
	public List<Map<String,Object>> getByConditions(String templateId,
			String nodeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("templateId", templateId);
		map.put("nodeId", nodeId);
		List<Map<String, Object>> result = medicalTemplateDao.getByConditions(map);
		return result;
	}
	@Override
	public MedicalTemplate getById(String templateId) {
		MedicalTemplate medicalTemplate = medicalTemplateDao.selectByPrimaryKey(templateId);
		return medicalTemplate;
	}

}
