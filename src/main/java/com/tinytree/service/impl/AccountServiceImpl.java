package com.tinytree.service.impl;

import com.tinytree.dao.AccountDao;
import com.tinytree.entity.Account;
import com.tinytree.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
/**
 * @Description:用户账户信息服务实现类
 * @ClassName: AccountServiceImpl
 * @Author：zhengzhong
 * @Date 2016-1-13
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Service
public class AccountServiceImpl extends BaseServiceImpl<Account, String> implements AccountService {
	
	@Autowired
	private AccountDao accountDao;
	
	public void setAccountDao(AccountDao accountDao){
		this.accountDao = accountDao;
	}

	@Resource
	public void setBaseDao(AccountDao accountDao){
		super.setBaseDao(accountDao);
	}

	@Override
	public Account getByUserName(String uesrName) {
		// TODO Auto-generated method stub
		Account account = accountDao.getByUserName(uesrName);
		return account;
	}

	@Override
	public int authLoginName(String uesrName){
		Account account1 = accountDao.getByUserName(uesrName);
		int regFlag ;
		do{
			if(account1!=null){
				regFlag = 0;
				break;
			}
			regFlag = 1;
		}while(false);
		return regFlag;
	}
}
