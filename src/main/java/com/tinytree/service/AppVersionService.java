package com.tinytree.service;

import com.tinytree.entity.AppVersion;

import java.util.Date;

/**
 * @Description:app版本相关服务操作接口
 * @ClassName: AppVersionService
 * @Author：zhengzhong
 * @Date 2016-1-8
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface AppVersionService extends BaseService<AppVersion, String> {
	
	/**
	 * @param appType app类型
	 * @param appName app名称 客户端 医生端
	 * @return 离当前时间最近的版本数据
	 */
	public AppVersion getLatestAppVerison(int appType,int appName);

	/**
	 * @param appType app类型
	 * @param appName app名称 客户端 医生端
	 * @return 离当前时间
	 */
	public Date getMaxDate(int appType,int appName);
}
