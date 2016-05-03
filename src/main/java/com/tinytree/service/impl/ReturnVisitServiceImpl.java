package com.tinytree.service.impl;

import com.github.pagehelper.PageHelper;
import com.tinytree.dao.ReturnVisitDao;
import com.tinytree.entity.ReturnVisit;
import com.tinytree.service.ReturnVisitService;
import com.tinytree.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description:回访记录服务实现类
 * @ClassName: ReturnVisitServiceImpl
 * @Author：tangyang
 * @Date 2016-1-23
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Service("returnVisitService")
public class ReturnVisitServiceImpl extends BaseServiceImpl<ReturnVisit, String> implements ReturnVisitService {
	@Autowired
	private ReturnVisitDao returnVisitDao;
	
	@Autowired
	public void setBaseDao(ReturnVisitDao returnVisitDao) {
		super.setBaseDao(returnVisitDao);
	}

	@Override
	public List getRebackList(String visitorId, int startPage, int pageSize,
			Date startTime, Date endTime,String visitStatus) {
		//PageHelper.startPage(startPage,pageSize);
		return returnVisitDao.getRebackList(visitorId, DateUtils.format2String(startTime, "yyyy-MM-dd"), DateUtils.format2String(endTime, "yyyy-MM-dd"),visitStatus);
	}
	
	@Override
	public List getRebackListByDate(String visitorId, int iStartPage,
			int iPageSize, Date startTime, Date endTime, String visitStatus) {
		return returnVisitDao.getRebackListByDate(visitorId, DateUtils.format2String(startTime, "yyyy-MM-dd"), DateUtils.format2String(endTime, "yyyy-MM-dd"),visitStatus);
	}
	
	@Override
	public List getRebackDetails(String userId, int startPage, int pageSize,
			Date startTime, Date endTime) {
		PageHelper.startPage(startPage,pageSize);
		return returnVisitDao.getRebackDetails(userId, DateUtils.format2String(startTime, "yyyy-MM-dd"), DateUtils.format2String(endTime, "yyyy-MM-dd"));
	}

	@Override
	public Map<String, Object> getRebackDetail(String rebackId) {
		Map<String, Object> result = returnVisitDao.getRebackDetail(rebackId);
		return result;
	}

}
