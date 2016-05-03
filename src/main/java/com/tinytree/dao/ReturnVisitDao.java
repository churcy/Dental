package com.tinytree.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kungfu.dental.entity.ReturnVisit;

/**
 * @Description:回访列表数据访问接口
 * @ClassName: ReturnVisitDao
 * @Author：tangyang
 * @Date 2016-1-23
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface ReturnVisitDao extends BaseDao<ReturnVisit>{

	/**
	 * 查询回访列表
	 * @param visitorId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List getRebackList(@Param("visitorId")String visitorId,
			@Param("startDate")String startDate,
			@Param("endDate")String endDate,@Param("visitStatus")String visitStatus);
	
	/**
	 * 回访详情列表
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List getRebackDetails(@Param("userId")String userId,@Param("startDate")String startDate,@Param("endDate")String endDate);

	/**
	 * 回访记录详情
	 * @param rebackId
	 * @return
	 */
	public Map<String, Object> getRebackDetail(String rebackId);
	
	/**
	 * 根据日期查询回访
	 * @param visitorId
	 * @param format2String
	 * @param format2String2
	 * @param visitStatus
	 * @return
	 */
	public List getRebackListByDate(@Param("visitorId")String visitorId,
			@Param("startDate")String startDate,
			@Param("endDate")String endDate,@Param("visitStatus")String visitStatus);
}
