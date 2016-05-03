package com.tinytree.dao;

import com.tinytree.entity.UserSysSetting;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserSysSettingDao extends BaseDao<UserSysSetting>{

	List<Map<String, Object>> getSysSetting(Map<String, Object> map);

}
