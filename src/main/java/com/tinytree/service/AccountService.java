package com.tinytree.service;

import com.tinytree.entity.Account;
import com.tinytree.entity.User;

/**
 * @Description:用户账户信息服务
 * @ClassName: AccountService
 * @Author：zhengzhong
 * @Date 2016-1-13
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface AccountService extends BaseService<Account, String>{

	/**
	 * 根据用户名查询用户
	 * @param uesrName
	 * @return boolean true or false
	 */
	public Account getByUserName(String uesrName);
	
	/**
	 * 根据用户名 密码注册 
	 * @param account
	 * @return 0 用户名已存在 1 用户名不存在 
	 */
	public int authLoginName(String uesrName);
	
}
