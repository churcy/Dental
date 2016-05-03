package com.tinytree.dao;

import java.util.List;
import java.util.Map;

import com.kungfu.dental.entity.UserSysSetting;

public interface UserSysSettingDao extends BaseDao<UserSysSetting>{

	List<Map<String, Object>> getSysSetting(Map<String, Object> map);

}
