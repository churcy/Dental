package com.tinytree.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tinytree.entity.ReserveInfo;
/**
 * @Description:预约信息服务接口
 * @ClassName: ReserveInfoService
 * @Author：tangyang
 * @Date 2016-1-13
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface ReserveInfoService extends BaseService<ReserveInfo, String>{
	
	/**
	 * 查询医生已预约的个数
	 * @param doctorId
	 * @param startTime
	 * @return
	 */
	public Integer getReserveInfoCount(String doctorId,Date startTime);
	
	/**
	 * 查询医生的预约列表
	 * @param doctorId
	 * @param startPage
	 * @param pageSize
	 * @param startDate
	 * @param endDate
	 * @param datePatten
	 * @return
	 */
	public List<Map<String, String>> findReserveList(String doctorId,String startPage,String pageSize,String startDate,String endDate);
	/**
	 * 查询患者的预约列表
	 * @param accountId
	 * @param startPage
	 * @param pageSize
	 * @param startDate
	 * @param endDate
	 * @param datePatten
	 * @return
	 */
	public List<Map<String, String>> findReserveListUser(String accountId,
			String startPage, String pageSize, String startDate, String endDate);
	/**
	 * 查询预约详情
	 * @param reserveId
	 * @return
	 */
	public Map<String, Object> findReserveDetail(String reserveId);
	
	/**
	 * 查询预约信息
	 * @param doctorId
	 * @param startTime
	 * @return
	 */
	public List<Map<String, String>> findReserveInfos(String doctorId,Date startTime);
	
	/**
	 * 查询预约信息
	 * @param doctorId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<ReserveInfo> findReserveList(String doctorId,Date startTime,Date endTime);
	
	/**
	 * 转换成日程周视图
	 * @param reserveInfos
	 * @return
	 */
	public Map<String, Map<String,String>> castCountByWeek(List<ReserveInfo> reserveInfos);
	
	/**
	 * 转换成月视图
	 * @param reserveInfos
	 * @return
	 */
	public Map<String,String> castCountByMonth(List<ReserveInfo> reserveInfos);
	
	/**
	 * 查询医生当天有几个预约
	 * @param date
	 * @return
	 */
	public List<Map<String,Object>> findReserveForDoctorByDate(Date date);
	
	/**
	 * 查询当天预约的列表
	 * @param date
	 * @return
	 */
	public List<Map<String, String>> findReserveListByDate(String doctorId,String startPage,String pageSize,String startDate);

	public List<ReserveInfo> findReserveListByDate2(Date today);

}
