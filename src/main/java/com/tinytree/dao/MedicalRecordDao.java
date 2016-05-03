package com.tinytree.dao;

import java.util.List;
import java.util.Map;

import com.tinytree.entity.MedicalRecord;
import org.springframework.stereotype.Repository;

/**
 * @Description:病历数据处理接口
 * @ClassName: MedicalRecordDao
 * @Author：tangyang
 * @Date 2016-1-21
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Repository
public interface MedicalRecordDao extends BaseDao<MedicalRecord>{
	
	/**
	 * 根据条件查询病历记录列表
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> findMedicalRecordList(Map<String, Object> map);
}
