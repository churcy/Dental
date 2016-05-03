package com.tinytree.service;

import java.util.List;

import com.tinytree.entity.Department;
/**
 * @Description:部门信息服务
 * @ClassName: DepartmentService
 * @Author：zhengzhong
 * @Date 2016-1-21
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface DepartmentService extends BaseService<Department, String>{

	/**
	 * 根据条件分页查询部门列表
	 * @param startPage
	 * @param pageSize
	 * @return 本部门列表
	 * @throws Exception 
	 */
	List<Department> findByConditions(String startPage, String pageSize) throws Exception;

}
