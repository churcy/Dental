package com.tinytree.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.kungfu.dental.entity.AppVersion;
/**
 * @Description:app版本数据库操作接口
 * @ClassName: AppVersionDao
 * @Author：zhengzhong
 * @Date 2016-1-8
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Repository
public interface AppVersionDao extends BaseDao<AppVersion>{
	
	/**
	 * @param appType
	 * @param appName
	 * @return 返回按条件的最近时间
	 */
	@Select("SELECT  max(create_date) AS create_date FROM app_version WHERE app_type = #{0} AND app_name= #{1}  AND status = 1 )")
	public Date getMaxDate(int appType,int appName);
	
	/**
	 * @param appType
	 * @param appName
	 * @return 返回按条件查询最近时间的完成数据
	 */
	public AppVersion getLatestAppVersion(int appType,int appName);
}
