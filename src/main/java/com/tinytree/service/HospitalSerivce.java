package com.tinytree.service;

import java.util.List;
import java.util.Map;

import com.tinytree.entity.Hospital;

/**
 * @Description:医院信息相关服务
 * @ClassName: HospitalSerivce
 * @Author：zhengzhong
 * @Date 2016-1-19
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface HospitalSerivce extends BaseService<Hospital, String>{
	
	/**
	 * 条件分页查询
	 * @param startPage
	 * @param pageSize
	 * @return 医院列表
	 * @throws Exception
	 */
	public List<Map<String,Object>> findByConditions(String startPage, String pageSize) throws Exception;

	/**
	 * 设置医院信息
	 * @param hospital
	 */
	public void setHospitalInfo(Hospital hospital);

}
