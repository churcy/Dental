package com.tinytree.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.kungfu.dental.entity.Stroke;

/**
 * @Description:日程安排数据库操作
 * @ClassName: UserDao
 * @Author：tangyang
 * @Date 2016-1-25
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface StrokeDao extends BaseDao<Stroke>{

	/**
	 * 查询固定的日程
	 * @param accountId
	 * @param date
	 * @return
	 */
	public List<Stroke> getFixedStrokes(Map<?,?> map);
	
	/**
	 * 
	 * @param accountId
	 * @return
	 */
	public List<Stroke> getStrokes(String accountId);
}
