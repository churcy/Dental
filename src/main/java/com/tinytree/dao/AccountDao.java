package com.tinytree.dao;

import com.tinytree.entity.Account;
import org.springframework.stereotype.Repository;

/**
 * @Description:用户账户信息相关数据库操作
 * @ClassName: AccountDao
 * @Author：zhengzhong
 * @Date 2016-1-13
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Repository
public interface AccountDao extends BaseDao<Account>{
	
	/**
	 * 根据手机号查询
	 * @param uesrName
	 * @param uesrPhone
	 * @return Account
	 */
	//@Select("SELECT * FROM account WHERE login_name = #{userName}")
	public Account getByUserName(String userName);
	
}
