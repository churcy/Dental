package com.tinytree.service.impl;

import com.tinytree.bean.PagerEx;
import com.tinytree.bean.PagerParam;
import com.tinytree.bean.PagerParam.ConditionType;
import com.tinytree.bean.UriBean;
import com.tinytree.dao.UserDao;
import com.tinytree.dao.UserSysSettingDao;
import com.tinytree.entity.GroupInfo;
import com.tinytree.entity.User;
import com.tinytree.entity.UserSysSetting;
import com.tinytree.service.FriendManageService;
import com.tinytree.service.UserService;
import com.tinytree.util.LoadProperty2Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description:用户信息相关服务实现
 * @ClassName: UserServiceImpl
 * @Author：zhengzhong
 * @Date 2016-1-13
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {
	
	public static final UriBean filePathBean = (UriBean)LoadProperty2Bean.getInstance().getBean(LoadProperty2Bean.URI);
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserSysSettingDao sysSettingDao;
	
	@Autowired
	private FriendManageService friendManageService;
	
	@Autowired
	private GroupInfoServiceImpl groupInfoServiceImpl;
	
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Resource
	public void setBaseDao(UserDao userDao) {
		super.setBaseDao(userDao);
	}

	@Override
	public Map<String, Object> getByUserId(String userId) {
		Map<String, Object> result = userDao.getByUserId(userId);
		return result;
	}

	@Override
	public User getByAccountId(String accountId) {
		User user = userDao.getByAccountId(accountId);
		return user;
	}

	@Override
	@Transactional
	public void setUserInfo(User user) {
		// TODO Auto-generated method stub
		//user
		userDao.insert(user);
		//super.save(user);
	}

	@Override
	public void modifyUserInfo(User user) {
		// TODO Auto-generated method stub
		userDao.updateByPrimaryKey(user);
	}
	
	@Override
	public List<String> getFilePath() {
		List<String>  paths = new ArrayList<>();
		String absolutePath = filePathBean.getAbsoluteUri();
		String relativePath = filePathBean.getRelativeUri();
		paths.add(absolutePath);
		paths.add(relativePath);
		return paths;
	}

	@Override
	public int authMobile(String userPhone) throws Exception {
		PagerEx pagerex = new PagerEx();
		List<PagerParam> pagerParams = new ArrayList<PagerParam>();
		PagerParam pagerParam = new PagerParam();
		pagerParam.setParam(ConditionType.isNotNull, "mobile", userPhone);
		PagerParam pagerParam2 = new PagerParam();
		pagerParam2.setParam(ConditionType.equal, "mobile", userPhone);
		pagerParams.add(pagerParam);
		pagerParams.add(pagerParam2);
		pagerex.setParams(pagerParams);
		List<User> list= super.findByPager(pagerex).getList();
		int regFlag;
		do{
			if(list != null){
				regFlag = 0;
				break;
			}
			regFlag = 1;
		}while(false);
		return regFlag;
	}

	@Override
	public User findRegUserByMobile(String mobile) throws Exception {
		PagerEx pagerex = new PagerEx();
		List<PagerParam> pagerParams = new ArrayList<PagerParam>();
		PagerParam pagerParam = new PagerParam();
		pagerParam.setParam(ConditionType.equal, "mobile", mobile);
		pagerParams.add(pagerParam);
		pagerex.setParams(pagerParams);
		List<User> list= super.findByPager(pagerex).getList();
		User user = null;
		if(list!=null){
			user = list.get(0);
		}
		return user;
	}

	@Override
	public User findUserByDoctorId(String doctorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int modifySysSetting(UserSysSetting userSysSetting) {
		sysSettingDao.updateByPrimaryKey(userSysSetting);
		return 0;
	}

	@Override
	public int saveSysSetting(UserSysSetting userSysSetting) {
		sysSettingDao.insert(userSysSetting);
		return 0;
	}

	@Override
	public UserSysSetting getSysSettingByCondition(String userId) {
		Map<String, Object> map  = new HashMap<String,Object>();
		map.put("userId", userId);
		List<Map<String, Object>> result = sysSettingDao.getSysSetting(map);
		String key = (String) result.get(0).get("id");
		UserSysSetting userSysSetting = sysSettingDao.selectByPrimaryKey(key);
		return userSysSetting;
	}

	@Override
	public List<Map<String, Object>> getSysSetting(String userId) {
		Map<String, Object> map =  new HashMap<String, Object>();
		map.put("userId", userId);
		List<Map<String, Object>> result = sysSettingDao.getSysSetting(map);
		return result;
	}

	@Override
	public List<Map<String, String>> getByConditions(String userId,
			String name, String phone) {
		Map<String, String> map = new HashMap<>();
		map.put("userId", userId);
		map.put("name", name);
    	map.put("phone", phone);
    	List<Map<String, String>> result = userDao.getByConditions(map);
		return result;
	}

	@Override
	@Transactional
	public int saveAndCreateDefaultGroup(User user, String defaultGroup) {
		userDao.insert(user);
		GroupInfo groupInfo = new GroupInfo();
		groupInfo.setName(defaultGroup);
		groupInfo.setUserId(user.getId());
		groupInfo.setType(1);
		groupInfoServiceImpl.save(groupInfo);;
		return 0;
	}

}
