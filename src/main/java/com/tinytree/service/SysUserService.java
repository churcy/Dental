package com.tinytree.service;

import com.tinytree.entity.SysUser;
/**
 * @Description:系统用户相关服务
 * @ClassName: SysUserService
 * @Author：zhengzhong
 * @Date 2016-1-12
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface SysUserService extends BaseService<SysUser, String>{
	
	/**
	 * 根据用户名和密码查询用户
	 * @param userName
	 * @param password
	 * @return 系统用户实体信息
	 */
	public SysUser findSysUserByNameAndPassword(String username,String password);
	
}
