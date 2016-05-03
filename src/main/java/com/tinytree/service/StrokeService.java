package com.tinytree.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tinytree.entity.Stroke;

/**
 * @Description:日程服务接口
 * @ClassName: StrokeService
 * @Author：tangyang
 * @Date 2016-1-25
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface StrokeService extends BaseService<Stroke, String>{

	/**
	 * 保存行程
	 * @param stroke
	 * @param date
	 */
	public void saveStroke(Stroke stroke,Date date);
	
	/**
	 * 更新行程
	 * @param stroke
	 */
	public void updateStroke(Stroke stroke);
	
	/**
	 * 查询日程
	 * @param accountId
	 * @param date
	 * @return
	 */
	public List<Stroke> getStrokes(String accountId,Date date) throws Exception;
	
	/**
	 * 根据日期查日程
	 * @param date
	 * @return
	 */
	public List<Stroke> getStrokes(Date date);
	
	/**
	 * 查询固定日程
	 * @param accountId
	 * @param date
	 * @return
	 */
	public List<Stroke> getFixedStrokes(String accountId,Date date) throws Exception;
	
	/**
	 * 查询所有日程
	 * @param accountId
	 * @return
	 */
	public List<Stroke> getStrokes(String accountId);
	
	/**
	 * 按周视图计算数量
	 * @param strokes
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Map<String,Map<String,String>> castCountByWeek(List<Stroke> strokes,Date startTime,Date endTime);
	
	/**
	 * 按月视图计算数量
	 * @param strokes
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Map<String,String> castCountByMonth(List<Stroke> strokes,Date startTime,Date endTime);
}
