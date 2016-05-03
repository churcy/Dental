package com.tinytree.dao;

import com.tinytree.entity.MedicalTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;


/**
 * @Description:病历模板数据库操作
 * @ClassName: MedicalTemplateDao
 * @Author：zhengzhong
 * @Date 2016-3-22
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Repository
public interface MedicalTemplateDao extends BaseDao<MedicalTemplate>{

	/**
	 * 查询模板
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getByConditions(Map<String, Object> map);
	
}
