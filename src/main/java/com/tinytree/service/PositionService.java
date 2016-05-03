package com.tinytree.service;

import java.util.List;

import com.tinytree.entity.Position;

/**
 * @Description:医生职位信息接口
 * @ClassName: PositionService
 * @Author：zhengzhong
 * @Date 2016-1-22
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface PositionService extends BaseService<Position, String> {

	/**
	 * 查询医生职位信息
	 * @param startPage
	 * @param pageSize
	 * @return
	 * @throws Exception 
	 */
	public List<Position> findByConditions(String startPage, String pageSize) throws Exception; 
}
