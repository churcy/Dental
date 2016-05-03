package com.tinytree.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinytree.bean.PagerEx;
import com.tinytree.bean.PagerParam;
import com.tinytree.bean.PagerParam.ConditionType;
import com.tinytree.dao.MedicalRecordBookTmpDao;
import com.tinytree.entity.MedicalRecordBookTmp;
import com.kungfu.dental.service.MedicalRecordBookTmpService;

/**
 * @Description:注册用户病历本服务实现类
 * @ClassName: MedicalRecordBookServiceImpl
 * @Author：tangyang
 * @Date 2016-1-21
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Service("medicalRecordBookTmpService")
public class MedicalRecordBookTmpServiceImpl extends BaseServiceImpl<MedicalRecordBookTmp, String> implements MedicalRecordBookTmpService{
	
	@Autowired
	private MedicalRecordBookTmpDao medicalRecordBookTmpDao;
	
	@Autowired
	public void setBaseDao(MedicalRecordBookTmpDao medicalRecordBookTmpDao) {
		super.setBaseDao(medicalRecordBookTmpDao);
	}

	@Override
	public MedicalRecordBookTmp getBook(String phone, String doctorId)
			throws Exception {
		if(StringUtils.isAnyBlank(phone,doctorId)){
			return null;
		}
		
		PagerEx page = new PagerEx();
		List<PagerParam> params = new ArrayList<>();
		
		PagerParam phoneParam = new PagerParam();
		phoneParam.setParam(ConditionType.equal, "phone", phone);
		params.add(phoneParam);
		
		PagerParam doctorIdParam = new PagerParam();
		doctorIdParam.setParam(ConditionType.equal, "doctor_id", doctorId);
		params.add(doctorIdParam);
		
		page.setParams(params);
		
		page = super.findByPager(page);
		
		List resultList = page.getList();
		if(resultList == null || resultList.isEmpty()){
			return null;
		}
		
		return (MedicalRecordBookTmp) resultList.get(0);
	}
}
