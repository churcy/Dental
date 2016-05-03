package com.tinytree.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * @Description:	DAO基类
 * @ClassName: BaseDao
 * @Author：tangyang
 * @Date 2016-1-20
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface BaseDao<T>{
	/*****************CRUD操作********************/
	public T selectByPrimaryKey(Object key);
	public Integer updateByPrimaryKey(T t);
	public Integer deleteByPrimaryKey(Object key);
	public Integer insert(T t);
	
	public Integer deleteByEntity(T entity);
	//public Integer deleteByMap()
	public List<T> selectBySql(@Param(value = "sql")String sql);
	public List<T> selectAll();
	public Integer count();
	public Integer updateBySql(@Param(value = "sql")String sql);
	public Integer deleteBySql(@Param(value = "sql")String sql);
	public Integer insertBySql(@Param(value = "sql")String sql);
	
	public Integer selectByMapCount(Map<?, ?>  map);
	public List<T> selectByMap(Map<?, ?>  map);
}
