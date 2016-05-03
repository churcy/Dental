package com.tinytree.dao;

import org.apache.ibatis.annotations.Select;

import com.kungfu.dental.entity.SysUser;

/**
 * @Description:系统用户相关数据库操作
 * @ClassName: SysUserDao
 * @Author：zhengzhong
 * @Date 2016-1-19
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface SysUserDao extends BaseDao<SysUser>{
	
	@Select("SELECT * FROM sys_user WHERE username = #{0} AND password = #{1}")
	public SysUser findSysUserByNameAndPassword(String username,String password);
	
}
