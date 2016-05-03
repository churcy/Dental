package com.tinytree.dao;

import com.tinytree.entity.DocSysSetting;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface DocSysSettingDao extends BaseDao<DocSysSetting>{
	
	/**
	 * 获取系统设置信息
	 * @param userId
	 * @return
	 */
	public List<Map<String,Object>>getSysSetting(Map<String, Object> map);
}
