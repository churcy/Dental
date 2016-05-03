package com.tinytree.service.impl;

import com.tinytree.dao.SysUserDao;
import com.tinytree.entity.SysUser;
import com.tinytree.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
/**
 * @Description:系统用户相关服务实现
 * @ClassName: SysUserServiceImpl
 * @Author：zhengzhong
 * @Date 2016-1-12
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, String> implements SysUserService {

	@Autowired
	private SysUserDao sysUserDao;
	
	public void setSysUserDao(SysUserDao sysUserDao){
		 this.sysUserDao = sysUserDao;
	}
	@Resource
	public void setBaseDao(SysUserDao sysUserDao) {
		super.setBaseDao(sysUserDao);
	}
	
	@Override
	public SysUser findSysUserByNameAndPassword(String username, String password) {
		// TODO Auto-generated method stub
		SysUser sysUser = sysUserDao.findSysUserByNameAndPassword(username, password);
		return sysUser;
	}

}
