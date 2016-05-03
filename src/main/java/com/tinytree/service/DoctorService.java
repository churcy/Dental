package com.tinytree.service;

import java.util.List;
import java.util.Map;

import com.tinytree.entity.DocSysSetting;
import com.tinytree.entity.Doctor;
/**
 * @Description:医生信息相关服务
 * @ClassName: DoctorService
 * @Author：zhengzhong
 * @Date 2016-1-19
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface DoctorService extends BaseService<Doctor, String>{
	
	/**
	 * 根据医生id查询医生信息
	 * @param id
	 * @return Doctor
	 */
	public Doctor getById(String id);
	
	/**
	 * 根据账户id查询医生信息
	 * @param accountId
	 * @return
	 */
	public Doctor getByAccountId(String accountId);

	/**
	 * 根据查询条件分页查询
	 * @param hospitalId
	 * @param type
	 * @param filter
	 * @param startPage
	 * @param pageSize
	 * @return 数据列表
	 * @throws Exception
	 */
	public List<Map<String, Object>> findByConditions(String hospitalId, String type, String startPage, String pageSize) throws Exception;
/*
	*//**
	 * 
	 * @param hospitalId
	 * @param type
	 * @param startPage
	 * @param pageSize
	 * @return
	 *//*
	public List<Map<String, String>> findByCondition(String hospitalId, String type, String startPage, String pageSize);*/
   /**
    * 判断是否是医生
    * @param userId
 	* @return
 	*/
	public List<Map<String, Object>> getByUserId(String userId);
	
	/**
	 * 获取系统设置信息
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getSysSetting(String userId);
	/**
	 * 修改系统设置信息
	 * @param userId
	 * @return
	 */
	public int modifySysSetting(DocSysSetting docSysSetting);
	/**
	 * 系统设置信息
	 * @param userId
	 * @return
	 */
	public int saveSysSetting(DocSysSetting docSysSetting);
	
	/**
	 * 系统设置信息
	 * @param userId
	 * @return
	 */
	public DocSysSetting getSysSettingByCondition(String userId);
}
