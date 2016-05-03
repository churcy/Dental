package com.tinytree.service.impl;

import com.tinytree.bean.Pager;
import com.tinytree.dao.DepartmentDao;
import com.tinytree.entity.Department;
import com.tinytree.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:部门信息相关服务实现
 * @ClassName: DepartmentServiceImpl
 * @Author：zhengzhong
 * @Date 2016-1-19
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Service
public class DepartmentServiceImpl extends BaseServiceImpl<Department, String> implements DepartmentService {
	@Autowired
	private DepartmentDao departmentDao;
	
	public void setDepartmentDao(DepartmentDao departmentDao){
		this.departmentDao = departmentDao;
	}

	@Resource
	public void setBaseDao(DepartmentDao departmentDao){
		super.setBaseDao(departmentDao);
	}

	@Override
	public List<Department> findByConditions(String startPage, String pageSize) throws Exception {
		Pager pager = new Pager();
		pager.setPageNumber(Integer.valueOf(startPage));
		pager.setPageSize(Integer.valueOf(pageSize));
		List<Department> list = this.findByPager(pager).getList();
		return list;
	}
}
