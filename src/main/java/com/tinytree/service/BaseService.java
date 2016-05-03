package com.tinytree.service;

import java.io.Serializable;
import java.util.List;

import com.tinytree.bean.Pager;
import com.tinytree.bean.PagerEx;



public interface BaseService<T,PK extends Serializable> {
	/**
	 * 根据ID获取实体对象.
	 * 
	 * @param id
	 *            记录ID
	 * @return 实体对象
	 */
	public T get(PK id);

	/**
	 * 获取所有实体对象集合.
	 * 
	 * @return 实体对象集合
	 */
	public List<T> getAll();
	
	/**
	 * 获取所有实体对象总数.
	 * 
	 * @return 实体对象总数
	 */
	public int getTotalCount();

	/**
	 * 保存实体对象.
	 * 
	 * @param entity
	 *            对象
	 * @return ID
	 */
	public void save(T entity);
	
	/**
	 * 批量保存
	 * @param entitys
	 */
	public void save(List<T> entitys);

	/**
	 * 更新实体对象.
	 * 
	 * @param entity
	 *            对象
	 */
	public void update(T entity);
	
	/**
	 * 批量更新
	 * @param entitys
	 */
	public void update(List<T> entitys);

	/**
	 * 删除实体对象.
	 * 
	 * @param entity
	 *            对象
	 * @return
	 */
	public void delete(T entity);

	/**
	 * 根据ID删除实体对象.
	 * 
	 * @param id
	 *            记录ID
	 */
	public void delete(PK id);

	/**
	 * 根据ID数组删除实体对象.
	 * 
	 * @param ids
	 *            ID数组
	 */
	public void delete(List<PK> ids);
	
	
	/**
	 * 根据Page对象进行查询(提供分页、查找、排序功能).
	 * 
	 * @param page
	 *            Page对象
	 * @return Page对象
	 */
	public Pager findByPager(Pager pager) throws Exception;
	
	/**
	 * 根据Pageex对象进行查询(提供分页、查找、排序功能).
	 * 
	 * @param page
	 *            Page对象
	 * @return Page对象
	 */
	public PagerEx findByPager(PagerEx pagerex);
	
}
