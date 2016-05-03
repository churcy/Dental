package com.tinytree.dao;

import com.tinytree.entity.Doctor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description:医生相关数据库操作
 * @ClassName: DoctorDao
 * @Author：zhengzhong
 * @Date 2016-1-19
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Repository
public interface DoctorDao  extends BaseDao<Doctor>{
	
	/**
	 * 根据id查询数据
	 * @param id
	 * @return Doctor
	 */
	public Doctor getById(String id);

	/**
	 * 根据accountId查询医生信息
	 * @param accountId
	 * @return Doctor
	 */
	public Doctor getByAccountId(String accountId);

	/**
	 * 条件查询预约医师列表
	 * @param hospitalId
	 * @param type
	 * @return
	 */
	public List<Map<String, Object>> findByConditions(Map<String, Object> map);

	/**
	 * 通过userId获取医生信息
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getByUserId(String userId);
}
