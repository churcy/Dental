package com.tinytree.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinytree.bean.Pager;
import com.tinytree.bean.PagerEx;
import com.tinytree.bean.PagerParam;
import com.tinytree.bean.PagerParam.ConditionType;
import com.tinytree.dao.MedicalRecordBookDao;
import com.tinytree.entity.MedicalRecordBook;
import com.kungfu.dental.service.MedicalRecordBookService;

/**
 * @Description:注册用户病历本服务实现类
 * @ClassName: MedicalRecordBookServiceImpl
 * @Author：tangyang
 * @Date 2016-1-21
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Service("medicalRecordBookService")
public class MedicalRecordBookServiceImpl extends BaseServiceImpl<MedicalRecordBook, String> implements MedicalRecordBookService{
	
	@Autowired
	private MedicalRecordBookDao medicalRecordBookDao;
	
	@Autowired
	public void setBaseDao(MedicalRecordBookDao medicalRecordBookDao) {
		super.setBaseDao(medicalRecordBookDao);
	}

	@Override
	public MedicalRecordBook getBookById(String userId,String doctorId) throws Exception {
		if(StringUtils.isAnyBlank(userId,doctorId)){
			return null;
		}
		
		PagerEx pager = new PagerEx();
		List<PagerParam> params = new ArrayList<>();
		PagerParam userIdParam = new PagerParam();
		userIdParam.setParam(ConditionType.equal, "user_id", userId);
		params.add(userIdParam);
		PagerParam doctorIdParam = new PagerParam();
		doctorIdParam.setParam(ConditionType.equal, "doctor_id", doctorId);
		params.add(doctorIdParam);
		pager.setParams(params);
		
		pager = super.findByPager(pager);
		
		List list = pager.getList();
		if(list == null || list.isEmpty()){
			return null;
		}
		return (MedicalRecordBook) list.get(0);
	}

	@Override
	public PagerEx getBookByDoctor(String doctorId,int startPage,int pageSize,Date startDate,Date endDate)
			throws Exception {
		PagerEx pageEx = new PagerEx();
		pageEx.setPageNumber(startPage);
		pageEx.setPageSize(pageSize);
		List<PagerParam> params = new ArrayList<>();
		
		PagerParam pkParam = new PagerParam();
		pkParam.setParam(ConditionType.equal, "doctor_id", doctorId);
		params.add(pkParam);
		
		if(startDate != null){
			PagerParam sDateParam = new PagerParam();
			sDateParam.setParam(ConditionType.LessThenOrEqual, "last_visit_date", startDate);
			params.add(sDateParam);
		}
		
		if(endDate != null){
			PagerParam eDateParam = new PagerParam();
			eDateParam.setParam(ConditionType.greaterThanOrEqual, "last_visit_date", endDate);
			params.add(eDateParam);
		}
		
		pageEx.setParams(params);
		return findByPager(pageEx);
	}

	@Override
	public List getBookListByUser(String userId) {
		
		return medicalRecordBookDao.getBookListByUser(userId);
	}

	@Override
	public List getBookList(String doctorAccountId) {
		// TODO Auto-generated method stub
		return medicalRecordBookDao.getBookList(doctorAccountId);
	}
	
}
