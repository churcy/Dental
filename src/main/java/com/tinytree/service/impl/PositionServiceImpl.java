package com.tinytree.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinytree.bean.Pager;
import com.tinytree.dao.DepartmentDao;
import com.tinytree.dao.PositionDao;
import com.tinytree.entity.Department;
import com.tinytree.entity.Position;
import com.kungfu.dental.service.PositionService;
/**
 * @Description:医生职位信息接口实现
 * @ClassName: PositionServiceImpl
 * @Author：zhengzhong
 * @Date 2016-1-22
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Service()
public class PositionServiceImpl extends BaseServiceImpl<Position, String> implements PositionService{
	@Autowired
	private PositionDao positionDao;
	
	public void setDepartmentDao(PositionDao positionDao){
		this.positionDao = positionDao;
	}

	@Resource
	public void setBaseDao(PositionDao positionDao){
		super.setBaseDao(positionDao);
	}

	@Override
	public List<Position> findByConditions(String startPage, String pageSize) throws Exception {
		Pager pager = new Pager();
		pager.setPageNumber(Integer.valueOf(startPage));
		pager.setPageSize(Integer.valueOf(pageSize));
		List<Position> list = this.findByPager(pager).getList();
		return list;
	}
	
}
