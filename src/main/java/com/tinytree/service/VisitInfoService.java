package com.tinytree.service;

import java.util.Date;

import com.tinytree.bean.PagerEx;
import com.tinytree.entity.VisitInfo;

/**
 * @Description:就诊记录服务接口
 * @ClassName: VisitInfoService
 * @Author：tangyang
 * @Date 2016-1-21
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface VisitInfoService extends BaseService<VisitInfo, String>{

	/**
	 * 获取就诊记录列表
	 * @param userId
	 * @param startPage
	 * @param pageSize
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public PagerEx getVisitInfoList(String userId,int startPage,int pageSize,Date startDate,Date endDate) throws Exception;
}
