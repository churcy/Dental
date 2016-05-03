package com.tinytree.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tinytree.dao.ReturnVisitRecordDao;
import com.tinytree.entity.ReturnVisit;
import com.tinytree.entity.ReturnVisitRecord;
import com.kungfu.dental.service.ReturnVisitRecordService;
import com.kungfu.dental.service.ReturnVisitService;

/**
 * @Description:回访记录详情服务实现类
 * @ClassName: ReturnVisitRecordServiceImpl
 * @Author：tangyang
 * @Date 2016-1-23
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Service("returnVisitRecordService")
public class ReturnVisitRecordServiceImpl extends BaseServiceImpl<ReturnVisitRecord, String> implements ReturnVisitRecordService{
	@Autowired
	private ReturnVisitRecordDao returnVisitRecordDao;
	
	@Autowired
	private ReturnVisitService returnVisitService;
	
	@Autowired
	public void setBaseDao(ReturnVisitRecordDao returnVisitRecordDao) {
		super.setBaseDao(returnVisitRecordDao);
	}

	@Override
	@Transactional
	public void createReturnVisitRecord(ReturnVisitRecord record) {
		if(record ==null){
			return;
		}
		
		super.save(record);
		ReturnVisit reback = returnVisitService.get(record.getReturnVisitId());
		if(reback != null){
			reback.setStatus(1);
			returnVisitService.update(reback);
		}
	}
}
