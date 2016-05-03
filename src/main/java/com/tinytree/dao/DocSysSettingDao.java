package com.tinytree.dao;

import java.util.List;
import java.util.Map;

import com.kungfu.dental.entity.DocSysSetting;

public interface DocSysSettingDao extends BaseDao<DocSysSetting>{
	
	/**
	 * 获取系统设置信息
	 * @param userId
	 * @return
	 */
	public List<Map<String,Object>>getSysSetting(Map<String, Object> map);
}
