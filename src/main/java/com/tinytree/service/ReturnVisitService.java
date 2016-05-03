package com.tinytree.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tinytree.entity.ReturnVisit;

/**
 * @Description:回访记录服务接口
 * @ClassName: ReturnVisitService
 * @Author：tangyang
 * @Date 2016-1-23
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface ReturnVisitService extends BaseService<ReturnVisit, String>{

	/**
	 * 查询回访列表
	 * @param visitorId
	 * @param startPage
	 * @param pageSize
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List getRebackList(String visitorId,int startPage,int pageSize,Date startTime,Date endTime,String visitStatus);
	/**
	 * 根据日期查询 回访
	 * @param visitorId
	 * @param iStartPage
	 * @param iPageSize
	 * @param dStartDate
	 * @param dEndDate
	 * @param visitStatus
	 * @return
	 */
	public List getRebackListByDate(String visitorId, int startPage, int pageSize, Date startTime, Date endTime, String visitStatus);
	/**
	 * 回访详情列表
	 * @param userId
	 * @param startPage
	 * @param pageSize
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List getRebackDetails(String userId,int startPage,int pageSize,Date startTime,Date endTime);

	/**
	 * 回访记录详情
	 * @param rebackId
	 * @return 详情数据
	 */
	public Map<String,Object> getRebackDetail(String rebackId);

	
}
