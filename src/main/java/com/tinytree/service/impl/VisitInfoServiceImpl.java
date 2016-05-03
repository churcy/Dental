package com.tinytree.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinytree.bean.PagerEx;
import com.tinytree.bean.PagerParam;
import com.tinytree.bean.PagerParam.ConditionType;
import com.tinytree.dao.VisitInfoDao;
import com.tinytree.entity.VisitInfo;
import com.kungfu.dental.service.VisitInfoService;

/**
 * @Description:就诊记录服务实现类
 * @ClassName: VisitInfoServiceImpl
 * @Author：tangyang
 * @Date 2016-1-21
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Service("visitInfoService")
public class VisitInfoServiceImpl extends BaseServiceImpl<VisitInfo, String> implements VisitInfoService{
	@Autowired
	private VisitInfoDao visitInfoDao;
	
	@Autowired
	public void setBaseDao(VisitInfoDao visitInfoDao) {
		super.setBaseDao(visitInfoDao);
	}

	@Override
	public PagerEx getVisitInfoList(String userId, int startPage,
			int pageSize, Date startDate, Date endDate) throws Exception {
		PagerEx pageEx = new PagerEx();
		pageEx.setPageNumber(startPage);
		pageEx.setPageSize(pageSize);
		List<PagerParam> params = new ArrayList<>();
		
		PagerParam pkParam = new PagerParam();
		pkParam.setParam(ConditionType.equal, "user_id", userId);
		params.add(pkParam);
		
		if(startDate != null){
			PagerParam sDateParam = new PagerParam();
			sDateParam.setParam(ConditionType.greaterThanOrEqual, "visit_date", startDate);
			params.add(sDateParam);
		}
		
		if(endDate != null){
			PagerParam eDateParam = new PagerParam();
			eDateParam.setParam(ConditionType.LessThenOrEqual, "visit_date", endDate);
			params.add(eDateParam);
		}
		
		pageEx.setParams(params);
		
		return super.findByPager(pageEx);
	}
}
