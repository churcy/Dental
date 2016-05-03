package com.tinytree.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinytree.dao.AppVersionDao;
import com.tinytree.entity.AppVersion;
import com.kungfu.dental.service.AppVersionService;
/**
 * @Description:app版本相关服务操作接口实现
 * @ClassName: AppVersionServiceImpl
 * @Author：zhengzhong
 * @Date 2016-1-8
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Service("appVersionService")
public class AppVersionServiceImpl extends BaseServiceImpl<AppVersion, String> implements AppVersionService{

	@Autowired
	private AppVersionDao appVersionDao;
	@Override
	public AppVersion getLatestAppVerison(int appType,int appName) {
		
		return appVersionDao.getLatestAppVersion(appType,appName);
	}
	@Override
	public Date getMaxDate(int appType,int appName) {
		
		return appVersionDao.getMaxDate(appType,appName);
	}

}
